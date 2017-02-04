package com.gmail.andreyksu.modelpack.pefrormcalc;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import org.apache.logging.log4j.*;


@SuppressWarnings("restriction")
public class PerformCalcByJS implements ICalculator {
    
    private static final Logger log = LogManager.getLogger(PerformCalcByJS.class);

    @SuppressWarnings("restriction")
    private ScriptEngine engine;

    @SuppressWarnings("restriction")
    public PerformCalcByJS() {
        engine = new ScriptEngineManager().getEngineByName("js");
    }

    @SuppressWarnings("restriction")
    public String performingCalculations(String str) {
        String result = "";
        try {
            result = engine.eval(str).toString();
        } catch (ScriptException e) {
            log.error("Невалидная строка", e.getCause());
//            System.out.print(">>>---");
//            System.out.println(e.getCause());
            return "Not valid Expression!!!";
        }
        return result;
    }

}
