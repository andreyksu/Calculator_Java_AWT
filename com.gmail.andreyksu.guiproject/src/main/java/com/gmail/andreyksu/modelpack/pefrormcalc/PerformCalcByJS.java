package com.gmail.andreyksu.modelpack.pefrormcalc;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.logging.log4j.*;

public class PerformCalcByJS implements ICalculator {

    private static final Logger log =
            LogManager.getLogger(PerformCalcByJS.class);

    private ScriptEngine engine;

    public PerformCalcByJS() {
        engine = new ScriptEngineManager().getEngineByName("js");
    }

    /**
     * Принимает на вход строук для расчета. Возвращает строку с результатом
     * расчетов, в протином случае строку с сообщением для пользователя.
     */
    public String performingCalculations(String str) {
        String result = "";
        try {
            result = engine.eval(str).toString();
        } catch (ScriptException e) {
            result = "Not valid Expression!";
            log.error(result, e.getCause());
            return result;
        }
        return result;
    }

}
