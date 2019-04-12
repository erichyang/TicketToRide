package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GraphicsBoard extends JPanel implements View, Graphics
{
	private static final long serialVersionUID = 1L;
	private PlayerEventListener listener;
	private GraphicsGraph graph;
	private GraphicsPlayer player;
	private String[] visible;
	private static BufferedImage background;

	static
	{
		try
		{
			background = ImageIO.read(new File("game_files\\background.jpg"));
		} catch (IOException e)
		{

		}
	}

	public GraphicsBoard(ViewEvent event)
	{

	}

	@Override
	public void draw(Graphics2D g)
	{
		g.drawImage(background, 0, 0, 1920, 1080, null);
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