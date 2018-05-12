package stochasticSimulation;
import java.util.Comparator;

/**
 * The comparator class for Individual class. 
 * It compares individuals by their comfort.
 *
 *@author Manuel Serra
 */
public class IndividualComparator implements Comparator<Individual>{

	/**
	 * Redefinition of the compare method to compare two Individuals by their comfort.
	 */
	@Override
	public int compare(Individual indA, Individual indB) {
		
		if(indA.comfort < indB.comfort)
			return 1;
		else if(indA.comfort > indB.comfort)
			return -1;
		else
			return 0;
		
	}
	
}