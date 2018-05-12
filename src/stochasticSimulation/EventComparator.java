package stochasticSimulation;
import java.util.Comparator;

/**
 * The comparator class for Events. 
 * It compares events by their time stamp.
 *
 *@author Manuel Serra
 */
public class EventComparator implements Comparator<Event> {
	
	/**
	 * Redefinition of the compare method to compare two Stochastic Events by their time.
	 */
	@Override
	public int compare(Event evA, Event evB) {
		
		if(evA.getTime() > evB.getTime())
			return 1;
		else if(evA.getTime() < evB.getTime())
			return -1;
		else
			return 0;
		
	}
}
