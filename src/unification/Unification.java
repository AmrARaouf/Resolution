package unification;
import function.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Unification {
	
	HashMap<String, HashSet<Var>> uniMap;

	public void unify(String exp1, String exp2) {
		exp1 = exp1.replaceAll(" ", "");
		exp2 = exp2.replaceAll(" ", "");
		uniMap = new HashMap<String, HashSet<Var>>();
		Var v1 = parse(exp1);
		Var v2 = parse(exp2);
		if (unify(v1, v2)) {
			outputUnificationResult();
		} else {
			System.out.println("Failed to unify expressions");
		}
	}

	private boolean unify(Var v1, Var v2) {
		//System.out.println("Unify: " + v1.name + " and " + v2.name);
		if (v1 instanceof Literal && v2 instanceof Literal) {
			// both literals
			if (v1.name.equals(v2.name)) {
				// same literal unifies to itself
				return true;
			} else {
				return addUnificationRule((Literal) v1, v2) && addUnificationRule((Literal) v2, v1);
			}
		} else if (v1 instanceof Literal && v2 instanceof Function) {
			return !isSelfReferential((Literal) v1, (Function) v2) && addUnificationRule((Literal) v1, v2);
		} else if (v1 instanceof Function && v2 instanceof Literal) {
			return !isSelfReferential((Literal) v2, (Function) v1) && addUnificationRule((Literal) v2, v1);
		} else {
			// both are functions
			Function f1 = (Function) v1;
			Function f2 = (Function) v2;
			if (f1.name.equals(f2.name) && f1.getArity() == f2.getArity()) {
				// unify parameters
				for (int i = 0; i < f1.getArity(); i++) {
					if (!unify(f1.getParameter(i), f2.getParameter(i))) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean addUnificationRule(Literal l, Var v) {
		if(uniMap.containsKey(l.name)) {
			HashSet<Var> set = uniMap.get(l.name);
			if (set.contains(v)) {
				return true;
			} else {
				set.add(v);
				for (Var nv : set) {
					if(!unify(v, nv)) {
						return false;
					}
				}
				return true;
			}
		} else {
			HashSet<Var> set = new HashSet<Var>();
			set.add(v);
			uniMap.put(l.name, set);
			return true;
		}
	}
	
	private boolean isSelfReferential(Literal l, Function f) {
		for (int i = 0; i < f.getArity(); i++) {
			if (f.getParameter(i) instanceof Literal) {
				if (l.equals(f.getParameter(i))) {
					return true;
				}
			} else {
				if (isSelfReferential(l, (Function)f.getParameter(i))) {
					return true;
				}
			}
		}
		return false;
	}
	
	// TODO change this method to "private"
	public Var parse(String exp) {
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

	private void parseFunctionParameters(Function f, String params) {
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
	
	private void outputUnificationResult() {
		Set<Entry<String, HashSet<Var>>> entrySet = uniMap.entrySet();
		for (Entry<String, HashSet<Var>> e : entrySet) {
			System.out.println("Variable " + e.getKey() + " unifies to:");
			for (Var v : e.getValue()) {
				System.out.println("\t" + v.toString());
			}
		}
	}
	
}
