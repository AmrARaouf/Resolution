package clauseForm;

public abstract class Expression {
	Quantifier quantifier;
	boolean isNegated;
	
	public Expression(Quantifier quantifier, boolean isNegated) {
		this.quantifier = quantifier;
		this.isNegated = isNegated;
	}
	
	@Override
	public abstract String toString();
}
