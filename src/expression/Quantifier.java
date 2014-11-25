package expression;
import function.Literal;

public class Quantifier {
	char type;
	Literal[] literals;
	
	public Quantifier(char type, Literal[] literals) {
		this.type = type;
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
	
	public String toString() {
		// TODO: print quantifier nicely
		return null;
	}
}
