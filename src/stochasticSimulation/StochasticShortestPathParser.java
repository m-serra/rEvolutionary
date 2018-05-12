package stochasticSimulation;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class StochasticShortestPathParser extends  DefaultHandler{
		
	protected boolean zcost = false;
	
	protected String finalinst, initpop, maxpop, comfortsens, 
		   colsnb, rowsnb, xinitial, yinitial, xfinal, yfinal, 
		   zxinitial, zyinitial, zxfinal, zyfinal, 
		   num, numSpZones, xpos, ypos, cost, dparam, rparam, mparam;
	
	protected int nObstacles, nSpZones = 0, maxCost = 1;
		
	protected Obstacle obstacles[];
	protected SpecialZone spZones[];			
	protected Population pop;
	protected Point initPoint = new Point(0,0);
	
	public Obstacle[] getObstacles() {
		return obstacles;
	}
	
	public int getMapRows() {
		return Integer.parseInt(rowsnb);
	}
	
	public int getMapCols() {
		return Integer.parseInt(colsnb);
	}
			
	public int getMapCMax() {
		return maxCost;
	}
	
	public SpecialZone[] getSpZones() {
		/*SpecialZone[] spZonesArray = new SpecialZone[spZones.size()];
		//copy list to array
		for (int i = 0; i < spZones.size(); i++) {
			spZonesArray[i] = spZones.getFirst();
		} */
		return spZones;
	}
	
	public double getFinalinst() {
		return Double.parseDouble(finalinst);
	}
	
	public Point getIntialPoint() {
		Point initialPoint = new Point( 
									Integer.parseInt(xinitial), 
									Integer.parseInt(yinitial));
		return initialPoint;
	}
	
	public Point getFinalPoint() {
		Point finalPoint = new Point( 
									Integer.parseInt(xfinal), 
									Integer.parseInt(yfinal));
		return finalPoint;
	}
	
	public int getInitpop() {
		return Integer.parseInt(initpop);
	}
	
	public Population getPopulation() {
		
		pop = new Population( 
				Integer.parseInt(initpop), 
				Integer.parseInt(maxpop),
				Integer.parseInt(comfortsens),
				initPoint
				);
		return pop;
	}
		
	public int getDeathParam() {
		return Integer.parseInt(dparam);
	}
	
	public int getReproductionParam() {
		return Integer.parseInt(rparam);
	}
	
	public int getMoveParam() {
		return Integer.parseInt(mparam);
	}
	
	@Override
	public void startElement( String uri, String localName, String qName, Attributes attributes) throws SAXException{
		
		if(qName.equalsIgnoreCase("simulation")) {
			finalinst =  attributes.getValue("finalinst");
			initpop =  attributes.getValue("initpop");
			maxpop =  attributes.getValue("maxpop");
			comfortsens =  attributes.getValue("comfortsens");
		}
		
		else if(qName.equalsIgnoreCase("grid")) {
			colsnb = attributes.getValue("colsnb");
			rowsnb = attributes.getValue("rowsnb");
		}
		else if(qName.equalsIgnoreCase("initialpoint")) {
			xinitial = attributes.getValue("xinitial");
			yinitial = attributes.getValue("yinitial");
			
			initPoint.setX(Integer.parseInt(xinitial));
			initPoint.setY(Integer.parseInt(yinitial));
		}
		else if(qName.equalsIgnoreCase("finalpoint")) {
			xfinal = attributes.getValue("xfinal");
			yfinal = attributes.getValue("yfinal");
		}
		else if(qName.equalsIgnoreCase("specialcostzones")) {
			numSpZones = attributes.getValue("num");
			spZones = new SpecialZone[Integer.parseInt(numSpZones)];
		}
		else if(qName.equalsIgnoreCase("zone")) {
			zxinitial = attributes.getValue("xinitial");
			zyinitial = attributes.getValue("yinitial");
			zxfinal = attributes.getValue("xfinal");
			zyfinal = attributes.getValue("yfinal");
			
			Point i = new Point(
								Integer.parseInt(zxinitial), 
								Integer.parseInt(zyinitial));
			
			Point f = new Point(
								Integer.parseInt(zxfinal), 
								Integer.parseInt(zyfinal));
			
			spZones[nSpZones] = new SpecialZone(0, i, f);
		
			zcost = true;
		}
		else if(qName.equalsIgnoreCase("obstacles")) {
			num = attributes.getValue("num");
			obstacles = new Obstacle[Integer.parseInt(num)];
		}
		else if(qName.equalsIgnoreCase("obstacle")) {
			xpos = attributes.getValue("xpos");
			ypos = attributes.getValue("ypos");
			
			obstacles[nObstacles] = new Obstacle(
												Integer.parseInt(xpos), 
												Integer.parseInt(ypos));
			nObstacles++;
		}
		
		else if(qName.equalsIgnoreCase("death")) {
			dparam = attributes.getValue("param");
		}
		else if(qName.equalsIgnoreCase("reproduction")) {
			rparam = attributes.getValue("param");
		}
		else if(qName.equalsIgnoreCase("move")) {
			mparam = attributes.getValue("param");
		}
		
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException{
		if(zcost) {
			cost = new String(ch, start, length);
			
			if(maxCost < Integer.parseInt(cost))
				maxCost = Integer.parseInt(cost);
			
			spZones[nSpZones].setCost(Integer.parseInt(cost));
			nSpZones++;
			
			zcost = false;
		}
	}
	
}
