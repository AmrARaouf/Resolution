import unification.Unification;

public class Main {

	static String unify(String str1, String str2) {
		// TODO: Implement unification algorithm
		return null;
	}

	static String clauseForm(String expression) {
		return renameClauseVariables(toSetOfClause(
				 flatten(toCNF(discardAQuantifiers(
						skolemize(renameVariables(pushNotInwards
								(elimImplication(elimEquivalence(expression))))))))));
	}

	static String elimEquivalence(String expression) {
		return null;
	}

	static String elimImplication(String expression) {
		return null;
	}

	static String pushNotInwards(String expression) {
		return null;
	}

	static String renameVariables(String expression) {
		return null;
	}

	static String skolemize(String expression) {
		return null;
	}

	static String discardAQuantifiers(String expression) {
		return null;
	}

	static String toCNF(String expression) {
		return null;
	}

	static String flatten(String expression) {
		return null;
	}

	static String toSetOfClause(String expression) {
		return null;
	}

	static String renameClauseVariables(String expression) {
		return null;
	}
	
	public static void main(String[] args) {
		Unification u = new Unification();
		System.out.println(u.parse("P(x,g(x),g(f(a)))").toString());
		System.out.println(u.parse("P(f(u),v,v)").toString());
		System.out.println(u.parse("P(a,y,f(y))").toString());
		System.out.println(u.parse("P(z,z,u)").toString());
		System.out.println(u.parse("f(x,g(x),x)").toString());
		System.out.println(u.parse("f(g(u),g(g(z)),z)").toString());
		System.out.println("===================================================");
		u.unify("P(x,g(x),g(f(a)))", "P(f(u),v,v)");
		System.out.println("===================================================");
		u.unify("P(a,y,f(y))", "P(z,z,u)");
		System.out.println("===================================================");
		u.unify("f(x,g(x),x)", "f(g(u),g(g(z)),z)");
		System.out.println("===================================================");
	}

}
