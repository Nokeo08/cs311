package acm.e;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int caseNum = 1;
		
		Scanner file = null;
		try {
			file = new Scanner(new File("./src/acm/e/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(file.hasNext()){
			int bulbsInString = Integer.parseInt(file.next());
			int time = Integer.parseInt(file.next());
			int bulbNum = Integer.parseInt(file.next());
			
			boolean state = false; 
			
			for(int bulbsToFlip = 1, steps = 1; steps <= time; bulbsToFlip = (bulbsToFlip+1) % bulbsInString, steps++){
				if(bulbsToFlip == 0) state = false;
				else if(bulbNum % bulbsToFlip == 0) state = state ? false : true;
			}
			
			System.out.println("Case "+ caseNum++ + (state ? ": On" : ": Off"));
			
		}
		file.close();

	}
}
