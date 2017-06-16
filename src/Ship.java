/**
 * Ship - A class that creates a ship object with a name, space count, and hit
 * count
 */
public abstract class Ship {
	String shipType;
	int spaceCount;
	int hitCount;

	/**
	 * Ship - initializes the shipy type and space count
	 * 
	 * @param type
	 *            the string of the ship type
	 */
	public Ship(String type) {
		shipType = type;
		spaceCount = this.getSpaceCount();
	}

	/**
	 * getShipType - A method that returns the ship type
	 * 
	 * @return shipType - the String containing the ship type
	 */
	public String getShipType() {
		return this.shipType;
	}

	/**
	 * getSpaceCount - A method that returns the space count of the ship
	 * 
	 * @return spaceCount - the number of spaces that each ship occupies on the
	 *         arraylist (board)
	 */
	public int getSpaceCount() {
		return this.getSpaceCount();
	}

	/**
	 * hit - A method that increases the hit count of the ship
	 */
	public void hit() {
		hitCount++;
	}

	/**
	 * getHitCount - A method that returns the number of times the ship has been
	 * hit
	 * 
	 * @return hitCount - the number of times the ship has been his
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * sunkMessage - A method that returns a message that is unique to each
	 * ship, the message is displayed when the ship has been sunk
	 * 
	 * @return sunkMessage - the message tellin the player when the ship has
	 *         been sunk
	 */
	public abstract String sunkMessage();

}
