package graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.HashMap;

import core.Player;
import core.PlayerEvent;
import core.Ticket;

public class GraphicsPlayer extends Graphics
{
	private ArrayList<GraphicsTicket> tickets;
	private ArrayList<String> hand;

	public GraphicsPlayer()
	{
		tickets = new ArrayList<GraphicsTicket>();
		hand = new ArrayList<String>();
//		tickets.add(new GraphicsTicket(new Point2D.Float(850,800), 10, "Your house", "School"));
//		hand.add("Red");
//		hand.add("Blue");
//		hand.add("Black");
	}

	public void draw(Graphics2D g)
	{

//		g.fillRect(0,0,1000,1000);
		int moving = 0;
//		System.out.println(tickets);
		for (GraphicsTicket ticket : tickets)
			ticket.draw(g);
		AffineTransform af = new AffineTransform();
		af.translate(150, 800);
		af.rotate(Math.toRadians(90));
		for (int i = 0; i < hand.size(); i++)
		{
			moving = 800 / hand.size() * i;
			af.translate(0, -moving);
			g.drawImage(color2Image(hand.get(i)), af, null);
			af.translate(0, moving);
		}
	}

	@Override
	public void update(Object obj)
	{
		tickets.clear();
		hand.clear();
		Player update = (Player)obj;
		ArrayList<Ticket> core = update.getTickets();
		for(int i = 0; i < core.size(); i++)
			tickets.add(new GraphicsTicket(new Point2D.Float(850 + i*200, 800), core.get(i).getPointCount(),core.get(i).getCities()));
		HashMap<String,Integer>pHand = update.getHand();
		pHand.keySet().forEach((key)->{
			for(int i=0; i<pHand.get(key); i++) {
				hand.add(key);
			}
		});
	}

	@Override
	public PlayerEvent contains(Float cord) 
	{
		if(cord.x >= 850 && cord.y >= 800)
			next();
		return null;
	}

	private void next()
	{
		// TODO Auto-generated method stub
		
	}
}
