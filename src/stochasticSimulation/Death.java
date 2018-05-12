package stochasticSimulation;

/**
 * @author Manuel Serra
 * 
 * Death is a subclass of StochasticEvent, implementing the simulateEvent method.
 * After the simulation of a death the individual associated with it is removed from the population where it lives
 * 
 */
class Death extends StochasticEvent{
	
	/**
	 * Constructor of a Death event.
	 * It defines the fields inherited from the superclass and computes a time for the event based on an exponential distribution,
	 * adding to the current time of the simulation.
	 * The time of death is then 'stamped' in the individual associated with the event.
	 * @param ind is the individual to be associated with the death event.
	 */
	Death(Individual ind){
		type = "death";
		this.ind = ind;
		
		time = sim.currentTime + 
				StochasticEvent.expRandom( (1 - Math.log( 1 - this.ind.comfort)) * sim.deathParam );
		
		ind.deathTime = time; //set the individual's time of death
	}
	
	/**
	 * Simulates the death event. The individual associated with the death is removed from the population and cleared from memory.
	 * Event counter is incremented.
	 */
	public void simulateEvent(){
		StochasticSimulation.eventCounter++;
		sim.pop.removeInd(this.ind);
	}
}
