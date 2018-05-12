package stochasticSimulation;

/**
 * The interface for Events
 * It defines that an Event must provide the simulateEvent() method, as well 
 * as getInd(), getInd() and getTime().
 * 
 * @author Manuel Serra
 */
public interface Event {
	
	public abstract void simulateEvent();
	public abstract int getIndID();
	public abstract Individual getInd();
	public abstract double getTime();

}
