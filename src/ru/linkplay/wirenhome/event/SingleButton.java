package ru.linkplay.wirenhome.event;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

public class SingleButton implements Event {

    final private String id;
    final private Runnable func;

    public SingleButton(Client client, String id, String fbTopic, Runnable func) {
        this.id = id;
        this.func = func;
        client.addIntegerListener(fbTopic, this::onChange);
    }

    public void onChange(int state) {
        if (state == 1) {
            Log.w("button " + id + " pressed");
            func.run();
        }
    }

}
