package unit_test;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.GraphicsCity;
import graphics.GraphicsGraph;

public class cordsCreator extends JPanel implements MouseListener
{
	/**
	 * 
	 */

//	private Timer timer;
	private static final long serialVersionUID = -7201200387032484629L;
	private GraphicsCity[] cities;
	private static BufferedImage map;
	public JFrame window;
//	private double x;
//	private double y;

	public static void main(String[] args) throws FileNotFoundException
	{
		cordsCreator test = new cordsCreator();
		test.window = new JFrame("Graphics Graph");
		test.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.window.setSize(1920, 1080);
		test.window.setVisible(true);
		test.window.add(test);
		test.window.addMouseListener(test);
//		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		while(true)
//			test.repaint();
		test.window.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));
		test.repaint();
	}

	static
	{
		try
		{
			map = ImageIO.read(new File("game_files\\cards\\ttr_board.jpg"));
		} catch (IOException e)
		{

		}
	}

	public cordsCreator() throws FileNotFoundException
	{
		new GraphicsGraph();
		@SuppressWarnings("resource")
		Scanner in = new Scanner(new File("game_files\\cities\\map.ttr"));
		cities = new GraphicsCity[in.nextInt()];
		for (int i = 0; i < cities.length; i++)
			cities[in.nextInt()] = new GraphicsCity(in.next().replaceAll("_", " "),
					new Point2D.Float(in.nextInt(), in.nextInt()));

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		double x = MouseInfo.getPointerInfo().getLocation().getX() - window.getLocationOnScreen().x;
		double y = MouseInfo.getPointerInfo().getLocation().getY() - window.getLocationOnScreen().y;
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(map, 0, 0, 1200, 805, null);
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(new Line2D.Double(x - 5, y, x + 5, y));
		g2d.draw(new Line2D.Double(x, y - 5, x, y + 5));
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		System.out.println(arg0.getLocationOnScreen());
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

//	public void draw(Graphics2D g)
//	{
//		g.drawImage(map, 0, 0, 1200,805, null);
//		g.setStroke(new BasicStroke(3));
//		for(int i = 0; i < cities.length; i++)
//			cities[i].draw(g);
//		for(int i = 0; i < rails.length; i++)
//			rails[i].draw(g);
//	}
//	
//	private void startTimer()
//	{
//		timer = new Timer(0, new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				x = MouseInfo.getPointerInfo().getLocation().getX() - window.getLocationOnScreen().x;
//				y = MouseInfo.getPointerInfo().getLocation().getY() - window.getLocationOnScreen().y;
//				// if graphic tiles .contains(e.x,e.y) then display plus or minus
////				System.out.println(x + " " + y);
//				repaint();
//			}
//		});
//		timer.setRepeats(true);
//		// timer.setDelay(0);
//		timer.start();
//	}
}
