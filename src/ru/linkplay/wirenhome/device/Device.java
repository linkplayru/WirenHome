package ru.linkplay.wirenhome.device;

public interface Device {

    boolean isOn();
    void on();
    void off();
    default void toggle() {
        if (isOn()) {
            off();
        } else {
            on();
        }
    }

}
