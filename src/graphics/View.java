package graphics;

public interface View
{
	public abstract void observe(ViewEvent e);

	public abstract String color();
}
