package ru.pet.taskMQTT.domain.sensors.mosquitto;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class SensorsSubscriber {
    MqttConnectOptions connectOptions = new MqttConnectOptions();
    SensorsSubscriber(){
        connectOptions.setCleanSession(false);
    }
}
