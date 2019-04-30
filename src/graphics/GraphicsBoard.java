package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import core.Player;
import core.PlayerEvent;
import core.Ticket;

public class GraphicsBoard extends Graphics implements View
{
//	private PlayerEventListener listener;
	private GraphicsGraph graph;
	private GraphicsPlayer player;
	private String[] visible;
	private static BufferedImage background;
	private static BufferedImage canvas;
	private static BufferedImage ticket;
//	private static BufferedImage leaderboard;
	
	private String color;
	private ViewEvent lastUpdate;
	
	// leader board
	private Color[] list;
	private int[] points;
	private int[] trains;
	private int[] tickets;
	private int[] trainCards;

	private boolean end;
	
	private GraphicsTicketSelections sel = new GraphicsTicketSelections(new ArrayList<Ticket>());
	
	static
	{
		try
		{
			background = ImageIO.read(new File("game_files\\background.jpg"));
			canvas = ImageIO.read(new File("game_files\\canvas.jpg"));
			ticket = ImageIO.read(new File("game_files\\cards\\ticket_card_back.jpg"));
//			leaderboard = ImageIO.read(new File("game_files\\leaderboard.jpg"));
		} catch (IOException e)
		{
		}
	}

	public GraphicsBoard() throws FileNotFoundException
	{
		graph = new GraphicsGraph();
		player = new GraphicsPlayer();
		list = new Color[4];
		points = new int[4];
		trains = new int[4];
		tickets = new int[4];
		trainCards = new int[4];
		visible = new String[6];
		visible[5] = "Back";
		color = "";
		sel = new GraphicsTicketSelections(new ArrayList<Ticket>());
		end = false;
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(background, 0, 0, 1920, 1080, null);
		g.setColor(new Color(214, 116, 25));
		g.setStroke(new BasicStroke(15));
		g.drawImage(canvas, 0, 0, 1240, 774, null);
		g.drawRect(0, 0, 1240, 780);
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(3));
		graph.draw(g);
		player.draw(g);

		g.setStroke(new BasicStroke(15));
		// 1300, 25
		for (int i = 0; i < visible.length; i++)
			g.drawImage(color2Image(visible[i]), 1255, 130 * i, 200, 125, null);
		
		g.drawImage(ticket, 1500, 650, 200, 125, null);
	
		// 1500 - 1900, 130
		g.setFont(new Font("Seriff", Font.BOLD, 64));
		g.setColor(new Color(226, 165, 83));
		g.fillRect(1455, 0, 500, 450);
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < 4; i++)
		{
			if (lastUpdate.getCurrentPlayer().equals(list[i]))
			{
				g.setStroke(new BasicStroke(10));
				g.setColor(Color.magenta);
				g.drawRect(1475, 50 + i * 100, 50, 50);
				g.setColor(list[i]);
				g.drawRect(5, 793, 1904, 253);
			}
			g.setColor(list[i]);
			g.fillRect(1475, 50 + i * 100, 50, 50);
			
			g.drawString("" + points[i], 1550, 100 + i * 100);
			g.drawString("" + trains[i], 1650, 100 + i * 100);
			g.drawString("" + tickets[i], 1750, 100 + i * 100);
			g.drawString("" + trainCards[i], 1850, 100 + i * 100);
		}
		
		g.setColor(Color.black);
	}

	@Override
	public void observe(ViewEvent event)
	{
		lastUpdate = event;
		update(event);
	}

	@Override
	public String color()
	{
		// return the color of the double rail
		return color;
	}

	@Override
	public void update(Object e)
	{
		ViewEvent update = (ViewEvent) e;
		if(update.getID()==1)
			end = true;
		graph.update(update.map);
		player.update(update.players.peek());
//		visible = update.visible;
		for (int i = 0; i < update.visible.length; i++)
			visible[i] = update.visible[i];
		visible[5] = "Back";

		Iterator<Player> iter = update.getSortedPlayer().iterator();
		for (int i = 0; i < update.getSortedPlayer().size(); i++)
		{
			Player temp = iter.next();
//			System.out.println(update.getSortedPlayer().size());
			System.out.println(temp.getName());
			switch (temp.getName())
			{
			case ("Smashboy"):
				list[i] = (Color.yellow);
				break;
			case ("Rail island Z"):
				list[i] = (Color.green);
				break;
			case ("Teewee"):
				list[i] = (new Color(142, 68, 173));
				break;
			case ("Cleveland Z"):
				list[i] = (Color.red);
				break;
			default:
				list[i] = Color.black;
			}
			points[i] = temp.getPoints();
			trains[i] = temp.getTrains();
			tickets[i] = temp.getTickets().size();
			trainCards[i] = temp.getTrainCardsNum();
		}
	}


	public PlayerEvent contains(Float cord)
	{
		if(end)
			return null;
		if(cord.x >= 1255 && cord.x <= 1455)
		{
			if(cord.y >= 0 && cord.y <= 125)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_ONE);
			if(cord.y >= 130 && cord.y <= 255)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_TWO);
			if(cord.y >= 260 && cord.y <= 385)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_THREE);
			if(cord.y >= 390 && cord.y <= 515)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_FOUR);
			if(cord.y >= 520 && cord.y <= 645)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_FIVE);
			if(cord.y >= 650 && cord.y <= 775)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_DECK);
		}
		PlayerEvent pE = graph.contains(cord);
		//System.out.println(pE);
		if(pE != null) return pE;
		
		if(cord.x >= 1500 && cord.x <= 1700 && cord.y >= 650 && cord.y <= 775)
		{
			sel = new GraphicsTicketSelections(new ArrayList<Ticket>(lastUpdate.tickets));
			sel.contains(cord);
		}
		return null;
	}
}