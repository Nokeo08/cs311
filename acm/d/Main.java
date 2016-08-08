package acm.d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static int count = 0;
	
	static void tower(int n, char from_post, char spare_post1, char spare_post2, char to_post){
	    if ( n == 1 )
	        count++;
	    else if ( n == 2 )
	        count+=3;
	    else{
	    	tower(n-2, from_post, spare_post2, to_post, spare_post1);
	        count+=3;
	        tower(n-2, spare_post1, from_post, spare_post2, to_post);
	    }
	}

	public static void main(String[] args){
	    int caseNum = 1;
	    
	    Scanner file = null;
		try {
			file = new Scanner(new File("./src/acm/d/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(file.hasNext()){
			tower(Integer.parseInt(file.next()), 'A', 'B', 'C', 'D');
			System.out.println("Case "+ caseNum++ +": "+count);
			count = 0;
		}
		file.close();
	}
	
	
}
