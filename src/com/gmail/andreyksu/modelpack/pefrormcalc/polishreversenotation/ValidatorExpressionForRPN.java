package com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation;

public class ValidatorExpressionForRPN {
	// TODO добавить в валидациях допуска оператора ^

	public boolean validate(String str) {
		char[] chars = str.toCharArray();
		long time = System.nanoTime();

		if (validateOnContainsOtherChar(chars) && validateOnPairBraces(chars) && isOrderedBraces(chars)
				&& isContainedEmptyBraces(chars) && isContainedPairOperators(chars)
				&& isPairedOperatorAndClosedBrace(chars) && isOperatorBeginOrEnd(str)) {
			time = System.nanoTime() - time;
			System.out.println("Time validate: " + (time));
			return true;
		}
		time = System.nanoTime() - time;
		System.out.println("Time validate: " + (time));
		return false;
	}

	/**
	 * Проверяет есть ли в вырожении символы отличные от ЦИФР,СКОБОК,ОПЕРАТОРОВ,
	 * ТОЧКИ
	 */
	private boolean validateOnContainsOtherChar(char[] chars) {
		for (char b : chars) {
			if ((b < '(' || b > '/') && (b < '0' || b > '9')) {
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
	private boolean isOrderedBraces(char[] chars) {
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
	private boolean isContainedEmptyBraces(char[] chars) {
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
	private boolean isContainedPairOperators(char[] chars) {
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
	private boolean isPairedOperatorAndClosedBrace(char[] chars) {
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
	private boolean isOperatorBeginOrEnd(String str) {
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

}
