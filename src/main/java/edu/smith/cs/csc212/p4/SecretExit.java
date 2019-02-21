package edu.smith.cs.csc212.p4;

public class SecretExit extends Exit {
	
//	The exits start out hidden.
	private boolean hidden = true;

	public SecretExit(String target, String description) {
		super(target, description);
		hidden = true;
	}
	
	
	/**
	 * We're saying the our exit is a secret and we won't reveal it until the players searchs for it.
	 */
	@Override
	public boolean isSecret() {
		return hidden;
	}

	/**
	 * If the player searches the area they will find hidden exits!
	 * the secret exit is no longer hidden.
	 */	
	@Override
	public void search() {
		hidden = false;
		  }
		
	
	
}
