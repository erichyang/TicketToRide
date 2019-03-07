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
	//want to make this recursive
	public void add(String cityName, Rail rail) {
		LinkedList<Rail> rails;
		if(!cityMap.containsKey(cityName)) {
			rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		}else {
			rails = cityMap.get(cityName);
			if(!rails.contains(rail)) {
				rails.add(rail);
				//return;
			}
			cityMap.put(cityName,  rails);
		}
		addInverse(rail.getCityB(),rail.inverse());
		//add(rail.getCityB(),rail.inverse());
	}
	
	//Real janky implementation, might change in the actual thing
	public void addInverse(String cityName, Rail rail) {
		LinkedList<Rail> rails;
		if(!cityMap.containsKey(cityName)) {
			rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		}else {
			rails = cityMap.get(cityName);
			if(!rails.contains(rail)) {
				rails.add(rail);
			}
			cityMap.put(cityName,  rails);
		}
		
	}
	
	public void correctCycle() {
		
	}
	
	public LinkedList<Rail> EdgeList(){
		LinkedList<Rail> RailList = new LinkedList<Rail>();
		for(String key: cityMap.keySet()) {
			RailList.addAll(cityMap.get(key));
		}
		System.out.println(RailList);
		return RailList;
	}
	
	public void detectCycle() {
		Map<String,Boolean> visited = new HashMap<String,Boolean>();
		for(String key: cityMap.keySet()) {
			visited.put(key, false);
		}
		for(String city: visited.keySet()) {
			if(!visited.get(city)) {
				
			}
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
