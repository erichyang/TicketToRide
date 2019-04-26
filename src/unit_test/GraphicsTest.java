package unit_test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.PlayerEvent;
import core.TicketToRide;
import graphics.GraphicsBoard;

@SuppressWarnings("serial")
public class GraphicsTest extends JPanel
{
	public static JFrame window;
	public static GraphicsBoard board;

	public static void main(String[] args) throws FileNotFoundException
	{
		GraphicsTest test = new GraphicsTest();
		window = new JFrame("Graphics Graph");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.add(test);
		window.setResizable(false);
		window.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));
		while (true)
			test.repaint();

	}

	public GraphicsTest() throws FileNotFoundException
	{
		board = new GraphicsBoard();
		window.addMouseListener(board);
		int[] pEvents = {1,2,3,1,2,3,1,2,3,158,159};
		TicketToRide ttr = new TicketToRide();
		ttr.setView(board);
		
		for(int i: pEvents)
			ttr.onPlayerEvent(new PlayerEvent(i));
	}
	
	@Override
	protected void paintComponent(Graphics fakeG)
	{
		super.paintComponent(fakeG);
		Graphics2D g = (Graphics2D) fakeG;

		board.draw(g);
		double x = MouseInfo.getPointerInfo().getLocation().getX() - window.getLocationOnScreen().x;
		double y = MouseInfo.getPointerInfo().getLocation().getY() - window.getLocationOnScreen().y;
		g.setStroke(new BasicStroke(4));
		g.setColor(new Color(129, 9, 255));
		g.draw(new Line2D.Double(x - 5, y, x + 5, y));
		g.draw(new Line2D.Double(x, y - 5, x, y + 5));
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
	}
}
