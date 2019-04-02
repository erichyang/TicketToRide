package unit_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import core.Player;
import core.Ticket;

public class PlayerRunner
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner input = new Scanner(System.in);
		Player player = new Player("Gamer", new ArrayList<String>(), new ArrayList<Ticket>());
		while (input.hasNext())
		{
			System.out.println("1 - Ticket, 2 - Add Train, 3 - Add Points");
			int choice = input.nextInt();
			switch (choice)
			{
			case 1:
				System.out.print("Input the ticket line: ");
				player.addTicket(new Ticket(input.nextLine()));
				break;
			case 2:
				System.out.print("Input the train card: ");
				player.addCards(input.nextLine());
				break;
			case 3:
				System.out.print("Input the points to add: ");
				player.addPoints(input.nextInt());
				break;
			}
			printPlayerHand(player);
		}
	}
	
	private static void printPlayerHand(Player pl)
	{
		System.out.println("Cards: " + pl.getHand().toString() + "\nTickets: " + pl.getTickets().toString() + "\nPoints: ");
	}

}
