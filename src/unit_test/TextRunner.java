package unit_test;

import java.io.FileNotFoundException;
import java.util.Scanner;

import core.Player;
import core.PlayerEvent;
import core.TicketToRide;

public class TextRunner
{
	public static void main(String[] args) throws FileNotFoundException
	{
		TicketToRide game = new TicketToRide();
		Scanner sc = new Scanner(System.in);
		do
		{
			Player pl = game.getCurrentPlayer();
			System.out.println("Current Player: " + pl.getName());
			System.out.println("5 cards: " + game.getVisCards());
			System.out.print("PlayerEvent: ");

			int choice = sc.nextInt();
			sc.nextLine();
			game.onPlayerEvent(new PlayerEvent(pl, choice, 1));

			printPlayerHand(pl);
		} while (sc.hasNext());
	}

	private static void printPlayerHand(Player pl)
	{
		System.out.println("Cards: " + pl.getHand().toString() + "\nTickets: " + pl.getTickets().toString()
				+ "\nPoints: " + pl.getPoints() + "\nCities: " + pl.getCities());
	}
}
