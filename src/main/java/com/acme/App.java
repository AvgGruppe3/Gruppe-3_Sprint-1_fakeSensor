package com.acme;


import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws MqttException {
        MqttAsyncClient myClient = connectToMqttBroker();

        try {
            while (true) {
                publishTemperature(myClient);
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            //do nothing
        }

    }

    private static MqttAsyncClient connectToMqttBroker() throws MqttException {
        MqttAsyncClient myClient = new MqttAsyncClient("tcp://192.168.0.171:1883", UUID.randomUUID().toString());
        IMqttToken token = myClient.connect();
        token.waitForCompletion();
        return myClient;
    }

    private static void publishTemperature(MqttAsyncClient myClient) throws MqttException {
        double min = 5;
        double max = 55;
        double temperature = ThreadLocalRandom.current().nextDouble(min, max + 1);

        MqttMessage message = new MqttMessage(String.valueOf(roundAvoid(temperature, 2)).getBytes());
        message.setQos(0);

        String topic = "hska/avg/temperature2";
        myClient.publish(topic, message);
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}
