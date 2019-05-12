package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import core.graph.Graph;
import core.graph.Rail;
import graphics.View;
import graphics.ViewEvent;

public class TicketToRide implements GameEventListener, PlayerEventListener
{

	private View observer;
	private Graph graph;
	private Queue<Player> players;
	private int roundWeight;
	private Deck GameDeck;
	private Stack<Ticket> tickets;
	private String[] visibleCards;
	private final int[] pointValues =
	{ 1, 2, 4, 7, 10, 15 };

	public TicketToRide() throws FileNotFoundException
	{
		GameDeck = new Deck();
		GameDeck.setListener(this);

		players = new LinkedList<Player>();
//		players.add(new Player("Rhail Island Z", new ArrayList<String>(), new ArrayList<Ticket>()));
		players.add(new Player("Cleveland Z", new ArrayList<String>(), new ArrayList<Ticket>()));
//		players.add(new Player("Smashboy", new ArrayList<String>(), new ArrayList<Ticket>()));
//		players.add(new Player("Teewee", new ArrayList<String>(), new ArrayList<Ticket>()));

		players.forEach(player ->
		{
			player.setListener(this);
			for (int i = 0; i < 4; i++)
				player.addCards(GameDeck.getCard(false));
		});

		roundWeight = 0;

		tickets = new Stack<Ticket>();
		Scanner sc = new Scanner(new File("game_files//tickets.in"));

		while (sc.hasNextLine())
			tickets.add(new Ticket(sc.nextLine()));
		Collections.shuffle(tickets);

		visibleCards = GameDeck.getVisibleCards();

		graph = new Graph();
		sc = new Scanner(new File("game_files\\cities\\graph.in"));
		while (sc.hasNextLine())
		{
			int num = sc.nextInt();
			sc.nextLine();
			for (int i = 0; i < num; i++)
			{
				String[] input = sc.nextLine().split(" ");
				graph.add(input[0], new Rail(input[0], input[1], Integer.parseInt(input[2]),
						Boolean.parseBoolean(input[3]), (input.length == 5) ? input[4] : input[4] + ";" + input[5]));
			}
		}
		sc.close();
	}

	public void setView(View observe)
	{
		observer = observe;
		observer.observe(new ViewEvent(2, this, players, GameDeck, graph, visibleCards, tickets, roundWeight));
		checkVis();
	}

