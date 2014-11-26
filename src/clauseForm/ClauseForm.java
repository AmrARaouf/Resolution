package clauseForm;

import expression.*;
import function.Function;
import function.Literal;

public class ClauseForm {
	
	public ClauseForm() {
		
	}

	public Expression toExpression(String s) {
		// TODO: string to expression
		return null;
	}

	public String findClauseForm(String s) {
		Expression expression = toExpression(s);
		elimEquivalence(expression);
		elimImplication(expression);
		pushNotInwards(expression);
		renameVariables(expression);
		skolemize(expression);
		discardAQuantifiers(expression);
		toCNF(expression);
		String res = flatten(expression);
		res = toSetOfClause(res);
		return renameClauseVariables(res);
	}

	public Expression elimEquivalence(Expression expression) {
		if (expression instanceof ExpA) {
			expression = (ExpA) expression;
			if (((ExpA) expression).operator == Operators.EQUIVILANT) {
				ExpA exp1 = new ExpA(
						null,
						false,
						((ExpA) expression).expression1,
						((ExpA) expression).expression2, Operators.IMPLIES);
				exp1 = (ExpA) elimEquivalence(exp1);
				Expression exp2 = new ExpA(
						null,
						false,
						((ExpA) expression).expression2,
						((ExpA) expression).expression1, Operators.IMPLIES);
				exp2 = elimEquivalence(exp2);
				ExpA exp = new ExpA(expression.quantifier,
						expression.isNegated, exp1, exp2, Operators.AND);
				expression = exp;
			}
		}
		return expression;
	}

	public Expression elimImplication(Expression expression) {
		return null;
	}

	public Expression pushNotInwards(Expression expression) {
		return null;
	}

	public Expression renameVariables(Expression expression) {
		return null;
	}

	public Expression skolemize(Expression expression) {
		return null;		
	}

	public Expression discardAQuantifiers(Expression expression) {
		return null;
	}

	public Expression toCNF(Expression expression) {
		return null;
	}

	public String flatten(Expression expression) {
		return null;
	}

	public String toSetOfClause(String expression) {
		return null;
	}

	public String renameClauseVariables(String expression) {
		return null;
	}
	

	public static void main(String[] args) {
		Literal[] l = new Literal[1];
		l[0] = new Literal("a");
		Quantifier q = new Quantifier('A', l);
		Quantifier q2 = new Quantifier('E', l);
		Function function1 = new Function("Q");
		function1.addParameter(new Literal("x"));
		ExpB e1 = new ExpB(null, true, function1);
		Function function2 = new Function("P");
		function2.addParameter(new Literal("a"));
		ExpB e2 = new ExpB(q2, false, function2);
		ExpA e = new ExpA(q, false, e1, e2, Operators.EQUIVILANT);
		System.out.println(e.toString());
		
		ClauseForm cf = new ClauseForm();
		e = (ExpA) cf.elimEquivalence(e);
		System.out.println(e.toString());
		
	}

}
