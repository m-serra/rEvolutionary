package stochasticSimulation;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *The Individual class that defines the individuals created and evolved during a stochastic simulation execution.
 *Individuals are the motor of the simulation, by mutating they achieve a greater comfort, which gives them a greater
 *chance at reproducing and surviving. Because of that, during the runtime of a simulation the population of individuals evolves
 *in such a way that as time goes a larger number of individuals with high comfort proliferates, increasing the chance of finding
 *the optimal solution to a given problem.
 *The implementation of Individual provided assumes that it is used in a problem taking place in a grid.
 * 
 * @author Manuel Serra
 */
public class Individual {

	/**
	 * The identifier number of an individual object. Unique within a Population.
	 */
	protected int id;
	/**
	 * The comfort parameter of the individual.
	 */
	protected double comfort = 0;
	/**
	 * The determined time of death of the individual. Defined with a setter.
	 */
	protected double deathTime;
	/**
	 * A boolean table that keeps track of the visited positions (true).
	 */
	protected boolean visited[][];
	/**
	 * A boolean that tells whether the final position has been reached (true) or not (false).
	 * Initialized with a setter.
	 */
	protected boolean reachedGoal = false;
	/**
	 * The total cost of the individual's path so far.
	 */
	protected int cost;
	/**
	 * The total length of the individual's path so far, in steps.
	 */
	protected int length;
	/**
	 * The distance between the current position of the individual and the goal, not taking obstacles into account.
	 */
	protected int dist;
	/**
	 * The population where the individual belongs.
	 */
	protected Population pop; //the population where the individual belongs
	/**
	 * the current position of the individual within the grid.
	 */
	protected Point position;
	/**
	 * the list of points that constitutes the path traversed by the individual so far.
	 */
	protected List<Point> path = new LinkedList<Point>();
	
	/**
	 *Constructor for an Individual.
	 *The id is set according to the next ID of the population
	 * @param pos is the initial position of the individual in the grid.
	 * @param pop is the population where the individual belongs.
	 */
	Individual( Point pos, Population pop) {
		this.id = pop.nextID;
		this.position = new Point(pos.getX(), pos.getY());	
		this.pop = pop;
		pop.nextID++;
		
		if(pop.grid != null) {
			this.newVisitedHist();
		}
	}
	
	/**
	 *This method allows the individual to reproduce.
	 *It starts by creating a new instance of Individual, the child. The child
	 *always receives the first 90% of the parent's path, and a fraction comfort
	 *of the remaining 10% is also given. The child's parameter are updated and the
	 *child is added to the same population of the parent.
	 *@return The child of the indiviudal.
	 */
	protected Individual makeChild() {
		
		int chunk = 0;
		
		//compute the length of the parent's path
		int pathLength = getPathLen();
		
		//create a new individual
		Individual child = new Individual( this.position, this.pop);
		
		//compute the chunk of the parent's path to copy to the child
		chunk = (int) Math.ceil( 0.9 * pathLength + comfort * 0.1 * pathLength );

		//copy the parent's path and update the child's parameters
		//probably not the most efficient way...
		int i = 0;	
		for(Point p : path) {
			if(i < chunk) {
				child.setPosition( new Point( p.getX() , p.getY() ) );
				child.update();
				i++;
			}
			else
				break;	
		}
							
		//add child to the population where it lives
		pop.addInd(child); 
		
		return child;
	} 
	
	/**
	 * A setter for the Individual's position.
	 * @param pos the Point to be set as the individual's new position.
	 */
	protected void setPosition(Point pos) {
		this.position.setX(pos.getX());
		this.position.setY(pos.getY());
	}
	
	/**
	 *This method updated the fields of the individual, namely it's
	 *visited positions, the path, length, cost, dist and comfort.
	 *It also detects whether the path of the individuals has any loops,
	 *in which case the loop is removed.
	 */
	protected void update() {
		
		//if the current position has been visited remove the path loop
		if( currPosVisited() ) {
			removePathLoop();
		//add new position as visited
		}else {
			setVisited(position);
			updatePath();
			updateLength();
			if(path.size() >= 2)
				updateCost( path.get(path.size()-2), path.get(path.size()-1));
		}
		updateDist();
		updateComfort();
		
		//check if the individual has reached the goal position
		if( reachedGoal == false && position.equals(pop.goal) ) reachedGoal = true;
		if(pop.sim.goalReached == false && position.equals(pop.goal)) pop.sim.goalReached = true;
	}
	
	/**
	 *This method updates the distance of the individual by calculating
	 *the distance between it's position and the goal.
	 */
	protected void updateDist() {
		this.dist = pop.grid.getDistanceToGoal(this.position);
	}
	
