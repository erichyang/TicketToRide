package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class GraphicsTicket
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
	
	public void draw(Graphics2D g)
	{
		g.setColor(new Color(244, 158, 66));
		g.fill(new Rectangle((int)cord.x, (int)cord.y, 125, 200));
		g.setColor(Color.black);
		g.drawLine((int)cord.x+60, (int)cord.y+35, (int)cord.x+60, (int)cord.y+60);
		g.setStroke(new BasicStroke(10));
		g.draw(new Rectangle((int)cord.x, (int)cord.y, 125, 200));
		g.setFont(new Font("Serif", Font.BOLD, 15));
		g.drawString(cityA, cord.x+25, cord.y + 25);
		g.drawString(cityB, cord.x+20, cord.y + 80);
		g.drawString("" + val, cord.x+50, cord.y+175);
		g.setStroke(new BasicStroke());
	}
}
