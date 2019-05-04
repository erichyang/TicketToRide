package graphics;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D.Float;

import core.PlayerEvent;

public class GraphicsColorSelections extends Graphics
{
	private boolean draw;
	private ArrayList<Rectangle> list;
	private String[] colors;
	private String result;

	public GraphicsColorSelections(ArrayList<String> available)
	{
		draw = true;
		list = new ArrayList<Rectangle>();
		colors = new String[available.size()];
		for(int i=0; i< available.size(); i++) {
			colors[i] = available.get(i);
		}
		int moving;
		moving = 1920/available.size()/2;
		for (int i = 0; i < available.size(); i++)
			list.add(new Rectangle(moving*(i+1),850, 50,50));
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		for(int i = 0; i < list.size(); i++)
			if(list.get(i).contains(cord))
				result = colors[i];
		return null;
	}

	@Override
	public void draw(Graphics2D g)
	{
		for (Rectangle rect:list)
			g.draw(rect);

		g.setColor(new Color(244, 158, 66));
		g.fillRect(1500, 500, 400, 100);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(10));
		g.drawRect(1700, 500, 200, 100);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString("DONE", 1750, 555);
		g.drawRect(1500, 500, 200, 100);
		g.drawString("CANCEL", 1537, 555);
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
	
	public String getColor()
	{
		return result;
	}
}
