package ru.linkplay.wirenhome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    final static String DIR_NAME = "WirenLogs";

    public static void w(String msg) {
        Date now = new Date();
        String formatMsg = "[" + now + "] " + msg;
        System.out.println(formatMsg);

        File dir = new File(DIR_NAME);
        dir.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        File file = new File(DIR_NAME + File.separator + dateFormat.format(now) + ".txt");
        try(FileWriter writer = new FileWriter(file, true)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            writer.write(formatMsg + "\n");
        } catch (IOException e) {
            i("can't write log");
        }
    }

    public static void i(String msg) {
        System.out.println(msg);
    }

}
