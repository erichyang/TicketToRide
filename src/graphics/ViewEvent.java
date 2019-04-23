package graphics;

import java.awt.AWTEvent;
import java.util.Queue;

import core.Deck;
import core.Player;
import core.graph.Graph;

public class ViewEvent extends AWTEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2886537750102927307L;
	private Queue<Player> players;
	private Deck gameDeck;
	private Graph map;
	private String[] visible;
	
	public static int ROUND_END = 0;
	public static int GAME_END = 1;
	public static int START_GAME = 2;
	
	public ViewEvent(int ID, Object source, Queue<Player> playerQueue, Deck GameDeck, Graph graph,String[] vis) {
		super(source,ID);
		map = graph;
		gameDeck = GameDeck;
		players = playerQueue;
		visible = vis;
	}
	
}
