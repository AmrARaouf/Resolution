package clause;
import java.util.ArrayList;

import expression.*;

public class ClauseSet {
	
	ArrayList<ArrayList<ExpB>> clauses;
	char clauseSeparator;
	char clauseSetSeparator;
	
	public ClauseSet(ArrayList<ArrayList<ExpB>> clauses, char clauseSeparator, char clauseSetSeparator) {
		this.clauses = clauses;
		this.clauseSeparator = clauseSeparator;
		this.clauseSetSeparator = clauseSetSeparator;
	}

	public ArrayList<ArrayList<ExpB>> getClauses() {
		return clauses;
	}

	public void setClauses(ArrayList<ArrayList<ExpB>> clauses) {
		this.clauses = clauses;
	}

	public char getClauseSeparator() {
		return clauseSeparator;
	}

	public void setClauseSeparator(char clauseSeparator) {
		this.clauseSeparator = clauseSeparator;
	}

	public char getClauseSetSeparator() {
		return clauseSetSeparator;
	}

	public void setClauseSetSeparator(char clauseSetSeparator) {
		this.clauseSetSeparator = clauseSetSeparator;
	}
	
	public String clauseToString(ArrayList<ExpB> clause) {
		String s = "{";
		for (int i = 0; i < clause.size(); i++) {
			if (i < clause.size() - 1) {
			s += clause.get(i).toString() + clauseSeparator;
			} else {
				s += clause.get(i).toString();
			}
		}
		return s + "}";
	}
		
	@Override
	public String toString() {
		String s = "{";
		for (int i = 0; i < clauses.size(); i++) {
			if (i < clauses.size() - 1) {
			s += clauseToString(clauses.get(i)) + clauseSetSeparator;
			} else {
				s += clauseToString(clauses.get(i));
			}
		}
		return s + "}";
	}
	

}
