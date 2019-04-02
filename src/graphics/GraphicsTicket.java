package graphics;

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
		g.draw(new Rectangle((int)cord.x, (int)cord.y, 50, 100));
	}
}
