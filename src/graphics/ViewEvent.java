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
	
	public ViewEvent(int ID, Object source, Queue<Players>) {
		super(source,ID);
	}
}
