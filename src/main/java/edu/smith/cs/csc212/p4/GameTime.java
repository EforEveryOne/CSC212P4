package edu.smith.cs.csc212.p4;

public class GameTime {

	private int hour;
	private int totalHour;
	
	
	
	public GameTime() {
		hour = 0;
		totalHour = 0;
		
	}
	
	public GameTime(int hour, int totalHour) {
		hour = 0;
		totalHour = 0;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getTotalHour() {
		return totalHour;
	}
	
	public void increaseHour() {
		if (hour > 23) {
			hour = 0;
			totalHour += 1;
		}
		else {
		hour += 1;
		totalHour += 1;
		}
		
	}
	
	
}
