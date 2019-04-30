package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import core.PlayerEvent;
import core.graph.Graph;

public class GraphicsGraph extends Graphics
{
	private GraphicsCity[] cities;
	private GraphicsRail[] rails;
//	private static BufferedImage map;

//	static
//	{
//		try
//		{
////			map = ImageIO.read(new File("game_files\\cards\\ttr_board.jpg"));
//		}
//		catch(IOException e)
//		{
//		
//		}
//	}

	public GraphicsGraph() throws FileNotFoundException
	{
		@SuppressWarnings("resource")
		Scanner in = new Scanner(new File("game_files\\cities\\map.ttr"));
		cities = new GraphicsCity[in.nextInt()];
		for (int i = 0; i < cities.length; i++)
			cities[in.nextInt()] = new GraphicsCity(in.next().replaceAll("_", " "),
					new Point2D.Float(in.nextInt(), in.nextInt()));
		rails = new GraphicsRail[in.nextInt()];
//		System.out.println(rails.length);
		for (int i = 0; i < rails.length; i++)
		{
			rails[in.nextInt() - 1] = new GraphicsRail(in.nextInt(), in.nextInt(), in.nextInt(), in.nextBoolean());
			in.nextLine();

			for (int j = 0 + ((rails[i].getDoubles()) ? 0 : 1); j < 2; j++)
			{
				rails[i].setColor(in.next());
				GraphicsCity cityA = cities[rails[i].getCityA()];
				GraphicsCity cityB = cities[rails[i].getCityB()];
				rails[i].setCords(cityA.getCord().getX(), cityA.getCord().getY(), cityB.getCord().getX(),
						cityB.getCord().getY());
			}
//			System.out.println(rails[i]);

		}
	}

	public void draw(Graphics2D g)
	{
//		g.drawImage(map, 0, 0, 1200,805, null);
		g.setStroke(new BasicStroke(3));
		for (int i = 0; i < rails.length; i++)
			rails[i].draw(g);
		// System.out.println(i);
		for (int i = 0; i < cities.length; i++)
			cities[i].draw(g);
	}

	@Override
	public void update(Object e)
	{
		// take all claimed rails in graph and tell graphic rails that they r claimed
		Graph update = (Graph) e;
		for (int i = 0; i < rails.length; i++)
			for (int j = 0; j < ((update.getRail(i).isDouble()) ? 2 : 1); j++)
			{
				if(update.getRail(i).getOwnerName(j) == null)
					continue;
				switch (update.getRail(i).getOwnerName(j))
				{
				case ("Smashboy"):
					rails[i].setOwner(Color.yellow);
					break;
				case ("Rail island Z"):
					rails[i].setOwner(Color.green);
					break;
				case ("Teewee"):
					rails[i].setOwner(new Color(142, 68, 173));
					break;
				case ("Cleveland Z"):
					rails[i].setOwner(Color.red);
					break;
				default:
					break;
				}
			}
	}

	@Override
	public PlayerEvent contains(Point2D.Float cord)
	{
		int count =0;
		for(GraphicsRail rail : rails) {
			PlayerEvent r = rail.contains(cord);
			//System.out.println(r);
			if(r != null) return new PlayerEvent(count*10+8);
			count ++;
		}
		return null;
	}
}
