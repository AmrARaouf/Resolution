package unification;

import java.util.ArrayList;

public class Function extends Var {

	ArrayList<Var> parameters;
	
	public Function(String name) {
		this.name = name;
		parameters = new ArrayList<>();
	}
	
	public void addParameter(Var v) {
		parameters.add(v);
	}
	
	public int getArity() {
		return parameters.size();
	}
	
	public Var getParameter(int index) {
		return parameters.get(index);
	}
	
	public String toString() {
		String s = name;
		s += "(";
		for (int i = 0; i < parameters.size(); i++) {
			s += parameters.get(i).toString();
			if (i != parameters.size() - 1) {
				s+=",";
			}
		}
		s += ")";
		return s;
	}
	
	@Override
	public int hashCode() {
		return (name + " function/" + getArity()).hashCode();
	}
	
	@Override
	public boolean equals(Object v) {
		if (v instanceof Function) {
			return this.toString().equals(((Var)v).toString()) &&
					this.parameters.equals(((Function)v).parameters);
		} else {
			return false;
		}
	}
	
}
