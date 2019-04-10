package core;

import java.util.Collections;
import java.util.Stack;

public class Deck
{
	private static Stack<String> discard;
	private static Stack<String> deck;
	private GameEventListener listen;

	public Deck()
	{
		deck = new Stack<String>();
		for (int i = 0; i < 11; i++)
		{
			deck.push("Pink");
			deck.push("White");
			deck.push("Blue");
			deck.push("Yellow");
			deck.push("Orange");
			deck.push("Black");
			deck.push("Red");
			deck.push("Green");
			deck.push("Wild");
		}
		deck.push("Wild");
		deck.push("Wild");
		discard = new Stack<String>();
		shuffle();
	}

	public void setListener(GameEventListener GEL)
	{
		listen = GEL;
	}

	public void addDiscardedCard(String card)
	{
		discard.add(card);
	}

	public String getCard()
	{
		String card = deck.pop();
		if (deck.isEmpty())
			listen.onGameEvent(new GameEvent(1, this));
		return card;
	}

	public String[] getVisibleCards()
	{
		String[] drawCards = new String[5];
		for (int i = 0; i < 5; i++)
			drawCards[i] = deck.pop();
		return drawCards;
	}

	public void shuffle()
	{
		Collections.shuffle(deck);
	}

	public void refillDeck()
	{
		deck.addAll(discard);
		shuffle();
		discard.clear();
	}
}
