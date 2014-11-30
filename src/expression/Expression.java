package expression;

import java.util.ArrayList;

import function.Function;
import function.Literal;
import function.Var;


public abstract class Expression {
	public Quantifier quantifier;
	public boolean isNegated;
	public Expression parent;
	
	public Expression(Quantifier quantifier, boolean isNegated) {
		this.quantifier = quantifier;
		this.isNegated = isNegated;
	}
	
	@Override
	public abstract String toString();

	public Quantifier getQuantifier() {
		return quantifier;
	}

	public void setQuantifier(Quantifier quantifier) {
		this.quantifier = quantifier;
	}

	public boolean isNegated() {
		return isNegated;
	}

	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}
	
	public void reverseNegation() {
		isNegated = !isNegated;
	}
	
	public void setParentExpression(Expression expression) {
		this.parent = expression;
	}
	
	public ArrayList<Literal> involvedLiterals() {
		ArrayList<Literal> literals = new ArrayList<Literal>();
		Expression exp = this.parent;
		while (exp != null) {
			if (exp.quantifier != null) {
				for (int i = 0; i < exp.quantifier.literals.length; i++) {
					literals.add(exp.quantifier.literals[i]);
				}
			}
			exp = exp.parent;
		}
		return literals;
	}
	
	public void replaceLiterals(Literal literal, Function replacement) {
		if (this instanceof ExpB) {
			for (int i = 0; i < ((ExpB) this).function.parameters.size(); i++ ) {
				Var v = ((ExpB) this).function.parameters.get(i);
				if (v instanceof Literal && v.name.equals(literal.name)) {
					((ExpB) this).function.parameters.set(i, replacement);
				}
			}
		} else if (this instanceof ExpA) {
			((ExpA)this).expression1.replaceLiterals(literal, replacement);
			((ExpA)this).expression2.replaceLiterals(literal, replacement);
		}
	}
	
}
