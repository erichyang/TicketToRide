package graphics;

import java.util.List;

public interface View
{
	public abstract void observe(ViewEvent e);

	public abstract String color();
	
	public abstract void drawPath(List<Integer> list);
}
