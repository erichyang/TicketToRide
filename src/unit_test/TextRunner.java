package unit_test;

import java.io.FileNotFoundException;
import java.util.Scanner;

import core.Player;
import core.TicketToRide;

public class TextRunner
{
	public static void main(String[] args) throws FileNotFoundException
	{
		TicketToRide game = new TicketToRide();
		Scanner sc = new Scanner(System.in);
		System.out.println("Current Player: " + game.getCurrentPlayer().getName());
		printPlayerHand(game.getCurrentPlayer());
	}
	
	private static void printPlayerHand(Player pl)
	{
		System.out.println("Cards: " + pl.getHand().toString() + "\nTickets: " + pl.getTickets().toString()
				+ "\nPoints: " + pl.getPoints() + "\nCities: " + pl.getCities());
	}
}
