package function;


public class Literal extends Var {

	public Literal (String s) {
		name = s;
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return (name + " literal").hashCode();
	}
	
	@Override
	public boolean equals(Object v) {
		if (v instanceof Literal) {
			return this.toString().equals(((Var)v).toString());
		} else {
			return false;
		}
	}
	
}
