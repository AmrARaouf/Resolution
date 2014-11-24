public class Main {

	static String unify(String str1, String str2) {
		// TODO: Implement unification algorithm
		return null;
	}

	static String clauseForm(String expression) {
		return renameClauseVariables(toSetOfClause(
				 flatten(toCNF(discardAQuantifiers(
						skolemize(renameVariables(pushNotInwards
								(elimEmplication(elimEquivalence(expression))))))))));
	}

	static String elimEquivalence(String expression) {
		return null;
	}

	static String elimEmplication(String expression) {
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

}
