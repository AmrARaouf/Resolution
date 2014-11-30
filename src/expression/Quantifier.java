package expression;
import java.util.ArrayList;

import function.Literal;

public class Quantifier {
	char type;
	boolean isNegated;
	ArrayList<Literal> literals;
	
	public Quantifier(char type, boolean isNegated, ArrayList<Literal> literals) {
		this.type = type;
		this.isNegated = isNegated;
		this.literals = literals;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public ArrayList<Literal> getLiterals() {
		return literals;
	}

	public void setLiterals(ArrayList<Literal> literals) {
		this.literals = literals;
	}
	
	public String toString() {
		String s = "";
		if (isNegated) {
			s += (char)172 + "";
		}
		s += (type == 'A')? (char)8704 + "":(char)8707 + "";
		for (int i = 0; i < literals.size(); i++) {
			s+= literals.get(i).name + ",";
		}
		return s.substring(0,s.length() - 1);
	}
}
