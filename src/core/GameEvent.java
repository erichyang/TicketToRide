package core;

import java.util.EventObject;

public class GameEvent extends EventObject{
	public static final int NO_TRAINS = 0;
	public static final int OUT_OF_TRAIN_CARDS = 1;
	public static final int THREE_WILDS = 2;
	
	private int eventID;
	
	public GameEvent(int ID,Object source) {
		super(source);
		eventID = ID;
	}
	
	public int getID(){
		return eventID;
	}
}//lemme merge plss
