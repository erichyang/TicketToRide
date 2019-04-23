package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

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

	public GraphicsBoard(ViewEvent event) throws FileNotFoundException
	{
		graph = new GraphicsGraph();
		player = new GraphicsPlayer();
		points = new int[4];
		trains = new int[4];
		tickets = new int[4];
		trainCards = new int[4];
		visible = new String[6];
		visible[5] = "Back";
		observe(event);
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
		player.update(update.players);
		visible = update.visible;
		
		
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