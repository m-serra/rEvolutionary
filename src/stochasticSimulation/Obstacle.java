package stochasticSimulation;

/**
 *The Obstacle class is a subclass of the Point class. In this implementation
 *it adds nothing to Point, it's purpose is contextual. Different kinds of obstacles
 *could be extended from this class.
 * 
 * @author Manuel Serra
 */
class Obstacle extends Point{
	
	/**
	 * 
	 * @param x the x coordinate of the point.
	 * @param y the y coordinate of the point.
	 */
	Obstacle(int x, int y) {
		super(x, y);
	}
	
}
