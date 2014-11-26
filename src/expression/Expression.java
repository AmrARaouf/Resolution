package expression;


public abstract class Expression {
	public Quantifier quantifier;
	public boolean isNegated;
	
	public Expression(Quantifier quantifier, boolean isNegated) {
		this.quantifier = quantifier;
		this.isNegated = isNegated;
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
