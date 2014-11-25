package clauseForm;
import function.Function;

public class ExpB extends Expression {
	private Function function;
	
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
		// TODO: pretty print
		return null;
	}
	
}
