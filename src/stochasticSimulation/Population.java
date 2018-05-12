package stochasticSimulation;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * The Population class aggregates the list of alive individuals and parameters common to all the Individuals.
 * It is also associated with the StochasticSimulation where the individuals are being evolved and with the grid
 * where they live, in case of a shortest path problem.
 * 
 * @author ManuelSerraNunes
 */
public class Population {
	
	/**
	 * The number of individuals currently alive in the population.
	 */
	protected int v; 
	/**
	 * The maximum number of individuals that can be alive in the population at a given time, before an epidemic occurs.
	 */
	protected final int vmax;
	/**
	 * The comfort sensibility parameter of all the individuals in the population.
	 */
	protected int comfortParam;
	/**
	 * The ID to give to the next individual to be born.
	 */
	protected int nextID = 0; //the next ID to give to an individual
	/**
	 * The goal point in case of a shortest path problem.
	 */
	protected Point goal = null;
	/**
	 * The linked list containing all the alive individuals at a given moment.
	 */
	protected LinkedList<Individual> individuals;
	/**
	 * The simulation where the individuals of the population are being evolved.
	 */
	protected StochasticSimulation sim; //a population is associated with a stochastic simulation
	/**
	 * The Grid where the population lives.
	 */
	protected Grid grid = null; //the grid where the population lives (if the stochastic problem is applied to a grid)
					 //initialized with a setter
	
	/**
	 * Constructor for a Population.
	 * Initializes the initial number of individuals, the maximum number of individuals the comfort sensibility.
	 * Creates the initial individuals of the population.
	 * @param v the initial size of the Population.
	 * @param vmax the maximum size of the Population
	 * @param comfortParam the comfort sensibility for the individuals of the population.
	 * @param initPos the initial position of the individuals of the population.
	 */
	Population(int v, int vmax, int comfortParam, Point initPos){
		this.v = v;
		this.vmax = vmax;
		individuals = new LinkedList<Individual>();
		this.comfortParam = comfortParam;
		
		for(int i = 0; i < v; i++) {
			Individual newInd = new Individual( initPos, this );
			individuals.add(newInd);		
		}
	}
	
	/**
	 * This method adds an individual to the Population, providing an API that detaches the functionality
	 * of the methods from their implementation (as a list).
	 * @param ind the individual to be added to the population.
	 */
	public void addInd(Individual ind) {
		individuals.add(ind);
		v++;
	}
	
	/**
	 * This method removes an individual from the Population, providing an API that detaches the functionality
	 * of the methods from their implementation (as a list).
	 * @param ind the individual to be removed from the population.
	 */
	public void removeInd(Individual ind) {
		individuals.remove(ind);
		v--;
	}
	
	/**
	 * This method counts the number of individuals in the Population, providing an API that detaches the functionality
	 * of the methods from their implementation (as a list).
	 * @return an int with the number of individuals in the population.
	 */
	public int countPopSize() {
		return individuals.size();
	}
	
	/**
	 * A setter for the initial position of the individuals
	 * @param init the initial position to be set
	 */
	public void setInitPos(Point init) {
		
		for(Individual ind : individuals) {
			ind.setPosition(init);
			ind.updatePath();
			ind.updateDist();
			ind.updateComfort();
		}
	}
	
	/**
	 * A setter for the Grid where the population lives.
	 * @param grid the grid to be associated with the population.
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
		
		//when a grid is associated with a population, the visited matrix of the population's individuals is initialized
		for(Individual ind : individuals) {
			ind.newVisitedHist();
			ind.setVisited(ind.position);
		}
		
	}
	
	/**
	 * A setter for the simulation where the individuals of the population evolve.
	 * @param sim the StochasticSimulation to be associated with the Population.
	 */
	public void setSim(StochasticSimulation sim) {
		this.sim = sim;
	}
	
	/**
	 * A setter for the goal of the population.
	 * @param goal the point to be set as goal.
	 */
	public void setGoal(Point goal) {
		this.goal = goal;
	}
	
	/**
	 * Redefinition of the toString method to present the whole population and it's parameters.
	 */
	@Override
	public String toString() {
		return "\nPopulation\nv: " + v + "; vmax: " + vmax + "; Individuals:" + Arrays.toString(individuals.toArray());
	}
	
	
}
