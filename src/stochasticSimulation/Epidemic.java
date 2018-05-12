package stochasticSimulation;

import java.util.Comparator;

import java.util.Iterator;

/**
 * An epidemic is a StochasticEvent Subclass. It creates an epidemic in which a population is renovated.
 * The 5 best individuals always survive and the rest of the individuals in the population survive with chance comfort.
 * 
 * @author Manuel Serra
 */
class Epidemic extends StochasticEvent{
	
	/**
	 *  Population where the epidemic takes place.
	*/
	protected Population pop;
	
	/**
	 *  Comparator between individuals by their comfort, to decide the 5 fittest.
	*/
	protected Comparator<Individual> comparator = new IndividualComparator();
	
	/**
	 * Constructor for an Epidemic.
	 * It associated the epidemic with the population in which it occurs, and sets the individual association inherited from StochasticEvent to null.
	 * The type of event is also set.
	 * @param pop the population where the epidemic will take place 
	 *
	 */
	Epidemic(Population pop){
		
		this.pop = pop;
		this.ind = null; //differing from other events, epidemic has no individual associated
		this.type = "epidemic";
	}
	
	
	/**
	 * The simulateEvent() of an epidemic starts by sorting the population using the comparator, in order to find
	 * the 5 individuals with best comfort. For the remaining individuals, a random number is generated which will decide if each individual survives or not
	 * depending on their comfort.
	 * At the end, new ID's are given to the survivors.
	 */
	public void simulateEvent(){
				
		//sort the population by comfort
		pop.individuals.sort(comparator);
				
		//remove all individuals except for the 5 best
		//remove their pending events from the PEC
		int i = 0;
		Iterator<Individual> iter = pop.individuals.iterator();
		while (iter.hasNext()) {
			
			Individual ind = iter.next();
			
			if(i < 5) {
				i++;
			}
			else {
				double survives = StochasticSimulation.random.nextDouble(); 		
				
				//if the random value is greater than the comfort the individual dies
				if(survives > ind.comfort) {
					sim.pec.removeIndEvents( ind );
					iter.remove();
				}
			}
		} 
						
		//set the number of individuals left
		pop.v = pop.countPopSize();
		
		//give new ID's
		pop.nextID = 0;
		for(Individual ind : pop.individuals) {
			ind.id = pop.nextID;
			pop.nextID ++;
		}
			
	}
}
