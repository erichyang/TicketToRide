package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class GraphicsCity
{
	private String name;
	private Point2D.Float cord;
	
	public GraphicsCity(String name, Point2D.Float cord)
	{
		this.name = name;
		this.cord = cord;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.orange);
		g.fill(new Ellipse2D.Double(cord.getX(), cord.getY(),20,20));
//		g.drawString(name, (float)cord.getX()+10, (float)cord.getY()+10);
	}
}
