package core;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import graph.Graph;

public class TicketToRide implements GameEventListener,PlayerEventListener{

	private static final int[] pointValues = {0,1,2,4,7,15,21};
	
	private Graph graph;
	private Queue<Player> players;
	private int roundWeight;
	private Deck GameDeck;
	private Stack<Ticket> tickets;
	private String[] visibleCards;
	
	public TicketToRide(){
		
		players = new LinkedList<Player>();
		players.add(new Player("Rhail island Z",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.add(new Player("Cleveland Z",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.add(new Player("Smashboy",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.add(new Player("Teewee",new ArrayList<String>(),new ArrayList<Ticket>()));
		players.forEach(player -> player.setListener(this));
		
		roundWeight =0;
		
		GameDeck = new Deck();
		
		tickets = new Stack<Ticket>();
		Scanner sc = new Scanner(new File("game_files//tickets.in"));
		
		while(sc.hasNextLine())
			tickets.add(new Ticket(sc.nextLine()));
		
		visibleCards = new String[5];
	}
	
	public void onPlayerEvent(PlayerEvent e) {
		int eventID = e.getID();
		Player currentPlayer = players.peek();
		
		if(eventID <= 4 && eventID >= 0) {
			int index = eventID;
			currentPlayer.addCard(visibleCards[index]);
			visibleCards[index] = GameDeck.getCard();
		}
		else if(eventID == 5) {
			currentPlayer.addCard(GameDeck.getCard());
		}
		else if(eventID == 6) {
			currentPlayer.addTicket(tickets.pop());
		}
		else if(eventID == 7) {
			currentPlayer.
		}
		else throw new IllegalArgumentException("invalid GameEvent ID number");
	}
	
	public void onGameEvent(GameEvent e) {
		int eventID = e.getID();
		
		if(eventID == 0) {
			endGame();
		}
		if(eventID == 1) {
			if (e.getSource() instanceof Deck) {
				e.getSource().refillDeck();
			}
			else throw new IllegalArgumentException("Not Deck");
		}
		if(eventID == 2) {
			
		}
		else throw new IllegalArgumentException("invalid PlayerEvent ID number");
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public Player endGame() {
		
	}
}