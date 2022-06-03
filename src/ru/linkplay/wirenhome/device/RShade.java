package ru.linkplay.wirenhome.device;

import ru.linkplay.wirenhome.Client;
import ru.linkplay.wirenhome.Log;
import ru.linkplay.wirenhome.TaskQueue;

public class RShade extends Thread implements Device {

    private Client client;
    final private String id;
    private String cmdRelayTopic;
    private String cmdDirTopic;
    final private int moveTime;
    private int safeTime;
    final private Runnable waitTask = () -> {
        try {
            Thread.sleep(safeTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };
    final private Runnable dirOpenTask = () -> {
        wasOpened = true;
        client.publish(cmdDirTopic, String.valueOf(0));
    };
    final private Runnable dirCloseTask = () -> {
        wasOpened = false;
        client.publish(cmdDirTopic, String.valueOf(1));
    };
    final private Runnable relayOnTask = () -> {
        isMoving = true;
        client.publish(cmdRelayTopic, String.valueOf(1));
    };
    final private Runnable relayOffTask = () -> {
        isMoving = false;
        client.publish(cmdRelayTopic, String.valueOf(0));
    };
    private boolean wasOpened;
    private boolean isMoving;
    final private TaskQueue queue = new TaskQueue();

    public RShade(Client client, String id, String cmdRelayTopic, String cmdDirTopic, int moveTime, int safeTime) {
        this.client = client;
        this.id = id;
        this.cmdRelayTopic = cmdRelayTopic;
        this.cmdDirTopic = cmdDirTopic;
        this.moveTime = moveTime;
        this.safeTime = safeTime;
        start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Runnable task;
            try {
                task = queue.get();
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void open() {
        Log.w("RShade " + id + " open");
        queue.clear();
        queue.put(relayOffTask);
        queue.put(waitTask);
        queue.put(dirOpenTask);
        queue.put(waitTask);
        queue.put(relayOnTask);
        for (int i = 0; i < moveTime / safeTime; i++) {
            queue.put(waitTask);
        }
        queue.put(relayOffTask);
    }

    public void close() {
        Log.w("RShade " + id + " close");
        queue.clear();
        queue.put(relayOffTask);
        queue.put(waitTask);
        queue.put(dirCloseTask);
        queue.put(waitTask);
        queue.put(relayOnTask);
        for (int i = 0; i < moveTime / safeTime; i++) {
            queue.put(waitTask);
        }
        queue.put(relayOffTask);
    }

    public void pause() {
        Log.w("RShade " + id + " pause");
        queue.clear();
        queue.put(relayOffTask);
    }

    public void one() {
        if (isMoving) {
            pause();
        } else {
            if (wasOpened) {
                close();
            } else {
                open();
            }
        }
    }

    @Override
    public boolean isOn() {
        return wasOpened;
    }

    @Override
    public void on() {
        open();
    }

    @Override
    public void off() {
        close();
    }

}
