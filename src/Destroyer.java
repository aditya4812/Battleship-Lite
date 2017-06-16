/**
 * Destroyer - A class that creates a destroyer ship (5 spaces)
 *
 */
public class Destroyer extends Ship {
	String shipType;
	int spaceCount;

	/**
	 * Destroyer - A constructor that initializes the name of the ship
	 */
	public Destroyer() {
		super("Destroyer");

	}

	/**
	 * sunkMessage - A method that returns a message, telling the player that
	 * the destroyer has been sunk
	 */
	public String sunkMessage() {
		return "You have sunk the Destroyer. This is the largest ship! Great job!";

	}

	/**
	 * getSpaceCount - A method that returns the number of spaces that the
	 * destroyer takes
	 */
	public int getSpaceCount() {

		return 5;
	}

}