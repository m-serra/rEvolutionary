package pec;
import stochasticSimulation.Individual;
import stochasticSimulation.Event;

/**
 * PEC interface. Defines the methods that must be implemented in a PEC
 * States that the PEC keeps objects of type Event and that
 * two public methods must be provided: getNextEvent() and addEvent().
 * 
 * @author Manuel Serra
 */
public interface IPEC {
		
		/**
		 *A method to retrieve the first element in the PEC
		 *
		 * @return An Event Object 
		 *
		 */
		public Event getNextEvent();
		
		/**
		 *A method to add a new element to the PEC
		 *
		 * @param ev the Event object to insert in the PEC
		 *
		 */
		public void addEvent(Event ev);
		
		/**
		 *A method to remove the events in the PEC associated with a 
		 *given individual
		 *
		 * @param ind the individual associated with the events to be removed
		 *
		 */
		public void removeIndEvents(Individual ind);
}
