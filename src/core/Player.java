package core;

import java.util.ArrayList;
import java.util.Set;

import graph.Rail;

public class Player {
	
=======
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import graph.Rail;

public class Player {
	
	private GameEventListener listen;
	private int trains;
	private int points;
	private ArrayList<String> hand;
	private ArrayList<Ticket> tickets;
	private String name;
	private ArrayList<Set<String>> cities;
	
	public Player(String playerName,ArrayList<String> trainCards, ArrayList<Ticket> chosenTickets) {
		trains = 45;
		points = 0;
		hand = null;
		tickets = null;
		name = playerName;
	}

	public void setListener(GameEventListener GEL) {
		listen = GEL;
	}
	
	public ArrayList<String> getHand() {
		return hand;
	}

	public void setHand(ArrayList<String> hand) {
		this.hand = hand;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public void addRail(Rail rail) {
		
		String cityA = rail.getCityA();
		String cityB = rail.getCityB();
		
		int aLocation = findCity(cityA);
		int bLocation = findCity(cityB);
		
		if(aLocation == -1 && bLocation == -1) 
			cities.add(Stream.of(cityA, cityB).collect(Collectors.toSet()));
		else if(bLocation == -1)
			cities.get(aLocation).add(cityB);
		else if(aLocation == -1)
			cities.get(bLocation).add(cityA);
		else {
			Set<String> aGroup = cities.get(aLocation);
			Set<String> bGroup = cities.get(bLocation);
			Set<String> mergeGroup = new HashSet<String>(aGroup);
			mergeGroup.addAll(bGroup);
			
			cities.remove(aLocation);
			cities.remove(bLocation);
			cities.add(mergeGroup);
		}
	}
	
	private int findCity(String city) {
		
		for(int i=0; i< cities.size();i++) {
			if(cities.get(i).contains(city)) return i;
		}
		return -1;
	}
	
	public void addPoints(int value) {
		points += value;
	}
	
	public void addCard(String newTrainCard) {
		hand.add(newTrainCard);
	}
	
	public void addTicket(Ticket newTicket) {
		tickets.add(newTicket);
	}
	
	public void removeTicket(String ticketName) {
		for(int i=tickets.size()-1; i>=0; i--) {
			if(tickets.get(i).getCities().equals(ticketName)) {
				tickets.remove(i);
			}
		}
	}
	
	public String getName() {
		return name;
	}
}
