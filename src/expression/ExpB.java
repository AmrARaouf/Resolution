package expression;
import function.Function;
import function.Literal;

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
		String s = "";
		if (quantifier != null)
			s += quantifier.toString() + ".";
		s += (isNegated)? (char)172 + "(" : "";
		s += function.toString();
		s += (isNegated)? ")" : "";
		return s;
	}
	
}
