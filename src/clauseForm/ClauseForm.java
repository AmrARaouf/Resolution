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
				ExpA exp1 = new ExpA(null, false,
						((ExpA) expression).expression1,
						((ExpA) expression).expression2, Operators.IMPLIES);
				Expression exp2 = new ExpA(null, false,
						((ExpA) expression).expression2,
						((ExpA) expression).expression1, Operators.IMPLIES);
				ExpA exp = new ExpA(expression.quantifier,
						expression.isNegated, exp1, exp2, Operators.AND);
				expression = exp;
			}
			((ExpA) expression).expression1 = elimEquivalence(((ExpA) expression).expression1);
			((ExpA) expression).expression2 = elimEquivalence(((ExpA) expression).expression2);
		}
		return expression;
	}

	public Expression elimImplication(Expression expression) {
		if (expression instanceof ExpA) {
			expression = (ExpA) expression;
			if (((ExpA) expression).operator == Operators.IMPLIES) {
				((ExpA) expression).operator = Operators.AND;
				((ExpA) expression).expression1.reverseNegation();
			}
			((ExpA) expression).expression1 = elimImplication(((ExpA) expression).expression1);
			((ExpA) expression).expression2 = elimImplication(((ExpA) expression).expression2);
		}
		return expression;
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
		Function f1 = new Function("Q");
		Function f2 = new Function("P");
		ExpB e1 = new ExpB(null, false, f1);
		ExpB e2 = new ExpB(null, false, f2);
		ExpA e3 = new ExpA(null, false, e1, e2, Operators.IMPLIES);
		ExpA e4 = new ExpA(null, false,
				new ExpB(null, false, new Function("A")), new ExpB(null, false,
						new Function("B")), Operators.IMPLIES);
		ExpA exp = new ExpA(null, false, e3, e4, Operators.IMPLIES);
		System.out.println(exp.toString());

		ClauseForm cf = new ClauseForm();
		exp = (ExpA) cf.elimEquivalence(exp);
		System.out.println(exp);
		cf.elimImplication(exp);
		System.out.println(exp);
	}

}
