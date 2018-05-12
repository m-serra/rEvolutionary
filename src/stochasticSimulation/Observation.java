package stochasticSimulation;

import java.util.Arrays;
import java.util.List;

/**
 *The Observation class is a subclass of DeterministicEvent. It's simulation causes the current state
 *of the StochaticSimulation to be printed to the console.
 *This implementation of the observation assumes a shortest path problem.
 * 
 * @author Manuel Serra
 */
class Observation extends DeterministicEvent {
	
	/**
	 * A counter of observations has to be set to static.
	 */
	protected static int obsCounter = 0;
	/**
	 * A boolean that tells if the goal has been reached during the simulation (true) or not (false).
	 */
	protected boolean goalReached;
	/**
	 * An int with the cost of the best path found so far.
	 */
	protected int bestCost;
	/**
	 * A deterministic event may be happening in a stochastic simulation.
	 */
	protected StochasticSimulation sim = null;
	/**
	 * The list of points that represents the best path found in the simulation, so far.
	 */
	protected List<Point> shortestPath;
	
	/**
	 * Constructor for an Observation object.
	 * 
	 * @param goalReached a boolean telling whether the goal has been reached or not.
	 * @param path a list of Points representing the best path found so far in the simulation.
	 * @param bestCost the cost of the best path found so far in the simulation.
	 */
	Observation(boolean goalReached, List<Point> path, int bestCost){
		this.goalReached = goalReached;
		shortestPath = path;
		this.bestCost = bestCost;

	}
	
	/**
	 * Constructor for an Observation event taking place in a stochastic simulation.
	 * 
	 * @param goalReached a boolean telling whether the goal has been reached or not.
	 * @param path a list of Points representing the best path found so far in the simulation.
	 * @param bestCost the cost of the best path found so far in the simulation.
	 * @param sim the StochasticSimulation where the Observation will take place.
	 */
	Observation(boolean goalReached, List<Point> path, int bestCost, StochasticSimulation sim){
		this.goalReached = goalReached;
		shortestPath = path;
		this.bestCost = bestCost;
		this.sim = sim;
	}
	
	/**
	 * The simulation of the Observation causes the current state of the Simulation to be printed to the console.
	 */
	public void simulateEvent() {
		
		obsCounter++;
		System.out.println("Observation number " + obsCounter + ":");
		System.out.println("\t\t\tPresent instant:\t" + Math.floor(sim.currentTime));
		System.out.println("\t\t\tNumber of realised events:\t" + StochasticSimulation.eventCounter);
		System.out.println("\t\t\tPopulation size:\t" + sim.pop.v);
		System.out.println("\t\t\tFinal point has been hit:\t" + goalReached);
		System.out.println("\t\t\tPath of the best fit individual:\t" + Arrays.toString( (shortestPath.toArray()) ).replace("[","{").replace("]", "}") );
		if(goalReached)
			System.out.println("\t\t\tCost:\t" + bestCost);
		else
			System.out.println("\t\t\tComfort:\t" + sim.bestComfort);
		System.out.println();
		
		if(obsCounter == 20)
			System.out.println("\nPath of the best fit individual = " +  Arrays.toString( shortestPath.toArray() ).replace("[","{").replace("]", "}") );
	}
	
	/**
	 * Getter for the Ind ID, required by the interface. In this case the event has no individual associated.
	 */
	public int getIndID() {
		return -1;
	}
	
	/**
	 * Getter for the Ind, required by the interface. In this case the event has no individual associated.
	 */
	public Individual getInd() {
		return null;
	}
	
}
