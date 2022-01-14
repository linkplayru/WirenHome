package ru.linkplay.wirenhome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Log {

    final private static String DIR_NAME = "WirenLogs";
    private static FileWriter writer;
    private static long lastDay;

    static {
        File dir = new File(DIR_NAME);
        if (dir.mkdirs()) {
            i("created new directory: " + dir.getAbsolutePath());
        } else {
            i("directory already exists: " + dir.getAbsolutePath());
        }
        openFile();
    }

    private static void openFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        Date now = new Date();
        lastDay = TimeUnit.MILLISECONDS.toDays(now.getTime());
        File file = new File(DIR_NAME + File.separator + dateFormat.format(now) + ".txt");
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    i("created new log file: " + file.getAbsolutePath());
                } else {
                    i("can't create new log file: " + file.getAbsolutePath());
                }
            } else {
                i("log file already exists: " + file.getAbsolutePath());
            }
            if (writer != null) {
                writer.close();
            }
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            i("can't create file writer");
        }
    }

    public static void closeFile() {
        try {
            writer.close();
        } catch (IOException e) {
            i("can't close file writer");
        }
    }

    public static void w(String msg) {
        Date now = new Date();
        if (TimeUnit.MILLISECONDS.toDays(now.getTime()) != lastDay) {
            openFile();
        }
        String formatMsg = "[" + now + "] " + msg;
        System.out.println(formatMsg);

        try {
            writer.write(formatMsg + "\n");
        } catch (IOException e) {
            i("can't write log");
        }
    }

    public static void i(String msg) {
        System.out.println(msg);
    }

}
