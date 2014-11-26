import unification.Unification;
import function.Var;

public class Main {
	
	public static void main(String[] args) {
		Unification u = new Unification();
		System.out.println(Var.parse("P(x,g(x),g(f(a)))").toString());
		System.out.println(Var.parse("P(f(u),v,v)").toString());
		System.out.println(Var.parse("P(a,y,f(y))").toString());
		System.out.println(Var.parse("P(z,z,u)").toString());
		System.out.println(Var.parse("f(x,g(x),x)").toString());
		System.out.println(Var.parse("f(g(u),g(g(z)),z)").toString());
		System.out.println("===================================================");
		u.unify("P(x,g(x),g(f(a)))", "P(f(u),v,v)");
		System.out.println("===================================================");
		u.unify("P(a,y,f(y))", "P(z,z,u)");
		System.out.println("===================================================");
		u.unify("f(x,g(x),x)", "f(g(u),g(g(z)),z)");
		System.out.println("===================================================");
	}

}
