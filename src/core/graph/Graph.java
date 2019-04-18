package core.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.Player;

public class Graph {
	private int c;
	private final Map<String, LinkedList<Rail>> cityMap = new HashMap<>();
	//private final Set<Rail> MasterSet = new HashSet<Rail>();					
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
	}
	
	public Map<String, LinkedList<Rail>> getMap(){
		return cityMap;
	}

	// Real janky implementation, might change in the actual thing
	private void addInverse(String cityName, Rail rail) {
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

	
	public Rail getRail(int index) {
		return EdgeList().get(index);
	}
	
	public LinkedList<Rail> EdgeList() {
		LinkedList<Rail> RailList = new LinkedList<Rail>();
		for (String key : cityMap.keySet()) {
			RailList.addAll(cityMap.get(key));
		}
		// System.out.println(RailList);
		return RailList;
	}

//	public int LongestPath(String city) {
//		result firstPass = DFS(city);
//		return DFS(firstPass.path.get(firstPass.path.size()-1).getCityB()).dis;
//	}
	
	public int LongestPath(Player p) {
		int maxDis = Integer.MIN_VALUE;
		for (String key : cityMap.keySet()) {
			int distance = DFS(key,p).dis;
			if(distance > maxDis) maxDis = distance;
		}
		return maxDis;
	}
	
	public result DFS(String city,Player p) {
		result max = new result(null, -1, null);
		List<Rail> list = cityMap.get(city);
		for(Rail r : list) {
			result a = DFSVisit(r, new HashSet<>(), new ArrayList<>(), r.getLength(),p);
			if(a.dis > max.dis) {
				max = a;
			}
		}
		return max;
	}

	public result DFSVisit(Rail rail, Set<Rail> visited, List<Rail> path, int sum,Player p) {
		if(visited.contains(rail)) {
			throw new AssertionError();
		}
		
		visited.add(rail);
		visited.add(rail.inverse());
		path.add(rail);

		result s = new result(new ArrayList<>(path), sum, new HashSet<>(visited));
		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
		while (edgeIterator.hasNext()) {
			Rail r = edgeIterator.next();
			if (!visited.contains(r) || !p.contains(r.getCityA(),r.getCityB())) {
				result a = DFSVisit(r, visited, path, sum + r.getLength(),p);
				if(a.dis > s.dis) {
					s = a;
				}
			}
		}

		return s;
	}
	
	static class result {
		public List<Rail> path;
		public int dis;
		public Set<Rail> set;
		public result(List<Rail> path, int dis, Set<Rail> set) {
			this.path = path;
			this.dis = dis;
			this.set = set;
		}
		@Override
		public String toString() {
			return "result [path=" + path + ", dis=" + dis + ", set=" + set + "]";
		}
	}
	
//	public static List<Rail> path;
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
//			path = new ArrayList<>(f);
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
