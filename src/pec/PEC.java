package pec;
import stochasticSimulation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;


/**
 *The class PEC implements the PEC interface. It stores and manages the events waiting to take place, by
 *offering an API to add, get and remove events from the PEC.
 *
 * @author Manuel Serra
 */
public class PEC implements IPEC{
	
	/**
	 *  An Event comparator that compares Events by their timestamp.
	*/
	Comparator<Event> comparator;
	
	/**
	 *  PriorityQueue of Events where the events waiting to take place are stored in order of timestamp.
	*/
	PriorityQueue<Event> pec = new PriorityQueue<>();

	/**
	 *Constructor method for a PEC object. A Priority Queue is instantiated
	 *using an EventComparator.
	 *
	 * @param initCap is the initial capacity of the PEC.
	 * @param comp is the comparator with which the events will be sorted.
	 *
	 */
	public PEC( int initCap, Comparator<Event> comp){
		this.comparator = comp;
		pec = new PriorityQueue<Event>(initCap, comparator);
	}
	
	/**
	 *This method retrieves the first element in the PEC.
	 *
	 * @return An Event object. 
	 */
	public Event getNextEvent() {
		return pec.remove();	
	}
	
	/**
	 *This method adds an element to the PEC, which is placed in the position corresponding
	 *to it's order by timestamp.
	 *If the input event has a negative timestamp it is not added to the PEC. Such an approach is
	 *useful when the random time given to an event is posterior to the associated individual's death.
	 *
	 * @param ev is the event to be added to the PEC.
	 *
	 */
	public void addEvent(Event ev) {
		if( ev.getTime() < 0.0 ) return;
		pec.add(ev);
	}
	
	/**
	 * This method checks if the PEC is empty. It essentially provides the isEmpty() method from java.util.PriorityQueue as
	 * a public method.
	 * It allows the PEC to be re-implemented using a different data structure without the need to change the code where the 
	 * PEC is used, as long as the API is followed.
	 *
	 * @return A boolean that is true if the PEC is empty and false otherwise. 
	 *
	 */
	public boolean isEmptyPEC() {
		return pec.isEmpty();
	}
	
	/**
	 *This method retrieves the number of elements in the PEC.
	 *It allows the PEC to be re-implemented using a different data structure without the need to change the code where the 
	 *PEC is used, as long as the API is followed.
	 *
	 * @return An int with the size of the PEC.
	 *
	 */
	public int getSize() {
		return pec.size();
	}
	
	/**
	 *This method removes the events associated with the input Individual from the PEC.
	 *Since the PEC is implemented as a PriorityQueue an iterator is used. The events
	 *to remove are identified by the ID of the individual associated with them.
	 *
	 * @param ind is the individual associated with the events to be removed.
	 *
	 */
	public void removeIndEvents(Individual ind) {	
		
		Iterator<Event> iter = pec.iterator();
		while (iter.hasNext()) {
			Event ev = iter.next();
			
			if(ev.getIndID() == ind.getID() )
				iter.remove();
		}
	}
	
	/**
	 *Redefinition of the toString method in order to print the elements of the PEC
	 *implemented as a Priority Queue.
	 */
	public String toString() {
		return "\nPEC \n" + Arrays.toString(pec.toArray());
	}
}
