package acm.f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int caseNum = 1;

		Scanner file = null;

		try {
			file = new Scanner(new File("./src/acm/f/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(file.hasNext()){
			int minPos = Integer.MAX_VALUE;
			
			int row = Integer.parseInt(file.next());
			int col = Integer.parseInt(file.next());

			char[][] maze = new char[row][col];
			
			for (int i = 0; i < row; i++) {
				String line = file.next();
				for (int j = 0; j < col; j++) {
					maze[i][j] = line.charAt(j);
				}
			}

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if(validPos(maze,i,j)){
						int temp = run(i,j, maze, 0);
						if(temp < minPos) minPos = temp;
					}
				}
			}

			System.out.println("Case "+ caseNum++ + ((minPos < Integer.MAX_VALUE) ? ": "+minPos : ": "+-1));

		}
		file.close();


	}

	public static int run(int row, int col, char[][] maze, int steps){
		if(mazeComplete(maze)) return steps;
		if(stuck(maze,row,col)) return Integer.MAX_VALUE;

		int up = Integer.MAX_VALUE;
		int down = Integer.MAX_VALUE;
		int left = Integer.MAX_VALUE;
		int right = Integer.MAX_VALUE;

		if(validPos(maze,row-1,col)){
			char[][] copy = clone(maze);
			copy[row][col] = '*';
			int offset = 1;
			while(validPos(copy,row-offset,col)){
				copy[row-offset][col] = '*';
				offset++;
			}
			up = run(row-(offset-1), col, copy, steps+1);
		}

		if(validPos(maze,row+1,col)){
			char[][] copy = clone(maze);
			copy[row][col] = '*';
			int offset = 1;
			while(validPos(copy,row+offset,col)){
				copy[row+offset][col] = '*';
				offset++;
			}
			down = run(row+(offset-1), col, copy, steps+1);
		}

		if(validPos(maze,row,col-1)){
			char[][] copy = clone(maze);
			copy[row][col] = '*';
			int offset = 1;
			while(validPos(copy,row,col-offset)){
				copy[row][col-offset] = '*';
				offset++;
			}
			left = run(row, col-(offset-1), copy, steps+1);

		}

		if(validPos(maze,row,col+1)){
			char[][] copy = clone(maze);
			copy[row][col] = '*';
			int offset = 1;
			while(validPos(copy,row,col+offset)){
				copy[row][col+offset] = '*';
				offset++;
			}
			right = run(row, col+(offset-1), copy, steps+1);

		}

		return Math.min(Math.min(up, down), Math.min(left, right));
	}

	public static char[][] clone(char[][] maze){
		char[][] copy = new char[getNumRow(maze)][getNumCol(maze)];
		for (int i = 0; i < getNumRow(maze); i++) {
			for (int j = 0; j < getNumCol(maze); j++) {
				copy[i][j] = maze[i][j];
			}
		}
		return copy;
	}

	public static boolean mazeComplete(char[][] maze){
		boolean result = true;
		for (int i = 0; i < getNumRow(maze) && result == true; i++) {
			for (int j = 0; j < getNumCol(maze) && result == true; j++) {
				if(maze[i][j] != '*') result = false;
			}
		}
		return result;
	}

	public static boolean stuck(char[][] maze, int row, int col){

		boolean upBlocked = false;
		boolean downBlocked = false;
		boolean leftBlocked = false;
		boolean rightBlocked = false;

		if(!validPos(maze,row-1,col)) upBlocked = true;
		if(!validPos(maze,row+1,col)) downBlocked = true;
		if(!validPos(maze,row,col-1)) leftBlocked = true;
		if(!validPos(maze,row,col+1)) rightBlocked = true;

		return upBlocked && downBlocked && leftBlocked && rightBlocked;
	}

	public static int getNumCol(char[][] maze){
		return maze[0].length;
	}

	public static int getNumRow(char[][] maze){
		return maze.length;
	}

	public static boolean validPos(char[][] maze, int row, int col){
		return (row >= 0) && (row < getNumRow(maze)) && (col >= 0) && (col < getNumCol(maze)) && maze[row][col] != '*';
	}


	public static void print(char[][] maze){
		for (int i = 0; i < getNumRow(maze); i++) {
			for (int j = 0; j < getNumCol(maze); j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
	}

}
