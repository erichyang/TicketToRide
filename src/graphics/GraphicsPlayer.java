package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
	private int iteration;
	private String name;
	private int hover;

	public GraphicsPlayer()
	{
		name = "";
		tickets = new ArrayList<GraphicsTicket>();
		hand = new ArrayList<String>();
		iteration = 0;
		hover = -1;
	}

	public String getName()
	{
		return name;
	}

	public void draw(Graphics2D g)
	{
//		g.fillRect(0,0,1000,1000);
		int moving = 0;
//		System.out.println(tickets);
		for (int i = iteration * 5; i < iteration * 5 + 5 && i < tickets.size(); i++)
		{
//			System.out.print(i);
//			System.out.println(tickets.get(i) + " " + iteration);
			tickets.get(i).draw(g);
//			System.out.println();
			if (tickets.size() > 5)
			{
				g.setFont(new Font("Serif", Font.PLAIN, 24));
				g.setColor(new Color(129, 9, 255));
				g.fillRect(1280, 1000, 170, 50);
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(5));
				g.drawRect(1280, 1000, 170, 50);
				g.drawString("Click to Scroll", 1300, 1027);
			}
		}
		AffineTransform af = new AffineTransform();

		for (int i = 0; i < hand.size(); i++)
		{
			if (hover == i)
				af.translate(150, 750);
			else
				af.translate(150, 820);
			af.rotate(Math.toRadians(90));
			moving = 800 / hand.size() * i;
			af.translate(0, -moving);
			g.drawImage(color2Image(hand.get(i)), af, null);
			af = new AffineTransform();
		}
		g.setColor(Color.BLACK);
//		Font nameFont = new Font("Seriff",Font.BOLD,30);
//		g.setFont(nameFont);
//		g.drawString(name, 1900 - g.getFontMetrics(nameFont).stringWidth(name), 770);
	}

	@Override
	public void update(Object obj)
	{
		iteration = 0;
		tickets.clear();
		hand.clear();
		Player update = (Player) obj;
		name = update.getName();
		ArrayList<Ticket> core = update.getTickets();
		for (int i = 0; i < core.size(); i++)
		{
			tickets.add(new GraphicsTicket(new Point2D.Float(925 + i % 5 * 200, 820), core.get(i).getPointCount(),
					core.get(i).getCities()));
			tickets.get(i).update(update);
		}
		HashMap<String, Integer> pHand = update.getHand();
		pHand.keySet().forEach((key) ->
		{
			for (int i = 0; i < pHand.get(key); i++)
				hand.add(key);
		});
		hover = -1;
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		if (cord.x >= 885 && cord.y >= 800)
			next();
		return null;
	}

	private void next()
	{
		iteration++;
		if ((iteration) * 5 == tickets.size())
			iteration--;
		if (iteration * 5 > tickets.size())
			iteration = 0;
	}

	public void hovers(Float cord)
	{
		hover = -1;
		if (cord.x <= 850 && cord.y >= 820)
			for (int i = 0; i < hand.size(); i++)
				// 150, 820
				// translate on x moving
				// moving = 800/hand.size()*i
				if (cord.x >= 800 / hand.size() * (i) + 25 && cord.x <= 150 + 800 / hand.size() * (i))
					hover = i;
	}
}
