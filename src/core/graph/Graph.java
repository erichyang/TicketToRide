package core.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Graph {
	final Map<String,LinkedList<Rail>> cityMap = new HashMap<>();//Trying a map sortta adjacency list maybe TreeMap(prob not)?
	
	public Graph(File fileName) throws FileNotFoundException 
	{
		Scanner sc = new Scanner(fileName);
		while(sc.hasNextLine()) {
			String[] input = sc.nextLine().split(" ");
			add(input[0],new Rail(input[0],input[1],Integer.parseInt(input[2]),Boolean.parseBoolean(input[3]),input[4]));
		}
		System.out.println(cityMap);
	}
	
	public void add(String cityName, Rail rail) {
		LinkedList<Rail> rails;
		if(!cityMap.containsKey(cityName)) {
			 rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		}else {
			rails = cityMap.get(cityName);
			if(!rails.contains(rail))rails.add(rail);
			cityMap.put(cityName,  rails);
		}
	}
//	public void add(String cityName, LinkedList<Rail> rails) {
//		if(!cityMap.containsKey(cityName)) {
//			cityMap.put(cityName, rails);
//		}else {
//			//i want it to throw something because this should never happen
//			throw new IllegalArgumentException();
//		}
//	}
}
