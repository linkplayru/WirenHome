package ru.linkplay.wirenhome.device;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

public class MrLight implements Device {

    final private Client client;
    final private String id;
    final private String cmdTopic;
    private boolean isOn;

    public MrLight(Client client, String id, String fbTopic, String cmdTopic) {
        this.client = client;
        this.id = id;
        this.cmdTopic = cmdTopic;
        client.addIntegerListener(fbTopic, this::onChange);
    }

    private void onChange(int status) {
        isOn = status == 1;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void on() {
        Log.w("MrLight " + id + " on");
        client.publish(cmdTopic, String.valueOf(1));
    }

    @Override
    public void off() {
        Log.w("MrLight " + id + " off");
        client.publish(cmdTopic, String.valueOf(0));
    }

}
