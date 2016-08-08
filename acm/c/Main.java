package acm.c;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static int K = 8;
	public static int caseNum = 1;

	public static void tower(int n, char from_post, char spare_post, char to_post) {
		if(n == 0) return;
		tower(n - 1, from_post, to_post, spare_post);
		K-=1;
		if(K == 0) System.out.println("Case "+ caseNum++ +": "+ n +" "+ from_post + " " + to_post);
		tower(n - 1, spare_post, from_post, to_post);
	}

	public static void main(String[] args) {

		Scanner file = null;
		try {
			file = new Scanner(new File("./src/acm/c/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(file.hasNext()){
			K = Integer.parseInt(file.next());
			int n = Integer.parseInt(file.next());
			if(K == n && K == 0) break;

			tower(n, 'A', 'B', 'C');
		}
		file.close();

	}

}
