package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Arrays;

public class GraphicsRail
{
	private int cityA;
	private int cityB;
	private int trains;
	private boolean doubles;
	private double curvature;
	private String[] colors;
	private double[][] cords;
	private Path2D[] path;

	public GraphicsRail(int cityA, int cityB, int trains, boolean doubles, double curvature)
	{
		this.cityA = cityA;
		this.cityB = cityB;
		this.trains = trains;
		this.doubles = doubles;
		this.curvature = curvature;
		colors = new String[(doubles) ? 2 : 1];
		cords = new double[(doubles) ? 2 : 1][6];
		Arrays.fill(cords[0], -1);
		if (cords.length == 2)
			Arrays.fill(cords[1], -1);
		path = new Path2D[doubles ? 2 : 1];
	}

	public boolean getDoubles()
	{
		return doubles;
	}

	public void setColor(String color)
	{
//		System.out.println(color + " " + colors[0] + " " + colors.length);
		if (colors[0] == null)
			colors[0] = color;
		else
			colors[1] = color;
	}

	public void setCords(double x1, double y1, double x2, double y2, double x3, double y3)
	{
		int a = (cords[0][0] == -1) ? 0 : 1;
		cords[a][0] = x1;
		cords[a][1] = y1;
		cords[a][2] = x2;
		cords[a][3] = y2;
		cords[a][4] = x3;
		cords[a][5] = y3;
		path[a].moveTo(cords[a][0], cords[a][1]);
		path[a].lineTo(cords[a][2], cords[a][3]);
		path[a].lineTo(cords[a][4], cords[a][5]);
		path[a].closePath();
	}

	public double[][] getCords()
	{
		return cords;
	}

	public void draw(Graphics2D g)
	{
//		g.setStroke(new BasicStroke(3));
		for (int i = 0; i < ((doubles) ? 1 : 0); i++)
		{
			g.setColor(string2Color(colors[i]));
			g.fill(path[i]);
		}
	}

	private Color string2Color(String color)
	{
		switch (color)
		{
		case ("Gray"):
			return Color.gray;
		case ("Pink"):
			return Color.pink;
		case ("White"):
			return Color.white;
		case ("Blue"):
			return Color.blue;
		case ("Yellow"):
			return Color.yellow;
		case ("Orange"):
			return Color.orange;
		case ("Black"):
			return Color.black;
		case ("Red"):
			return Color.red;
		case ("Green"):
			return Color.green;
		default:
			return null;
		}
	}

	@Override
	public String toString()
	{
		return "[" + cityA + "," + cityB + "," + trains + "," + doubles + "," + curvature + ","
				+ Arrays.toString(colors) + Arrays.toString(cords[0]) + "]";
	}
}
