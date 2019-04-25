package unit_test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import core.Player;
import core.Ticket;
import core.graph.Rail;

public class PlayerRunner
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("1 - Ticket, 2 - Train, 3 - Points, 4 - Rails, 5 - Use Cards");
		Player player = new Player("Gamer", new ArrayList<String>(), new ArrayList<Ticket>());
		do
		{

			int choice = input.nextInt();
			input.nextLine();
			switch (choice)
			{
			case 1:
				System.out.print("Input the ticket line: ");
				String temp = input.nextLine();
				player.addTicket(new Ticket(temp));
				break;
			case 2:
				System.out.print("Input the train card: ");
				temp = input.nextLine();
				player.addCards(temp);
				break;
			case 3:
				System.out.print("Input the points to add: ");
				int add = input.nextInt();
				player.addPoints(add);
				break;
			case 4:
				System.out.print("Input the rails: ");
				String[] railData = input.nextLine().split("\\|");
				player.addRail(new Rail(railData[0], railData[1], Integer.parseInt(railData[2]),
						Boolean.parseBoolean(railData[3]), railData[4]));
				break;
			case 5:
				System.out.print("Choose cards to remove: ");
//				String[] cardData = input.nextLine().split("\\|");
//				player.useCards(cardData[0], Integer.parseInt(cardData[1]));
				break;
			}
			printPlayerHand(player);
			System.out.println("1 - Ticket, 2 - Train, 3 - Points, 4 - Rails, 5 - Use Cards");
		} while (input.hasNext());
		input.close();
	}

	private static void printPlayerHand(Player pl)
	{

		System.out.println("Cards: " + pl.getHand().toString() + "\nTickets: " + pl.getTickets().toString()
				+ "\nPoints: " + pl.getPoints() + "\nCities: " + pl.getCities());
	}

}
