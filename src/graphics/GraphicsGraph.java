package graphics;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphicsGraph
{
	private GraphicsCity[] cities;
	private GraphicsRail[] rails;
	
	public GraphicsGraph() throws FileNotFoundException
	{
		@SuppressWarnings("resource")
		Scanner in = new Scanner(new File("game_files\\cities\\map.ttr"));
		cities = new GraphicsCity[in.nextInt()];
		for(int i = 0; i < cities.length; i++)
			cities[in.nextInt()] = new GraphicsCity(in.next().replaceAll("_", " "), new Point2D.Float(in.nextInt(),in.nextInt()));
		rails = new GraphicsRail[in.nextInt()];
		for(int i = 0; i < rails.length; i++)
		{
			rails[in.nextInt()] = new GraphicsRail(in.nextInt(),in.nextInt(),in.nextInt(), in.nextBoolean(), in.nextInt(), in.nextLine());
			
		}
	}
}
