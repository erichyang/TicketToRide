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

public class GraphicsPlayer implements Graphics
{
	private ArrayList<GraphicsTicket> tickets;
	private ArrayList<String> hand;
	private static BufferedImage pink;
	private static BufferedImage white;
	private static BufferedImage blue;
	private static BufferedImage yellow;
	private static BufferedImage orange;
	private static BufferedImage black;
	private static BufferedImage red;
	private static BufferedImage green;
	private static BufferedImage wild;
	static
	{
		try
		{
			pink = ImageIO.read(new File("game_files\\cards\\purple_train_card.jpg"));
			white = ImageIO.read(new File("game_files\\cards\\white_train_card.jpg"));
			blue = ImageIO.read(new File("game_files\\cards\\blue_train_card.jpg"));
			yellow = ImageIO.read(new File("game_files\\cards\\yellow_train_card.jpg"));
			orange = ImageIO.read(new File("game_files\\cards\\orange_train_card.jpg"));
			black = ImageIO.read(new File("game_files\\cards\\black_train_card.jpg"));
			red = ImageIO.read(new File("game_files\\cards\\red_train_card.jpg"));
			green = ImageIO.read(new File("game_files\\cards\\green_train_card.jpg"));
			wild = ImageIO.read(new File("game_files\\cards\\locomotive_train_card.png"));
		} catch (IOException e)
		{
		}
	}

	public GraphicsPlayer()
	{
		tickets = new ArrayList<GraphicsTicket>();
		hand = new ArrayList<String>();
		
		tickets.add(new GraphicsTicket(new Point2D.Float(850,800), 10, "Your house", "School"));
		
		hand.add("Red");
		hand.add("Blue");
		hand.add("Black");
	}

//	public GraphicsPlayer(ViewEvent event)
//	{
//		update(event);
//	}
//	
//	public void update(ViewEvent event)
//	{
//		//things happen here
//	}

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

	private Image color2Image(String color)
	{
		switch(color)
		{
		case("Pink"): return pink;
		case("White"): return white;
		case("Blue"): return blue;
		case("Yellow"): return yellow;
		case("Orange"): return orange;
		case("Black"): return black;
		case("Red"): return red;
		case("Green"): return green;
		case("Wild"): return wild;
		default: return null;
		}
	}
}
