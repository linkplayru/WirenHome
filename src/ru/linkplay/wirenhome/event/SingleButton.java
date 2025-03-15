package ru.linkplay.wirenhome.event;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SingleButton implements Event {

    final private String id;
    final private Runnable func;
    private int mode; //0 - normal, 1 - double, 2 - off
    private int count; //for double mode

    public SingleButton(Client client, String id, String fbTopic, String modeTopic, Runnable func) {
        this.id = id;
        this.func = func;
        client.addIntegerListener(fbTopic, this::onChange);
        client.addIntegerListener(modeTopic, state -> mode = state);
    }

    public void onChange(int state) {
        if (state == 1) {
            if (mode == 0) {
                Log.w("button " + id + " pressed");
                func.run();
            } else if (mode == 1) {
                if (count == 0) {
                    Timer waitTimer = new Timer();
                    waitTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (count == 2) {
                                Log.w("button " + id + " pressed double");
                                func.run();
                            }
                            count = 0;
                            waitTimer.cancel();
                        }
                    }, 500);
                }
                if (count < 2) {
                    count++;
                }
            }
        }
    }

}
