package ru.linkplay.wirenhome;

import java.util.ArrayList;

public class TaskQueue {

    final private ArrayList<Runnable> tasks = new ArrayList<>();

    synchronized public void clear() {
        tasks.clear();
    }

    synchronized public void put(Runnable task) {
        tasks.add(task);
        notify();
    }

    synchronized public Runnable get() throws InterruptedException {
        while (tasks.isEmpty()) {
            wait();
        }
        Runnable task = tasks.get(0);
        tasks.remove(0);
        return task;
    }

}
