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
		Collections.shuffle(deck);
	}

	public void setListener(GameEventListener GEL)
	{
		listen = GEL;
	}

	public void addDiscardedCard(String card)
	{
		discard.add(card);
	}
	
	public String getDiscardCard()
	{
		if(discard.isEmpty()) {
			return "";
		}else return discard.pop();
	}

	public String getCard()
	{
		if(deck.isEmpty()) return "";
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

	public void refillDeck()
	{
		deck.addAll(discard);
		Collections.shuffle(deck);
		discard.clear();
	}

	public Stack<String> getDeck()
	{
		return deck;
	}

	public Stack<String> getDiscard()
	{
		return discard;
	}
	
	public boolean disWildCheck() {
		Stack<String> clone = (Stack<String>) discard.clone();
		
		int count =0;
		while(clone.contains("Wild")) {
			clone.remove("Wild");
			count++;
		}
		//System.out.println("CLONE: "+clone);
		return (count >= 3) && (clone.size() <= 2) && deck.size() < 5;	
	}
}
