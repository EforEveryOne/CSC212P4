package edu.smith.cs.csc212.p4;

import java.util.List;

/**
 * This is our main class for P4. It interacts with a GameWorld and handles user-input.
 * @author jfoley
 *
 */
public class InteractiveFiction {

	
	/**
	 * This is where we play the game.
	 * @param args
	 * @param hour 
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing.
		GameWorld game = new TowerEscape();
		
//		Access to our time class.
		GameTime time = new GameTime(0, 0); 
		
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		String place = game.getStart();

		
		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
		
			// Print the description of where you are.
			Place here = game.getPlace(place);
			System.out.println(here.getDescription());
			
			System.out.println("Your watch reads: " + time.getHour() + " o'clock.");
			time.increaseHour();
			
			// Game over after print!
			if (here.isTerminalState()) {
				System.out.println("You have been trapped in the tower for: " + time.getTotalHour() + " hours in total.");
				break;
			}

			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();
			for (int i=0; i<exits.size(); i++) {
			    Exit e = exits.get(i);
				System.out.println(" ["+i+"] " + e.getDescription());
			}
			
			
			// Figure out what the user wants to do.
			List<String> words = input.getUserWords(">");
			if (words.size() == 0) {
				System.out.println("Must type something!");
				continue;
			} else if (words.size() > 1) {
				System.out.println("Only give me 1 word at a time!");
				continue;
			}
			
			// Get the word they typed as lowercase, and no spaces.
			String action = words.get(0).toLowerCase().trim();

//			Supposed to be inventory command. Not functional.
			if (action.equals("stuff")) {
				System.out.println("You have nothing but your wrist watch. ");
				continue;
			}
			
//			If the player rests they spend more time here and time passes. No functionality beyond that.
			else if (action.equals("rest")) {
				time.increaseHour();
				time.increaseHour();
			}

//			Player searches the room and finds any hidden exits.
			else if (action.equals("search")) {
				here.search();
//				Searching takes time!
				time.increaseHour();
				continue;
			}
		
			// This code handles manual quitting of the game.			
			else if(action.equals("quit") || action.equals("q") || action.equals("escape")) {
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			}
			
			
			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}
			
			if (exitNum < 0 || exitNum > exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}

			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			place = destination.getTarget();
		}

		// You get here by "quit" or by reaching a Terminal Place.
		System.out.println("\n\n>>> GAME OVER <<< \n\n");
		System.out.println(">>> CONGRATULATIONS <<<");
	}
}
