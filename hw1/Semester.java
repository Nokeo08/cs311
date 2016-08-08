package hw1;

import java.util.ArrayList;

public class Semester {
	private ArrayList<Class> list;
	private int max;

	private char term;

	public Semester(char term, int max) {
		list = new ArrayList<Class>();
		this.term = term;
		this.max = max;
	}

	public void add(Class c){
		list.add(c);
	}

	public char getTerm(){ 
		return term;
	}

	public boolean full(){ 
		return list.size() == max;
	}
	

	public boolean contains(Class cls){
		boolean result = false;
		for(Class c : list)
			if (c.equals(cls)) {
				result = true;
			}
		return result;
	}

}
