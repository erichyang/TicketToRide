package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import core.graph.Graph;
import core.graph.Rail;
import graphics.View;
import graphics.ViewEvent;

public class TicketToRide implements GameEventListener, PlayerEventListener
{

	private static final int[] pointValues =
	{ 0, 1, 2, 4, 7, 15, 21 };

	private View observer;
	private Graph graph;
	private Queue<Player> players;
	private int roundWeight;
	private Deck GameDeck;
	private Stack<Ticket> tickets;
	private String[] visibleCards;

	public TicketToRide() throws FileNotFoundException
	{
		GameDeck = new Deck();
		GameDeck.setListener(this);
		players = new LinkedList<Player>();
		players.add(new Player("Rhail island Z", new ArrayList<String>(), new ArrayList<Ticket>()));
		players.add(new Player("Cleveland Z", new ArrayList<String>(), new ArrayList<Ticket>()));
		players.add(new Player("Smashboy", new ArrayList<String>(), new ArrayList<Ticket>()));
		players.add(new Player("Teewee", new ArrayList<String>(), new ArrayList<Ticket>()));

		players.forEach(player ->
		{
			player.setListener(this);
			for (int i = 0; i < 4; i++)
				player.addCards(GameDeck.getCard());
		});

		roundWeight = 0;

		tickets = new Stack<Ticket>();
		Scanner sc = new Scanner(new File("game_files//tickets.in"));

		while (sc.hasNextLine())
			tickets.add(new Ticket(sc.nextLine()));

		visibleCards = GameDeck.getVisibleCards();	
		
		graph = new Graph();
		sc = new Scanner(new File("game_files\\cities\\graph.in"));
		while (sc.hasNextLine()) {
			int num = sc.nextInt();
			sc.nextLine();
			for(int i=0; i<num; i++) {
				String[] input = sc.nextLine().split(" ");
				graph.add(input[0],
				new Rail(input[0], input[1], Integer.parseInt(input[2]), Boolean.parseBoolean(input[3]), input[4]));
			}
		}
		System.out.println(graph.EdgeList().size()/2 + 7);
	}

	public void setView(View observe)
	{
		observer = observe;
		observer.observe(new  ViewEvent(2,this,players,GameDeck,graph));
	}

	public void onPlayerEvent(PlayerEvent e)
	{
		if ((roundWeight + e.getWeight()) > 2)
		{
			System.out.println("invalid action");
			return;
		}

		int eventID = e.getID();
		Player currentPlayer = players.peek();

		if (eventID <= 4 && eventID >= 0)
		{
			System.out.println("RoundWeight: "+roundWeight);
			int index = eventID;
			String card = visibleCards[index];

			System.out.println("Card:"+ card);
			
			if(card == "Wild") {
				if(roundWeight >0) {
					this.onPlayerEvent(e.reEvent());
					return;
				}
				roundWeight++;
			}
			
			currentPlayer.addCards(card);
			visibleCards[index] = GameDeck.getCard();
			checkVis();
		} else if (eventID == 5)
		{
			currentPlayer.addCards(GameDeck.getCard());
		} else if (eventID == 6)
		{
			currentPlayer.addTicket(tickets.pop());
		} else if (eventID == 7)
		{
			// currentPlayer.removeTicket(eventID);
			// IDK how to do this yet Ill wait for eric
		} else if (eventID <= graph.EdgeList().size() + 7) {

			Player source = (Player) e.getSource();
			Rail rail = graph.getRail(eventID - 7);
			System.out.println("rail: "+ rail+"source: "+ source);
			if (!source.useCards(rail))
			{
				System.out.println("not enough cards");
				return;
			}
			source.addRail(rail);
		} else
			throw new IllegalArgumentException("invalid GameEvent ID number");

		roundWeight += e.getWeight();

		if (roundWeight == 2)
		{
			roundWeight = 0;
			nextRound();
		}
		//observer.observe(new ViewEvent(0, this, players, GameDeck, graph));
	}

	private void checkVis() {
		int count =0;
		for(String card : visibleCards)
			if(card.equals("Wild")) count++;
		if(count>=3) 
			this.onGameEvent(new GameEvent(3, this));
	}

	public void onGameEvent(GameEvent e)
	{
		int eventID = e.getID();

		if (eventID == 0)
		{
			players.peek().finalTurn();
		} else if (eventID == 1)
		{
			if (e.getSource() instanceof Deck)
			{
				((Deck) e.getSource()).refillDeck();
			} else
				throw new IllegalArgumentException("Not Deck");
		} else if (eventID == 2)
		{
			for (int i = 0; i < visibleCards.length; i++)
			{
				GameDeck.addDiscardedCard(visibleCards[i]);
				visibleCards[i] = GameDeck.getCard();
			}
		} else if (eventID == 3)
		{
//			endGame();
			System.out.println(endGame());
		} else
			throw new IllegalArgumentException("invalid PlayerEvent ID number");
	}

	public void nextRound()
	{
		players.offer(players.poll());
	}

	public Graph getGraph()
	{
		return graph;
	}

	public String[] getVisCards()
	{
		return visibleCards;
	}

	public Player endGame() {
		
		players.forEach(player -> player.countTickets());

		Player winner = null;
		int mostPoints = Integer.MIN_VALUE;
		for (Player p : players)
		{
			if (p.points() > mostPoints)
			{
				mostPoints = p.points();
				winner = p;
			}
		}
		observer.observe(new ViewEvent(1, this, players, GameDeck, graph));
		return winner;
	}

	public Player getCurrentPlayer()
	{
		return players.peek();
	}

	public Deck getDeck()
	{
		return GameDeck;
	}
}