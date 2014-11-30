package expression;
import function.Literal;

public class Quantifier {
	public char type;
	public boolean isNegated;
	public Literal[] literals;
	
	public Quantifier(char type, boolean isNegated, Literal[] literals) {
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

	public Literal[] getLiterals() {
		return literals;
	}

	public void setLiterals(Literal[] literals) {
		this.literals = literals;
	}
	
	public void reverseType() {
		type = (type == 'A')? 'E':'A';
	}
	
	public void reverseNegation() {
		isNegated = !isNegated;
	}
	
	public String toString() {
		String s = "";
		if (isNegated) {
			s += (char)172 + "";
		}
		s += (type == 'A')? (char)8704 + "":(char)8707 + "";
		for (int i = 0; i < literals.length; i++) {
			s+= literals[i].name + ",";
		}
		return s.substring(0,s.length() - 1);
	}
}
