package core.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
	final Map<String, LinkedList<Rail>> cityMap = new HashMap<>();// Trying a map sortta adjacency list maybe
																	// TreeMap(prob not)?
	// want to make this recursive
	public void add(String cityName, Rail rail) {
		LinkedList<Rail> rails;
		if (!cityMap.containsKey(cityName)) {
			rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		} else {
			rails = cityMap.get(cityName);
			if (!rails.contains(rail)) {
				rails.add(rail);
				// return;
			}
			cityMap.put(cityName, rails);
		}
		addInverse(rail.getCityB(), rail.inverse());
		// add(rail.getCityB(),rail.inverse());
	}

	// Real janky implementation, might change in the actual thing
	public void addInverse(String cityName, Rail rail) {
		LinkedList<Rail> rails;
		if (!cityMap.containsKey(cityName)) {
			rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		} else {
			rails = cityMap.get(cityName);
			if (!rails.contains(rail)) {
				rails.add(rail);
			}
			cityMap.put(cityName, rails);
		}
	}

	public LinkedList<Rail> EdgeList() {
		LinkedList<Rail> RailList = new LinkedList<Rail>();
		for (String key : cityMap.keySet()) {
			RailList.addAll(cityMap.get(key));
		}
		// System.out.println(RailList);
		return RailList;
	}

	public Holds3Things DFS(String city) {
		Holds3Things max = new Holds3Things(null, -1, null);
		List<Rail> list = cityMap.get(city);
		for(Rail r : list) {
			Holds3Things a = DFSVisit(r, new HashSet<>(), new ArrayList<>(), r.getLength());
			if(a.dis > max.dis) {
				max = a;
			}
		}
		return max;
	}

	public Holds3Things DFSVisit(Rail rail, Set<Rail> visited, List<Rail> f, int sum) {
		if(visited.contains(rail)) {
			throw new AssertionError();
		}
		
		visited.add(rail);
		visited.add(rail.inverse());
		f.add(rail);

		Holds3Things s = new Holds3Things(new ArrayList<>(f), sum, new HashSet<>(visited));
		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
		while (edgeIterator.hasNext()) {
			Rail r = edgeIterator.next();
			if (!visited.contains(r)) {
				Holds3Things a = DFSVisit(r, visited, f, sum + r.getLength());
				if(a.dis > s.dis) {
					s = a;
				}
			}
		}
		
		visited.remove(rail);
		visited.remove(rail.inverse());
		f.remove(rail);
		
		return s;
	}
	
	static class Holds3Things {
		public List<Rail> fff;
		public int dis;
		public Set<Rail> set;
		public Holds3Things(List<Rail> fff, int dis, Set<Rail> set) {
			this.fff = fff;
			this.dis = dis;
			this.set = set;
		}
		@Override
		public String toString() {
			return "Holds3Things [fff=" + fff + ", dis=" + dis + ", set=" + set + "]";
		}
	}
	
//	public static List<Rail> fff;
//	public static int dis;
//	public static Set<Rail> set;
//	public void DFSVisit(Rail rail, Set<Rail> visited, List<Rail> f, int sum) {
//		if(visited.contains(rail)) {
//			throw new AssertionError();
//		}
//		
//		visited.add(rail);
//		visited.add(rail.inverse());
//		f.add(rail);
//		
//		if(sum > dis) {
//			dis = sum;
//			fff = new ArrayList<>(f);
//			set = new HashSet<>(visited);
//		}
//
//		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
//		while (edgeIterator.hasNext()) {
//			Rail r = edgeIterator.next();
//			if (!visited.contains(r)) {
//				DFSVisit(r, visited, f, sum + r.getLength());
//			}
//		}
//		
//		visited.remove(rail);
//		visited.remove(rail.inverse());
//		f.remove(rail);
//	}
	
//	public void DFSVisit(Rail rail, Set<Rail> visited) {
//		System.out.println("!");
//		if(visited.contains(rail)) {
//			throw new AssertionError();
//		}
//		visited.add(rail);
//		visited.add(rail.inverse());
//
//		System.out.println(rail);
//
//		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
//		while (edgeIterator.hasNext()) {
//			Rail r = edgeIterator.next();
//			if (!visited.contains(r)) {
//				DFSVisit(r, visited);
//			}
//		}
//	}
}
