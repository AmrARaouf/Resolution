package clauseForm;

import java.util.ArrayList;

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
			elimEquivalence(((ExpA) expression).expression1);
			elimEquivalence(((ExpA) expression).expression2);
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
			elimImplication(((ExpA) expression).expression1);
			elimImplication(((ExpA) expression).expression2);
		}
		return expression;
	}

	public Expression pushNotInwards(Expression expression) {
		if (expression.quantifier != null && expression.quantifier.isNegated) {
			expression.quantifier.reverseNegation();
			expression.quantifier.reverseType();
			expression.reverseNegation();
		}
		if (expression instanceof ExpA) {
			if (expression.isNegated) {
				if (((ExpA) expression).operator == Operators.AND) {
					expression.reverseNegation();
					((ExpA) expression).expression1.reverseNegation();
					((ExpA) expression).expression2.reverseNegation();
					((ExpA) expression).operator = Operators.OR;
				} else if (((ExpA) expression).operator == Operators.OR) {
					expression.reverseNegation();
					((ExpA) expression).expression1.reverseNegation();
					((ExpA) expression).expression2.reverseNegation();
					((ExpA) expression).operator = Operators.AND;
				}
			}
			pushNotInwards(((ExpA) expression).expression1);
			pushNotInwards(((ExpA) expression).expression2);
		}
		return expression;
	}

	public Expression renameVariables(Expression expression) {
		return null;
	}

	public Expression skolemize(Expression expression) {
		int functionCount = 0;
		if (expression.quantifier.type == 'E') {
			Function f = new Function("f" + ++functionCount);
			ArrayList<Literal> literals = expression.involvedLiterals();
			while (!literals.isEmpty()) {
				f.addParameter(literals.remove(0));
			}
			if (f.parameters.size() != 0) {
				for (int i = 0; i < expression.quantifier.literals.length; i++) {
					expression.replaceLiterals(
							expression.quantifier.literals[i], f);
				}
			}
			expression.quantifier = null;
		}
		if (expression instanceof ExpA) {
			skolemize(((ExpA) expression).expression1);
			skolemize(((ExpA) expression).expression2);
		}
		return expression;
	}

	public Expression discardAQuantifiers(Expression expression) {
		if (expression.quantifier != null && expression.quantifier.type == 'A') {
			expression.quantifier = null;
		}
		if (expression instanceof ExpA) {
			discardAQuantifiers(((ExpA) expression).expression1);
			discardAQuantifiers(((ExpA) expression).expression2);
		}
		return expression;
	}

	public Expression toCNF(Expression expression) {
		if (expression instanceof ExpA) {
			if (((ExpA) expression).expression1 instanceof ExpB
					&& ((ExpA) expression).expression2 instanceof ExpA) {
				if (((ExpA) expression).operator == Operators.AND) {
					// unfinished
				}
			}
		}
		return expression;
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
		Literal[] l = { new Literal("x") };
		f1.addParameter(l[0]);
		f1.addParameter(new Literal("y"));
		f2.addParameter(l[0]);
		Quantifier q = new Quantifier('E', false, l);
		ExpB e1 = new ExpB(q, false, f1);
		ExpB e2 = new ExpB(q, false, f2);
		ExpA e3 = new ExpA(new Quantifier('A', false, l), false, e1, e2,
				Operators.AND);
		// ExpA e4 = new ExpA(null, false,
		// new ExpB(null, false, new Function("A")), new ExpB(null, false,
		// new Function("B")), Operators.IMPLIES);
		// ExpA exp = new ExpA(q, false, e3, e4, Operators.IMPLIES);
		/*
		 * System.out.println(exp.toString());
		 * 
		 * 
		 * cf.elimEquivalence(exp); System.out.println(exp);
		 * cf.elimImplication(exp); System.out.println(exp);
		 * cf.pushNotInwards(exp); System.out.println(exp);
		 */
		ClauseForm cf = new ClauseForm();
		System.out.println(e3);
		cf.skolemize(e3);
		System.out.println(e3);
		cf.discardAQuantifiers(e3);
		System.out.println(e3);
	}

}
