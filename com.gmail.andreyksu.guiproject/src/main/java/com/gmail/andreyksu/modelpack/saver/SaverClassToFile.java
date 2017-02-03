package com.gmail.andreyksu.modelpack.saver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaverClassToFile implements SaverInterface {

    PrintWriter pw;

    public SaverClassToFile() {
        // TODO Auto-generated constructor stub
    }

    public String save(String path, String time, String result,
            String expression) {
        String message = null;
        try {
            pw = new PrintWriter(
                    new BufferedWriter(new FileWriter(path, true)));
            try {
                pw.println(time + "   " + result + "   " + expression);
            } finally {
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            message = new String(
                    "Не удалось создать файл или произвести запись в него");
        }
        return message;
    }

}
