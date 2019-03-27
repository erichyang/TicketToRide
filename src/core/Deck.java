package core;

import java.util.Collections;
import java.util.Stack;

public class Deck
{
	private static Stack<String> discard;
	private static Stack<String> deck;
	
	public Deck()
	{
		deck = new Stack<String>();
	}
	
	public void addDiscardedCard(String card)
	{
		discard.add(card);
	}
	public String getCard()
	{
		return deck.pop();
	}
	
	public String[] getVisibleCards()
	{
		String[] drawCards = new String[5];
		for( int i = 0; i < 5; i++ )
			drawCards[i] = deck.pop();
		return drawCards;
	}
	
	public static void shuffle()
	{
		Collections.shuffle(deck);
	}
	
	public static void refillDeck()
	{
		deck.addAll(discard);
		shuffle();
		discard.clear();
	}
}
