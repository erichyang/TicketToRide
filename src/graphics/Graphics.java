package graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Graphics
{
	private static BufferedImage pink;
	private static BufferedImage white;
	private static BufferedImage blue;
	private static BufferedImage yellow;
	private static BufferedImage orange;
	private static BufferedImage black;
	private static BufferedImage red;
	private static BufferedImage green;
	private static BufferedImage wild;
	private static BufferedImage back;
	
	static
	{
		try
		{
			pink = ImageIO.read(new File("game_files\\cards\\purple_train_card.jpg"));
			white = ImageIO.read(new File("game_files\\cards\\white_train_card.jpg"));
			blue = ImageIO.read(new File("game_files\\cards\\blue_train_card.jpg"));
			yellow = ImageIO.read(new File("game_files\\cards\\yellow_train_card.jpg"));
			orange = ImageIO.read(new File("game_files\\cards\\orange_train_card.jpg"));
			black = ImageIO.read(new File("game_files\\cards\\black_train_card.jpg"));
			red = ImageIO.read(new File("game_files\\cards\\red_train_card.jpg"));
			green = ImageIO.read(new File("game_files\\cards\\green_train_card.jpg"));
			wild = ImageIO.read(new File("game_files\\cards\\locomotive_train_card.png"));
			back = ImageIO.read(new File("game_files\\cards\\card_back.jpg"));
		} catch (IOException e)
		{
		}
	}
	
//	public PlayerEvent contains(int x, int y);
	public abstract void draw(Graphics2D g);
	
	protected Image color2Image(String color)
	{
		switch(color)
		{
		case("Pink"): return pink;
		case("White"): return white;
		case("Blue"): return blue;
		case("Yellow"): return yellow;
		case("Orange"): return orange;
		case("Black"): return black;
		case("Red"): return red;
		case("Green"): return green;
		case("Wild"): return wild;
		default: return back;
		}
	}
}
