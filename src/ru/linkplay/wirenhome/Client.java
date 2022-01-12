package ru.linkplay.wirenhome;

import org.eclipse.paho.client.mqttv3.*;

import java.util.*;
import java.util.function.Consumer;

public class Client implements MqttCallbackExtended {

    final private HashSet<String> subscribedSet;
    final private HashMap<String, List<Consumer<Integer>>> integerListeners;

    private MqttAsyncClient client;

    private boolean isWorking;
    private Date lastLost;

    public Client(String broker) {
        subscribedSet = new HashSet<>();
        integerListeners = new HashMap<>();
        try {
            client = new MqttAsyncClient(broker, "Shalash");
            client.setCallback(this);
        } catch (MqttException e) {
            e.printStackTrace();
            Log.w("error, end of work");
            System.exit(0);
        }
    }

    public void addIntegerListener(String topic, Consumer<Integer> func) {
        Log.w("mqtt add integer listener " + topic);
        integerListeners.computeIfAbsent(topic, k -> new ArrayList<>()).add(func);
        subscribe(topic);
    }

    private void subscribe(String topic) {
        subscribedSet.add(topic);
    }

    private void subscribeAll() {
        for (String topic : subscribedSet) {
            try {
                Log.w("mqtt client subscribe to " + topic);
                client.subscribe(topic, 2);
            } catch (MqttException e) {
                Log.w("mqtt client error subscribing to " + topic);
            }
        }
    }

    public void publish(String topic, String message) {
        Log.w("mqtt client publish to " + topic + " - " + message);
        try {
            client.publish(topic, message.getBytes(), 2, false);
        } catch (MqttException e) {
            Log.w("mqtt client error publishing to " + topic + " - " + message);
        }
    }

    public void start() {
        Log.w("mqtt client start");
        isWorking = true;
        connect();
    }

    public void stop() {
        Log.w("mqtt client stop");
        isWorking = false;
        disconnect();
    }
    public String getLastConnectionLost() {
        if (lastLost == null) {
            return "no connection lost";
        } else {
            return lastLost.toString();
        }
    }

    private void connect() {
        if (isWorking) {
            Log.w("mqtt client connection start");
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setConnectionTimeout(2);
            try {
                IMqttToken connectToken = client.connect(options);
                connectToken.waitForCompletion(2000);
            } catch (MqttException e) {
                Log.w("mqtt client connection error");
                reconnect();
            }
        }
    }

    private void reconnect() {
        Timer reconnectTimer = new Timer();
        reconnectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.w("mqtt client reconnect");
                connect();
                reconnectTimer.cancel();
            }
        }, 5000);
    }

    private void disconnect() {
        Log.w("mqtt client disconnect");
        if (client.isConnected()) {
            try {
                client.disconnect(10000);
            } catch (MqttException e) {
                Log.w("mqtt client disconnection error");
            }
        }
    }

    @Override
    public void connectComplete(boolean b, String s) {
        Log.w("mqtt connection complete " + s);
        subscribeAll();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.w("mqtt connection lost");
        lastLost = new Date();
        reconnect();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Log.w("mqtt client message arrived to " + topic + " - " + message);
        for (Consumer<Integer> func : integerListeners.get(topic)) {
            func.accept(Integer.parseInt(message.toString()));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
