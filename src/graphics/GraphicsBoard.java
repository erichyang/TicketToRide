package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import core.Player;

public class GraphicsBoard extends Graphics implements View
{
//	private PlayerEventListener listener;
	private GraphicsGraph graph;
	private GraphicsPlayer player;
	private String[] visible;
	private static BufferedImage background;
	private static BufferedImage canvas;

	// leader board 
	private int points[];
	private int trains[];
	private int tickets[];
	private int trainCards[];

	static
	{
		try
		{
			background = ImageIO.read(new File("game_files\\background.jpg"));
			canvas = ImageIO.read(new File("game_files\\canvas.jpg"));
		} catch (IOException e)
		{
		}
	}

	public GraphicsBoard() throws FileNotFoundException
	{
		graph = new GraphicsGraph();
		player = new GraphicsPlayer();
		points = new int[4];
		trains = new int[4];
		tickets = new int[4];
		trainCards = new int[4];
		visible = new String[6];
		visible[5] = "Back";
	}

	@Override
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
		//1500 - 1900, 130
		g.setFont(new Font("Seriff",Font.BOLD,64));
		for(int i = 0; i < 4; i++)
		{
			g.drawString("" + points[i], 1500 + i * 50, 100);
			g.drawString("" + trains[i], 1500 + i * 50, 150);
			g.drawString("" + tickets[i], 1500 + i * 50, 200);
			g.drawString("" + trainCards[i], 1500 + i * 50, 250);
		}
	}

	@Override
	public void observe(ViewEvent event)
	{
		update(event);
	}

	@Override
	public String color()
	{
		// return the color of the double rail
		return "THIS ISNT DONE YET";
	}

	@Override
	public void update(Object e)
	{
		ViewEvent update = (ViewEvent)e;
		graph.update(update.map);
		player.update(update.players.peek());
//		visible = update.visible;
		for(int i = 0; i < update.visible.length; i++)
			visible[i] = update.visible[i];
		visible[5] = "Back";
		
		Iterator<Player> iter = update.getSortedPlayer().iterator();
		for(int i = 0; i < update.getSortedPlayer().size(); i++)
		{
			Player temp = iter.next();
			points[i] = temp.getPoints();
			trains[i] = temp.getTrains();
			tickets[i] = temp.getTickets().size();
			trainCards[i] = temp.getTrainCardsNum();
		}
	}

//	- listener:PlayerEventListener
//	- graph:GraphicsGraph
//	- player:GraphicsPlayer
//	- tickets:Stack<GraphicsTicket>
//	- visible:GraphicsString[5]
//	- deck:Stack<GraphicsString>

//	+ GraphicsBoard()
//	+ paintComponent(Graphics):void
//	+ setListener(PlayerEventListener listen):void
//	+ beginGame():void + drawTrain():void
//	+ drawTicket():void
//	+ update(GameEvent):void
}