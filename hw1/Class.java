package hw1;
import java.util.ArrayList;

/**
 * @author cmbranc
 *
 */
public class Class implements Comparable<Class>  {
	private String name;
	private char semesterOffered;
	private ArrayList<Class> preReqs;
	
	public Class(String name, char semesterOffered, ArrayList<String> preReqs) {
		this.name = name;
		this.semesterOffered = semesterOffered;
		this.preReqs = new ArrayList<Class>();
		for(String s : preReqs)
			this.preReqs.add(new Class(s));
	}
	
	public Class(String name){
		this.name = name;
	}
	

	public String getName() {
		return name;
	}


	public char getSemesterOffered() {
		return semesterOffered;
	}


	public ArrayList<Class> getPreReqs() {
		return preReqs;
	}
	
	public boolean hasPreReq(Class cls){
		boolean result = false;
		for(Class c : preReqs)
			if (c.equals(cls)) {
				result = true;
			}
		return result;
	}
	
	public boolean hasPreReq(){
		return preReqs.size() > 0;
	} 
	
	public boolean offeredThisTerm(char term){
		if (semesterOffered == 'B') {
			return true;
		}else if(term == 'B' || semesterOffered == term){
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Class [name=" + name + ", semesterOffered=" + semesterOffered + ", preReqs=" + preReqs + "]";
	}

	@Override
	public int compareTo(Class o) {
		return this.name.compareTo(o.name);
	}

}