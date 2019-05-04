package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

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
	private int roundWeight;
//	private static BufferedImage leaderboard;

	private boolean color;
	private ViewEvent lastUpdate;
	private Float mouseLoc;
	
	// leader board
	private Color[] list;
	private int[] points;
	private int[] trains;
	private int[] tickets;
	private int[] trainCards;

	private boolean end;

	private GraphicsTicketSelections sel;
	private GraphicsColorSelections col;

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
		color = false;
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
		if (!end)
			player.draw(g);

		g.setStroke(new BasicStroke(15));
		// 1300, 25

		if (!end)
			for (int i = 0; i < visible.length; i++)
				g.drawImage(color2Image(visible[i]), 1255, 130 * i, 200, 125, null);
		if(!end)

		for (int i = 0; i < visible.length; i++) {
			g.drawImage(color2Image(visible[i]), 1255, 130 * i, 200, 125, null);
			if(visible[i].equals("Wild") && roundWeight >0) {
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(1255, 130 * i, 200, 125);
			}
		}
		

		g.drawImage(ticket, 1500, 650, 200, 125, null);

		// 1500 - 1900, 130
		g.setFont(new Font("Seriff", Font.BOLD, 60));
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

		if (lastUpdate.players.peek().getTickets().size() == 0)
		{
			sel = drawStartTickets();
			sel.setDraw(true);
		}
		
		g.setFont(new Font("Seriff", Font.BOLD, 16));
		g.setColor(Color.black);
		g.drawString("points", 1540, 50);
		g.drawString("trains", 1660, 50);
		g.drawString("tickets", 1745, 50);
		g.drawString("trainCards", 1825, 50);

		if (sel.getDraw()) {
			sel.setLoc(mouseLoc);
			sel.draw(g);
		}

		g.setColor(Color.black);
		
		if(col!=null && col.getDraw())
			col.draw(g);
	}

	@Override
	public void observe(ViewEvent event)
	{
		lastUpdate = event;
		update(event);
	}

	@Override
	public String color(int length)
	{
		col = new GraphicsColorSelections(lastUpdate.getSuffColors(length));	
		return col.getColor();
	}

	public boolean ended()
	{
		return end;
	}

	@Override
	public void update(Object e)
	{
		ViewEvent update = (ViewEvent) e;
		roundWeight = update.roundWeight;
		if (update.getID() == 1)
			end = true;
		graph.update(update.map);
		player.update(update.players.peek());
//		visible = update.visible;
		for (int i = 0; i < update.visible.length; i++) {
			visible[i] = update.visible[i];
		}
		visible[5] = "Back";

		Iterator<Player> iter = update.getSortedPlayer().iterator();
		for (int i = 0; i < update.getSortedPlayer().size(); i++)
		{
			Player temp = iter.next();
//			System.out.println(update.getSortedPlayer().size());
//			System.out.println(temp.getName());
			switch (temp.getName())
			{
			case ("Smashboy"):
				list[i] = (Color.yellow);
				break;
			case ("Rhail Island Z"):
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

	public boolean getDraw()
	{
		return sel != null && sel.getDraw();
	}

	public PlayerEvent contains(Float cord)
	{
		if (end)
			return null;

		if (sel.getDraw())
			return sel.contains(cord);

		if (cord.x >= 1255 && cord.x <= 1455)
		{
			if (cord.y >= 0 && cord.y <= 125)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_ONE);
			if (cord.y >= 130 && cord.y <= 255)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_TWO);
			if (cord.y >= 260 && cord.y <= 385)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_THREE);
			if (cord.y >= 390 && cord.y <= 515)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_FOUR);
			if (cord.y >= 520 && cord.y <= 645)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_FIVE);
			if (cord.y >= 650 && cord.y <= 775)
				return new PlayerEvent(PlayerEvent.PLAYER_DRAW_DECK);
		}

		PlayerEvent pE = graph.contains(cord);
		// System.out.println(pE);
		if (pE != null)
			return pE;

		if (cord.x >= 1500 && cord.x <= 1700 && cord.y >= 650 && cord.y <= 775)
		{
//			System.out.println("Tickets selection");
			@SuppressWarnings("unchecked")
			Stack<Ticket> temp1 = (Stack<Ticket>) lastUpdate.tickets.clone();
			ArrayList<Ticket> temp2 = new ArrayList<Ticket>();
			if (temp1.size() < 3)
				return null;
			for (int i = 0; i < 3; i++)
				temp2.add(temp1.pop());
			sel = new GraphicsTicketSelections(temp2, 3);
			sel.contains(cord);
		}

		player.contains(cord);

		return null;
	}

	public GraphicsTicketSelections drawStartTickets()
	{
		@SuppressWarnings("unchecked")
		Stack<Ticket> temp1 = (Stack<Ticket>) lastUpdate.tickets.clone();
		ArrayList<Ticket> temp2 = new ArrayList<Ticket>();
		for (int i = 0; i < 5; i++)
			temp2.add(temp1.pop());
		GraphicsTicketSelections select = new GraphicsTicketSelections(temp2, 5);
		select.setLoc(mouseLoc);
		return select;
	}

	public void graphSetRails()
	{
		graph.setRails(mouseLoc);
	}
	
	public void ticketCheck()
	{
		if(sel != null)sel.setLoc(mouseLoc);
	}
	
	public void setLoc(Float loc) {
		mouseLoc = loc;
	}

	public boolean containsPoint(Float point)
	{
		if (sel.getDraw())
			return false;
		return (graph.contains(point) != null);
	}
}