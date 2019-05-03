package graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
	public Float mouseLoc;

	public GraphicsTicketSelections(ArrayList<Ticket> selection, int num)
	{
		this.selection = new ArrayList<GraphicsTicket>();
		this.num = num;
		int moving;
		moving = 1920/num/2;
		for (int i = 0; i < selection.size(); i++)
		{
			this.selection.add(new GraphicsTicket(new Float(moving, 825), selection.get(i).getPointCount(),
					selection.get(i).getCities()));
			moving += 1920/num/1.2;
		}
		valid = false;
	}
	
	public void setLoc(Float loc) {
		mouseLoc = loc;
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
//select
		draw = true;
		for (int i = 0; i < selection.size(); i++)
//			System.out.println(selection.get(i).contains(cord));
			if (selection.get(i).contains(cord) != null)
				flip[i] = !flip[i];
//cancel
		if (cord.x >= 1475 && cord.x < 1675 && cord.y >= 500 && cord.y <= 600) {
			draw = false;
		}
//done		
		if (valid && cord.x > 1700 && cord.x <= 1900 && cord.y >= 500 && cord.y <= 600)
		{
			//1700, 500, 200, 100
			draw = false;
			idCat = "";
			for (int i = num - 1; i >= 0; i--)
				if (flip[i])
					idCat += "" + PlayerEvent.PLAYER_DRAW_TICKET;
				else
					idCat += "" + PlayerEvent.PLAYER_DISCARD_TICKET;
			Arrays.fill(flip, true);
			return new PlayerEvent(Integer.parseInt(idCat));
		}
//		System.out.println(draw);
		return null;
	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(5, 793, 1904, 253);
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

		Color cancelC = Color.BLACK;
		Color doneC = Color.BLACK;
		
		if (mouseLoc != null && mouseLoc.x >= 1475 && mouseLoc.x < 1675 && mouseLoc.y >= 500 && mouseLoc.y <= 600) {
			cancelC = Color.YELLOW;
		}
		if (mouseLoc != null && mouseLoc.x > 1700 && mouseLoc.x <= 1900 && mouseLoc.y >= 500 && mouseLoc.y <= 600) {
			doneC = Color.YELLOW;
		}
		
		drawCancel(g,cancelC);
		drawDone(g,doneC);
	}
	
	private void drawCancel(Graphics2D g, Color color) {
		g.setColor(new Color(244, 158, 66));
		g.fillRect(1475, 500, 200, 100);
		g.setColor(color);
		g.setStroke(new BasicStroke(10));
		g.drawRect(1475, 500, 200, 100);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString("CANCEL", 1510, 555);
	}
	
	private void drawDone(Graphics2D g, Color color) {
		g.setColor(new Color(244, 158, 66));
		g.fillRect(1700, 500, 200, 100);
		g.setColor(color);
		g.setStroke(new BasicStroke(10));
		g.drawRect(1700, 500, 200, 100);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString("DONE", 1750, 555);
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
