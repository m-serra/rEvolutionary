package stochasticSimulation;

/**
 * The class special zone defines special cost zones within a Grid.
 * Special cost zones have higher cost of movement than normal zones.
 * The zone is defined by the initial and final point of a square.
 * @author ManuelSerraNunes
 *
 */
class SpecialZone {
	
	/**
	 * The cost of the special cost zone.
	 */
	protected int cost;
	/**
	 * The initial point of the square that forms the special cost zone.
	 */
	protected Point pInitial;
	/**
	 * The final point of the square that forms the special cost zone.
	 */
	protected Point pFinal;
	
	/**
	 * Constructor for SpecialCost zones.
	 * @param cost the cost of the zone
	 * @param i the initial point of the square that forms the special cost zone.
	 * @param f the final point of the square that forms the special cost zone.
	 */
	SpecialZone(int cost, Point i, Point f){
		this.cost = cost;
		pInitial = i;
		pFinal = f;
	}
	
	/**
	 * Setter for the the cost
	 * @param cost the cost to be set.
	 */
	protected void setCost(int cost){
		this.cost = cost;
	}
	
	/**
	 * Redefinition of the toString method to retrieve all the fields of the zone.
	 */
	@Override
	public String toString() {
		return "cost: " + cost + "; Initial: " + pInitial + "; Final: " + pFinal;
	}
}
