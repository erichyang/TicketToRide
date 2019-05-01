package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.graph.Rail;

public class Player
{
	//private Graph playerGraph;
	@SuppressWarnings("unused")
	private GameEventListener listen;
	private int trains;
	private int points;
	private HashMap<String, Integer> hand;
	private ArrayList<Ticket> ticketList;
	private String name;
	private ArrayList<Set<String>> cities;
	private boolean isFinalTurn;

	public Player(String playerName, ArrayList<String> trainCards, ArrayList<Ticket> chosenTickets)
	{
		trains = 45;
		setPoints(0);
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
		ticketList = new ArrayList<>();
		name = playerName;
		cities = new ArrayList<Set<String>>();
		isFinalTurn = false;
	}
	
	public boolean isFinalTurn() {
		return isFinalTurn;
	}
	
	public void finalTurn() {
		isFinalTurn = true;
	}
	
	public int getNumCards()
	{
		int sum = 0;
		for(Integer val : hand.values())
			sum+=val;
		return sum;
	}
	
	public ArrayList<String> useCards(Rail rail)
	{
		//if not enough cards return false
		//if enough cards, first draw from normal color, then draw from wild
		if(contains(rail.getCityA(),rail.getCityB())) return null;
		
		String color = rail.getColor();
		
		int num = rail.getLength();
		
		if(trains<num) return null;
		//System.out.println(hand.get(color));
		//System.out.println("COLOR: "+color+ " RAIL: " + rail);
		if(hand.get(color) == null) {
			System.out.println("BAD COLOR: "+color);
			return null;
		}
		System.out.println(hand.get(color));
		int amount = hand.get(color);
		if(amount + hand.get("Wild") < num)
			return null;
		
		ArrayList<String> usedCards = new ArrayList<>();
		
		if(num <= amount) {
			//System.out.println("A");
			for(int i =0; i < num; i++)
				usedCards.add(color);
			hand.put(color, amount-num);
		}
		if(num > amount)
		{
			//System.out.println("B");
			int wildNum =num-amount;
			//System.out.println("Wnum: "+wildNum);
			hand.put("Wild", wildNum);
			for(int i =0; i < wildNum; i++) {
				//System.out.println("i: "+i+ "Wnum: "+wildNum);
				usedCards.add("Wild");
			}
			hand.put(color, 0);
			for(int i =0; i < amount; i++)
				usedCards.add(color);
		}
		//System.out.println("usedCards: " + usedCards + "amount: "+ amount + "num: "+num);
		return usedCards;
	}
	
	
	public String addCards(String color)
	{
		if(hand.get(color) == null) {
			return null;
		}
		hand.put(color, hand.get(color)+1);
		return color;
	}
	
	
	public void setListener(GameEventListener GEL)
	{
		listen = GEL;
	}

	public ArrayList<Ticket> getTickets()
	{
		return ticketList;
	}

//	public void setTickets(ArrayList<Ticket> tickets)
//	{
//		this.tickets = tickets;
//	}

	public void addRail(Rail rail)
	{
		//playerGraph.add(rail.getCityA(), rail);
		addPoints(rail.getLength());
		
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
		
		if(rail.isDouble()) {
			
		}
	}
	
	public void countTickets() {
		ticketList.forEach(ticket->addPoints((contains(ticket.getCities().split(",")[0],ticket.getCities().split(",")[1])?ticket.getPointCount():-ticket.getPointCount())));
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

	public boolean contains(String cityA, String cityB) {
		int aLoc = findCity(cityA);
		int bLoc = findCity(cityB);
		
		if(aLoc == bLoc && aLoc != -1) {
			return true;
		}else return false;
	}

	public void addTicket(Ticket newTicket)
	{
		ticketList.add(newTicket);
		System.out.println("tickeets: "+ticketList);
	}

	public void removeTicket(String ticketName)
	{
		for (int i = ticketList.size() - 1; i >= 0; i--)
		{
			if (ticketList.get(i).getCities().equals(ticketName))
			{
				ticketList.remove(i);
			}
		}
	}

	public void addPoints(int value)
	{
		points += value;
	}
	
	public int points() {
		return points;
	}
	
	public String getName()
	{
		return name;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}
	public HashMap<String, Integer> getHand()
	{
		return hand;
	}
	public ArrayList<Set<String>> getCities()
	{
		return cities;
	}
	
	public int getTrains()
	{
		return trains;
	}
	
	public int getTrainCardsNum()
	{
		int sum = 0;
		for(Integer num : hand.values())
			sum+=num;
		return sum;
	}
	public String toString() {
		return getName()+" Points: "+ getPoints();
	}
}
