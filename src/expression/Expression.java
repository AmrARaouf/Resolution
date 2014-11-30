package expression;


public abstract class Expression {
	public Quantifier quantifier;
	public boolean isNegated;
	
	public Expression(Quantifier quantifier, boolean isNegated) {
		this.quantifier = quantifier;
		this.isNegated = isNegated;
	}
	
	public static Expression parse(String expr) {
		if (expr.equals("")) {
			return null;
		}
		Quantifier quantifier = null;
		boolean isNegated = false;
		if (isQuantifier(expr.charAt(0))) {
			int index = 1;
			String s = "";
			do {
				while(index < expr.length() && Character.isLetter(expr.charAt(index))) {
					s += expr.charAt(index++);
				}
			} while (index < expr.length() && expr.charAt(index++) == ',');
		}
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
	
}
