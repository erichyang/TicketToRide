package unit_test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.GraphicsTicket;

@SuppressWarnings("serial")
public class GraphicsTicketTest extends JPanel
{
	public GraphicsTicket ticket;
	public static void main(String[] args) throws FileNotFoundException
	{
		GraphicsTicketTest test = new GraphicsTicketTest();
		JFrame window = new JFrame("Graphics Graph");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.add(test);
		
		test.ticket = new GraphicsTicket(new Point2D.Float(1000,500), 21, "New York", "Seattle");
		
		test.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics fakeG)
	{
		Graphics2D g = (Graphics2D)fakeG;
		
		ticket.draw(g);
	}
}
