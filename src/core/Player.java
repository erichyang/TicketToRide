package core;

import java.util.ArrayList;
import java.util.Set;
import core.graph.Rail;

public class Player {
	
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
	}
}
