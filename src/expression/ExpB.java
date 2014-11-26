package expression;
import function.Function;

public class ExpB extends Expression {
	public Function function;
	
	public ExpB(Quantifier quantifier, boolean isNegated, Function function) {
		super(quantifier, isNegated);
		this.function = function;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	@Override
	public String toString() {
		String s = (isNegated)? (char)172 + "" : "";
		if (quantifier != null)
			s += quantifier.toString() + ".";
		s += function.toString();
		return s;
	}
	
}
