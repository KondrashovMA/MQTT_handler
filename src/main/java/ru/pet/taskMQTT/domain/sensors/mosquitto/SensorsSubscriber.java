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
import ru.pet.taskMQTT.domain.sensors.model.Sensor;
import ru.pet.taskMQTT.domain.sensors.service.SensorsService;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;

@Component
@ComponentScan("ru.pet.taskMQTT")
@Slf4j
//@AllArgsConstructor
public class SensorsSubscriber {

    @Autowired
    SensorsService sensorsService;

    @Autowired
    MqttGateway mqttGateway;

    @Value("${topic.sensors.light}")
    private String topicSensorsLight;

    @Value("${topic.sensors.temperature}")
    private String topicSensorsTemperature;

    @Value("${topic.sensors.doors}")
    private String topicSensorsDoors;

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler(){
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();

                if(topic.equals(topicSensorsLight)){
                    Sensor sensor = new Sensor(message.getHeaders().get(
                            MqttHeaders.RECEIVED_TOPIC).toString(),
                            message.getPayload().toString(),
                            new Timestamp(System.currentTimeMillis()));
                    sensorsService.save(sensor);
                    var value = parseNumber(message.getPayload().toString(), topicSensorsLight);
                    if(value != null){
                        if(value.doubleValue() > 800.0) {
                            log.info("Exceed lighting");
                            mqttGateway.sendToMqtt("light", "/signalization/device/light");
                        }
                    }
                    log.info(message.getPayload().toString());
                    return;
                }

                if(topic.equals(topicSensorsTemperature)){
                    Sensor sensor = new Sensor(message.getHeaders().get(
                            MqttHeaders.RECEIVED_TOPIC).toString(),
                            message.getPayload().toString(),
                            new Timestamp(System.currentTimeMillis()));
                    sensorsService.save(sensor);
                    var value = parseNumber(message.getPayload().toString(), topicSensorsTemperature);
                    if(value != null){
                        if(value.doubleValue() > 50.0) {
                            log.debug("Exceed temperature");
                            mqttGateway.sendToMqtt("temperature", "/signalization/device/temperature");
                        }
                    }
                    log.info(message.getPayload().toString());
                    return;
                }

                if(topic.equals(topicSensorsDoors)){
                    Sensor sensor = new Sensor(message.getHeaders().get(
                            MqttHeaders.RECEIVED_TOPIC).toString(),
                            message.getPayload().toString(),
                            new Timestamp(System.currentTimeMillis()));
                    sensorsService.save(sensor);
                    if(message.getPayload().toString().equals("open")){
                        log.debug("Door opened");
                        mqttGateway.sendToMqtt("door Open", "/signalization/device/doors");
                    }
                    log.info(message.getPayload().toString());
                    return;
                }

                log.debug(message.getPayload().toString());
            }
        };
    }

    private Number parseNumber(String value, String topc){
        try {
            return NumberFormat.getNumberInstance().parse(value);
        }catch (ParseException e){
            log.warn("Error while parsing value: {} from topic: {}", value, topc);
            return null;
        }
    }
}