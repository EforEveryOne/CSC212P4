package edu.smith.cs.csc212.p4;

public class GameTime {

//	We pass these into InteractiveFiction, via our constructor GameTime, where they get displayed.
	private int hour;
	private int totalHour;
	
//	Our constructor that we utilize in InteractiveFiction.
	public GameTime(int hour, int totalHour) {
		hour = 0;
		totalHour = 0;
	}
	
	/**
	 * This returns the current hour.
	 * @return
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * This returns the total time spent in the game. We use this at the end.
	 * @return
	 */
	public int getTotalHour() {
		return totalHour;
	}
	
	/**
	 * This is our time calculations for the game. 24 hour clock.
	 */
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