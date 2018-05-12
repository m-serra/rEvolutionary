package stochasticSimulation;

/**
 * An abstract class that implements the interface Event.
 * It establishes that stochastic events have an association with a stochastic simulation
 * and may have with an individual.
 * It also provides methods that are useful in any stochastic event.
 * @author ManuelSerraNunes
 *
 */
public abstract class StochasticEvent implements Event{
	
	/**
	 * The timestamp of the event.
	 */
	protected double time;
	/**
	 * A string containing the type of event.
	 */
	protected String type;
	/**
	 * Association with the simulation in which the stochastic event is taking place.
	 */
	protected static StochasticSimulation sim; //stochastic events happen in a stochastic simulation so they are associated with one
	/**
	 * A stochastic event may have an associated individual;
	 */
	protected Individual ind; //a stochastic event might be associated with an individual
	
	/**
	 * Leaves the implementation of the simulate() method, defined in the interface for the subclasses.
	 */
	public abstract void simulateEvent();
	
	/**
	 * This method retrieves a random double from an exponential distribution with mean m.
	 * @param m the mean of the exponential distribution.
	 * @return A random number drawn from the distribution.
	 */
	public static double expRandom(double m) { 
		double next = StochasticSimulation.random.nextDouble(); 
		return -m*Math.log(1.0-next);
	}
	
	/**
	 * A getter for the timestamp.
	 * @return Returns the timestamp.
	 */
	public double getTime() {
		return time;
	}
	
	/**
	 * A setter for the stochastic simulation associated with the event.
	 * @param sim the simulation to be associated with the event.
	 */
	public static void setSim(StochasticSimulation sim) {
		StochasticEvent.sim = sim;
	}
	
	/**
	 * A getter for the id of the individual associated with the simulation.
	 * @return An int with the id of the individual.
	 */
	public int getIndID() {
		return ind.id;
	}
	
	/**
	 * A getter for  the individual associated with the simulation.
	 * @return The individual associated with the simulation.
	 */
	public Individual getInd() {
		return ind;
	}
	
	/**
	 * Redefinition of the toString method.
	 */
	@Override
	public String toString() {
		return "IndID: " + ind.id + "; time: " + time + "; type: " + type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((ind == null) ? 0 : ind.hashCode());
		long temp;
		temp = Double.doubleToLongBits(time);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		/*if (getClass() != obj.getClass())
			return false;*/
		StochasticEvent other = (StochasticEvent) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (ind == null) {
			if (other.ind != null)
				return false;
		} else if (!ind.equals(other.ind))
			return false;
		
		return true;
	}
	
	
}
