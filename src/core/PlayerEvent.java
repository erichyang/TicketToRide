package core;

import java.awt.AWTEvent;
import java.io.File;

public class PlayerEvent extends AWTEvent
{

	public static final int PLAYER_DRAW_ONE = 0;
	public static final int PLAYER_DRAW_TWO = 1;
	public static final int PLAYER_DRAW_THREE = 2;
	public static final int PLAYER_DRAW_FOUR = 3;
	public static final int PLAYER_DRAW_FIVE = 4;
	public static final int PLAYER_DRAW_DECK = 5;
//	public static final int PLAYER_PLACE_TRACK = 6;
	public static final int PLAYER_DRAW_TICKET = 6;
	public static final int PLAYER_DISCARD_TICKET = 7;

	// remember to add seven to the ID for rails
	private static int eventWeight;

	public PlayerEvent(Object source, int id)
	{
		super(source, id);
		setWeight(id);
	}

	public int getWeight()
	{
		return eventWeight;
	}
	
	public void setWeight(int id) {
		
		if(id >= 0 && id<6) 
			eventWeight = 1;
		else
			eventWeight = 2;
	}
	
	public PlayerEvent reEvent() {
		 PlayerEvent clone = new PlayerEvent(this.getSource(),this.getID());
		 clone.setWeight(-1);	
		 this.consume();
		 return clone;
	}

}
