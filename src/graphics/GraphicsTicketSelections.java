package graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Float;

import core.PlayerEvent;
import core.Ticket;

public class GraphicsTicketSelections extends Graphics
{
	private ArrayList<GraphicsTicket> selection;
	private static boolean[] flip =
	{ true, true, true, true, true };
	private static String idCat;
	private boolean draw;
	private boolean valid;
	private int num;

	public GraphicsTicketSelections(ArrayList<Ticket> selection, int num)
	{
		this.selection = new ArrayList<GraphicsTicket>();
//		System.out.println(selection);
		this.num = num;
		int moving;
		moving = 192;
		for (int i = 0; i < selection.size(); i++)
		{
			this.selection.add(new GraphicsTicket(new Float(moving, 400), selection.get(i).getPointCount(),
					selection.get(i).getCities()));
			moving += 350;
//			System.out.println(selection.size());
		}
//		flip = new boolean[selection.size()];
		valid = false;
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		draw = true;
		for (int i = 0; i < selection.size(); i++)
//			System.out.println(selection.get(i).contains(cord));
			if (selection.get(i).contains(cord) != null)
				flip[i] = !flip[i];

		if (valid && cord.x >= 800 && cord.x <= 1000 && cord.y >= 650 && cord.y <= 750)
		{
			draw = false;
			idCat = "";
			for (int i = flip.length - 1; i >= 0; i--)
				if (flip[i])
					idCat += "" + PlayerEvent.PLAYER_DRAW_TICKET;
				else
					idCat += "" + PlayerEvent.PLAYER_DISCARD_TICKET;
//			System.out.println(idCat);
			Arrays.fill(flip, true);
			return new PlayerEvent(Integer.parseInt(idCat));
		}
		return null;
	}

	@Override
	public void draw(Graphics2D g)
	{
		for (int i = 0; i < selection.size(); i++)
		{
			GraphicsTicket ticket = selection.get(i);
			ticket.draw(g);
			if (flip[i])
				ticket.drawBorder(g);
		}
		int sum = 0;
		for (boolean val : flip)
			if (val)
				sum++;
		valid = (sum >= 3) ? true : false;
		g.fillRect(800, 650, 200, 100);
	}

	@Override
	public void update(Object obj)
	{
		// not used
	}

	public boolean getDraw()
	{
		return draw;
	}

	public void setDraw(boolean draw)
	{
		this.draw = draw;
	}
}
