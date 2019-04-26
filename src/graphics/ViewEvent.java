package graphics;

import java.awt.AWTEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import core.Deck;
import core.Player;
import core.Ticket;
import core.graph.Graph;

public class ViewEvent extends AWTEvent{

	/**
	 * 
	 */

	private static final long serialVersionUID = 2886537750102927307L;
	public Queue<Player> players;
	public Deck gameDeck;
	public Graph map;
	public String[] visible;
	public Stack<Ticket> tickets;
	
	public static int ROUND_END = 0;
	public static int GAME_END = 1;
	public static int START_GAME = 2;
	
	public ViewEvent(int ID, Object source, Queue<Player> playerQueue, Deck GameDeck, Graph graph,String[] vis, Stack<Ticket>ticketStack) {
		super(source,ID);
		map = graph;
		gameDeck = GameDeck;
		players = playerQueue;
		visible = vis;
		tickets = ticketStack;
	}
	
	public ArrayList<Player> getSortedPlayer()
	{
		ArrayList<Player> pSet = new ArrayList<Player>(); //unsorted
		for(Player p: players) {
			pSet.add(p);
		}
		Collections.sort(pSet, new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return o2.getPoints() - o1.getPoints();
			}
		});
		System.out.println(pSet);
		return pSet;
	}
}
