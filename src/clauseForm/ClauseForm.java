package clauseForm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

import sun.util.locale.StringTokenIterator;

import expression.ExpA;
import expression.ExpB;
import expression.Expression;
import expression.Operators;
import expression.Quantifier;
import function.Function;
import function.Literal;

public class ClauseForm {
	
	boolean trace;

	public ClauseForm(boolean trace) {
		this.trace = trace;
	}

	public Expression toExpression(String s) {
		return Expression.parse(s);
	}

	public String findClauseForm(String s) {
		System.out.println("Input expression:\n" + s + "\n");
		Expression expression = toExpression(s);
		expression = elimEquivalence(expression);
		if (trace)
			System.out.println("Step 1 - Eliminate Equivilence:\n" + expression.toString() + "\n");
		expression = elimImplication(expression);
		if (trace)
			System.out.println("Step 2 - Eliminate Implications:\n" + expression.toString() + "\n");
		expression = pushNotInwards(expression);
		if (trace)
			System.out.println("Step 3 - Push NOT operators in:\n" + expression.toString() + "\n");
		expression = renameVariables(expression);
		if (trace)
			System.out.println("Step 4 - Standardize:\n" + expression.toString() + "\n");
		expression = skolemize(expression);
		if (trace)
			System.out.println("Step 5 - Skolemize:\n" + expression.toString() + "\n");
		expression = discardAQuantifiers(expression);
		if (trace)
			System.out.println("Step 6 - Discard FOR ALL quantifiers:\n" + expression.toString() + "\n");
		expression = toCNF(expression);
		if (trace)
			System.out.println("Step 7 - To CNF:\n" + expression.toString() + "\n");
		String res = flatten(expression);
		if (trace)
			System.out.println("Step 8 - Flatten expression from brackets:\n" + res + "\n");
		res = toClause(res);
		if (trace)
			System.out.println("Step 9 - Convert to clauses:\n" + res + "\n");
		res = toSetOfClause(res);
		if (trace)
			System.out.println("Step 10 - Convert to set of clauses:\n" + res + "\n");
		res = renameClauseVariables(res);
		if (trace)
			System.out.println("Step 11 - Standardize between clauses:\n" + res + "\n");
		else
			System.out.println("Clause Form:\n" + res + "\n");
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
		LinkedList<String> av = getAvailableNames(expression);
		changeDescent(expression, new HashSet<String>(), av);
		return expression;
	}
	
	private void changeDescent(Expression expression, HashSet<String> used, LinkedList<String> av) {
		if (expression.quantifier != null) {
			for (int i = 0;i < expression.quantifier.literals.size(); i++) {
				if (used.contains(expression.quantifier.literals.get(i).name)) {
					String rep = av.removeFirst();
					renameAll(expression, expression.quantifier.literals.get(i).name, rep);
					used.add(rep);
				} else {
					used.add(expression.quantifier.literals.get(i).name);
				}
			}
		}
		if (expression instanceof ExpA) {
			changeDescent(((ExpA) expression).expression1, used, av);
			changeDescent(((ExpA) expression).expression2, used, av);
		}
	}
	
	private void renameAll(Expression expr, String old, String rep) {
		/*System.out.println(expr);
		System.out.println(old);
		System.out.println(rep);*/
		if (expr.quantifier != null) {
			for (int i = 0;i < expr.quantifier.literals.size(); i++) {
				if (expr.quantifier.literals.get(i).name.equals(old)) {
					expr.quantifier.literals.get(i).name = rep;
				}
			}
		}
		if (expr instanceof ExpA) {
			renameAll(((ExpA) expr).expression1, old, rep);
			renameAll(((ExpA) expr).expression2, old, rep);
		} else {
			Function f = ((ExpB) expr).getFunction();
			renameFunctionLiterals(f, old, rep);
		}
	}

	private void renameFunctionLiterals(Function f, String old, String rep) {
		for (int i = 0; i < f.getArity(); i++) {
			if (f.getParameter(i) instanceof Literal) {
				if (((Literal)(f.getParameter(i))).name.equals(old)) {
					((Literal)(f.getParameter(i))).name = rep;
				}
			} else {
				renameFunctionLiterals((Function)f.getParameter(i), old, rep);
			}
		}
	}

