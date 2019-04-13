package graphics;

import java.awt.AWTEvent;
import java.util.Queue;

import core.Deck;
import core.Player;
import core.graph.Graph;

public class ViewEvent extends AWTEvent{

	private Queue<Player> players;
	private Deck gameDeck;
	private Graph map;
	
	public static int ROUND_END = 0;
	public static int GAME_END = 1;
	public static int START_GAME = 2;
	
	public ViewEvent(int ID, Object source, Queue<Player> playerQueue, Deck GameDeck, Graph graph) {
		super(source,ID);
		map = graph;
		gameDeck = GameDeck;
		players = playerQueue;
	}
}
