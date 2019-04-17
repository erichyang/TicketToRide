package unit_test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.GraphicsCity;
import graphics.GraphicsGraph;
import graphics.GraphicsRail;

@SuppressWarnings("serial")
public class GraphicsGraphTest extends JPanel
{
	public GraphicsGraph gGraph;
	public GraphicsRail rail;
	public GraphicsCity city;
	public static void main(String[] args) throws FileNotFoundException
	{
		GraphicsGraphTest test = new GraphicsGraphTest();
		JFrame window = new JFrame("Graphics Graph");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1920, 1080);
		window.setVisible(true);
		window.add(test);
//		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		test.gGraph = new GraphicsGraph();
//		while(true)
//			test.repaint();
		test.rail = new GraphicsRail(0, 0, 2, false);
		test.rail.setCords(100, 100, 200, 200);
		test.rail.setColor("Red");
		test.city = new GraphicsCity("A", new Point2D.Float(100, 100));
		
		test.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics fakeG)
	{
//		super.paintComponent(fakeG);
		Graphics2D g = (Graphics2D)fakeG;
		
//		city.draw(g);
//		rail.draw(g);
		gGraph.draw(g);
	}
}
