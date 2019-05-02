package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D.Float;
import java.util.Arrays;

import core.PlayerEvent;

public class GraphicsRail extends Graphics
{
	private int cityA;
	private int cityB;
	private int trains;
	private boolean doubles;
	private String[] colors;
	private double[][] cords;
	private Color[] owners;
	private Line2D[] lines;
	public boolean[] hovered;

	public GraphicsRail(int cityA, int cityB, int trains, boolean doubles)
	{
		hovered = new boolean[(doubles) ? 2:1];
		Arrays.fill(hovered, false);
		lines = new Line2D[(doubles) ? 2:1];
		this.cityA = cityA;
		this.cityB = cityB;
		this.trains = trains;
		this.doubles = doubles;
		colors = new String[(doubles) ? 2 : 1];
		cords = new double[(doubles) ? 2 : 1][4];
		Arrays.fill(cords[0], -1);
		if (cords.length == 2)
			Arrays.fill(cords[1], -1);
		owners = new Color[doubles ? 2 : 1];
//		path = new Path2D[doubles ? 2 : 1];
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

	public void setCords(double x1, double y1, double x2, double y2)
	{
		int a = (cords[0][0] == -1) ? 0 : 1;
		cords[a][0] = x1;
		cords[a][1] = y1;
		cords[a][2] = x2;
		cords[a][3] = y2;

//		cords[a][4] = x3;
//		cords[a][5] = y3;
//		path[a] = new Path2D.Double();
//		path[a].moveTo(cords[a][0], cords[a][1]);
//		path[a].quadTo(cords[a][2], cords[a][3],cords[a][4],cords[a][5]);
//		path[a].closePath();
	}

	public double[][] getCords()
	{
		return cords;
	}

	public void draw(Graphics2D g)
	{
//		g.setStroke(new BasicStroke(3));
//		g.setStroke(new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[]
//				{ 33, 3 }, 22));
//		g.drawString(""+path.length, 1000, 500);
		for (int i = 0; i < ((doubles) ? 2 : 1); i++)
		{
			double x = cords[i][0] - cords[i][2];
			double y = cords[i][1] - cords[i][3];

			double result = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			int length = (int) (result / trains);

			double alpha = Math.atan(y / x);

			double deltaX = 5 * Math.sin(alpha);
			double deltaY = 5 * Math.cos(alpha);

			g.setStroke(new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[]
			{ length - 2, 2 }, 0));
			g.setColor(string2Color(colors[i]));
			
			if(hovered[i] && owners[i] == null) {
				g.setColor(g.getColor().darker());
				//System.out.println("Triggered");
			}
			
			if (doubles)
			{
//				System.out.println(Arrays.toString(cords[i]));
//				g.setColor(Color.CYAN);
//				g.drawLine((int)(cords[i][0]),(int) (cords[i][1]), (int)(cords[i][2]),(int)(cords[i][3]));
				g.setColor(string2Color(colors[i]));
				if(hovered[i]  && owners[i] == null) g.setColor(g.getColor().darker());
				
				lines[i] = new Line2D.Float((int)(cords[i][0] + (-deltaX + 2 * deltaX * (i))),
						(int) (cords[i][1] - (-deltaY + 2 * deltaY * (i))),
						(int) (cords[i][2] - deltaX + 2 * deltaX * (i)),
						(int) (cords[i][3] - (-deltaY + 2 * deltaY * (i))));
//				System.out.println(Math.toDegrees(alpha) +"("+ deltaX+ ","+deltaY+")"+" "+"("+x+","+y+")");
			} else
			{
				lines[0] = new Line2D.Float((int) (cords[i][0]), (int) (cords[i][1]), (int) (cords[i][2]), (int) (cords[i][3]));
			}
			g.draw(lines[i]);
			//System.out.println(lin);
			if (owners[i] != null)
			{
				g.setColor(owners[i]);
				g.setStroke(new BasicStroke(3));
				if (doubles)
				{
					g.drawLine((int) (cords[i][0] + (-deltaX + 2 * deltaX * (i))),
							(int) (cords[i][1] - (-deltaY + 2 * deltaY * (i))),
							(int) (cords[i][2] - deltaX + 2 * deltaX * (i)),
							(int) (cords[i][3] - (-deltaY + 2 * deltaY * (i))));
				} else
				{
					g.drawLine((int) (cords[i][0]), (int) (cords[i][1]), (int) (cords[i][2]), (int) (cords[i][3]));
				}
			}
		}

//		g.drawOval((int)cords[0][0], (int)cords[0][1], 5,5);
//		g.drawOval((int)cords[0][2], (int)cords[0][3], 5,5);
//		g.drawOval((int)cords[0][4], (int)cords[0][5], 5,5);
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

	public int getCityA()
	{
		return cityA;
	}

	public int getCityB()
	{
		return cityB;
	}
	@Override
	public void update(Object obj)
	{
		return;
	}

	public void setOwner(Color owner, int i)
	{
		this.owners[i] = owner;
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		//System.out.println(cord+","+ lin.ptLineDist(cord));
		if(lines[0] != null && lines[0].ptSegDist(cord)<= 3) {
			//System.out.println(this);
			return new PlayerEvent(-2);
		}
		else if(doubles && lines[1] != null && lines[1].ptSegDist(cord)<= 3) {
			//System.out.println(this);
			return new PlayerEvent(-3);
		}else return null;
	}
	
	public String toString() {
		return cityA + "->"+cityB+" Owners: "+Arrays.toString(owners)
		+"Colors: "+Arrays.toString(colors);
	}
}
