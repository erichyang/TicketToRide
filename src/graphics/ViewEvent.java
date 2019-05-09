package graphics;

import java.awt.AWTEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

import core.Deck;
import core.Player;
import core.Ticket;
import core.graph.Graph;
import core.graph.Rail;

public class ViewEvent extends AWTEvent
{

	/**
	 * 
	 */

	private static final long serialVersionUID = 2886537750102927307L;
	public Queue<Player> players;
	public Deck gameDeck;
	public Graph map;
	public String[] visible;
	public Stack<Ticket> tickets;
	public int roundWeight;

	public static int ROUND_END = 0;
	public static int GAME_END = 1;
	public static int START_GAME = 2;
	public static int ROUND_CONT = 3;

	public ViewEvent(int ID, Object source, Queue<Player> playerQueue, Deck GameDeck, Graph graph, String[] vis,
			Stack<Ticket> ticketStack, int Weight)
	{
		super(source, ID);
		map = graph;
		gameDeck = GameDeck;
		players = playerQueue;
		visible = vis;
		tickets = ticketStack;
		roundWeight = Weight;
	}

	public ArrayList<Player> getSortedPlayer()
	{
		ArrayList<Player> pSet = new ArrayList<Player>();
		for (Player p : players)
			pSet.add(p);
		Collections.sort(pSet, new Comparator<Player>()
		{
			@Override
			public int compare(Player o1, Player o2)
			{
				return o2.getPoints() - o1.getPoints();
			}
		});
		return pSet;
	}
	
	public Player getFirstPlayer()
	{
		Iterator<Player> iter = getSortedPlayer().iterator();
		Player temp = iter.next();
		if(temp.getPoints()==iter.next().getPoints())
			return null;
		return temp;
	}

	public Color getCurrentPlayer()
	{
		switch (players.peek().getName())
		{
		case ("Smashboy"):
			return (Color.yellow);
		case ("Rhail Island Z"):
			return (Color.green);
		case ("Teewee"):
			return (new Color(142, 68, 173));
		case ("Cleveland Z"):
			return (Color.red);
		default:
			return Color.black;
		}
	}

	public ArrayList<String> getSuffColors(Rail rail)
	{
		ArrayList<String> result = new ArrayList<String>();
		HashMap<String, Integer> hand = players.peek().getHand();
		int demand = rail.getLength();
		// System.out.println(""+players.peek() + players.peek().getHand());
		if (players.peek().contains(rail.getCityA(), rail.getCityB()) || roundWeight == 1)
			return result;
		if (hand.get("Pink") + hand.get("Wild") >= demand)
			result.add("Pink");
		if (hand.get("White") + hand.get("Wild") >= demand)
			result.add("White");
		if (hand.get("Blue") + hand.get("Wild") >= demand)
			result.add("Blue");
		if (hand.get("Yellow") + hand.get("Wild") >= demand)
			result.add("Yellow");
		if (hand.get("Orange") + hand.get("Wild") >= demand)
			result.add("Orange");
		if (hand.get("Black") + hand.get("Wild") >= demand)
			result.add("Black");
		if (hand.get("Red") + hand.get("Wild") >= demand)
			result.add("Red");
		if (hand.get("Green") + hand.get("Wild") >= demand)
			result.add("Green");
		return result;
	}
}
