package clauseForm;

public class ExpA extends Expression {
	private Expression expression1;
	private Expression expression2;
	private Operators operator;
	
	public ExpA(Quantifier quantifier, boolean isNegated, Expression expression1, Expression expression2, Operators operator) {
		super(quantifier, isNegated);
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.operator = operator;
	}
	
	public Expression getExpression1() {
		return expression1;
	}
	public void setExpression1(Expression expression1) {
		this.expression1 = expression1;
	}
	public Expression getExpression2() {
		return expression2;
	}
	public void setExpression2(Expression expression2) {
		this.expression2 = expression2;
	}
	public Operators getOperator() {
		return operator;
	}
	public void setOperator(Operators operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		// TODO: pretty print
		return null;
	}
}
