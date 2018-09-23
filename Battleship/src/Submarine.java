/**
 * Submarine - A class that creates a submarine ship (4 spaces)
 *
 */
public class Submarine extends Ship {
	String shipType;
	int spaceCount;

	/**
	 * Submarine - A constructor that initializes the name of the ship
	 */
	public Submarine() {
		super("Submarine");
	}

	/**
	 * sunkMessage - A method that returns a message, telling the player that the
	 * submarine has been sunk
	 */
	public String sunkMessage() {
		return "You have sunk the Submarine. This is a medium size ship!";
	}

	/**
	 * getSpaceCount - A method that returns the number of spaces that the submarine
	 * takes
	 */
	public int getSpaceCount() {

		return 4;
	}

}
