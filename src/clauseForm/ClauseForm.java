package clauseForm;
import expression.*;

public class ClauseForm {
	
	Expression expression;
	String expressionString;
	
	public ClauseForm(String expressionString) {
		this.expressionString = expressionString;
		expression = toExpression(expressionString);
	}
	
	public Expression toExpression(String s) {
		// TODO: string to expression
		return null;
	}
	
	public String findClauseForm() {
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
	
	public void elimEquivalence(Expression expression) {
		
	}
	
	public void elimImplication(Expression expression) {
		
	}
	
	public void pushNotInwards(Expression expression) {
		
	}
	
	public void renameVariables(Expression expression) {

	}
	
	public void skolemize(Expression expression) {

	}
	
	public void discardAQuantifiers(Expression expression) {

	}
	
	public void toCNF(Expression expression) {

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
	
}
