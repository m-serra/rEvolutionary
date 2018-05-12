package stochasticSimulation;

/**
 * The class Reproduction is a subclass of StochasticEvent.
 * When simulated, a Reproduction brings a new individual to the population.
 * Reproduction instances have a timestamp that is defined by the sum of the current time of the simulation with a number
 *drawn from an exponential distribution. 
 * @author ManuelSerraNunes
 *
 */
class Reproduction extends StochasticEvent {
	
	/**
	 * Reproduction constructor.
	 * It associates the Reproduction with an Individual. It also computes the time of the move by
	 * adding the currentTime of the simulation where the reproduction takes place at the time the reproduction is created with
	 * a number drawn from an exponential distribution. If the time of the reproduction is posterior to the death time
	 * of the associated Individual the time is set to -1.
	 * 
	 * @param ind the Individual that will reproduce.
	 */
	Reproduction( Individual ind ){
		
		type = "repr";
		this.ind = ind;
		
		time = sim.currentTime + 
				StochasticEvent.expRandom( (1 - Math.log(this.ind.comfort)) * sim.reprParam );
		
		//if the time is set after the individual's death set time to -1
		if(time > ind.deathTime) {
			time = -1;
		}	
	}
	
	/**
	 * When a reproduction is simulated the next reproduction of the individual is added to the PEC.
	 * Then, the individual creates it's child.
	 * The child's first move, first reproduction and death are added to the PEC.
	 */
	public void simulateEvent(){
		
		StochasticSimulation.eventCounter++;
			
		//add the next reproduction of the parent
		sim.pec.addEvent( new Reproduction( this.ind ) );
		
		//create the parent's child
		Individual child = this.ind.makeChild();
					
		//add child's first move, first reproduction and death to the PEC		
		sim.pec.addEvent( new Death( child ) );
		
		sim.pec.addEvent( new Move( child, child.pop.grid ) );

		sim.pec.addEvent( new Reproduction( child ) );
		
	}
}
