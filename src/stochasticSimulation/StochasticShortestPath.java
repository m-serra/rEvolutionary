package stochasticSimulation;

import java.util.LinkedList;
import java.util.List;

/**
 * StochasticShortestPath is a subclass of StochasticEvent. It is more specific than
 * it's superclass in the sense that it is an application of a stochastic simulation in finding
 * shortest paths within a grid, using a StochasticSimulation to evolve individuals that in time
 * converge to finding the shortest path.
 * @author ManuelSerraNunes
 *
 */
public class StochasticShortestPath extends StochasticSimulation{
	
	/**
	 * The grid where the simulation is taking place.
	 */
	protected Grid grid;
	/**
	 * The shortestPath found so far.
	 */
	protected List<Point> shortestPath = new LinkedList<Point>();
	
	/**
	 * Constructor for a StochasticShorstestpath instance. It uses
	 * the superclass constructor and further associates a grid.
	 * @param pop the population of the simulation.
	 * @param grid the grid where the simulation is taking place.
	 * @param moveParam the parameter for the mean of Move events.
	 * @param deathParam the parameter for the mean of Death events.
	 * @param reprParam the parameter for the mean of Reproduction events.
	 * @param simulationTime the final time of the simulation.
	 */
	StochasticShortestPath(Population pop, Grid grid, int moveParam, 
							int deathParam, int reprParam, double simulationTime){
		
		super( pop, moveParam, deathParam, reprParam, simulationTime);
		this.grid = grid;
	}
	
	/**
	 * This method evolves a population of individuals that evolve and mutate in a stochastic way,
	 * converging to a solution of the problem of finding the shortest path between two point in a grid.
	 * Initial events Move, Reproduction and Death are added to each individual of the initial population.
	 * From that point on the initial population evolves inside a while loop, where their events are simulated.
	 * By the end of the simulation the best individuals should have gone through the ideal path between the two 
	 * predetermined points.
	 */
	public void simulate() {
		
		int lastObs = 0;
		
		//add the first events to the PEC
		for(Individual ind : pop.individuals) {						
			pec.addEvent( new Death( ind ));
			pec.addEvent( new Move( ind, grid ));
			pec.addEvent(new Reproduction( ind ));
		}
					
		//get the first event from the pec
		currentEv = pec.getNextEvent();
		currentTime = currentEv.getTime();

		//simulate events until the end of the simulation time
		while( currentTime < simulationTime ) {
			
			//if the population exceeds the maximum an Epidemic is launched
			if(pop.v >= pop.vmax) {
				StochasticEvent evE = new Epidemic( pop );
				evE.simulateEvent();
			}
			
			//simulate the current event
			currentEv.simulateEvent();
			
			//check if there is need to update the best path so far
			if( !this.goalReached || (currentEv.getInd()).position.equals(pop.goal) ) {	
				if((currentEv.getInd()).position.equals(pop.goal)) 
					this.goalReached = true;
										
				saveBest( currentEv.getInd());
			} 
			
			//if there are no more events to simulate stop the simulation
			if(pec.isEmptyPEC()) break;
			
			//get the next event form the PEC
			currentEv = (StochasticEvent) pec.getNextEvent();
			
			//fast forward to the current event time
			currentTime = currentEv.getTime();
			
			//an observation is launched every simulationTime/20 time units
			if( currentTime - lastObs >= simulationTime/20 || currentTime > simulationTime) {
		
				DeterministicEvent obs = new Observation( goalReached, shortestPath, bestCost, this);
				obs.simulateEvent();
				lastObs = (int)Math.floor(currentTime);
			} 
		}		
	}	
	
	/**
	 * This method keeps track of the best path found so far in the simulation by updating
	 * the best path each time an individual gets to the goal or, if no individual has arrived
	 * at the goal yet, by keeping track of the path of the individual with the highest comfort.
	 */
	public void saveBest(Individual ind) {
		
		boolean newShortestPath = false;
		
		//if no individual has reached the goal
		if( !this.goalReached ) {
			
			//use comfort as criteria for the best path
			if( ind.comfort > bestComfort ) {
				bestComfort = ind.comfort;
				newShortestPath = true;
			}
		//otherwise use the cost as criteria for the best path
		} else	if( (ind.reachedGoal && (ind.cost < bestCost || bestCost == -1)) ){
				bestCost = ind.cost;
				newShortestPath = true;
		}
		
		//if a new shortest path is found
		if(newShortestPath) {
			
			//delete the previous shortest path
			clearShortestPath();
			
			//copy the new shortest path
			shortestPath = ind.copyPath();
			
		}
	}	
	
	/**
	 * This method clears the shortest path found so far.
	 */
	protected void clearShortestPath() {
		shortestPath.clear();
	}

}
