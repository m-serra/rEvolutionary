package stochasticSimulation;
import java.util.Map;

/**
 * The Grid class which implements the Gird interface.
 * In the context of a StochasticSimulation it is used to host the individuals of a Population, that
 * can find a shortest path between points by moving and evolving within the grid. 
 * 
 * @author Manuel Serra
 */
public class Grid implements IGrid{
	
	/**
	 * the number of columns in the grid.
	 */
	protected int dimN; // number of columns - x
	/**
	 * the number of rows in the grid.
	 */
	protected int dimM; // number of rows - y
	/**
	 * the maximum cost of a move in the grid.
	 */
	protected int cmax = 1; // maximum cost of an edge (default 1)
	/**
	 * a table that keeps the costs of the grid.
	 */
	protected int costMap[][]; //a matrix with the cost of each position
	/**
	 * Point representing the goal if a best path is to be found.
	 */
	protected Point goal = null; //a grid may or may not have a goal, defined with a setter
	/**
	 * an array of Obstacles that are present in the grid configuration.
	 */
	protected Obstacle[] obstacles;
	/**
	 * an array of SpecialZones which are present in the grid configuration.
	 */
	protected SpecialZone[] spZones;
	
	/**
	 *Constructor for a simple grid with no obstacles or special cost zones.
	 * @param N is the number of columns of the grid.
	 * @param M is the number of rows in the grid.
	 */
	public Grid(int N, int M) {
		dimN = N;
		dimM = M;
	}
	
	/**
	 * Constructor for a grid with no obstacles but with special zones.
	 * @param N is the number of columns of the grid.
	 * @param M is the number of rows in the grid.
	 * @param C is the maximum cost of a special zone.
	 * @param spZones is an array of the special zones existing in the grid.
	 */
	public Grid(int N, int M, int C, SpecialZone[] spZones) {
		dimN = N;
		dimM = M;
		cmax = C;
		costMap = new int[dimM][dimN];
		this.spZones = spZones;
		setCostMap();
	}
	
	/**
	 * Constructor for a grid with no special zones but with obstacles. 
	 * @param N is the number of columns of the grid.
	 * @param M is the number of rows in the grid.
	 * @param obstacles is an array of the obstacles existing in the grid.
	 */
	public Grid(int N, int M, Obstacle[] obstacles) {
		dimN = N;
		dimM = M;
		this.obstacles = obstacles;
	}

	/**
	 * Constructor for a grid with both obstacles and special zones.
	 * @param N is the number of columns of the grid.
	 * @param M is the number of rows in the grid.
	 * @param C is the maximum cost of a special zone.
	 * @param spZones is an array of the special zones existing in the grid.
	 * @param obstacles is an array of the obstacles existing in the grid.
	 */
	public Grid(int N, int M, int C, SpecialZone[] spZones, Obstacle[] obstacles) {
		dimN = N;
		dimM = M;
		cmax = C;
		this.spZones = spZones;
		this.obstacles = obstacles;
		newCostMap();
		setCostMap();
	}
	
	/**
	 *This method allows the best path between to Points in a grid to be found.
	 *It instantiates a StochasticShortestPath and runs the simulate() method.
	 *Redefinitions of the method can be made to compute the best path between two
	 *points with other algorithms such as Dijkstra's algorithm.
	 * @param origin is the initial point of the shortest path to be found.
	 * @param goal is the destination point of the shortest path to be found.
	 * @param params are the parameters to initialize the StochasticsShortestPath object.
	 */
	public void findBestPath(Point origin, Point goal, Map<String, Object> params) {
							
		this.goal = goal;
		
		//set the initial position of the population and the grid where it lives
		Population pop = (Population)params.get("pop");
		pop.setGrid(this);
		pop.setInitPos(origin);
		
		//Instantiate a new Stochastic Simulation to find the shortest path
		StochasticSimulation stochSim = new StochasticShortestPath(		
																	pop,
																	this,
																	(Integer) params.get("move"),
																	(Integer) params.get("death"),
																	(Integer) params.get("repr"),
																	(Double) params.get("finalinst"));
		
		//associate all future stochastic events and the population with this StochasticSimulation
		StochasticEvent.setSim( stochSim );
		pop.setSim( stochSim );
		pop.setGoal(goal);
					
		//Run the StochasticSimulation
		stochSim.simulate();

	}
	
	/**
	 *This method allows allows an individual of the population of a stochasticSimulation to give steps in the grid.
	 *It checks the valid moves according to the grids limits and obstacles and selects a random move from the pool of valid moves.
	 *After the move is selected the position of the individual is changes accordingly.
	 *If no valid move is found the position is maintained as it was.
	 * @param ind is the individual that will give a step in the grid.
	 */
	public void step( Individual ind){
		
		int move, moveIndx;
		Integer[] validMoves;
		
		//get the pool of valid moves.
		validMoves = getValidMoves( ind.position );
		
		//no valid move found
		if(validMoves.length == 0) return;

		//get random index from the validMoves array
		moveIndx = StochasticSimulation.random.nextInt( validMoves.length );
		move = validMoves[moveIndx];
		
		//go up	
		if(move == 0) ind.position.incrementY();
			
		//go right	
		else if(move == 1) ind.position.incrementX();
		
		//go down
		else if(move == 2) ind.position.decrementY();	

		//go left
		else if(move == 3) ind.position.decrementX();			
	}
	
