package function;

public abstract class Var {
	public String name;
	
	public static Var parse(String exp) {
		if (exp.equals("")) {
			return null;
		}
		int index = 0;
		// Extract the name
		String name = "";
		while (index < exp.length() && Character.isLetter(exp.charAt(index))) {
			name += exp.charAt(index++);
		}
		// Check if the string is empty now
		if (exp.length() - index == 0) {
			// If that's the case then this is a literal
			return new Literal(name);
		}
		// This is a function then
		Function f = new Function(name);
		parseFunctionParameters(f, exp.substring(index + 1, exp.length() - 1));
		return f;
	}

	private static void parseFunctionParameters(Function f, String params) {
		int openBrackets = 0, startIndex = 0;
		for (int i = 0; i < params.length(); i++) {
			switch (params.charAt(i)) {
			case '(':
				openBrackets++;
				break;
			case ')':
				openBrackets--;
				break;
			case ',':
				if (openBrackets == 0) {
					f.addParameter(parse(params.substring(startIndex, i)));
					startIndex = i + 1;
				}
				break;
			default:
				break;
			}
		}
		f.addParameter(parse(params.substring(startIndex, params.length())));
	}
	
}
