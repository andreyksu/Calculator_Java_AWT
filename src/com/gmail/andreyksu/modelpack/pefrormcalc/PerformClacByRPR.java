package com.gmail.andreyksu.modelpack.pefrormcalc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PerformClacByRPR implements PerformCalcIntefface {

	private char[] chars;

	private String expression;

	String result;

	private String removeFlaw(String str) {
		return str.replaceAll(" ", "");
	}

	@Override
	public boolean validate(String str) {
		expression = removeFlaw(str);
		chars = expression.toCharArray();
		long time = System.nanoTime();

		if (validateOnContainsOtherChar(chars) && validateOnPairBraces(chars) && orderedBraces(chars)
				&& voidBraces(chars) && moreThenOneOperators(chars) && operatorAndClosedBraces(chars)
				&& endByNegativeOperator(str)) {
			System.out.println(time -= System.nanoTime());
			return true;
		}
		System.out.println(time -= System.nanoTime());
		return false;
	}

	/**
	 * Проверяет есть ли в вырожении символы отличные от ЦИФР,СКОБОК,ОПЕРАТОРОВ,
	 * ТОЧКИ
	 */
	private boolean validateOnContainsOtherChar(char[] chars) {
		for (char b : chars) {
			if ((b < '(' || b > '/') & (b < '0' || b > '9')) {
				return false;
			}
			if (b == ',')
				return false;
		}
		return true;
	}

	/**
	 * Проверяет, есть ли в вырожении скобки без пары, т.е. проверка на парность
	 * выражения
	 */
	private boolean validateOnPairBraces(char[] chars) {
		int i = 0;
		int y = 0;
		for (char b : chars) {
			if (b == '(') {
				i++;
			}
			if (b == ')') {
				y++;
			}
		}
		if (y != i) {
			return false;
		}
		return true;
	}

	/** Проверяет, парные ли скобки в выражении */
	private boolean orderedBraces(char[] chars) {
		int countBraces = 0;
		for (char b : chars) {
			if (b == ')') {
				countBraces -= 1;
			}
			if (countBraces < 0) {
				return false;
			}
			if (b == '(') {
				countBraces += 1;
			}
		}
		return true;
	}

	/** Проверяет есть ли пустые скобки */
	private boolean voidBraces(char[] chars) {
		int x = 0, y = 0, count = 0;
		for (char b : chars) {
			if (b == '(') {
				x = count;
			} else if (b == ')') {
				y = count;
			}
			if (y != 0) {
				if (y - x <= 1)
					return false;
				else {
					x = y = 0;
				}
			}
			count++;
		}
		return true;
	}

	/** Проверяет есть ли подряд идущие операторы. Пример ++, +-, **, итд */
	private boolean moreThenOneOperators(char[] chars) {
		int count1 = 0;
		for (char c : chars) {
			if (c == '/' || c == '-' || c == '+' || c == '*' || c == '.') {
				count1++;
				if (count1 > 1) {
					return false;
				}
			} else {
				count1 = 0;
			}
		}
		return true;
	}

	/**
	 * Проверяет есть оператор и сразу идущая закрывающая скобка. Пример *) итд.
	 * Если да то вернет false
	 * 
	 * @param chars
	 *            - входной массив симолов
	 * @return false, если последовательность вида "*)" или "-)", true, если
	 *         таких последовательностей нет.
	 */
	private boolean operatorAndClosedBraces(char[] chars) {
		int count2 = 0;
		for (char c : chars) {
			if (c == '/' || c == '-' || c == '+' || c == '*' || c == '.') {
				count2++;
			} else if (count2 > 0 && (c == ')' || c == '.')) {
				return false;
			} else {
				count2 = 0;
			}
		}
		return true;
	}

	/**
	 * Проверяет является ли первый или последний символ знаком оператора, точка
	 * или запятая. т.е. выражения вида *5-(5), недопустимо. Так как первый
	 * символ "*" а последний ",".
	 */
	private boolean endByNegativeOperator(String str) {
		char[] chats = { '+', '*', '/', '.' };
		for (char c : chats) {
			if (str.charAt(0) == c)
				return false;
			if (str.charAt(str.length() - 1) == c) {
				return false;
			}
		}
		if (str.charAt(str.length() - 1) == '-')
			return false;
		return true;
	}

	/**
	 * Добавляет перед перед символом "-", символ "0", нужно из за того, что
	 * обратная польская запись не работает с унарными операциями. т.е. -(3+4) в
	 * польской записи не вычисляется. По этой причине приводим вырожение к виду
	 * 0-(3+4).
	 */
	public String addZerroBeforeNegativeOperator(String str) {
		StringBuilder strBuilder = new StringBuilder(str);
		strBuilder = addZerroInFirstPosition(strBuilder);
		strBuilder = addZerro(strBuilder);

		chars = strBuilder.toString().toCharArray();
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
	private StringBuilder addZerro(StringBuilder strBuilder) {
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
	 * 
	 */
	private List<String> makePollandString(char[] chars) {
		StringBuilder assamblyString = new StringBuilder("");
		List<String> pollandString = new ArrayList<String>();
		Stack<Character> stack = new Stack<Character>();

		for (char c : chars) {
			// Если число или точка, то записываем во временную строку и идем за
			// следующим символом.
			if ((c >= '0' && c <= '9') || c == '.') {
				assamblyString.append(c);
				continue;
			} else {
				// Если временная строка не пуста, т.е. там есть цифры и точка,
				// то записываем полученную строку в List
				if (assamblyString.length() != 0) {
					pollandString.add(assamblyString.toString());
					assamblyString = new StringBuilder(new String());
				}
				// Если стек пуст или же входной символ открвающая скобка, то
				// просто записываем в стек и идем за следующим символом
				if (stack.isEmpty() || c == '(') {
					stack.push(c);
					continue;
				}
				// Если существующий оператор в стеке по приоритету ниже
				// текущего символа, то записсываем тек. символ в стек.
				if (proirMethod(stack.peek()) < proirMethod(c)) {
					stack.push(c);
				} else if (c != ')') {
					// Если не закрывающая скобка то из стека забираем все
					// операторы пока их приоритет больше или равен текущему
					// оператору. Как таковые заканчиваются, записываем тек.
					// символ в стек и уходим за следующим символом.
					while (!stack.isEmpty() && (proirMethod(stack.peek()) >= proirMethod(c))) {
						pollandString.add(new Character(stack.pop()).toString());
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
						pollandString.add(new Character(stack.pop()).toString());
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
		if (assamblyString.length() != 0) {
			pollandString.add(assamblyString.toString());
			assamblyString = new StringBuilder(new String());
		}
		while (!stack.isEmpty()) {
			pollandString.add(new Character(stack.pop()).toString());
		}

		return pollandString;
	}

	private int proirMethod(char c) {
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
		default:
			return -1;
		}
	}

	private String calcResultPollandString(List<String> list) {
		List<String> listTmp = list;
		Stack<Float> stack = new Stack<Float>();
		float first = 0, second = 0, third = 0;

		for (String s : listTmp) {
			if (s.length() == 1
					&& (s.charAt(0) == '*' || s.charAt(0) == '/' || s.charAt(0) == '-' || s.charAt(0) == '+')) {
				if (stack.toArray().length >= 2) {
					second = stack.pop();
					first = stack.pop();
				}
				switch (s.charAt(0)) {
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
					break;
				}
				stack.push(third);
			} else {
				try {
					stack.push((float) Integer.parseInt(s));
				} catch (NumberFormatException e) {
					return result = "Содержется недопустимая последовательность символов";
				} catch (NullPointerException e) {
					return result = "NPE - поправьте строку";
				}

			}
		}
		result = stack.pop().toString();
		return result;
	}

	/**
	 * Выполняет рассчет предварительно провалидированного выражения методом
	 * {@link validate(String str)}
	 */
	@Override
	public String perform(String str) {
		if (validate(str)) {
			String result = addZerroBeforeNegativeOperator(str);
			System.out.println(str);
			System.out.println(result);
			List<String> list = makePollandString(chars);
			System.out.println(list);
			result = calcResultPollandString(list);
			return result;
		}
		return "Not valide!!!";

	}

}
