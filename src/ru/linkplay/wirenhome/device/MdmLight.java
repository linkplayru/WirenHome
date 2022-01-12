package ru.linkplay.wirenhome.device;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

public class MdmLight implements Device {

    final private Client client;
    final private String id;
    final private String cmdRelayTopic;
    final private String cmdChannelTopic;
    private boolean isOn;

    public MdmLight(Client client, String id, String fbTopic, String cmdRelayTopic, String cmdChannelTopic) {
        this.client = client;
        this.id = id;
        this.cmdRelayTopic = cmdRelayTopic;
        this.cmdChannelTopic = cmdChannelTopic;
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
        Log.w("MdmLight " + id + " on");
        client.publish(cmdRelayTopic, String.valueOf(1));
        client.publish(cmdChannelTopic, String.valueOf(100));
    }

    @Override
    public void off() {
        Log.w("MdmLight " + id + " off");
        client.publish(cmdRelayTopic, String.valueOf(0));
        client.publish(cmdChannelTopic, String.valueOf(0));
    }
}
