package main;

import java.io.File;
import javax.xml.parsers.*;
import java.util.HashMap;
import java.util.Map;


//import stochasticShortestPath.Grid;
//import stochasticShortestPath.StochasticShortestPathParser;
//import stochasticShortestPath.Point;
import stochasticSimulation.*;


/**
 *  
 * This is the main class.
 * It receives as input a .xml file with the configuration of the map.
 * An exception is thrown if the file isn't correctly read.
 * After the file has been parsed, a Grid is instantiated and initialized and
 * the method grid.findBestPath() is called between the initial and final point
 * determined in the input file.
 * This also function received as parameter a Map containing the parameters needed for a
 * stochastic simulation.
 * 
 *@author Manuel Serra 
 */
public class Main {

	public static void main(String[] args){
			
		try {
			
			File inputFile = new File(args[0]);
			SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser saxParser = fact.newSAXParser();
			StochasticShortestPathParser handler = new StochasticShortestPathParser();
			saxParser.parse( inputFile, handler);
			
			
			Grid grid = new Grid( 
								handler.getMapCols(), 
								handler.getMapRows(), 
								handler.getMapCMax(),
								handler.getSpZones(),
								handler.getObstacles());
			
			Map<String, Object> params = new HashMap<>();
			params.put("pop", handler.getPopulation());
			params.put("finalinst", handler.getFinalinst());
			params.put("death", handler.getDeathParam());
			params.put("repr", handler.getReproductionParam());
			params.put("move", handler.getMoveParam());
	
			Point a = handler.getIntialPoint();
			Point b = handler.getFinalPoint();
			
			grid.findBestPath(a, b, params);
								
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

} 
