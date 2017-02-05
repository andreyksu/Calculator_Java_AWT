package com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gmail.andreyksu.modelpack.pefrormcalc.ICalculator;
import com.gmail.andreyksu.modelpack.pefrormcalc.PerformCalcByJS;

public class CalculatorWithRPN implements ICalculator {

    // TODO продумать в обратной польской записе проверку на валиднось без
    // валидатора. Минимальное сделан, выкидывается исключение при определении
    // приоритета операции. Нужно максимально возложить и скобки и операторы.

    // TODO продумать про ограничение на макс. значение рассчета.

    private static final Logger log =
            LogManager.getLogger(CalculatorWithRPN.class);

    ValidatorExpressionForRPN validator = new ValidatorExpressionForRPN();

    private boolean validate(String str) {
        String expression = removeFlaw(str);
        boolean valid = validator.validate(expression);
        return valid;
    }

    private String removeFlaw(String str) {
        return str.replaceAll(" ", "");
    }

    /**
     * Добавляет перед перед символом "-", символ "0", нужно из за того, что
     * обратная польская запись не работает с унарными операциями. т.е. -(3+4) в
     * польской записи не вычисляется. По этой причине приводим вырожение к виду
     * 0-(3+4).
     */
    public String addZeroToNegativeValue(String str) {
        String result;
        StringBuilder strBuilder = new StringBuilder(str);
        strBuilder = addZerroInFirstPosition(strBuilder);
        strBuilder = addZerroNonFirstPosition(strBuilder);
        result = strBuilder.toString();
        return result;
    }

    /**
     * Добавляется 0 в тех случаях, когда самый первый символ в выражении
     * является "-"
     */
    private StringBuilder addZerroInFirstPosition(StringBuilder strBuilder) {
        int position = 0;
        StringBuilder tmp = new StringBuilder(strBuilder);
        position = tmp.indexOf("-", position);
        if (position == 0) {
            tmp.insert(position, "0");
        }
        return tmp;
    }

    /**
     * Добавляет символ 0 перед минусом, в тех случаях где до символа "-" нет
     * числа. в данном конкретном методе добавляется для случая "(-"
     */
    private StringBuilder addZerroNonFirstPosition(StringBuilder strBuilder) {
        StringBuilder tmp = new StringBuilder(strBuilder);
        int position = 0;
        while (position != -1) {
            position = tmp.indexOf("(-", position);
            if (position != -1) {
                tmp.insert(position + 1, "0");
            }
        }
        return tmp;
    }

