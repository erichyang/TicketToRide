package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import graph.Graph;

public class TicketToRide implements GameEventListener,PlayerEventListener{

	private static final int[] pointValues = {0,1,2,4,7,15,21};
	
	private Graph graph;
	private Queue<Player> players;
	private int roundWeight;
	private deck GameDeck;
	private Stack<Ticket> tickets;
	private String[] visibleCards;
	
	public TicketToRide(){
		
		players = new LinkedList<Player>();
		players.add(new Player("Cecil Rhails",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.add(new Player("John Henry",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.add(new Player("Henry Stanley",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.add(new Player("John Rockefeller",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.forEach(player -> player.setListener(this));
		
		roundWeight =0;
		
		GameDeck = new deck();
		
		tickets = new Stack<Ticket>();
		tickets 
	}
}
