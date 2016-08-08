package acm.h;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int caseNum = 1;
		
		Scanner file = null;
		try {
			file = new Scanner(new File("./src/acm/h/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(file.hasNext()){
			String string1 = file.next();
			String string2 = file.next();
			
			if(string1.equals("END")) break;
			
			char[] char1 = string1.toCharArray();
			char[] char2 = string2.toCharArray();
			
			Arrays.sort(char1);
			Arrays.sort(char2);
			
			String sorted1 = new String(char1);
			String sorted2 = new String(char2);
			
			
			System.out.println("Case "+ caseNum++ + (sorted1.equals(sorted2) ? ": same" : ": different"));
			
		}
		file.close();

	}
}
