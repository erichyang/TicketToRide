package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.graph.Graph;
import core.graph.Rail;

public class Player
{
	private Graph playerGraph;
	private GameEventListener listen;
	private int trains;
	private int points;
	private HashMap<String, Integer> hand;
	private ArrayList<Ticket> tickets;
	private String name;
	private ArrayList<Set<String>> cities;

	public Player(String playerName, ArrayList<String> trainCards, ArrayList<Ticket> chosenTickets)
	{
		trains = 45;
		points = 0;
		hand = new HashMap<>();
		hand.put("Pink", 0);
		hand.put("Red", 0);
		hand.put("Black", 0);
		hand.put("Blue", 0);
		hand.put("Orange", 0);
		hand.put("White", 0);
		hand.put("Yellow", 0);
		hand.put("Green", 0);
		hand.put("Wild", 0);
		//pink, red, black, blue, orange, white, yellow, green, wild
		tickets = new ArrayList<>();
		name = playerName;
	}
	
	public int getNumCards()
	{
		int sum = 0;
		for(Integer val : hand.values())
			sum+=val;
		return sum;
	}
	
	public boolean useCards(String color, int num)
	{
		//if not enough cards return false
		//if enough cards, first draw from normal color, then draw from wild
		int amount = hand.get(color);
		if(amount + hand.get("Wild") < num)
			return false;
		if(num == amount)
			hand.put(color, 0);
		if(num > amount)
		{
			hand.put("Wild", hand.get("Wild")-num+hand.get(color));
			hand.put(color, 0);
		}
		return true;
	}
	
	
	public void addCards(String color)
	{
		hand.put(color, hand.get(color)+1);
	}
	
	
	public void setListener(GameEventListener GEL)
	{
		listen = GEL;
	}

	public ArrayList<Ticket> getTickets()
	{
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets)
	{
		this.tickets = tickets;
	}

	public void addRail(Rail rail)
	{
		playerGraph.add(rail.getCityA(), rail);
		
		String cityA = rail.getCityA();
		String cityB = rail.getCityB();

		int aLocation = findCity(cityA);
		int bLocation = findCity(cityB);

		if (aLocation == -1 && bLocation == -1)
			cities.add(Stream.of(cityA, cityB).collect(Collectors.toSet()));
		else if (bLocation == -1)
			cities.get(aLocation).add(cityB);
		else if (aLocation == -1)
			cities.get(bLocation).add(cityA);
		else
		{
			Set<String> aGroup = cities.get(aLocation);
			Set<String> bGroup = cities.get(bLocation);
			Set<String> mergeGroup = new HashSet<String>(aGroup);
			mergeGroup.addAll(bGroup);

			cities.remove(aLocation);
			cities.remove(bLocation);
			cities.add(mergeGroup);
		}
	}

	private int findCity(String city)
	{

		for (int i = 0; i < cities.size(); i++)
		{
			if (cities.get(i).contains(city))
				return i;
		}
		return -1;
	}

	public void addPoints(int value)
	{
		points += value;
	}

	public void addTicket(Ticket newTicket)
	{
		tickets.add(newTicket);
	}

	public void removeTicket(String ticketName)
	{
		for (int i = tickets.size() - 1; i >= 0; i--)
		{
			if (tickets.get(i).getCities().equals(ticketName))
			{
				tickets.remove(i);
			}
		}
	}

	public String getName()
	{
		return name;
	}
}
