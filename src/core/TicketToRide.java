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
	private Queue<Ticket> ticketQueue;

	public TicketToRide() throws FileNotFoundException
	{
		GameDeck = new Deck();
		GameDeck.setListener(this);
		
		ticketQueue = new LinkedList<Ticket>();
		
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
				new Rail(input[0], input[1], Integer.parseInt(input[2]), Boolean.parseBoolean(input[3]), (input.length ==5) ? input[4]: input[4]+";"+input[5]));
			}
		}
		
	    //System.out.println(graph.indexList());
		sc.close();
	}

	public void setView(View observe)
	{
		observer = observe;
		observer.observe(new  ViewEvent(2,this,players,GameDeck,graph,visibleCards,tickets));
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

		if(eventID == -1) {
			nextRound();
			System.out.println("Forced Next Turn");
			return;
		}		
		else if (eventID <= 4 && eventID >= 0)
		{
			System.out.println("RoundWeight: "+roundWeight);
			int index = eventID;
			String card = visibleCards[index];

			System.out.println("Card:"+ card);
			
			if(card == "Wild") {
				//System.out.println("wils");
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
			getCurrentPlayer().addTicket(tickets.pop());
		} else if (eventID == 7) {
			getCurrentPlayer().addTicket(tickets.pop());
			tickets.add(tickets.size()-1,getCurrentPlayer().throwTicket());
			//eventID is rail number * 10 + 8 if number ends in 9, is a single rail or the first rail of the double rail.
			//If it is 0, then it is the second rail of a double rail
		} else if (eventID <= 10*(graph.indexList().size()-1) + 8 && eventID >= 8) {	
			Player current = getCurrentPlayer();
			Rail rail = graph.getRail((eventID-8)/10);
			String origColor = rail.getColor();
			
			if(eventID%10 == 8) {
				rail.setColor(rail.getColor().split(";")[0]);
				//System.out.println("A");
			}
			else if(eventID%10 == 9) {
				System.out.println(rail.getColor());
				rail.setColor(rail.getColor().split(";")[1]);
				//System.out.println("B");
			}
			else throw new IllegalArgumentException("invalid PlayerEvent ID number"); 
			
			//System.out.println(rail.toString());
			
			if(rail.getColor().equals("Gray")) {
				//System.out.println("gray rail "+ rail);
				String color = observer.color();
				rail.setColor(color);
			}		
			
			ArrayList<String> usedCards = current.useCards(rail);
			
			System.out.println("Rail: "+ rail + " OrigColor: "+ origColor);
			
			if(origColor.split(";")[0].equals(rail.getColor()) && rail.getOwnerName(0) == (null)) {
				rail.setOwner(getCurrentPlayer().getName(),0);
			}else if(origColor.split(";")[1].equals(rail.getColor()) && rail.getOwnerName(1) == (null)) {
				rail.setOwner(getCurrentPlayer().getName(),1);
			}else {
				System.out.println("not enough cards");
				rail.setColor(origColor);
				return;
			}
			rail.setColor(origColor);
			
			if (usedCards == null) {
				System.out.println("not enough cards");
				return;
			}
			
			usedCards.forEach(train -> GameDeck.addDiscardedCard(train));
			current.addRail(rail);
			current.addPoints(pointValues[rail.getLength()-1]);
			
		}else if(eventID%10 == 6 || eventID%10 == 7){
			int num = eventID;
			while(num >1) {
				PlayerEvent ticketEvent = new PlayerEvent(num%10);
				ticketEvent.setWeight(0);
				onPlayerEvent(ticketEvent);
				num = num/10;
			}
		}
			else throw new IllegalArgumentException("invalid GameEvent ID number");

		roundWeight += e.getWeight();

		System.out.println(getCurrentPlayer().getPoints());
		
		if (roundWeight == 2)
			nextRound();
		//observer.observe(new ViewEvent(0, this, players, GameDeck, graph,visibleCards,tickets));
	}

	private void checkVis() {
		int count =0;
		for(String card : visibleCards)
			if(card.equals("Wild")) count++;
		if(count>=3) 
			this.onGameEvent(new GameEvent(2, this));
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
		roundWeight = 0;
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
		//observer.observe(new ViewEvent(1, this, players, GameDeck, graph,visibleCards,tickets));
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