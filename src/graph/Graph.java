package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
	final Map<String,LinkedList<Rail>> cityMap = new HashMap<>();//Trying a map sortta adjacency list maybe TreeMap(prob not)?
	
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
	public void add(String cityName, LinkedList<Rail> rails) {
		if(!cityMap.containsKey(cityName)) {
			cityMap.put(cityName, rails);
		}else {
			//i want it to throw something because this should never happen
		}
	}
}
