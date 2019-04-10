package unit_test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.GraphicsGraph;
import graphics.GraphicsPlayer;

@SuppressWarnings("serial")
public class GraphicsPlayerTest extends JPanel implements MouseListener
{
	public GraphicsPlayer player;
	public GraphicsGraph graph;
	public static JFrame window;

	public static void main(String[] args) throws FileNotFoundException
	{
		GraphicsPlayerTest test = new GraphicsPlayerTest();
		window = new JFrame("Graphics Graph");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.add(test);
		window.addMouseListener(test);

		window.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));

		while (true)
			test.repaint();
	}

	public GraphicsPlayerTest() throws FileNotFoundException
	{
		graph = new GraphicsGraph();
		player = new GraphicsPlayer();
	}
	
	@Override
	protected void paintComponent(Graphics fakeG)
	{
		super.paintComponent(fakeG);
		Graphics2D g = (Graphics2D) fakeG;
		
		double x = MouseInfo.getPointerInfo().getLocation().getX() - window.getLocationOnScreen().x;
		double y = MouseInfo.getPointerInfo().getLocation().getY() - window.getLocationOnScreen().y;
		g.draw(new Line2D.Double(x - 5, y, x + 5, y));
		g.draw(new Line2D.Double(x, y - 5, x, y + 5));
		
		graph.draw(g);
		
		player.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		System.out.println(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}
