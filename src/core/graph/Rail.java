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
	
	public Rail inverse() {
		return new Rail(cityB, cityA, length,isDouble,color);
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCityA() {
		return cityA;
	}

	public void setCityA(String cityA) {
		this.cityA = cityA;
	}

	public String getCityB() {
		return cityB;
	}

	public void setCityB(String cityB) {
		this.cityB = cityB;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public boolean isDouble() {
		return isDouble;
	}

	public void setDouble(boolean isDouble) {
		this.isDouble = isDouble;
	}

	public boolean isSeen() {
		return seen;
	}
	
	public boolean isInverse(Rail otherRail) {
		return this.getCityA().equals(otherRail.getCityB()) && 
				this.getCityB().equals(otherRail.getCityA());
	}
	
	public boolean equals(Rail otherRail) {
		return this.getCityA().equals(otherRail.getCityA()) && 
				this.getCityB().equals(otherRail.getCityB()) &&
				this.getLength() == otherRail.getLength() &&
				this.getColor().equals(otherRail.getColor());
	}

	public String toString() {
		return cityA +"-> "+cityB+", "+length+", "+isDouble+", "+color+"\n";
	}
}
