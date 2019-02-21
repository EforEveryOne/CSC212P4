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
				Place.create("firstFloor", "You wake up in a large room lit by torches. \n"
						+ "The walls are made out of stone cold bricks. \n\n"));
		firstFloor.addExit(new Exit("spiralStairs", "A long spiraling staircase extends upwards. "));
		firstFloor.addSecretExit(new SecretExit("firstFloorSecretExit", "There is a brick that looks out of place on the wall, maybe you should press it?"));
		
				
		Place spiralStairs = insert(
				Place.create("spiralStairs", "The staircase is very long but there are torches to guide you. \nYou reach the top after several minutes. \n\n"));
		spiralStairs.addExit(new Exit("firstFloor", "There is a large wooden door with a rustic handle. "));
		spiralStairs.addExit(new Exit("towerTop", "You see sunshine shimmer through down the stairs. Is this the end?"));
		
		
		Place firstFloorSecretExit = insert(
				Place.terminal("firstFloorSecretExit", "After you press the brick the wall rumbles and the bricks slide away revealing an exit from the tower. "
						+ "\nRays of sunshine fill the exit. \nYou're free."));
		
		Place towerTop = insert(
				Place.terminal("towerTop", "You can see everything from atop the tower."
						+ "\nA giant moth offers to fly you home. \nYou're free."));
		
//		Place basement = insert(
//				Place.create("basement", "You have found the basement of the mansion.\n" + 
//		                           "It is darker down here.\n" +
//						"You get the sense a secret is nearby, but you only see the stairs you came from."
//						));
//		basement.addExit(new Exit("startRoom", "There are stairs leading up."));
//		basement.addExit(new Exit("basement2", "There's a large celler door..."));
//		
//		Place basement2 = insert(Place.create("basement2", "It was difficult to open. \n" + "It smells rancid in here."));
//		basement2.addExit(new Exit("basement", "Go back."));
//
//		Place attic = insert(Place.create("attic",
//				"Something rustles in the rafters as you enter the attic. Creepy.\n" + "It's big up here."));
//		attic.addExit(new Exit("startRoom", "There are stairs leading down."));
//		attic.addExit(new Exit("attic2", "There is more through an archway."));
//
//		Place attic2 = insert(Place.create("attic2", "There's definitely a bat in here somewhere.\n"
//				+ "This part of the attic is brighter, so maybe you're safe here."));
//		attic2.addExit(new Exit("attic", "Go back to the attic entrance."));
//		attic2.addExit(new Exit ("attic3", "There's a door ahead."));
//		
//		Place attic3 = insert(Place.create("attic3", "The room is dark and empty. The attic ends here. "));
//		attic3.addExit(new Exit("attic2", "Leave the room. "));
//		
//		
//		Place kitchen = insert(Place.create("kitchen", "You've found the kitchen. You smell old food and some kind of animal."));
//		kitchen.addExit(new Exit("startRoom", "There is a red door."));
//		kitchen.addExit(new Exit("dumbwaiter", "There is a dumbwaiter."));
//		
//		Place dumbwaiter = insert(Place.create("dumbwaiter", "You crawl into the dumbwaiter. What are you doing?"));
//		dumbwaiter.addExit(new Exit("secretRoom", "Take it to the bottom."));
//		dumbwaiter.addExit(new Exit("kitchen", "Take it to the middle-level."));
//		dumbwaiter.addExit(new Exit("attic2", "Take it up to the top."));
//		
//		Place secretRoom = insert(Place.create("secretRoom", "You have found the secret room."));
//		secretRoom.addExit(new Exit("hallway0", "There is a long hallway."));
//		secretRoom.addExit(new Exit("basement", "This is an Exit from secretRoom to the basment. "));
//		
//		int hallwayDepth = 4;
//		int lastHallwayPart = hallwayDepth - 1;
//		for (int i=0; i<hallwayDepth; i++) {
//			Place hallwayPart = insert(Place.create("hallway"+i, "This is a very long hallway.\n" + "There is a #" + (i + 1) + " scratched onto the wall."));
//			if (i == 0) {
//				hallwayPart.addExit(new Exit("secretRoom", "Go back.", false));
//			} else {
//				hallwayPart.addExit(new Exit("hallway"+(i-1), "Go back.", false));
//			}
//			if (i != lastHallwayPart) {
//				hallwayPart.addExit(new Exit("hallway"+(i+1), "Go forward.", false));
//			} else {
//				hallwayPart.addExit(new Exit("crypt", "There is darkness ahead.", false));
//			}
//		}
//		
//		Place crypt = insert(Place.terminal("crypt", "You have found the crypt.\n"
//				+"It is scary here, but there is an exit to outside.\n"+
//				"Maybe you'll be safe out there."));
		
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
