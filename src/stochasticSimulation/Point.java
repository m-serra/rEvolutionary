package stochasticSimulation;


/**
 * The class Point aggregates two dimensional coordinates x (columns of a grid ) and y (rows of a grid).
 
 * @author ManuelSerraNunes
 *
 */
public class Point {

	/**
	 * x coordinate of the Point.
	 */
	protected int x;
	/**
	 * y coordinate of the Point.
	 */
	protected int y;

	/**
	 * Constructor for a point.
	 * 
	 * @param x the x cordinate of the point (columns).
	 * @param y the y coordinate of the point (rows).
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This method increments the x coordinate of a point.
	 */
	public void incrementX() {
		 this.x++;
	}
	
	/**
	 * This method increments the y coordinate of a point.
	 */
	public void incrementY() {
		 this.y++;
	}
	
	/**
	 * This method decrements the x coordinate of a point.
	 */
	public void decrementX() {
		 this.x--;
	}
	
	/**
	 * This method decrements the y coordinate of a point.
	 */
	public void decrementY() {
		 this.y--;
	}
	
	/**
	 * Getter for the x coordinate of a point.
	 * @return The x coordinate of the point.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter for the y coordinate of a point.
	 * @return The y coordinate of the point.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Setter for the x coordinate of a point.
	 * @param x the x coordinate to be set.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Setter for the y coordinate of a point.
	 * @param y the y coordinate to be set.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 *Redefinition of the equals method for the Point to be compared with other classes, namely subclasses
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		/*if (getClass() != obj.getClass()) //so that points can be compared with obstacles
			return false; */
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * Redefinition of the toString method to present both coordinates together.
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