	/**
	 *This method updates the path of the individual by adding it's current position
	 *to the path.
	 */
	protected void updatePath() {
		Point p = new Point( this.position.getX(), this.position.getY() );
		path.add( p );
	}
	
	/**
	 *This method updates the length of the individual's path by one or by 0 if the
	 *path has length 1.
	 */
	protected void updateLength() {
		//if no step has been given the length is zero
		if(this.path.size() == 1) length = 0;
		//otherwise increase by one
		else this.length++;
	}
	
	/**
	 *This method updates the cost of the individual by adding the cost of a step
	 *to the previous cost.
	 *@param origin the initial point of the step.
	 *@param destination the final point of the step.
	 */
	protected void updateCost(Point origin, Point destination) {
		
		int pathSize = getPathLen();
		
		if(pathSize == 1) return;
		
		cost = cost + pop.grid.getStepCost(origin, destination);
	
	}
	
	/**
	 *This method updates the comfort of an individual according to it's parameters
	 */
	protected void updateComfort() {
	
		double aux1 =  1 - (double)(cost - length + 2)/( (pop.grid.cmax - 1) * length + 3 );
		double aux2 =  1 - (double) dist / ( pop.grid.dimN + pop.grid.dimM + 1 )  ;
		this.comfort = (Math.pow(aux1, pop.comfortParam) * Math.pow(aux2, pop.comfortParam));
	}
	
	/**
	 *This method removes loops from the path. It must be called in the moment
	 *when a loop is detected. It starts by deleting the path from the point of origin
	 *of the loop on and then updates the length and cost of the individual.
	 */
	protected void removePathLoop() {
		
		//get the index of the point where the loop starts
		int deletePoint = this.path.indexOf(this.position);
				
		//delete the path from the origin of the loop on
		int i = 0;	
		for(Iterator<Point> iter = path.iterator(); iter.hasNext(); ) {
			Point point = iter.next();
			if( i <= deletePoint ) {
				i++;
				continue;
			}
			setNotVisited(point);
			iter.remove();	
		} 
		
		//this.cost = this.path.size();
		this.length = this.path.size()-1;
		
		//compute the cost again (there must be a better way of doing it...)
		this.cost = 0;
		Point prevPoint = getPathFirst();
		for(Iterator<Point> iter = path.iterator(); iter.hasNext(); ) {
			
			Point presPoint = iter.next();
			
			updateCost(prevPoint, presPoint);
			
			prevPoint = presPoint;
		} 
		
	}
	
	/**
	 * Makes a complete copy of the individual's path.
	 * @return A LinkedList of Points representing the path.
	 */
	protected List<Point> copyPath(){

		List<Point> pathCopy = new LinkedList<Point>();
		
		for(Point p : path) {
			pathCopy.add( new Point( p.getX() , p.getY() ) );
		}
		
		return pathCopy;
	}
	
	/**
	 *This method retrieves the first point in the Individual's path.
	 *@return The first point of the path of the individual.
	 */
	protected Point getPathFirst() {
		return path.get(0);
	}
	
	/**
	 * This method retrieved the size of the path of the individual.
	 * It is provided to reduce the need of change in code in case a redefinition of
	 * Individual is made with a different data structure for the path.
	 * @return An int with the length of the path.
	 */
	protected int getPathLen() {
		return this.path.size();
	}
	
	/**
	 *This method creates a new table of visited with the dimensions of the grid where the individual lives.
	 */
	protected void newVisitedHist() {
		visited = new boolean[pop.grid.dimM][pop.grid.dimN]; //default as false
	}
	
	/**
	 *Setter for the visited table as true.
	 *@param p the point to be marked as visited.
	 */
	protected void setVisited(Point p) {
		visited[p.getY()-1][p.getX()-1] = true;
	}
	
	/**
	 *Setter for the visited table as false.
	 *@param p the point to be marked as not visited.
	 */
	protected void setNotVisited(Point p) {
		
		visited[p.getY()-1][p.getX()-1] = false;
	}
	
	/**
	 *This method tells whether the current position of the Individual has been reached.
	 *@return boolean that is set as true if the position has been visited and as false otherwise.
	 */
	protected boolean currPosVisited() {
		return visited[this.position.getY()-1][this.position.getX()-1];
	}
	
	/**
	 *Getter for the ID field.
	 *@return the ID if the individual.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 *Redefinition of the toString() method.
	 */
	@Override
	public String toString() {
		return "\nID: " + id + "; Cost: " + cost + "; Comf: " + comfort + "; Pos: " + position;
	}

}