    /**
     * Используя алгоритм описанный в wikipedia записываю строку в обратную
     * польскую запись. при этом отличие от приведенного алгоритма в wiki не
     * проверяю здесь парность скобок так как делается это в валидаторе. Т.е.
     * разбираю чистую/провалидированную строку.
     * 
     * @throws UnsupportedOperatorException
     */
    private List<String> trimToRPN(char[] chars)
            throws UnsupportedOperatorException {
        StringBuilder compositeLine = new StringBuilder("");
        List<String> polishList = new ArrayList<String>();
        Stack<Character> stack = new Stack<Character>();

        for (char c : chars) {
            // Если число или точка, то записываем во временную строку и идем за
            // следующим символом.
            if ((c >= '0' && c <= '9') || c == '.') {
                compositeLine.append(c);
                continue;
            } else {
                // Если временная строка не пуста, т.е. там есть цифры и точка,
                // то записываем полученную строку в List
                if (compositeLine.length() != 0) {
                    polishList.add(compositeLine.toString());
                    compositeLine = new StringBuilder(new String());
                }
                // Если стек пуст или же входной символ открвающая скобка, то
                // просто записываем в стек и идем за следующим символом
                if (stack.isEmpty() || c == '(') {
                    stack.push(c);
                    continue;
                }
                // Если существующий оператор в стеке по приоритету ниже
                // текущего символа, то записсываем тек. символ в стек.
                if (getOperatorPriority(stack.peek()) < getOperatorPriority(
                        c)) {
                    stack.push(c);
                } else if (c != ')') {
                    // Если не закрывающая скобка то из стека забираем все
                    // операторы пока их приоритет больше или равен текущему
                    // оператору. Как таковые заканчиваются, записываем тек.
                    // символ в стек и уходим за следующим символом.
                    while (!stack.isEmpty() && (getOperatorPriority(
                            stack.peek()) >= getOperatorPriority(c))) {
                        polishList.add(new Character(stack.pop()).toString());
                    }
                    stack.push(c);
                    continue;
                }
                // Если тек. символ закрывающая скобка, то забираем все
                // операторы в List пока не встретится открывающая скобка. После
                // этого вытаскиваем открыающему скобку и идем за след.
                // символом.
                if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        polishList.add(new Character(stack.pop()).toString());
                    }
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                }
                continue;
            }
        }
        // Сюда приходим когда строка закончилась, дописываем в строку то, что
        // во временной строке и то что осталось в стеке.
        if (compositeLine.length() != 0) {
            polishList.add(compositeLine.toString());
            compositeLine = new StringBuilder(new String());
        }
        while (!stack.isEmpty()) {
            polishList.add(new Character(stack.pop()).toString());
        }

        return polishList;
    }

    private int getOperatorPriority(char c)
            throws UnsupportedOperatorException {
        switch (c) {
        case '^':
            return 4;
        case '*':
            return 3;
        case '/':
            return 3;
        case '-':
            return 2;
        case '+':
            return 2;
        case '(':
            return 1;
        case ')':
            return 1;
        default:
            throw new UnsupportedOperatorException(new Character(c).toString());
        }
    }

    private String calcRPNStrin(List<String> list)
            throws UnsupportedOperatorException {
        String result;
        List<String> listTmp = list;
        Stack<Double> stack = new Stack<Double>();
        double first = 0, second = 0, third = 0;

        for (String s : listTmp) {
            if (s.length() == 1 && (s.charAt(0) == '*' || s.charAt(0) == '/'
                    || s.charAt(0) == '-' || s.charAt(0) == '+'
                    || s.charAt(0) == '^')) {
                if (stack.toArray().length >= 2) {
                    second = stack.pop();
                    first = stack.pop();
                }
                switch (s.charAt(0)) {
                case '^':
                    third = Math.pow(first, second);
                    break;
                case '*':
                    third = first * second;
                    break;
                case '/':
                    third = first / second;
                    break;
                case '-':
                    third = first - second;
                    break;
                case '+':
                    third = first + second;
                    break;
                default:
                    throw new UnsupportedOperatorException("2");
                }
                stack.push(third);
            } else {
                try {
                    stack.push((double) Integer.parseInt(s));
                } catch (NumberFormatException exceptionInteger) {
                    try {
                        stack.push((double) Double.parseDouble(s));
                    } catch (NumberFormatException exceptionFloat) {
                        return result =
                                "Содержется недопустимая последовательность символов";
                    }
                } catch (NullPointerException e) {
                    return result = "NPE - поправьте строку";
                }

            }
        }
        result = stack.pop().toString();
        return result;
    }

    /**
     * Принимает на вход предварительно провалидированную строку. Строку для
     * расчета. Возвращает строку с результатом расчетов, в протином случае
     * строку с сообщением для пользователя.
     */
    public String performingCalculations(String str) {
        if (validate(str)) {
            try {
                String readyLine = addZeroToNegativeValue(str);
                char[] chars = readyLine.toCharArray();
                List<String> list = trimToRPN(chars);
                log.info("Raw String:  " + str);
                log.info("Ready string to polish pars:  " + readyLine);
                log.info("Polish string:  " + list);
                readyLine = calcRPNStrin(list);
                return readyLine;
            } catch (UnsupportedOperatorException e) {
                log.error("Невалидная строка. Невозможно произвести расчет.",
                        e.getCause());
                return "Contain Unsupported Opreator";
            }
        }
        return "Not valide expression!!!";
    }

}
