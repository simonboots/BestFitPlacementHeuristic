package scp.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import scp.common.Shape;
import scp.common.ShapeSortBySizeComparator;

public class ShapePool implements Iterable<Shape> {
	
	ArrayList<Shape> shapeList = null;

	public ShapePool() {
		shapeList = new ArrayList<Shape>();
	}
	
	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}

	public void setShapeList(List<Shape> shapeList) {
		this.shapeList = (ArrayList<Shape>) shapeList;
	}
	
	public void add(Shape s) {
		shapeList.add(s);
	}
	
	public int size() {
		return shapeList.size();
	}

	public Iterator<Shape> iterator() {
		return shapeList.iterator();
	}
	
	private void rotateIfNeeded() {
		for (Shape s: shapeList) {
			s.makeWiderThanHigher();
		}
	}
	
    public void sort() {
    	rotateIfNeeded();
    	Collections.sort(shapeList, new ShapeSortBySizeComparator());
    }
    
    public Shape findBestShapeforWidth(int width) {
    	Shape bestFit = null;
    	
    	for (Shape s : shapeList) {  
    		
    		// Abbruchbedingung
    		if (s.getWidth() < width) {
    			// Noch kein bestFit gefunden
    			if (bestFit == null) {
    				bestFit = s;
    			} else if (bestFit.getWidth() < s.getWidth()) {
    				bestFit.makeWiderThanHigher();
    				bestFit = s;
    			}
    			
    			break;
    		}
    		
    		// Fall 1: Shape passt exakt
    		if (s.getWidth() == width) {
    			bestFit = s;
    			break;
    		}
    		
    		// Fall 2: Shape zu breit
    		if (s.getWidth() > width) {
    			
    			// Fall 2a: Shapehoehe passt exakt
    			if (s.getHeight() == width) {
    				// Rotiere Shape und Ende
    				s.rotate();
    				bestFit = s;
    				break;
    			}
    			
    			// Fall 2b: Shapehoehe ist zu klein (ist aber Kandidat)
    			if (s.getHeight() < width) {
    				
    				// Noch kein bestFit gefunden
    				if (bestFit == null) {
    					s.rotate();
    					bestFit = s;
    					continue;
    				}
    				
    				// Besserer bestFit
    				if (bestFit.getHeight() < s.getHeight()) {
    					bestFit.makeWiderThanHigher();
    					bestFit = s;
    					continue;
    				}
    			}
    		}
    	}
    	
    	shapeList.remove(bestFit);
    	return bestFit;
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer("");
    	for (Shape s : shapeList) {
    		sb.append(s.toString() + " ");
    	}
    	sb.deleteCharAt(sb.length() - 1);
    	
    	return sb.toString();
    }
}