	/**
	 *This method computes the cost of a step in the grid, according to the grid costs.
	 *Since the costs are set for each square in costMap, the move cost can be computed as follows:
	 * 1) a move has cost 1 if the origin or destination of the move has cost 1.
	 * 2) a move has cost different than one if it is taken between two squares of cost
	 * different than one. In this case the cost of the move is the cost of the destination square.
	 * The moves are coded as follows:
	 * 0 - up ; 1 - right ; 2 - down ;  3 - left
	 * @param origin is the initial point of the step
	 * @param destination is the final point of the step
	 * @return An integer with the cost of the step
	 */
	protected int getStepCost(Point origin, Point destination) {
		
		//if the origin or the destination of the move are not special zones
		//the cost of the move will be 1
		if( costMap[origin.getY()-1][origin.getX()-1] == 1 
			|| 
			costMap[destination.getY()-1][destination.getX()-1] == 1) { 
			return 1;
		}
		//if the move is between zones of special cost the cost of the move is
		//the cost associated with the destination position
		else
			return costMap[destination.getY()-1][destination.getX()-1];

	}
	
	/**
	 *This method retrieves the possible moves from a position given as input.
	 *It tries out every move and checks if the resulting position is an object or is out
	 *of the grid's bounds. If so, the move is invalid, otherwise it's a valid move.
	 *The moves are coded as follows:
	 * 0 - up ; 1 - right ; 2 - down ;  3 - left
	 *@param indPosition the Point where the move is taking place from.
	 * @return An Integer array with the predefined codes of the valid moves.
	 */
	protected Integer[] getValidMoves( Point indPosition ) {
		
		int validMoves[] = {-1, -1, -1, -1};
		int validCount = 0;
		Point next = new Point( indPosition.getX() , indPosition.getY());
		
		//iterate all possible move codes (0 to 3)
		//simulate every move and check its validity
		for( int move = 0; move < 4; move++) {
			
			//go up
			if(move == 0){ 
				
				next.incrementY();
				
				//if the new coordinates are not an obstacle and don't go over the grid's limits
				if( ! ( isObstacle(next) || next.getY() > dimM ) ) {
					//add the move to the list of valid moves
					validMoves[validCount] = move;
					validCount++;
				}
				next.decrementY();
			}
			//go right
			else if(move == 1) {
				
				next.incrementX();
				if( ! ( isObstacle(next) || next.getX() > dimN ) ) {
					validMoves[validCount] = move;
					validCount++;
				}
				next.decrementX();
			}
			//go down
			else if(move == 2) {
				next.decrementY();
				if( ! ( isObstacle(next) || next.getY() <= 0 ) ) {
					validMoves[validCount] = move;
					validCount++;
				}
				next.incrementY();
			}
			//go left
			else if(move == 3) {
				next.decrementX();
				if( ! ( isObstacle(next) || next.getX() <= 0 ) ) {
					validMoves[validCount] = move;
					validCount++;
				}
				next.incrementX();
			}
		}
		
		//return an array containing only the codes of the valid moves
		Integer[] validMovesOut = new Integer[validCount];
		for(int i = 0; i < validCount; i++) {
			validMovesOut[i] = validMoves[i]; 
		}
		
		return validMovesOut;
	}
	
	/**
	 *This method tells if a given point in the grid is an obstacle.
	 * @param point is the point that the method must tell if it is an obstacle or not
	 * @return a boolean indicating that the point is an obstacle if true or not, if false.
	 */
	private boolean isObstacle(Point point) {
		
		for(Obstacle obst : obstacles) {
			if(point.equals( obst ) )
				return true;
		}
		
		return false;
	}
		
	/**
	 *This method computes the distance between a point and the goal in hops, 
	 *independently of the presence of obstacles.
	 * @param point the point from where the distance to the goal is to be calculated.
	 * @return An integer with the value of the distance.
	 */
	protected int getDistanceToGoal(Point point) {
		
		return Math.abs( goal.getY() - point.getY() ) + Math.abs( goal.getX() - point.getX() );
		
	}
	
	/**
	 *This method initializes the cost map according to the special cost zones array. It assumes
	 *that the cost map is implemented as a table so it would have to be redefined if a different implementation
	 *is desired.
	 */
	protected void setCostMap() {
			
		SpecialZone currZone;
		int maxX=0, maxY=0,minX=0, minY=0;
		
		//initialize all with 1
		for(int i = 0; i < dimN; i++) {		//x
			for(int j = 0; j < dimM; j++) { //y
				costMap[j][i] = 1;
			}
		}
		
		//iterate all grid coordinates
		for(int i = 0; i < dimN; i++) {		//x
			for(int j = 0; j < dimM; j++) { //y
				
				//iterate all specialZones
				for(int k = 0; k < spZones.length; k++) {
					currZone = spZones[k];
					maxX = Math.max(currZone.pInitial.getX(), currZone.pFinal.getX());
					minX = Math.min(currZone.pInitial.getX(), currZone.pFinal.getX());
					maxY = Math.max(currZone.pInitial.getY(), currZone.pFinal.getY());
					minY = Math.min(currZone.pInitial.getY(), currZone.pFinal.getY());
						
					if( (i+1 >= minX && i+1 <= maxX && j+1 >= minY && j+1 <= maxY) 
						&&
						(i+1 == minX || i+1 == maxX || j+1 == minY || j+1 == maxY)){
			
						if(costMap[j][i] < currZone.cost) //always keep the maximum cost of a zone
							costMap[j][i] = currZone.cost;
					}		
				}
			}
		}	
	}
		
	/**
	 *This method declares a new cost map with the grid dimensions.
	 */
	protected void newCostMap() {
		this.costMap = new int[dimM][dimN];
	}
}

