package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class GraphicsCity extends Graphics
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
		g.setColor(new Color(244, 164, 66));
		g.fill(new Ellipse2D.Double(cord.getX()-10, cord.getY()-10,20,20));
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawOval((int)cord.getX()-11, (int)cord.getY()-11, 22, 22);
		g.setColor(new Color(129, 9, 255));
		g.setFont(new Font("Seriff",Font.BOLD,12));
		g.drawString(name, (float)cord.getX()+20, (float)cord.getY()+ 15);
	}
	
	public Point2D.Float getCord()
	{
		return cord;
	}

	@Override
	public void update(Object obj)
	{
		//nothing to update cities are final
	}
}
