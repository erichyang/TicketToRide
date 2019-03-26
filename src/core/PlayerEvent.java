package core;

import java.awt.AWTEvent;
import java.io.File;

public class PlayerEvent extends AWTEvent{
	
	public static final int PLAYER_DRAW_ONE =0;
	public static final int PLAYER_DRAW_TWO =1;
	public static final int PLAYER_DRAW_THREE =2;
	public static final int PLAYER_DRAW_FOUR =3;
	public static final int PLAYER_DRAW_FIVE =4;
	public static final int PLAYER_DRAW_DECK =5;
//	public static final int PLAYER_PLACE_TRACK = 6;
	public static final int PLAYER_DRAW_TICKET = 6;
	public static final int PLAYER_DISCARD_TICKET =7;
	
	//remember to add seven to the ID for rails
	private static int eventWeight;
	
	public PlayerEvent(Object source, int id,int weight) {
		super(source, id);
		eventWeight = weight;
	}
	
	public int getWeight() {
		return eventWeight;
	}
	
}
