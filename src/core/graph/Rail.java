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
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityA == null) ? 0 : cityA.hashCode());
		result = prime * result + ((cityB == null) ? 0 : cityB.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (isDouble ? 1231 : 1237);
		result = prime * result + length;
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		result = prime * result + (seen ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Rail)) {
			return false;
		}
		Rail other = (Rail) obj;
		if (cityA == null) {
			if (other.cityA != null) {
				return false;
			}
		} else if (!cityA.equals(other.cityA)) {
			return false;
		}
		if (cityB == null) {
			if (other.cityB != null) {
				return false;
			}
		} else if (!cityB.equals(other.cityB)) {
			return false;
		}
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (isDouble != other.isDouble) {
			return false;
		}
		if (length != other.length) {
			return false;
		}
		if (ownerName == null) {
			if (other.ownerName != null) {
				return false;
			}
		} else if (!ownerName.equals(other.ownerName)) {
			return false;
		}
		if (seen != other.seen) {
			return false;
		}
		return true;
	}

	public String toString() {
		return cityA +"-> "+cityB+", "+length+", "+isDouble+", "+color+"\n";
	}
}
