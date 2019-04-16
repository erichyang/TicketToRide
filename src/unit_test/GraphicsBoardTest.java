package unit_test;

import java.awt.BasicStroke;
import java.awt.Color;
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

import graphics.GraphicsBoard;

@SuppressWarnings("serial")
public class GraphicsBoardTest extends JPanel implements MouseListener
{
	public static JFrame window;
	public GraphicsBoard board;

	public static void main(String[] args) throws FileNotFoundException
	{
		GraphicsBoardTest test = new GraphicsBoardTest();
		window = new JFrame("Graphics Graph");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.add(test);
		window.addMouseListener(test);
		window.setResizable(false);
		window.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));

		while (true)
			test.repaint();
	}

	public GraphicsBoardTest() throws FileNotFoundException
	{
		board = new GraphicsBoard();
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
