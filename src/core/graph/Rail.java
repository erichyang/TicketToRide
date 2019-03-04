package core.graph;

public class Rail {
	private int length;
	private String cityA;
	private String cityB;
	private String color;
	private String ownerName;
	private boolean isDouble;
	private boolean seen;
	
	public Rail(String cityOne, String cityTwo, int weight,boolean isDoubleRail,String railColor) {
		cityA = cityOne;
		cityB = cityTwo;
		length = weight;
		isDouble = isDoubleRail;
		color = railColor;
		seen = false;
		ownerName = "";
	}
	
	public void setSeen(boolean hasSeen){
		seen = hasSeen;
	}
	
	public void setOwner(String owner){
		ownerName = owner;
	}
	
	public String toString() {
		return cityA +"-> "+cityB+", "+length+", "+isDouble+", "+color+"\n";
	}
}
