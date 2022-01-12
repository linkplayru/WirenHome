package ru.linkplay.wirenhome.device;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

public class RgbwLight implements Device {

    final private Client client;
    final private String id;
    final private String cmdTopic;
    private boolean isOn;

    public RgbwLight(Client client, String id, String fbTopic, String cmdTopic) {
        this.client = client;
        this.id = id;
        this.cmdTopic = cmdTopic;
        client.addIntegerListener(fbTopic, this::onChange);
    }

    private void onChange(int status) {
        isOn = status > 0;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void on() {
        Log.w("RgbwLight " + id + " on");
        client.publish(cmdTopic, String.valueOf(255));
    }

    @Override
    public void off() {
        Log.w("RgbwLight " + id + " off");
        client.publish(cmdTopic, String.valueOf(0));
    }
}
