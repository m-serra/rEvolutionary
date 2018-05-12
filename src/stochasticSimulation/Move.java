package stochasticSimulation;

/**
 *The Move class is a subclass of StochasticEvent. It is associated with an Individual and a Grid and it's
 *simulation causes the associated individual to take a step in the grid, from it's current position.
 * Move instances have a timestamp that is defined by the sum of the current time of the simulation with a number
 *drawn from an exponential distribution. 
 * 
 * @author Manuel Serra
 */
class Move extends StochasticEvent{
	
	/**
	 * The grid where the move will take place.
	 */
	Grid grid; //the grid where the step is taking place
	
	/**
	 * Constructor for a Move object.
	 * It associates the Move with an Individual and a Grid. It also computes the time of the move by
	 * adding the currentTime of the simulation where the move takes place at the time the move is created with
	 * a number drawn from an exponential distribution. If the time of the move is posterior to the death time
	 * of the associated Individual the time is set to -1.
	 * 
	 * @param ind the Individual that will make the move.
	 * @param grid the Grid where the move is taking place.
	 *
	 */
	Move( Individual ind, Grid grid ){
		
		type = "move";
		this.ind = ind;
		
		time = sim.currentTime + StochasticEvent.expRandom( (1 - Math.log(this.ind.comfort)) * (double)sim.moveParam );
		
		//if the time is set after the individual's death set time to -1
		if(time >= ind.deathTime) {
			time = -1;
		}	
	}
	
	/**
	 * Implementation of the simulateEvent() defined as abstract in the superclass.
	 * The Individual's next move is added to the PEC. A step is taken in the grid.
	 * The individual's parameters are updated. 
	 */
	public void simulateEvent(){
		
		StochasticSimulation.eventCounter++;
					
		//add the next move of the individual
		sim.pec.addEvent( new Move( ind, grid ) );
		
		//take a step in the grid
		ind.pop.grid.step( ind );
				
		//after a move the individual's fields must be updated
		ind.update();
	}
}
