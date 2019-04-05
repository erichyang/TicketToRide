package core;

import java.util.Arrays;

public class Ticket {

	private String city1;
	private String city2;
	private int points;
	
	public Ticket(String ticketsLine){
		String[] lineData = ticketsLine.split("\\|");
		points = Integer.parseInt(lineData[0]);
		city1 = lineData[1];
		city2 = lineData[2];
//		System.out.println(Arrays.toString(lineData));
	}
	
	public Ticket(int pointNum,String cityOne,String cityTwo) {
		points = pointNum;
		city1 = cityOne;
		city2 = cityTwo;
	}
	
	public String getCities() {
		return city1+","+city2;
	}
	
	public int getPointCount() {
		return points;
	}
	
	public String toString() {
		return getCities() + " " + getPointCount();
	}
}
