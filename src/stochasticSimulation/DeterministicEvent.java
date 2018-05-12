package stochasticSimulation;

/**
 * @author Manuel Serra
 * An implementation of Event for events deterministic in time.
 * Requires a simulateEvent() method.
 */
public abstract class DeterministicEvent implements Event{
	
	/**
	 * The timestamp of the event.
	 */
	double time;

	
	/**
	 * Leaves the implementation of the simulateEvent method open and required for the creator of 
	 * deterministic event subclasses.
	 */
	public abstract void simulateEvent();
	
	
	
	/**
	 * Getter for the time stamp of the event.
	 * @return a double with the timestamp.
	 */
	public double getTime() {
		return time;
	}
}
