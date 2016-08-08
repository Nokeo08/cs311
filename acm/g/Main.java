package acm.g;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
	public static int K = 8;
	public static int caseNum = 1;

	public static void main(String[] args) {

		int caseNum = 1;

		Scanner file = null;
		try {
			file = new Scanner(new File("./src/acm/g/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(file.hasNext()){

			int n = Integer.parseInt(file.next());
			if(n < 0) break;

			int[] x = new int[n], y = new int[n], m = new int[n];

			double a = 0, b = 0;
			boolean aSet = false, bSet = false;
			
			for (int i = 0; i < n; i++) {
				x[i] = Integer.parseInt(file.next());
				y[i] = Integer.parseInt(file.next());
				m[i] = Integer.parseInt(file.next());
			}

			int mX = 0;
			int mY = 0;

			for (double j = 0.00; j < Double.MAX_VALUE; j = new BigDecimal(j+=.01).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) {
				for (int i = 0; i < n; i++) {
					if(!aSet)
						mY += m[i]*(j-x[i]);
					if(!bSet)
						mX += m[i]*(j-y[i]);
				}
				if(!aSet){
					if(mY == 0){
						a = j;
						aSet = true;
					}
					else
						mY = 0;
				}
				if(!bSet){
					if(mX == 0){
						b = j;
						bSet = true;
					}
					else
						mX = 0;
				}
				if(aSet && bSet)
					break;
			}

			System.out.println("Case "+ caseNum++ +": "+ new DecimalFormat("#.00").format(a) +" "+ new DecimalFormat("#.00").format(b));

		}
		file.close();

	}

}
