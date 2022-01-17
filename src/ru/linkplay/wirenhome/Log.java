package ru.linkplay.wirenhome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        LocalDate now = LocalDate.now();
        lastDay = now.getDayOfMonth();
        File file = new File(DIR_NAME + File.separator + now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + ".txt");
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
        LocalDateTime now = LocalDateTime.now();
        if (now.getDayOfMonth() != lastDay) {
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
