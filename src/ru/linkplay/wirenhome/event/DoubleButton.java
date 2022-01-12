package ru.linkplay.wirenhome.event;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

import java.util.Timer;
import java.util.TimerTask;

public class DoubleButton implements Event {

    final private String id;
    final private Runnable singleFunc;
    final private Runnable doubleFunc;
    private int count;

    public DoubleButton(Client client, String id, String fbTopic, Runnable singleFunc, Runnable doubleFunc) {
        this.id = id;
        this.singleFunc = singleFunc;
        this.doubleFunc = doubleFunc;
        client.addIntegerListener(fbTopic, this::onChange);
    }

    public void onChange(int state) {
        if (state == 1) {
            if (count == 0) {
                Timer waitTimer = new Timer();
                waitTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (count == 1) {
                            Log.w("button " + id + " pressed single");
                            singleFunc.run();
                        } else if (count == 2) {
                            Log.w("button " + id + " pressed double");
                            doubleFunc.run();
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
