package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Main  {

	public static void main(String[] args) {
		ArrayList<Class> classes = new ArrayList<>();

		Scanner file = null;
		try {
			file = new Scanner(new File("./src/hw1/file.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(file.hasNext()){
			int numOfClasses = Integer.parseInt(file.next());
			if (numOfClasses == -1) break;
			int maxPerSemester = Integer.parseInt(file.next());
			ArrayList<String> classesList = new ArrayList<>();
			for (int i = 0; i < numOfClasses; i++) classesList.add(file.next());
			for (int i = 0; i < classesList.size(); i++) {
				String course = file.next();
				char semester = file.next().charAt(0);
				int nPreReqs = Integer.parseInt(file.next());
				ArrayList<String> preReqs = new ArrayList<>();
				for (int j = 0; j < nPreReqs; j++) {
					preReqs.add(file.next().trim());
				}
				classes.add(new Class(course, semester, preReqs));
			}
			System.out.println("The minimum number of semesters required to graduate is " + schedule(new ArrayList<Class>(classes), maxPerSemester, 'F') +".");
			classes.clear();
		}
		file.close();

	}

	public static int schedule(ArrayList<Class> classes, int max, char term){
		ArrayList<Semester> schedule = new ArrayList<Semester>();
		sort(classes);
		int index = 0;
		while (classes.size() > 0) {
			schedule.add(new Semester(term, max));
			Iterator<Class> iter = classes.iterator();
			while(iter.hasNext()) {
				Class c = iter.next();
				if (schedule.get(index).full()) {
					break;
				}
				else if (c.offeredThisTerm(schedule.get(index).getTerm())) {
					if (c.getSemesterOffered() == schedule.get(index).getTerm()) {
						if (c.hasPreReq() || isAPreReq(classes, c)) {
							if (!c.hasPreReq() && isAPreReq(classes, c)) {
								schedule.get(index).add(c);
								iter.remove();
								continue;
							}else if (isAPreReq(classes, c) && c.hasPreReq() && preReqsMet(schedule, c)) {
								schedule.get(index).add(c);
								iter.remove();
								continue;
							}else if (!isAPreReq(classes, c) && c.hasPreReq() && preReqsMet(schedule, c)) {
								schedule.get(index).add(c);
								iter.remove();
								continue;
							}
						}
					}else{
						if (c.hasPreReq() || isAPreReq(classes, c)) {
							if (!c.hasPreReq() && isAPreReq(classes, c)) {
								schedule.get(index).add(c);
								iter.remove();
								continue;
							}else if (isAPreReq(classes, c) && c.hasPreReq() && preReqsMet(schedule, c)) {
								schedule.get(index).add(c);
								iter.remove();
								continue;
							}else if (!isAPreReq(classes, c) && c.hasPreReq() && preReqsMet(schedule, c)) {
								schedule.get(index).add(c);
								iter.remove();
								continue;
							}
						}
					}
					if(c.getSemesterOffered() == schedule.get(index).getTerm()){
						if (!c.hasPreReq() && !isAPreReq(classes, c)) {
							schedule.get(index).add(c);
							iter.remove();
							continue;
						}
					}else{
						if (!c.hasPreReq() && !isAPreReq(classes, c)) {
							schedule.get(index).add(c);
							iter.remove();
							continue;

						}
					}

				}

			}
			index++;
			term = nextTerm(term);
		}

		return schedule.size();
	}

	public static boolean isAPreReq(ArrayList<Class> classes, Class cls){
		boolean result = false;
		for(Class c : classes)
			if(c.hasPreReq(cls))
				result = true;
		return result;
	}

	public static boolean preReqsMet(ArrayList<Semester> schedule, Class cls){
		boolean allMet = true;
		boolean[] result = new boolean[cls.getPreReqs().size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = false;
		}
		for(int i = 0; i < cls.getPreReqs().size(); i++){
			for (int j = 0; j < schedule.size()-1; j++) {
				if(schedule.get(j).contains(cls.getPreReqs().get(i))){
					result[i] = true;
					continue;
				}
			}
		}
		for (int i = 0; i < result.length; i++) {
			allMet = allMet && result[i];
		}
		return allMet;
	}

	public static char nextTerm(char term){
		return (term == 'F') ? 'S' : 'F';
	}

	public static void sort(ArrayList<Class> list){
		ArrayList<Class> copy = new ArrayList<Class>(list);
		list.clear();
		Iterator<Class> iter = copy.iterator();
		while(iter.hasNext()) {
			Class c = iter.next();
			if(!c.hasPreReq() && isAPreReq(copy, c)){
				list.add(c);
				iter.remove();
			}
		}
		Collections.sort(list);
		iter = copy.iterator();
		while(iter.hasNext()) {
			Class c = iter.next();
			if(!c.hasPreReq() && !isAPreReq(copy, c)){
				list.add(c);
				iter.remove();
			}
		}
		list.addAll(copy);
	}

}
