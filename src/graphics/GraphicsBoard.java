package graphics;

import java.awt.Graphics2D;
import java.util.Stack;

import javax.swing.JPanel;

public class GraphicsBoard extends JPanel implements View, Graphics
{
	private static final long serialVersionUID = 1L;
	private PlayerEventListener listener;
	private GraphicsGraph graph;
	private GraphicsPlayer player;
	private String[] visible;
	
	public GraphicsBoard(ViewEvent event)
	{
		
	}

	@Override
	public void draw(Graphics2D g)
	{
		// TODO Auto-generated method stub
		
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