package stochasticSimulation;

import java.util.Map;

/**
 * The interface for Grids.
 * It states that in the in the context of a stochastic simulation an implementation
 * of a grid must provide a findBestPath() between two points method and a step() method.
 * 
 * @author Manuel Serra
 */
public interface IGrid {
	void findBestPath(Point a, Point b, Map<String, Object> params);
	void step( Individual ind);
}
