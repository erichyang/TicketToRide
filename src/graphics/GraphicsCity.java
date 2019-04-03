package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class GraphicsCity
{
	private Point2D.Float cord;
	
	public GraphicsCity(String name, Point2D.Float cord)
	{
		this.cord = cord;
//		this.cord.x *= 1.25;
//		this.cord.y *= 1.5;
//		this.cord.x -= 50;
//		this.cord.y -= 50;
//		this.cord.x *= ;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.green);
		g.fill(new Ellipse2D.Double(cord.getX(), cord.getY(),20,20));
//		g.drawString(name, (float)cord.getX()+10, (float)cord.getY()+10);
	}
	
	public Point2D.Float getCord()
	{
		return cord;
	}
}
