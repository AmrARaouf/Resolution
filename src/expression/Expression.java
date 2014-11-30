package expression;

import java.util.ArrayList;

import function.Function;
import function.Literal;


public abstract class Expression {
	public Quantifier quantifier;
	public boolean isNegated;
	public Expression parent;
	
	public Expression(Quantifier quantifier, boolean isNegated) {
		this.quantifier = quantifier;
		this.isNegated = isNegated;
	}
	
	public static Expression parse(String expr) {
		//System.out.println(expr);
		if (expr.equals("")) {
			return null;
		}
		int start = 0, end = expr.length();
		Quantifier quantifier = null;
		boolean isNegated = false, isQuantifierNegated = false;
		if (expr.charAt(0) == '(' || expr.charAt(0) == '[' ) {
			start++;
			end--;
		}
		if (isNot(expr.charAt(start))) {
			if (isQuantifier(expr.charAt(start + 1))) {
				isQuantifierNegated = true;
			} else {
				isNegated = true;
			}
			start++;
		}
		if (isQuantifier(expr.charAt(start))) {
			int index = start;
			ArrayList<Literal> literals = new ArrayList<>();
			String s = "";
			do {
				index++;
				while(index < end && Character.isLetter(expr.charAt(index))) {
					s += expr.charAt(index++);
				}
				literals.add(new Literal(s));
			} while (index < end && expr.charAt(index) == ',');
			quantifier = new Quantifier(expr.charAt(start) == '∀'? 'A' : 'E', isQuantifierNegated, literals);
			start = index + 1;
			end--;
		}
		if (isNot(expr.charAt(start))) {
			isNegated = true;
			start++;
			if (expr.charAt(start + 1) == '(' || expr.charAt(start + 1) == '[') {
				start++;
				end--;
			} else {
				// of the form ¬P(x)
				return new ExpB(quantifier, isNegated, (Function)Function.parse(expr.substring(start, end)));
			}
		}
		int openBrackets = 0;
		int index = start;
		while (true) {
			if (expr.charAt(index) == '(' || expr.charAt(index) == '[' ) {
				openBrackets++;
			} else if (expr.charAt(index) == ')' || expr.charAt(index) == ']' ) {
				openBrackets--;
				if (openBrackets == 0) {
					break;
				}
			}
			index++;
		}
		int s1 = start, e1 = index + 1;
		if (e1 == end) {
			return new ExpB(quantifier, isNegated, (Function)Function.parse(expr.substring(s1, e1)));
		} else {
			start = e1;
			expression.Operators op = parseOperator(expr.charAt(start));
			start++;
			index = start;
			openBrackets = 0;
			while (true) {
				if (expr.charAt(index) == '(' || expr.charAt(index) == '[' ) {
					openBrackets++;
				} else if (expr.charAt(index) == ')' || expr.charAt(index) == ']' ) {
					openBrackets--;
					if (openBrackets == 0) {
						break;
					}
				}
				index++;
			}
			int s2 = start, e2 = index + 1;
			if (e2 != end) {
				System.out.println("Failed");
				return null;
			} else {
				return new ExpA(quantifier, isNegated, parse(expr.substring(s1, e1)), parse(expr.substring(s2, e2)), op);
			}
		}
	}
	
	private static expression.Operators parseOperator(char c) {
		if (c == '\u2227') {
			return expression.Operators.AND;
		} else if (c == '\u2228') {
			return expression.Operators.OR;
		} else if (c == '\u21D2') {
			return expression.Operators.IMPLIES;
		} else if (c == '\u21D4') {
			return expression.Operators.EQUIVILANT;
		} else {
			return null;
		}
	}

	private static boolean isNot(char c) {
		return c == '\u00AC';
	}

	private static boolean isQuantifier(char c) {
		return c == ((char)8704) || c == ((char)8707);
	}

	@Override
	public abstract String toString();

	public Quantifier getQuantifier() {
		return quantifier;
	}

	public void setQuantifier(Quantifier quantifier) {
		this.quantifier = quantifier;
	}

	public boolean isNegated() {
		return isNegated;
	}

	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}
	
	public void reverseNegation() {
		isNegated = !isNegated;
	}
	
	public void setParentExpression(Expression expression) {
		this.parent = expression;
	}
	
}
