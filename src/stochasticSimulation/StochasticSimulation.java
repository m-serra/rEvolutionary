package stochasticSimulation;

import java.util.Comparator;
import java.util.Random;

import pec.PEC;

/**
 * An abstract class that implements the interface IStochasticSimulation.
 * It establishes that stochastic simulations have an association with a Population of
 * individuals, with a PEC and have a currentEv.
 * It also declares some fields that are to be present in any Stochastic Simulation, such as currentTime.
 * @author ManuelSerraNunes
 *
 */
public abstract class StochasticSimulation implements IStochasticSimulation {
	
	/**
	 * The random seed of every stochastic event or decision that takes place in the simulation.
	 * No other random seed exists in the simulation.
	 */
	static Random random = new Random();
	/**
	 * The multiplicative parameter for the mean time between death events in the simulation.
	 */
	protected int deathParam;
	/**
	 * The multiplicative parameter for the mean time between reproduction events in the simulation.
	 */
	protected int reprParam;
	/**
	 * The multiplicative parameter for the mean time between move events in the simulation.
	 */
	protected int moveParam;
	/**
	 * The best cost to find the goal of the simulation so far.
	 */
	protected int bestCost = -1;
	/**
	 * The time that has elapsed since the simulation started.
	 */
	protected double currentTime = 0.0;
	/**
	 * The total/final time of simulation.
	 */
	protected double simulationTime;
	/**
	 * The best comfort an individual has achieved during the simulation.
	 */
	protected double bestComfort = 0;
	/**
	 * A counter for the number of events simulated during the simulation.
	 */
	protected static int eventCounter;
	/**
	 * A boolean that tels whether the goal of the simulation has been achieved by any individual.
	 */
	protected boolean goalReached;
	
	/**
	 * The population being evolved in the StochasticSimulation.
	 */
	protected Population pop;
	/**
	 * The pending event container of the simulation.
	 */
	protected PEC pec;
	/**
	 * The currentEv being simulated in the StochasticSimulation.
	 */
	protected Event currentEv;
	
	/**
	 * The constructor of StochasticSimulation associates the simulation with a population, creates a PEC with initial size 3 * size of the population
	 * and initializes the parameters.
	 * @param pop the population to be associated with the Simulation.
	 * @param moveParam the multiplicative parameter for the mean time between move events in the simulation.
	 * @param deathParam the multiplicative parameter for the mean time between death events in the simulation.
	 * @param reprParam the multiplicative parameter for the mean time between reproduction events in the simulation.
	 * @param simulationTime The final instant of the simulation.
	 */
	StochasticSimulation(Population pop, int moveParam, int deathParam, int reprParam, double simulationTime){
	
		this.pop = pop;
		this.moveParam = moveParam;
		this.reprParam = reprParam;
		this.deathParam = deathParam;
		this.simulationTime = simulationTime;
		
		Comparator<Event> comparator = new EventComparator();
		pec = new PEC( 3 * pop.v, comparator ); //by dafault the PEC starts with space for 3 events per individual in the population
		
	}
	
	/**
	 * Leaves the implementation of the simulate() method, defined in the interface for the subclasses.
	 */
	public abstract void simulate();
	/**
	 * Leaves the implementation of the simulate() method, defined in the interface for the subclasses.
	 */
	public abstract void saveBest(Individual ind);
	

}