	private LinkedList<String> getAvailableNames(Expression expr) {
		LinkedList<String> al = new LinkedList<>();
		for (int i = 0; i < 26; i++) {
			if (!isUsed(expr, (char)('a' + i)+"")) {
				al.add((char)('a' + i)+"");
			}
		}
		return al;
	}

	private boolean isUsed(Expression expr, String s) {
		if (expr.quantifier != null) {
			for (int i = 0;i < expr.quantifier.literals.size(); i++) {
				if (s.equals(expr.quantifier.literals.get(i).name))
					return true;
			}
		}
		if (expr instanceof ExpA) {
			return isUsed(((ExpA) expr).expression1, s) || isUsed(((ExpA) expr).expression2, s);
		} else {
			return false;
		}
	}

	public Expression skolemize(Expression expression) {
		int functionCount = 0;
		if (expression.quantifier != null && expression.quantifier.type == 'E') {
			Function f = new Function("f" + ++functionCount);
			ArrayList<Literal> literals = expression.involvedLiterals();
			while (!literals.isEmpty()) {
				f.addParameter(literals.remove(0));
			}
			if (f.parameters.size() != 0) {
				for (int i = 0; i < expression.quantifier.literals.size(); i++) {
					expression.replaceLiterals(
							expression.quantifier.literals.get(i), f);
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
				if (((ExpA) expression).operator != ((ExpA)((ExpA) expression).expression2).operator) {
					ExpA e1 = new ExpA(
							null,
							false,
							((ExpA) expression).expression1,
							((ExpA) ((ExpA) expression).expression2).expression1,
							((ExpA) expression).operator);
					ExpA e2 = new ExpA(
							null,
							false,
							((ExpA) expression).expression1,
							((ExpA) ((ExpA) expression).expression2).expression2,
							((ExpA) expression).operator);
					expression = new ExpA(null, false, e1, e2,
							((ExpA) ((ExpA) expression).expression2).operator);
				}
			} else if (((ExpA) expression).expression2 instanceof ExpB
					&& ((ExpA) expression).expression1 instanceof ExpA) {
				if (((ExpA) expression).operator != ((ExpA)((ExpA) expression).expression2).operator) {
					ExpA e1 = new ExpA(
							null,
							false,
							((ExpA) expression).expression2,
							((ExpA) ((ExpA) expression).expression1).expression1,
							((ExpA) expression).operator);
					ExpA e2 = new ExpA(
							null,
							false,
							((ExpA) expression).expression2,
							((ExpA) ((ExpA) expression).expression1).expression2,
							((ExpA) expression).operator);
					expression = new ExpA(null, false, e1, e2,
							((ExpA) ((ExpA) expression).expression1).operator);
				}
			}
			toCNF(((ExpA) expression).expression1);
			toCNF(((ExpA) expression).expression2);
		}
		return expression;
	}

	public String flatten(Expression expression) {
		String unbracketedExp = flattenHelper(expression);
		StringTokenizer st = new StringTokenizer(unbracketedExp,"AND");
		String s = "";
		while (st.hasMoreTokens()) {
			if (st.countTokens() > 1) {
				s += "(" + st.nextToken() + ") " + (char)8743 + " ";
			} else {
				s += "(" + st.nextToken() + ")";
			}
		}
		return s;
	}
	
	private String flattenHelper(Expression expression) {
		if (expression instanceof ExpA) {
			String operator = (((ExpA) expression).operator == Operators.AND)? "AND" : (char)8744 + "";
			return flattenHelper(((ExpA) expression).expression1) + operator + flattenHelper(((ExpA) expression).expression2);
		} else if (expression instanceof ExpB) {
			return expression.toString();
		} else {
			return "";
		}
	}
	
	public String toClause(String expression) {
		String s = "{" + expression.substring(1, expression.length() - 1) + "}";
		s = s.replaceAll("\\) " + (char)8743 + " \\(","} " + (char)8743 + " {");
		return s.replaceAll("" + (char)8744, ",");
	}

	public String toSetOfClause(String expression) {
		return "{" + expression.replaceAll(" "+ (char)8743, ",") + "}";
	}

	public String renameClauseVariables(String expression) {
		return expression;
	}

	public static void main(String[] args) {
		String s = "∀x[P(x)∨∃x[Q(x)⇒¬P(x)]]";
		ClauseForm cf = new ClauseForm(true);
		cf.findClauseForm(s);
	}

}
