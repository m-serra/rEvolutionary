package stochasticSimulation;

/**
 * The interface for StochasticSimulations.
 * It states that an implementation of StochticSimulation must provide
 * a simulate() method and a sevaeBest() method.
 * 
 * @author Manuel Serra
 */
public interface IStochasticSimulation {
		
	public void simulate();
	void saveBest(Individual ind);
}
