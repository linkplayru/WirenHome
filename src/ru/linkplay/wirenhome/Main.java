package ru.linkplay.wirenhome;

import ru.linkplay.wirenhome.device.DeviceGroup;
import ru.linkplay.wirenhome.device.MdmLight;
import ru.linkplay.wirenhome.device.MrLight;
import ru.linkplay.wirenhome.device.RgbwLight;
import ru.linkplay.wirenhome.event.DoubleButton;
import ru.linkplay.wirenhome.event.SingleButton;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main {

    static private Client client;
    static private long startTime;

    public static void main(String[] args) {
        boolean isWorking = true;
        startTime = System.currentTimeMillis();
        Log.w("start work");

        String broker = "test.mosquitto.org";
        if (args.length == 1) {
            broker = args[0];
        }
        client = new Client("tcp://" + broker + ":1883");

        MdmLight light_11 = new MdmLight(client, "light_11", "/devices/D3/controls/K1", "/devices/D3/controls/K1/on", "/devices/D3/controls/Channel 1/on");
        RgbwLight light_12 = new RgbwLight(client, "light_12", "/devices/D1/controls/Blue", "/devices/D1/controls/Blue/on");
        MrLight light_21 = new MrLight(client, "light_21", "/devices/R1/controls/K1", "/devices/R1/controls/K1/on");
        MrLight light_22 = new MrLight(client, "light_22", "/devices/R1/controls/K2", "/devices/R1/controls/K2/on");
        MdmLight light_31 = new MdmLight(client, "light_31", "/devices/D3/controls/K2", "/devices/D3/controls/K2/on", "/devices/D3/controls/Channel 2/on");
        MrLight light_32 = new MrLight(client, "light_32", "/devices/R1/controls/K3", "/devices/R1/controls/K3/on");
        RgbwLight light_33 = new RgbwLight(client, "light_33", "/devices/D1/controls/Red", "/devices/D1/controls/Red/on");
        MrLight light_34 = new MrLight(client, "light_34", "/devices/R1/controls/K4", "/devices/R1/controls/K4/on");
        MrLight light_41 = new MrLight(client, "light_41", "/devices/R1/controls/K5", "/devices/R1/controls/K5/on");
        MdmLight light_51 = new MdmLight(client, "light_51", "/devices/D3/controls/K3", "/devices/D3/controls/K3/on", "/devices/D3/controls/Channel 3/on");
        RgbwLight light_52 = new RgbwLight(client, "light_52", "/devices/D1/controls/Green", "/devices/D1/controls/Green/on");
        MrLight light_53 = new MrLight(client, "light_53", "/devices/R1/controls/K6", "/devices/R1/controls/K6/on");
        MrLight light_54 = new MrLight(client, "light_54", "/devices/R2/controls/K1", "/devices/R2/controls/K1/on");
        MrLight light_61 = new MrLight(client, "light_61", "/devices/R2/controls/K2", "/devices/R2/controls/K2/on");
        RgbwLight light_62 = new RgbwLight(client, "light_62", "/devices/D1/controls/White", "/devices/D1/controls/White/on");
        RgbwLight light_63 = new RgbwLight(client, "light_63", "/devices/D2/controls/Blue", "/devices/D1/controls/Blue/on");
        MrLight light_64 = new MrLight(client, "light_64", "/devices/R2/controls/K3", "/devices/R2/controls/K3/on");
        MrLight light_65 = new MrLight(client, "light_65", "/devices/R2/controls/K4", "/devices/R2/controls/K4/on");
        MrLight light_71 = new MrLight(client, "light_71", "/devices/R2/controls/K5", "/devices/R2/controls/K5/on");
        MrLight light_72 = new MrLight(client, "light_72", "/devices/R2/controls/K6", "/devices/R2/controls/K6/on");
        MrLight light_73 = new MrLight(client, "light_73", "/devices/R3/controls/K1", "/devices/R3/controls/K1/on");
        MrLight light_74 = new MrLight(client, "light_74", "/devices/R3/controls/K2", "/devices/R3/controls/K2/on");
        MrLight light_75 = new MrLight(client, "light_75", "/devices/R3/controls/K3", "/devices/R3/controls/K3/on");
        RgbwLight light_76 = new RgbwLight(client, "light_76", "/devices/D2/controls/Red", "/devices/D1/controls/Red/on");
        RgbwLight light_77 = new RgbwLight(client, "light_77", "/devices/D2/controls/Green", "/devices/D1/controls/Green/on");
        MrLight light_78 = new MrLight(client, "light_78", "/devices/R3/controls/K4", "/devices/R3/controls/K4/on");
        MrLight light_79 = new MrLight(client, "light_79", "/devices/R3/controls/K5", "/devices/R3/controls/K5/on");

        DeviceGroup deviceGroup_all = new DeviceGroup();
        deviceGroup_all.add(light_11);
        deviceGroup_all.add(light_12);
        deviceGroup_all.add(light_21);
        deviceGroup_all.add(light_22);
        deviceGroup_all.add(light_31);
        deviceGroup_all.add(light_32);
        deviceGroup_all.add(light_33);
        deviceGroup_all.add(light_34);
        deviceGroup_all.add(light_51);
        deviceGroup_all.add(light_52);
        deviceGroup_all.add(light_53);
        deviceGroup_all.add(light_54);
        deviceGroup_all.add(light_61);
        deviceGroup_all.add(light_62);
        deviceGroup_all.add(light_63);
        deviceGroup_all.add(light_64);
        deviceGroup_all.add(light_65);
        deviceGroup_all.add(light_71);
        deviceGroup_all.add(light_72);
        deviceGroup_all.add(light_73);
        deviceGroup_all.add(light_74);
        deviceGroup_all.add(light_75);
        deviceGroup_all.add(light_76);
        deviceGroup_all.add(light_77);
        deviceGroup_all.add(light_78);
        deviceGroup_all.add(light_79);

        DeviceGroup deviceGroup_21_22 = new DeviceGroup();
        deviceGroup_21_22.add(light_21);
        deviceGroup_21_22.add(light_22);

        DeviceGroup deviceGroup_31_33_34 = new DeviceGroup();
        deviceGroup_31_33_34.add(light_31);
        deviceGroup_31_33_34.add(light_33);
        deviceGroup_31_33_34.add(light_34);

        DeviceGroup deviceGroup_52_53 = new DeviceGroup();
        deviceGroup_52_53.add(light_52);
        deviceGroup_52_53.add(light_53);

        DeviceGroup deviceGroup_61_63_65 = new DeviceGroup();
        deviceGroup_61_63_65.add(light_61);
        deviceGroup_61_63_65.add(light_63);
        deviceGroup_61_63_65.add(light_65);

        DeviceGroup deviceGroup_62_65 = new DeviceGroup();
        deviceGroup_62_65.add(light_62);
        deviceGroup_62_65.add(light_65);

        DeviceGroup deviceGroup_71_72 = new DeviceGroup();
        deviceGroup_71_72.add(light_71);
        deviceGroup_71_72.add(light_72);

        DeviceGroup deviceGroup_76_77_78_79 = new DeviceGroup();
        deviceGroup_76_77_78_79.add(light_76);
        deviceGroup_76_77_78_79.add(light_77);
        deviceGroup_76_77_78_79.add(light_78);
        deviceGroup_76_77_78_79.add(light_79);

        DeviceGroup deviceGroup_7 = new DeviceGroup();
        deviceGroup_7.add(light_71);
        deviceGroup_7.add(light_72);
        deviceGroup_7.add(light_73);
        deviceGroup_7.add(light_74);
        deviceGroup_7.add(light_75);
        deviceGroup_7.add(light_76);
        deviceGroup_7.add(light_77);
        deviceGroup_7.add(light_78);
        deviceGroup_7.add(light_79);

        DoubleButton vk_111 = new DoubleButton(client, "vk_111", "/devices/wb-gpio/controls/EXT1_IN1", light_11::toggle, deviceGroup_all::off);
        DoubleButton vk_112 = new DoubleButton(client, "vk_111", "/devices/wb-gpio/controls/EXT1_IN2", light_12::toggle, deviceGroup_all::off);
        SingleButton vk_121 = new SingleButton(client, "vk_121", "/devices/wb-gpio/controls/EXT1_IN3", light_11::toggle);
        SingleButton vk_122 = new SingleButton(client, "vk_122", "/devices/wb-gpio/controls/EXT1_IN4", deviceGroup_31_33_34::toggle);
        SingleButton vk_123 = new SingleButton(client, "vk_123", "/devices/wb-gpio/controls/EXT1_IN5", light_32::toggle);
        SingleButton vk_131 = new SingleButton(client, "vk_131", "/devices/wb-gpio/controls/EXT1_IN6", deviceGroup_61_63_65::toggle);
        SingleButton vk_132 = new SingleButton(client, "vk_132", "/devices/wb-gpio/controls/EXT1_IN7", deviceGroup_62_65::toggle);
        SingleButton vk_321 = new SingleButton(client, "vk_321", "/devices/wb-gpio/controls/EXT1_IN8", light_34::toggle);
        SingleButton vk_322 = new SingleButton(client, "vk_322", "/devices/wb-gpio/controls/EXT1_IN9", light_32::toggle);
        SingleButton vk_33 = new SingleButton(client, "vk_33", "/devices/wb-gpio/controls/EXT1_IN10", light_41::toggle);
        SingleButton vk_21 = new SingleButton(client, "vk_21", "/devices/wb-gpio/controls/EXT1_IN11", deviceGroup_21_22::toggle);
        SingleButton vk_511 = new SingleButton(client, "vk_511", "/devices/wb-gpio/controls/EXT1_IN12", light_51::toggle);
        SingleButton vk_512 = new SingleButton(client, "vk_512", "/devices/wb-gpio/controls/EXT1_IN13", deviceGroup_52_53::toggle);
        SingleButton vk_513 = new SingleButton(client, "vk_513", "/devices/wb-gpio/controls/EXT1_IN14", light_54::toggle);

        SingleButton vk_531 = new SingleButton(client, "vk_531", "/devices/wb-gpio/controls/EXT2_IN1", light_51::toggle);
        SingleButton vk_532 = new SingleButton(client, "vk_532", "/devices/wb-gpio/controls/EXT2_IN2", light_54::toggle);
        SingleButton vk_611 = new SingleButton(client, "vk_611", "/devices/wb-gpio/controls/EXT2_IN3", light_64::toggle);
        SingleButton vk_612 = new SingleButton(client, "vk_612", "/devices/wb-gpio/controls/EXT2_IN4", light_63::toggle);
        SingleButton vk_613 = new SingleButton(client, "vk_613", "/devices/wb-gpio/controls/EXT2_IN5", light_62::toggle);
        SingleButton vk_614 = new SingleButton(client, "vk_614", "/devices/wb-gpio/controls/EXT2_IN6", light_61::toggle);
        DoubleButton vk_711 = new DoubleButton(client, "vk_711", "/devices/wb-gpio/controls/EXT2_IN7", deviceGroup_71_72::toggle, deviceGroup_7::off);
        SingleButton vk_712 = new SingleButton(client, "vk_712", "/devices/wb-gpio/controls/EXT2_IN8", deviceGroup_76_77_78_79::toggle);
        SingleButton vk_721 = new SingleButton(client, "vk_721", "/devices/wb-gpio/controls/EXT2_IN9", deviceGroup_71_72::toggle);
        SingleButton vk_722 = new SingleButton(client, "vk_722", "/devices/wb-gpio/controls/EXT2_IN10", light_74::toggle);
        SingleButton vk_731 = new SingleButton(client, "vk_731", "/devices/wb-gpio/controls/EXT2_IN11", deviceGroup_71_72::toggle);
        SingleButton vk_732 = new SingleButton(client, "vk_732", "/devices/wb-gpio/controls/EXT2_IN12", light_75::toggle);
        SingleButton vk_741 = new SingleButton(client, "vk_741", "/devices/wb-gpio/controls/EXT2_IN13", light_73::toggle);
        SingleButton vk_742 = new SingleButton(client, "vk_742", "/devices/wb-gpio/controls/EXT2_IN14", deviceGroup_76_77_78_79::toggle);

        client.start();

        Scanner s = new Scanner(System.in);
        while (isWorking) {
            switch (s.nextLine()) {
                case "u" -> Log.i("uptime: " + getUptime());
                case "l" -> Log.i("last connection lost: " + client.getLastConnectionLost());
                case "t" -> {
                    Log.i("test vk_111");
                    test();
                }
                case "e" -> {
                    client.stop();
                    Log.w("end of work");
                    Log.closeFile();
                    isWorking = false;
                }
            }
        }
    }

    private static String getUptime() {
        long nowTime = System.currentTimeMillis();
        long upTime = nowTime - startTime;
        return String.format("%d days, %d hours, %d minutes",
                TimeUnit.MILLISECONDS.toDays(upTime),
                TimeUnit.MILLISECONDS.toHours(upTime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(upTime)),
                TimeUnit.MILLISECONDS.toMinutes(upTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(upTime)));
    }

    private static void test() {
        Timer testTimer = new Timer();
        testTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.publish("/devices/wb-gpio/controls/EXT1_IN1", "1");
            }
        }, 100);
        testTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.publish("/devices/wb-gpio/controls/EXT1_IN1", "0");
                testTimer.cancel();
            }
        }, 2100);
    }

}
