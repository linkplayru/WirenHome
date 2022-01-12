package ru.linkplay.wirenhome.device;

import java.util.HashSet;

public class DeviceGroup implements Device {

    final private HashSet<Device> devices;

    public DeviceGroup() {
        devices = new HashSet<>();
    }

    public void add(Device device) {
        devices.add(device);
    }

    @Override
    public boolean isOn() {
        boolean someIsOn = false;
        for (Device device : devices) {
            if (device.isOn()) {
                someIsOn = true;
                break;
            }
        }
        return someIsOn;
    }

    @Override
    public void on() {
        for (Device device : devices) {
            device.on();
        }
    }

    @Override
    public void off() {
        for (Device device : devices) {
            device.off();
        }
    }

}
