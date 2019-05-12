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
		//System.out.println(" RES: "+reresult);
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
			//System.out.println(a);
//			for(int i = a.path.size()-1; i>0; i--) {
//				Rail r1 = a.path.get(i);
//				Rail r2 = a.path.get(i-1);
//				//System.out.println(r1.getCityA()+r2.getCityA());
//				if(r1.getCityA().equals(r2.getCityA())) {
//					//System.out.println("hello");
//					a.path.remove(r1.getLength()<r2.getLength() ? i:i-1);
//					a.dis -= r2.getLength();
//				}
//			}
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
		path.add(rail);
		//System.out.println(path);	
		result s = new result(new ArrayList<>(path), sum, new HashSet<>(visited));
		Iterator<Rail> edgeIterator = cityMap.get(rail.getCityB()).iterator();
		while (edgeIterator.hasNext())
		{
			Rail r = edgeIterator.next();
			//System.out.println(r + " ,V: "+!visited.contains(r));
			if (!visited.contains(r))
			{
	//			System.out.println(p+"Rail: "+r);
				result a = DFSVisit(r, visited, path, sum + r.getLength());
				//System.out.println("A"+a+"\nS"+s);
				if (a.dis > s.dis)
				{
					//System.out.println("A:"+a.path+"\nS"+s.path);
					s = a;
				}
			}
		}
		return s;
	}

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
