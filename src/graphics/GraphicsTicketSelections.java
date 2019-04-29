package graphics;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Float;

import core.PlayerEvent;
import core.Ticket;

public class GraphicsTicketSelections extends Graphics
{
	private ArrayList<GraphicsTicket> selection;
	
	public GraphicsTicketSelections(ArrayList<Ticket> selection)
	{
		int moving = selection.size()/2000;
		for(Ticket ticket : selection)
		{
			this.selection.add(new GraphicsTicket(new Float(800+moving,800), ticket.getPointCount(), ticket.getCities()));
			moving += selection.size()/2000;
		}
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		
		return null;
	}

	@Override
	public void draw(Graphics2D g)
	{
		for(GraphicsTicket ticket: selection)
			ticket.draw(g);
		g.drawRect(1200, 900, 200, 100);
	}

	@Override
	public void update(Object obj)
	{
		//not used
	}
	
	
}
