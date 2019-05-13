package core.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph
{

	private final Map<String, LinkedList<Rail>> cityMap = new HashMap<>();
	// private final Set<Rail> MasterSet = new HashSet<Rail>();
	private final ArrayList<Rail> indexList = new ArrayList<Rail>();
	// want to make this recursive
	private List<Rail> path = new ArrayList<Rail>();

	public void add(String cityName, Rail rail)
	{
		LinkedList<Rail> rails;
		if (!cityMap.containsKey(cityName))
		{
			rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		} else
		{
			rails = cityMap.get(cityName);
			if (!rails.contains(rail))
			{
				rails.add(rail);
				// return;
			}
			cityMap.put(cityName, rails);
		}
		indexList.add(rail);
		addInverse(rail.getCityB(), rail.inverse());
	}

	public Map<String, LinkedList<Rail>> getMap()
	{
		return cityMap;
	}

	// Real janky implementation, might change in the actual thing
	private void addInverse(String cityName, Rail rail)
	{
		LinkedList<Rail> rails;
		if (!cityMap.containsKey(cityName))
		{
			rails = new LinkedList<Rail>();
			rails.add(rail);
			cityMap.put(cityName, rails);
		} else
		{
			rails = cityMap.get(cityName);
			if (!rails.contains(rail))
			{
				rails.add(rail);
			}
			cityMap.put(cityName, rails);
		}
		// indexList.add(rail);
	}

	public Rail getRail(int index)
	{
		return indexList.get(index);
	}

	public ArrayList<Rail> indexList()
	{
		return indexList;
	}

	public Rail getInverse(Rail r)
	{
		for (String key : cityMap.keySet())
		{
			for (Rail rail : cityMap.get(key))
				if (r.isInverse(rail))
					return rail;
		}
		return null;
	}

	public int LongestPath()
	{
		result reresult = null;
		int maxDis = Integer.MIN_VALUE;
		for (String key : cityMap.keySet())
		{
			result res = DFS(key);
			//System.out.println(res);
			int distance = res.dis;
			//System.out.println(res);
			if (distance > maxDis) {
				maxDis = distance;
				reresult = res;
			}
		}
		//System.out.println(cityMap);
		if(reresult != null) path.addAll(reresult.path);
		//System.out.println(maxDis);
		return maxDis;
	}
	
	public List<Rail> getLongestPath(){
		return path;
	}

	public result DFS(String city)
	{
		result max = new result(null, -1, null);
		List<Rail> list = cityMap.get(city);
		//System.out.println(list);
		for (Rail r : list)
		{
			result a = DFSVisit(r, new HashSet<>(), new ArrayList<>(), r.getLength());
			if (a.dis > max.dis)
			{
					max = a;
			}
		}
		return max;
	}

	public result DFSVisit(Rail rail, Set<Rail> visited, List<Rail> path, int sum)
	{
		if (visited.contains(rail))
		{
			throw new AssertionError();
		}
		visited.add(rail);
		visited.add(getInverse(rail));
		ArrayList<Rail> tmpPath = new ArrayList<Rail>(path);
		tmpPath.add(rail);
		//path.add(rail);
		//System.out.println(path);	
		result s = new result(new ArrayList<>(tmpPath), sum, new HashSet<>(visited));
		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
		
		if ( !edgeIterator.hasNext())
			path = new ArrayList<Rail>(tmpPath);
		else {
			while (edgeIterator.hasNext())
			{
				Rail r = edgeIterator.next();		
				if (!visited.contains(r))
				{
					//System.out.println("Rail: "+r);
					result a = DFSVisit(r, visited, tmpPath, sum + r.getLength());
					//System.out.println("A"+a.path+"\nS"+s.path);
					if (a.dis > s.dis)
					{
						//a.path.remove(s.path.size()-1);
						//System.out.println("A:"+a.path+"\nS"+s.path);
						s = a;
						
						path = new  ArrayList<Rail>(tmpPath);					
					}
				}
	   			visited.add(r);
				visited.add(getInverse(r));
			}
		}
		return s;
	}
	
//	public result DFSVisit(Rail rail, Set<Rail> visited, List<Rail> path, int sum)
//	{
//		if (visited.contains(rail))
//		{
//			throw new AssertionError();
//		}
//		visited.add(rail);
//		visited.add(getInverse(rail));
//		path.add(rail);
//		System.out.println(path);	
//		result s = new result(new ArrayList<>(path), sum, new HashSet<>(visited));
//		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
//		while (edgeIterator.hasNext())
//		{
//			Rail r = edgeIterator.next();
//			if (!visited.contains(r))
//			{
//				System.out.println("Rail: "+r);
//				result a = DFSVisit(r, visited, path, sum + r.getLength());
//				//System.out.println("A"+a.path+"\nS"+s.path);
//				if (a.dis > s.dis)
//				{
//					//a.path.remove(s.path.size()-1);
//					//System.out.println("DAMN");
//					System.out.println("A:"+a.path+"\nS"+s.path);
//					s = a;
//					
//				}
//			}
//		}
//		return s;
//	}

	static class result
	{
		public List<Rail> path;
		public int dis;
		public Set<Rail> set;

		public result(List<Rail> path, int dis, Set<Rail> set)
		{
			this.path = path;
			this.dis = dis;
			this.set = set;
		}

		@Override
		public String toString()
		{
			return "result [path=" + path + ", dis=" + dis + ", set=" + set + "]";
		}
	}
}
