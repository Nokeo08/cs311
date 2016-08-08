package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner file = null;
		try {
			file = new Scanner(new File("./src/hw2/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(file.hasNext()){
			int numOfBrokers = Integer.parseInt(file.next());
			if(numOfBrokers == 0) break;

			int[][] graph = new int[numOfBrokers][numOfBrokers];

			for (int i = 0; i < numOfBrokers; i++) {
				for (int j = 0; j < numOfBrokers; j++) {
					if(i==j)
						graph[i][j] = 0;
					else
						graph[i][j] = 1001;
				}
				
				int numConnections = Integer.parseInt(file.next());
				
				for (int j = 0; j < numConnections; j++) {
					int brokerIndex = Integer.parseInt(file.next())-1;
					int time = Integer.parseInt(file.next());
					graph[i][brokerIndex] = time;
				}
				
			}

			for (int k = 0; k < numOfBrokers; k++) {
				for (int i = 0; i < numOfBrokers; i++) {
					for (int j = 0; j < numOfBrokers; j++) {
						graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
					}
				}
			}

			int broker = 0;
			int min = Integer.MAX_VALUE;
			
			boolean disjoint = true;
			
			for (int i = 0; i < graph.length; i++) {
				int max = 0;
				for(int j : graph[i]) 
					if(j > max) 
						max = j;
				disjoint = disjoint && max == 1001;
				if(max < min){
					min = max;
					broker = i+1;
				}
			}
			
			System.out.println(disjoint ? "disjoint" : broker+" "+min);

		}
		file.close();
	}
/*
	public static void printMatrix(int[][] Matrix, int N) {
		System.out.print("\n\t");
		for (int j = 0; j < N; j++) {
			System.out.print(j + "\t");
		}
		System.out.println();
		for (int j = 0; j < 35; j++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i = 0; i < N; i++) {
			System.out.print(i + " |\t");
			for (int j = 0; j < N; j++) {
				if(Matrix[i][j] == 1001)
					System.out.print(-1);
				else
					System.out.print(Matrix[i][j]);
				System.out.print("\t");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}
*/
}
