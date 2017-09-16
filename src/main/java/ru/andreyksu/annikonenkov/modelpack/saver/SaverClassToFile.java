package com.gmail.andreyksu.modelpack.saver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation.ValidatorExpressionForRPN;

// TODO:Заменить консольные выводы на логирование через LOG4J.

/**
 * Используется моделью для сохранения результатов рассчета в файл.
 */
public class SaverClassToFile implements ISaver {

    private static final Logger log =
            LogManager.getLogger(SaverClassToFile.class);

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
            message = new String(
                    "Не удалось создать файл или произвести запись в него");
            log.error(message, e.getMessage(), e);

        }
        return message;
    }

}
