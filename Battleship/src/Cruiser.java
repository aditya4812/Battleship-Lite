/**
 * Cruiser - A class that creates a cruiser ship (3 spaces)
 *
 */
public class Cruiser extends Ship {
	String shipType;

	/**
	 * Cruiser - A constructor that initializes the name of the ship
	 */
	public Cruiser() {
		super("Cruiser");

	}

	/**
	 * sunkMessage - A method that returns a message, telling the player that the
	 * cruiser has been sunk
	 */
	public String sunkMessage() {
		return "You have sunk the Cruiser. This is a three space ship! Good job!";

	}

	/**
	 * getSpaceCount - A method that returns the number of spaces that the cruiser
	 * takes
	 */
	public int getSpaceCount() {
		return 3;
	}

}
