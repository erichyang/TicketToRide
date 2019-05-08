package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.graph.Rail;

public class Player {
	// private Graph playerGraph;
	private GameEventListener listen;
	private int trains;
	private int points;
	private HashMap<String, Integer> hand;
	private ArrayList<Ticket> ticketList;
	private String name;
	private ArrayList<Set<String>> cities;
	private boolean isFinalTurn;
	private boolean[] winners;
	private int numCompletedTickets;
	private ArrayList<Rail> railList;

	public Player(String playerName, ArrayList<String> trainCards, ArrayList<Ticket> chosenTickets) {
		railList = new ArrayList<Rail>();
		winners = new boolean[3];
		Arrays.fill(winners, false);
		numCompletedTickets = 0;
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
		// pink, red, black, blue, orange, white, yellow, green, wild
		ticketList = new ArrayList<>();
		name = playerName;
		cities = new ArrayList<Set<String>>();
		isFinalTurn = false;
	}

	public void setWins(boolean b, int index) {
		winners[index] = b;
	}
	
	public boolean getWin(int index) {
		return winners[index];
	}
	
	public boolean isFinalTurn() {
		return isFinalTurn;
	}

	public void finalTurn() {
		isFinalTurn = true;
	}

	public int getNumCards() {
		int sum = 0;
		for (Integer val : hand.values())
			sum += val;
		return sum;
	}

	public ArrayList<String> useCards(Rail rail) {
		// if not enough cards return false
		// if enough cards, first draw from normal color, then draw from wild
		System.out.println("hello A");
		if (railList.contains(rail)) {
			System.out.println("hello");
			return null;
		}
		if (!useTrains(rail.getLength()))
			return null;		
		String color = rail.getColor();

		int num = rail.getLength();

		if (trains < num)
			return null;
		// System.out.println(hand.get(color));
		// System.out.println("COLOR: "+color+ " RAIL: " + rail);
		if (hand.get(color) == null) {
			//System.out.println("BAD COLOR: " + color);
			return null;
		}
		//System.out.println(hand.get(color));
		int amount = hand.get(color);
		if (amount + hand.get("Wild") < num)
			return null;

		ArrayList<String> usedCards = new ArrayList<>();

		if (num <= amount) {
			// System.out.println("A");
			for (int i = 0; i < num; i++)
				usedCards.add(color);
			hand.put(color, amount - num);
		}
		if (num > amount) {
			// System.out.println("B");
			int wildNum = num - amount;
			// System.out.println("Wnum: "+wildNum);
			hand.put("Wild", wildNum);
			for (int i = 0; i < wildNum; i++) {
				// System.out.println("i: "+i+ "Wnum: "+wildNum);
				usedCards.add("Wild");
			}
			hand.put(color, 0);
			for (int i = 0; i < amount; i++)
				usedCards.add(color);
		}
		// System.out.println("usedCards: " + usedCards + "amount: "+ amount + "num:
		// "+num);
		return usedCards;
	}

	public boolean useTrains(int num) {
		if(trains - num < 0) return false;
		if ((trains - num) <= 2) listen.onGameEvent(new GameEvent(0, this));		
		trains -= num;
		return true;
//		if(trains - num < 0) return false;
//		trains -= num;
//		if ((trains) <= 2) {
//			finalTurn();
//			return true;
//		}
//		return true;
	}

	public String addCards(String color) {
		if (hand.get(color) == null) {
			return null;
		}
		hand.put(color, hand.get(color) + 1);
		return color;
	}

	public void setListener(GameEventListener GEL) {
		listen = GEL;
	}

	public ArrayList<Ticket> getTickets() {
		return ticketList;
	}

	public void addRail(Rail rail) {
		String cityA = rail.getCityA();
		String cityB = rail.getCityB();

		int aLocation = findCity(cityA);
		int bLocation = findCity(cityB);

		if (aLocation == -1 && bLocation == -1) {
			cities.add(Stream.of(cityA, cityB).collect(Collectors.toSet()));
			System.out.println(name+"A");
		}
		else if (bLocation == -1) {
			cities.get(aLocation).add(cityB);
			System.out.println(name+"B");
		}
		else if (aLocation == -1) {
			cities.get(bLocation).add(cityA);
			System.out.println(name+"C");
		}
		else if(aLocation != -1 && bLocation != -1){
			Set<String> aGroup = cities.get(aLocation);
			Set<String> bGroup = cities.get(bLocation);
			Set<String> mergeGroup = new HashSet<String>(aGroup);
			mergeGroup.addAll(bGroup);

			cities.remove(findCity(cityA));
			cities.remove(findCity(cityB));
			cities.add(mergeGroup);
			//System.out.println(name+"D");
		}else {
			System.out.println("uh oh");
		}
		railList.add(rail);
		System.out.println("CITIES: "+cities);
	}

	public int countTickets() {
		int count = 0;
//			addPoints((contains(ticket.getCities().split(",")[0],ticket.getCities().split(",")[1])?ticket.getPointCount():-ticket.getPointCount()));
		for (int i = 0; i < ticketList.size(); i++) {
			Ticket ticket = ticketList.get(i);
			if (contains(ticket.getCities().split(",")[0], ticket.getCities().split(",")[1])) {
				count++;
				addPoints(ticket.getPointCount());
			} else {
				addPoints(-ticket.getPointCount());
			}
		}
		numCompletedTickets = count;
		return count;
	}

	public int getComp() {
		return numCompletedTickets;
	}

	private int findCity(String city) {

		for (int i = 0; i < cities.size(); i++)
			if (cities.get(i).contains(city))
				return i;
		return -1;
	}

	public boolean contains(String cityA, String cityB) {
		int aLoc = findCity(cityA);
		int bLoc = findCity(cityB);

		if (aLoc == bLoc && aLoc != -1)
			return true;
		else
			return false;
	}

	public void addTicket(Ticket newTicket) {
		ticketList.add(newTicket);
	}

	public void removeTicket(String ticketName) {
		for (int i = ticketList.size() - 1; i >= 0; i--)
			if (ticketList.get(i).getCities().equals(ticketName))
				ticketList.remove(i);
	}

	public void addPoints(int value) {
		points += value;
	}

	public int points() {
		return points;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public HashMap<String, Integer> getHand() {
		return hand;
	}

	public ArrayList<Set<String>> getCities() {
		return cities;
	}

	public int getTrains() {
		return trains;
	}

	public int getTrainCardsNum() {
		int sum = 0;
		for (Integer num : hand.values())
			sum += num;
		return sum;
	}

	public ArrayList<String> cityList() {
		ArrayList<String> result = new ArrayList<String>();
		for (Set<String> s : cities)
			result.addAll(s);
		return result;
	}

	public String toString() {
		return getName() + ": " + getPoints();
	}
}
