package ru.linkplay.wirenhome.event;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;

import java.util.Timer;
import java.util.TimerTask;

public class DoubleButton implements Event {

    final private String id;
    final private Runnable singleFunc;
    final private Runnable doubleFunc;
    private int mode; //0 - normal, 1 - double, 2 - off
    private int count;

    public DoubleButton(Client client, String id, String fbTopic, String modeTopic, Runnable singleFunc, Runnable doubleFunc) {
        this.id = id;
        this.singleFunc = singleFunc;
        this.doubleFunc = doubleFunc;
        client.addIntegerListener(fbTopic, this::onChange);
        client.addIntegerListener(modeTopic, state -> mode = state);
    }

    public void onChange(int state) {
        if (state == 1) {
            if (mode == 0) {
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
            } else if (mode == 1) {
                if (count == 0) {
                    Timer waitTimer = new Timer();
                    waitTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (count == 2) {
                                Log.w("button " + id + " pressed double");
                                singleFunc.run();
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
