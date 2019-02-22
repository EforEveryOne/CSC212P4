package edu.smith.cs.csc212.p4;

import java.util.HashMap;
import java.util.Map;

/**
 * SpookyMansion, the game.
 * @author jfoley
 *
 */
public class TowerEscape implements GameWorld {
	private Map<String, Place> places = new HashMap<>();
	
	/**
	 * Where should the player start?
	 */
	@Override
	public String getStart() {
		return "firstFloor";
	}

	/**
	 * This constructor builds our SpookyMansion game.
	 */
	public TowerEscape() {
		Place firstFloor = insert(
				Place.create("firstFloor", "You wake up in a large room lit by torches. \nYou have a strange watch on your wrist. \n"
						+ "The walls are made out of stone cold bricks. \n\n"));
		firstFloor.addExit(new Exit("spiralStairs", "A long spiraling staircase extends upwards. "));
		firstFloor.addSecretExit(new SecretExit("firstFloorSecretExit", "There is a brick that looks out of place on the wall, maybe you should press it?"));
// This is our bottom secret exit.
		Place firstFloorSecretExit = insert(
				Place.terminal("firstFloorSecretExit", "After you press the brick the wall rumbles and the bricks slide away revealing an exit from the tower. "
						+ "\nRays of sunshine fill the exit. \nYou're free."));
				
		
		Place spiralStairs = insert(
				Place.create("spiralStairs", "The staircase is very long but there are torches to guide you. \nIt takes several minutes to traverse the steps. \n\n"));
		spiralStairs.addExit(new Exit("firstFloor", "Go back to the first floor. "));
		spiralStairs.addExit(new Exit("secondFloor", "There is a large wooden door with a rustic handle at the top. "));
		
		Place secondFloor = insert(
				Place.create("secondFloor", "You reached the second floor. \nIn the torchlight you see old rotted and dusty furniture around the room. \n\n"));
		secondFloor.addExit(new Exit("spiralStairs", "Go back to the spiral stairs."));
		secondFloor.addExit(new Exit("secondFloorHall", "There is a dimly lit hallway to your right. "));
		secondFloor.addExit(new Exit("stairs2", "There is another staircase that goes up in the corner of the room. "));
		
		Place secondFloorHall = insert(
				Place.create("secondFloorHall", "The hallway opens into a massive room but it's empty... \nYou feel a chill run down your spine."));
		secondFloorHall.addExit(new Exit("secondFloor", "Go back through the hallway "));

		
		Place stairs2 = insert(
				Place.create("stairs2", "These stairs are narrow and you have to feel your way around because there are no torches. "));
		stairs2.addExit(new Exit("secondFloor", "Go back to the second floor."));
		stairs2.addExit(new Exit("thirdFloor", "There's a door at the top. "));
		
		Place thirdFloor = insert(
				Place.create("thirdFloor", "You made it to the third floor. There is a set table with rotting food. "));
		thirdFloor.addExit(new Exit("stairs2", "Go back to the the dark stairs. "));
		thirdFloor.addExit(new Exit("thirdFloorHall", "There's a dark hallway to your left. "));
		thirdFloor.addExit(new Exit("towerTopStairs", "Grand stairs are well lit with torches at the far end of the room. "));
		
		Place thirdFloorHall = insert(
				Place.create("thirdFloorHall", "It's hard to see in this hall. "));
		thirdFloorHall.addExit(new Exit("thirdFloor", "Go back to third floor room. "));
		thirdFloorHall.addExit(new Exit("stairs2Passage", "There's a trap door at the end of the hall. You can't see the bottom. Jump down? "));

		Place stairs2Passage = insert(
				Place.create("stairs2Passage", "You fall and land on your butt. Torches light up suddenly light up the room."
						+ "\nThere is a scrap of paper on a table."
						+ "\nThe paper reads: SEARCH (you can type search in console) "));
		stairs2Passage.addExit(new Exit("stairs2", "The only exit is an opening the drops into a dark and narrow stairway. "));
		
		Place towerTopStairs = insert(
				Place.create("towerTopStairs", "These stairs are narrow and you have to feel your way around because there are no torches. "));
		towerTopStairs.addExit(new Exit("thirdFloor", "Go back to the third floor. "));
		towerTopStairs.addExit(new Exit("towerTop", "The stairs are very wide and look like they spiral upward forever but you think you see light at the very top. "));
		
		
		Place towerTop = insert(
				Place.create("towerTop", "You made it to the top! The sky is bright blue. You can see everything from up here. "
						+ "\n\nThere is a scrap of paper pinned to the center of the floor. "
						+ "\nThe paper reads: AS ABOVE SO BELOW "
						+ "\n\nWeird... "));
		towerTop.addExit(new Exit("towerTopStairs", "Go back down the stairs. "));
		towerTop.addSecretExit(new SecretExit("ropeToFreedom", "You found a super long rope! Maybe you can use this to lower yourself down from the tower? "));
// This is our top secret exit.
		Place ropeToFreedom = insert(
				Place.terminal("ropeToFreedom", "You slowly scale down the tower with your newfound rope. \n\nYou are free!")
				);
		
		// Make sure your graph makes sense!
		checkAllExitsGoSomewhere();
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	protected Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}

	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	public Place getPlace(String id) {
		return this.places.get(id);		
	}
}
