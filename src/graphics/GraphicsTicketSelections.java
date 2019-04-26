package graphics;

import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import core.Ticket;

public class GraphicsTicketSelections
{
	private ArrayList<GraphicsTicket> selection;
	
	public GraphicsTicketSelections(ArrayList<Ticket> selection)
	{
		int moving = selection.size()/2000;
		for(Ticket ticket : selection)
		{
			this.selection.add(new GraphicsTicket(new Point2D.Float(800+=50,800), ticket.getPointCount(), ticket.getCities()));
		}
	}
}