	public void onPlayerEvent(PlayerEvent e)
	{
		//System.out.println(e.getID());
		if ((roundWeight + e.getWeight()) > 2)
			return;

		int eventID = e.getID();
		Player currentPlayer = players.peek();
		
		if (eventID == -1)
		{
			nextRound();
			return;
		} else if (eventID <= 4 && eventID >= 0)
		{
			int index = eventID;
			String card = visibleCards[index];
			if (card == "")
				return;
			if (card == "Wild")
			{
				if (roundWeight > 0)
				{
					this.onPlayerEvent(e.reEvent());
					return;
				}
				roundWeight++;
			}
			visibleCards[index] = GameDeck.getCard(false);
			checkVis();
			if (currentPlayer.addCards(card) == null)
				return;
		} else if (eventID == 5)
		{
			if (currentPlayer.addCards(GameDeck.getCard(false)) == null)
				return;
		} else if (eventID == 6)
		{
			getCurrentPlayer().addTicket(tickets.pop());
		} else if (eventID == 7)
		{
			Stack<Ticket> temp = new Stack<Ticket>();
			temp.add(tickets.pop());
			temp.addAll(tickets);
			tickets = temp;
			/*
			 * eventID is rail number * 10 + 8 if number ends in 8, is a single rail or the
			 * first rail of the double rail. If it is 9, then it is the second rail of a
			 * double rail
			 */
		} else if (eventID <= 10 * (graph.indexList().size() - 1) + 9 && eventID >= 8
				&& (eventID % 10 == 8 || eventID % 10 == 9))
		{
			//System.out.println(eventID);
			Rail rail = graph.getRail((eventID - 8) / 10);
			Rail inverse = graph.getInverse(rail);
			String origColor = rail.getColor();
			int railNum = 0;

			if (eventID % 10 == 8)
			{
				railNum = 0;
				//System.out.println(rail);
				if(rail.isDouble() && rail.getOwnerName(1) != null && rail.getOwnerName(1).equals(currentPlayer.getName())) {
					//System.out.println("oops 1");
					return;
				}
				rail.setColor(rail.getColor().split(";")[0]);
			} else if (eventID % 10 == 9)
			{
				railNum = 1;
				if(rail.isDouble() && rail.getOwnerName(0) != null && rail.getOwnerName(0).equals(currentPlayer.getName())) {
					//System.out.println("oops 2");
					return;
				}
				//System.out.println(rail.getColor());
				rail.setColor(rail.getColor().split(";")[1]);
			} else
				throw new IllegalArgumentException("invalid GameEvent ID number");
			//System.out.println(rail.toString());
			if (rail.getColor().equals("Gray"))
			{
				//System.out.println("gray rail " + rail);
				String color = observer.color();
				//System.out.println("Color:" + color);
				if (color == null || color.equals(""))
				{
					rail.setColor(origColor);
					return;
				}
				rail.setColor(color);
			}
			//System.out.println("Rail: "+ rail + " OrigColor: "+ origColor);
			ArrayList<String> usedCards = currentPlayer.useCards(rail);

			if (usedCards == null)
			{
				//System.out.println("not enough cards");
				if (origColor.split(";")[railNum].equals("Gray"))
					if (railNum == 0)
					{
						// System.out.println(origColor);
						rail.setColor(rail.getColor() + ";" + origColor.split(";")[railNum]);
					}else if (railNum == 1)
						rail.setColor(origColor.split(";")[1] + ";" + rail.getColor());
				rail.setColor(origColor);
				return;
			}
			String col = origColor.split(";")[railNum];

			if ((col.equals(rail.getColor()) || col.equals("Gray")) && rail.getOwnerName(railNum) == (null))
				rail.setOwner(currentPlayer.getName(), railNum);
			else
			{
				rail.setColor(origColor);
				return;
			}
			rail.setColor(origColor);
			inverse.setColor(rail.getColor());
			inverse.setOwner(rail.getOwnerName(0), 0);
			inverse.setOwner(rail.getOwnerName(railNum), railNum);

			usedCards.forEach(train -> GameDeck.addDiscardedCard(train));
			if(GameDeck.getDeck().isEmpty()) GameDeck.refillDeck();
			checkVis();
			currentPlayer.addRail(rail);

			currentPlayer.addPoints(pointValues[rail.getLength() - 1]);
			// System.out.println("Rail: " + rail + " OrigColor: " + origColor);
		} else if (eventID % 10 == 6 || eventID % 10 == 7)
		{
			int num = eventID;
			while (num > 1)
			{
				if (num % 10 == 6)
				{
					currentPlayer.addTicket(tickets.pop());
				} else
				{
					Stack<Ticket> temp = new Stack<Ticket>();
					temp.add(tickets.pop());
					temp.addAll(tickets);
					tickets = temp;
				}
				num /= 10;
			}
		} else
		{
			// System.out.println(eventID);
			throw new IllegalArgumentException("invalid PlayerEvent ID number");
		}
		
		if (currentPlayer.isFinalTurn()) {
			//System.out.println(currentPlayer);
			onGameEvent(new GameEvent(3, currentPlayer));
		}
		
		roundWeight += e.getWeight();
		if (roundWeight == 2)
		{
			nextRound();
		}
		else
			checkVis();
		observer.observe(new ViewEvent(3, this, players, GameDeck, graph, visibleCards, tickets, roundWeight));
	}

