package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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
		players.add(new Player("Rhail Island Z", new ArrayList<String>(), new ArrayList<Ticket>()));
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
		// System.out.println(graph.indexList());
		sc.close();
	}

	public void setView(View observe)
	{
		observer = observe;
		observer.observe(new ViewEvent(2, this, players, GameDeck, graph, visibleCards, tickets,roundWeight));
		checkVis();
	}

	public void onPlayerEvent(PlayerEvent e)
	{
		// System.out.println("ID "+e.getID());
		if ((roundWeight + e.getWeight()) > 2)
		{
			//System.out.println("invalid action");
			return;
		}

		int eventID = e.getID();
		Player currentPlayer = players.peek();

		if (eventID == -1)
		{
			nextRound();
			//System.out.println("Forced Next Turn");
			return;
		} else if (eventID <= 4 && eventID >= 0)
		{
			// System.out.println("RoundWeight: "+roundWeight);
			int index = eventID;
			String card = visibleCards[index];
//			System.out.println("Card:"+ card);			
			if (card == "Wild")
			{
				if (roundWeight > 0)
				{
					this.onPlayerEvent(e.reEvent());
					return;
				}
				roundWeight++;
			}

			visibleCards[index] = GameDeck.getCard();
			checkVis();
			if (currentPlayer.addCards(card) == null)
			{
				return;
			}
		} else if (eventID == 5)
		{
			if (currentPlayer.addCards(GameDeck.getCard()) == null)
			{
				return;
			}
		} else if (eventID == 6)
		{
			getCurrentPlayer().addTicket(tickets.pop());
		} else if (eventID == 7)
		{
			Stack<Ticket> temp = new Stack<Ticket>();
			temp.add(tickets.pop());
			temp.addAll(tickets);
			tickets = temp;
			// eventID is rail number * 10 + 8 if number ends in 9, is a single rail or the
			// first rail of the double rail.
			// If it is 0, then it is the second rail of a double rail
		} else if (eventID <= 10 * (graph.indexList().size() - 1) + 8 && eventID >= 8
				&& (eventID % 10 == 8 || eventID % 10 == 9))
		{
			Player current = getCurrentPlayer();
			Rail rail = graph.getRail((eventID - 8) / 10);
			String origColor = rail.getColor();

			if (eventID % 10 == 8)
			{
				rail.setColor(rail.getColor().split(";")[0]);
			} else if (eventID % 10 == 9)
			{
				// System.out.println(rail.getColor());
				rail.setColor(rail.getColor().split(";")[1]);
			} else
				throw new IllegalArgumentException("invalid GameEvent ID number");

			// System.out.println(rail.toString());

			if (rail.getColor().equals("Gray"))
			{
				//System.out.println("gray rail " + rail);
				String color = observer.color(rail.getLength());
				rail.setColor(color);
			}
			// System.out.println("Rail: "+ rail + " OrigColor: "+ origColor);
			ArrayList<String> usedCards = current.useCards(rail);

			if (usedCards == null)
			{
				//System.out.println("not enough cards");
				rail.setColor(origColor);
				return;
			}

			if (origColor.split(";")[0].equals(rail.getColor()) && rail.getOwnerName(0) == (null))
			{
				rail.setOwner(getCurrentPlayer().getName(), 0);
			} else if (rail.isDouble() && origColor.split(";")[1].equals(rail.getColor())
					&& rail.getOwnerName(1) == (null))
			{
				rail.setOwner(getCurrentPlayer().getName(), 1);
			} else
			{
				//System.out.println("Already Owned");
				rail.setColor(origColor);
				return;
			}
			rail.setColor(origColor);

			usedCards.forEach(train -> GameDeck.addDiscardedCard(train));
			checkVis();
			current.addRail(rail);
			final int[] pointValues =
			{1, 2, 4, 7, 10, 15 };
			
			current.addPoints(pointValues[rail.getLength()-1]);

			//System.out.println("Rail: " + rail + " OrigColor: " + origColor);

		} else if (eventID % 10 == 6 || eventID % 10 == 7)
		{
			int num = eventID;
			while (num > 1)
			{
				if (num % 10 == 6)
				{
					getCurrentPlayer().addTicket(tickets.pop());
				} else
				{
					Stack<Ticket> temp = new Stack<Ticket>();
					temp.add(tickets.pop());
					temp.addAll(tickets);
					tickets = temp;
				}
				num = num / 10;
			}
		} else
			throw new IllegalArgumentException("invalid PlayerEvent ID number");

		roundWeight += e.getWeight();
		// System.out.println(getCurrentPlayer().getPoints());
		if (roundWeight == 2)
			nextRound();
		observer.observe(new ViewEvent(0, this, players, GameDeck, graph, visibleCards, tickets,roundWeight));
	}

	private void checkVis()
	{
		int count = 0;
//		for (String card : visibleCards)
//			if (card.equals("Wild"))
//				count++;
		for(int i=0; i< visibleCards.length; i++) {
			String card = visibleCards[i];
			if (card.equals("Wild"))
				count++;
			else if(card.equals(""))
				visibleCards[i] = GameDeck.getDiscardCard();
		}
		if (count >= 3)
		{
			if (GameDeck.disWildCheck())
			{
				this.onGameEvent(new GameEvent(3, this));
			} else
				this.onGameEvent(new GameEvent(2, this));
		}
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
			checkVis();
		} else if (eventID == 3)
		{
//			endGame();
			System.out.println(endGame());
		} else
			throw new IllegalArgumentException("invalid PlayerEvent ID number");

		observer.observe(new ViewEvent(3, this, players, GameDeck, graph, visibleCards, tickets,roundWeight));
	}

	public void nextRound()
	{
		roundWeight = 0;
		players.offer(players.poll());
		observer.observe(new ViewEvent(0, this, players, GameDeck, graph, visibleCards, tickets,roundWeight));
	}

	public Graph getGraph()
	{
		return graph;
	}

	public String[] getVisCards()
	{
		return visibleCards;
	}

	public Player endGame()
	{
		onPlayerEvent(new PlayerEvent(-1));
		for (int i = 0; i < visibleCards.length; i++)
		{
			visibleCards[i] = null;
		}

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
		observer.observe(new ViewEvent(1, this, players, GameDeck, graph, visibleCards, tickets,roundWeight));
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