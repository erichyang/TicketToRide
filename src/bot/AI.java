package bot;

import java.util.ArrayList;

import core.Player;
import core.PlayerEvent;
import core.Ticket;

public class AI extends Player
{
	
	Ticket smallestTicket;
	
	public AI(String playerName, ArrayList<String> trainCards, ArrayList<Ticket> chosenTickets)
	{
		super(playerName, trainCards, chosenTickets);
	}
	
	public PlayerEvent choice()
	{
		if(smallestTicket == null)
		{
			int min = Integer.MAX_VALUE;
			for(Ticket item:ticketList)
				if(!super.contains(item) && min > item.getPointCount())
				{
					min = item.getPointCount();
					smallestTicket = item;
				}
		}
		else
		{
			
		}
		int id = Integer.MAX_VALUE;
		return new PlayerEvent(id);
	}
}
