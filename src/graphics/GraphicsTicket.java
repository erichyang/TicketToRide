package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import core.PlayerEvent;

public class GraphicsTicket extends Graphics
{
	private Point2D.Float cord;
	private int val;
	private String cityA;
	private String cityB;
	
	public GraphicsTicket(Point2D.Float cord, int val, String cityA, String cityB)
	{
		this.cord = cord;
		this.val = val;
		this.cityA = cityA;
		this.cityB = cityB;
	}
	
	public GraphicsTicket(Point2D.Float float1, int val, String cities)
	{
		this.cord = float1;
		this.val = val;
		this.cityA = cities.substring(0, cities.indexOf(","));
		this.cityB = cities.substring(cities.indexOf(","));
	}

	public void draw(Graphics2D g)
	{
		g.setColor(new Color(244, 158, 66));
		g.fill(new Rectangle((int)cord.x, (int)cord.y, 125, 200));
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(3));
		g.drawLine((int)cord.x+60, (int)cord.y+35, (int)cord.x+60, (int)cord.y+60);
		g.setStroke(new BasicStroke(10));
		g.draw(new Rectangle((int)cord.x, (int)cord.y, 125, 200));
		g.setFont(new Font("Serif", Font.BOLD, 15));
		g.drawString(cityA, cord.x+25, cord.y + 25);
		g.drawString(cityB, cord.x+20, cord.y + 80);
		g.drawString("" + val, cord.x+50, cord.y+175);
		g.setStroke(new BasicStroke(3));
	}

	@Override
	public void update(Object obj)
	{
		
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		if((this.cord.x <= cord.x && this.cord.x + 125 >= cord.x) && (this.cord.y <= cord.y && this.cord.y + 200 >= cord.y))
			return new PlayerEvent(Integer.MAX_VALUE);
		return null;
	}

	public void drawBorder(Graphics2D g)
	{
		g.setColor(Color.YELLOW);
		g.setStroke(new BasicStroke(10));
		g.draw(new Rectangle((int)cord.x, (int)cord.y, 125, 200));
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(3));
	}
}
