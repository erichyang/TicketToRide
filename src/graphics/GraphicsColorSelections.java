package graphics;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D.Float;

import core.PlayerEvent;

public class GraphicsColorSelections extends Graphics
{
	private boolean draw;
	private ArrayList<Rectangle> list;
	private String[] colors;
	private String result;

	public GraphicsColorSelections(ArrayList<String> available)
	{
		draw = true;
		list = new ArrayList<Rectangle>();
		colors = new String[available.size()];
		for(int i=0; i< available.size(); i++) {
			colors[i] = available.get(i);
		}
		int moving;
		moving = (int) (1920/available.size()/2.25);
		for (int i = 0; i < available.size(); i++)
			list.add(new Rectangle(850+moving*(i+1),850, 100,100));
//			list.add(new Rectangle(moving*(i+1),1200, 100,100));
	}

	@Override
	public PlayerEvent contains(Float cord)
	{
		for(int i = 0; i < list.size(); i++)
			if(list.get(i).contains(cord))
				result = colors[i];
		return null;
	}

	@Override
	public void draw(Graphics2D g)
	{
		//System.out.println(list);
		g.setColor(Color.MAGENTA.darker().darker().darker());
		g.setStroke(new BasicStroke(10));
		g.fillRect((int)list.get(0).getX(), 850, (int) (list.get(list.size()-1).getX()-(int)list.get(0).getX()+(int)list.get(0).getWidth()), 100);
		for (int i =0; i < list.size(); i++)
		{
			g.setColor(string2Color(colors[i]));
			g.fill(list.get(i));
		}
		g.setColor(Color.MAGENTA.darker().darker().darker());
		g.setStroke(new BasicStroke(10));
		g.drawRect((int)list.get(0).getX(), 850, (int) (list.get(list.size()-1).getX()-(int)list.get(0).getX()+(int)list.get(0).getWidth()), 100);
	}

	@Override
	public void update(Object obj)
	{
		// not used
	}

	public boolean getDraw()
	{
		return draw;
	}

	public void setDraw(boolean draw)
	{
		this.draw = draw;
	}
	
	public String getColor()
	{
		return result;
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
}
