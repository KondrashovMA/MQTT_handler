package ru.pet.taskMQTT.domain.sensors.mosquitto;

import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SensorsSubscriber {

//    @Autowired
//    MqttClient mqttClient = new MqttClient();



    @PostConstruct
    public void test(){
        MqttClient mqttClient;
        System.out.println("123");
    }

    public SensorsSubscriber() throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        client.setCallback(new MqttCallBack());
        client.connect();
    }
//    client.
//    MqttCallBack
//    MqttConnectOptions connectOptions = new MqttConnectOptions();
//    SensorsSubscriber() throws MqttException {
//        connectOptions.setCleanSession(false);
//    }
}
