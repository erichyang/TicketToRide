package unit_test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import core.Deck;
import core.Player;
import core.PlayerEvent;
import core.TicketToRide;

public class TextRunner
{
	public static void main(String[] args) throws FileNotFoundException
	{
		TicketToRide game = new TicketToRide();
		Deck decks = game.getDeck();
		Scanner sc = new Scanner(System.in);
		do
		{

			Player pl = game.getCurrentPlayer();

			System.out.println("Current Player: " + pl.getName());
			printPlayerHand(pl);
			System.out.println("5 cards: " + Arrays.toString(game.getVisCards()));
			System.out.println("Deck: " + Arrays.toString((decks.getDeck().toArray())));
			System.out.println("Discard: " + Arrays.toString((decks.getDiscard().toArray())));
			System.out.print("PlayerEvent: ");

			int choice = sc.nextInt();
			sc.nextLine();
			game.onPlayerEvent(new PlayerEvent(pl, choice, 1));

			printPlayerHand(pl);
		} while (sc.next() != "finish");
	}

	private static void printPlayerHand(Player pl)
	{
		System.out.println(
				"Cards: " + pl.getHand().toString() /*
													 * + "\nTickets: " + pl.getTickets().toString() + "\nPoints: " +
													 * pl.getPoints() + "\nCities: " + pl.getCities()
													 */);
	}

}
