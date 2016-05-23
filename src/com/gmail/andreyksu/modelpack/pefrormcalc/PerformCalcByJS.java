package com.gmail.andreyksu.modelpack.pefrormcalc;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PerformCalcByJS implements PerformCalcIntefface {

    private ScriptEngine engine;

    public PerformCalcByJS() {
        engine = new ScriptEngineManager().getEngineByName("js");
    }

    @Override
    public boolean validate(String str) {
        return true;
    }

    @Override
    public String perform(String str) {
        String result = "";
        try {
            result = engine.eval(str).toString();
        } catch (ScriptException e) {
            System.out.print(">>>---");
            System.out.println(e.getCause());
            return "Not valid Expression!!!";
        }
        return result;
    }

}