	private void checkVis()
	{
		int count = 0;
		for (int i = 0; i < visibleCards.length; i++)
		{
			String card = visibleCards[i];
			if (card.equals("Wild"))
				count++;
		}
		int nullCount = 0;
		for (int i = 0; i < visibleCards.length; i++)
		{
			String card = visibleCards[i];
			if (card.equals(""))
			{
				nullCount++;
				visibleCards[i] = GameDeck.getCard(count >= 2);
				if (visibleCards[i].equals("Wild"))
					count++;
			}
		}
		if (nullCount + count == 5 && roundWeight == 1)
		{
			// System.out.println("uh oh");
			onPlayerEvent(new PlayerEvent(-1));
		}
		if (count >= 3)
			onGameEvent(new GameEvent(2, this));
	}

	public void onGameEvent(GameEvent e)
	{
		int eventID = e.getID();

		if (eventID == 0)
		{
			getCurrentPlayer().finalTurn();
			//System.out.println(getCurrentPlayer());
			//onPlayerEvent(new PlayerEvent(-1));
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
				visibleCards[i] = GameDeck.getCard(true);
			}
			checkVis();
		} else if (eventID == 3)
		{
			endGame();
			//return;
			// else System.out.println(getCurrentPlayer());
			// System.out.println(endGame());
		} else
			throw new IllegalArgumentException("invalid PlayerEvent ID number");
		
		observer.observe(new ViewEvent(3, this, players, GameDeck, graph, visibleCards, tickets, roundWeight));
	}

	public void nextRound()
	{
		roundWeight = 0;
		players.offer(players.poll());
		observer.observe(new ViewEvent(0, this, players, GameDeck, graph, visibleCards, tickets, roundWeight));
	}

	public String[] getVisCards()
	{
		return visibleCards;
	}

	public void endGame()
	{
		//onPlayerEvent(new PlayerEvent(-1));

		for (int i = 0; i < visibleCards.length; i++)
			visibleCards[i] = "";

		int mostTickets = Integer.MIN_VALUE;
		int longestPath = Integer.MIN_VALUE;
		int[] paths = new int[players.size()];
		int[] ticketNum = new int[players.size()];
		int index = 0;

		for (Player P : players)
		{
			int num = P.countTickets();
			int path = P.setPath();
			//System.out.println(P.getName()+" , "+path);

			paths[index] = path;
			ticketNum[index] = num;

			if (mostTickets < num)
				mostTickets = num;

			if (longestPath < path)
				longestPath = path;

			index++;
		}
		index = 0;
		for (Player P : players)
		{
			if (P.getComp() == mostTickets)
			{
				P.addPoints(10);
				P.setWins(true, 2);
			}
			if (paths[index] == longestPath)
			{
				P.addPoints(15);
				P.setWins(true, 1);
				List<Integer>pathList = new ArrayList<Integer>();
				for(Rail r: P.getPath()) {
					int railIndex = graph.indexList().indexOf(r);
					if(railIndex == -1) railIndex = graph.indexList().indexOf(graph.getInverse(r));
//					railIndex*=10;
//					if(r.getOwnerName(0) != null && r.getOwnerName(0).equals(P.getName())) railIndex += 8;
//					else if(r.isDouble() && r.getOwnerName(1) != null && r.getOwnerName(1).equals(P.getName()))railIndex += 9;
//					else System.out.println(r+" "+r.getOwnerName(0)+r.getOwnerName(1));
					pathList.add(railIndex);
				}
				//System.out.println("LIST: "+pathList);
				observer.drawPath(pathList);
			}
			index++;
		}

		int mostPoints = Integer.MIN_VALUE;
		for (Player p : players)
			if (p.points() > mostPoints)
				mostPoints = p.points();

		for (Player p : players)
			if (p.points() == mostPoints)
				p.setWins(true, 0);

		observer.observe(new ViewEvent(1, this, players, GameDeck, graph, visibleCards, tickets, roundWeight));
	}

	public Player getCurrentPlayer()
	{
		return players.peek();
	}

}