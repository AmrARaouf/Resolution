package expression;
import function.*;

public class ExpA extends Expression {
	public Expression expression1;
	public Expression expression2;
	public Operators operator;
	
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
		String s = "";
		if (quantifier != null)
			s += quantifier.toString();
		s += (isNegated)? (char)172 : "";
		s += "(";
		s += expression1.toString() + " ";
		switch (operator) {
			case AND:
				s += (char)8743; break;
			case OR:
				s += (char)8744; break;
			case EQUIVILANT:
				s += "<=>"; break;
			case IMPLIES:
				s += "=>"; break;
		}
		s += " " + expression2.toString() + ")";
		s += (isNegated)? ")" : "";
		return s;
	}
	
	public static void main(String[] args) {
		Literal[] l = new Literal[1];
		l[0] = new Literal("a");
		Quantifier q = new Quantifier('A', true, l);
		Quantifier q2 = new Quantifier('E', false, l);
		Function function1 = new Function("Q");
		function1.addParameter(new Literal("x"));
		ExpB e1 = new ExpB(null, true, function1);
		Function function2 = new Function("P");
		function2.addParameter(new Literal("a"));
		ExpB e2 = new ExpB(q2, false, function2);
		ExpA e = new ExpA(q, false, e1, e2, Operators.EQUIVILANT);
		System.out.println(e.toString());
	}
}
