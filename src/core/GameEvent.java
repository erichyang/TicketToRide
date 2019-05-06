package core;

import java.util.EventObject;

public class GameEvent extends EventObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4374121583162111039L;
	public static final int NO_TRAINS = 0;
	public static final int OUT_OF_TRAIN_CARDS = 1;
	public static final int THREE_WILDS = 2;
	public static final int END_GAME = 3;
	
	private int eventID;
	
	public GameEvent(int ID,Object source) {
		super(source);
		eventID = ID;
	}
	
	public int getID(){
		return eventID;
	}
}//lemme merge plss