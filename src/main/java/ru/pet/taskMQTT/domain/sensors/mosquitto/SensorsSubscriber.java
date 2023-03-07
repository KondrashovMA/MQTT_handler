package ru.pet.taskMQTT.domain.sensors.mosquitto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import ru.pet.taskMQTT.domain.sensors.model.DetectorDto;
import ru.pet.taskMQTT.domain.sensors.model.Sensor;
import ru.pet.taskMQTT.domain.sensors.model.SignalizationDto;
import ru.pet.taskMQTT.domain.sensors.mosquitto.util.DetectorDeserializer;
import ru.pet.taskMQTT.domain.sensors.mosquitto.util.SignalizationJsonSerializer;
import ru.pet.taskMQTT.domain.sensors.service.SensorsService;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@ComponentScan("ru.pet.taskMQTT")
@Slf4j
public class SensorsSubscriber {

    @Autowired
    SensorsService sensorsService;

    @Autowired
    MqttGateway mqttGateway;

    @Autowired
    SignalizationJsonSerializer signalizationJsonSerializer;

    @Autowired
    DetectorDeserializer detectorDeserializer;

    @Value("${topic.sensors}")
    private String topicSensors;

    @Value("${topic.signalization}")
    private String topicSignalization;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler(){
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                if(topic.equals(topicSensors)) {
                    DetectorDto detectorDto = detectorDeserializer.deserialize(message.getPayload().toString());
                    if(detectorDto == null){
                        return;
                    }

                    // go through all sensors and check the trigger conditions
                    for (Map.Entry<String, String> entry : detectorDto.getSensorsValues().entrySet()) {
                        if (entry.getKey().equals("light")) {
                            var value = parseNumber(entry.getValue());
                            if (value != null) {
                                if (value.doubleValue() > 800.0) {
                                    log.info("Exceed lighting in sensor: {}", entry.getKey());
                                    sendMqtt(detectorDto.getDetecorName(),entry.getKey(), value, "lux",
                                            sensorsService.countSensorByPathAndValue(detectorDto.getDetecorName()
                                                    + "/" + entry.getKey(), value.toString()));
                                }
                            }
                        }
                        if (entry.getKey().equals("fire")) {
                            var value = parseNumber(entry.getValue());
                            if (value != null) {
                                if (value.doubleValue() > 50) {
                                    log.info("Exceed temperature in sensor: {}", entry.getKey());
                                    sendMqtt(detectorDto.getDetecorName(), entry.getKey(), value,"degrees",
                                            sensorsService.countSensorByPathAndValue(detectorDto.getDetecorName() +
                                                    "/" + entry.getKey(), value.toString()));
                                }
                            }
                        }
                        if (entry.getKey().equals("door")) {
                            if (entry.getValue().equals("open")) {
                                log.info("Door opened in sensor: {}", entry.getKey());
                                sendMqtt(detectorDto.getDetecorName(), entry.getKey(), entry.getValue(),"open/close",
                                        sensorsService.countSensorByPathAndValue(detectorDto.getDetecorName() +
                                                "/" + entry.getKey(), entry.getValue()));
                            }
                        }

                        sensorsService.save(new Sensor(detectorDto.getDetecorName() + "/" + entry.getKey(),
//                                entry.getValue(), Timestamp.valueOf(String.valueOf(Instant.now()))));
                                entry.getValue(), dateFormat.format(new Date())));
                    }
                }
                log.debug(message.getPayload().toString());
            }
        };
    }

    private void sendMqtt(String device, String sensor, Object value, String units, int repeated){
        mqttGateway.sendToMqtt(signalizationJsonSerializer.serializeToJson(
                        new SignalizationDto(
                                device,
                                sensor,
                                value,
                                units,
                                repeated
                        )),
                topicSignalization);
    }

    private Number parseNumber(String value){
        try {
            return NumberFormat.getNumberInstance().parse(value);
        }catch (ParseException e){
            log.warn("Error while parsing value: {}", value);
            return null;
        }
    }
}