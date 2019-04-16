package graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GraphicsPlayer extends Graphics
{
	private ArrayList<GraphicsTicket> tickets;
	private ArrayList<String> hand;
	

	public GraphicsPlayer()
	{
		tickets = new ArrayList<GraphicsTicket>();
		hand = new ArrayList<String>();
		
		tickets.add(new GraphicsTicket(new Point2D.Float(850,800), 10, "Your house", "School"));
		
		
		hand.add("Red");
		hand.add("Blue");
		hand.add("Black");
	}

	public GraphicsPlayer(ViewEvent event)
	{
		update(event);
	}
	
	public void update(ViewEvent event)
	{
		int moving = 1100 - 900;
		//moving / size here
		//instantiate tickets with the moving
	}

	public void draw(Graphics2D g)
	{
		
//		g.fillRect(0,0,1000,1000);
		int moving = 0;
		for (GraphicsTicket ticket : tickets)
			ticket.draw(g);
		AffineTransform af = new AffineTransform();
		af.translate(150, 800);
		af.rotate(Math.toRadians(90));
		for (int i = 0; i < hand.size(); i++)
		{
			moving = 800 / hand.size() * i;
			af.translate(0,-moving);
			g.drawImage(color2Image(hand.get(i)), af, null);
			af.translate(0, moving);
		}
	}

	
}
