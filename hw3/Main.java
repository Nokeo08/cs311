package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

	private static Hashtable<Triplet, Integer> calcs;

	public static class Triplet{
		private int x, y, n;
		public Triplet(int x, int y, int n) {
			super();
			this.x = x;
			this.y = y;
			this.n = n;
		}
		@Override
		public String toString() {
			return "Triplet [x=" + x + ", y=" + y + ", n=" + n + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + n;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Triplet other = (Triplet) obj;
			if (n != other.n)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}

	public static int walk(int x, int y, int n){
		if(n == 0)
			return (x == 0 && y == 0) ? 1 : 0;

		Triplet state = new Triplet(x,y,n);

		if(calcs.get(state) == null){
			int left = walk(x-1, y, n-1);
			int downLeft = walk(x-1, y-1, n-1);
			int down = walk(x, y-1, n-1);

			int right = walk(x+1, y, n-1);
			int upright = walk(x+1, y+1, n-1);
			int up = walk(x, y+1, n-1);

			int paths = left + downLeft + down + right + upright + up;
			calcs.put(state, paths);
			return paths;
		} 
		else
			return calcs.get(state);
	}

	public static void main(String[] args){

		calcs = new Hashtable<Triplet, Integer>();

		Scanner file = null;
		try {
			file = new Scanner(new File("./src/hw3/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		
//		file = new Scanner(System.in);
		
		while(file.hasNext()){
			int n = file.nextInt();
			if(n == -1) break;
			long before = System.currentTimeMillis();
			System.out.print(walk(0,0,n));
			long after = System.currentTimeMillis();
			System.out.println(" - "+Math.abs((before-after)) + " milliSec\n");
		}
		
		file.close();
	}

}