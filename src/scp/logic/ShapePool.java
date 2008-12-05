package scp.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import scp.common.Shape;
import scp.common.ShapeSortBySizeComparator;

/**
 * Shape pool
 * 
 * @author sst
 *
 */
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
	
	/**
	 * adds shape to shapelist
	 * @param s shape
	 */
	public void add(Shape s) {
		shapeList.add(s);
	}
	
	/**
	 * @return size of shapelist
	 */
	public int size() {
		return shapeList.size();
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Shape> iterator() {
		return shapeList.iterator();
	}
	
	/**
	 * Rotates all shapes in pool so that they are wider than higher
	 */
	private void rotateIfNeeded() {
		for (Shape s: shapeList) {
			s.makeWiderThanHigher();
		}
	}
	
    /**
     * Rotates and sorts all shapes in pool according to size (big to small)
     */
    public void sort() {
    	rotateIfNeeded();
    	Collections.sort(shapeList, new ShapeSortBySizeComparator());
    }
    
    /**
     * Searches for best fitting shape for width
     * 
     * This method may rotate the shape if it fits better when it is rotated
     * Shape is then removed from pool
     * 
     * @param width width
     * @return best fitting shape
     */
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
    		if (s.getWidth() > width && !s.isSquare()) {
    			
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
    
    /**
     * Forces all shapes in pool to match size constraints
     * 
     * @param mindim minimal length for width and length
     * @param maxdim maximal length for width or length
     * @return list of rejected shapes
     */
    public List<Shape> forceSizeConstraints(int mindim, int maxdim) {
    	List<Shape> rejectedShapes = new ArrayList<Shape>();
    	for (Shape s : shapeList) {
    		// check min dimension
    		if (s.getWidth() < mindim || s.getHeight() < mindim) {
    			rejectedShapes.add(s);
    			continue;
    		}
    		
    		// check max dimension
    		if (s.getWidth() > maxdim && s.getHeight() > maxdim) {
    			rejectedShapes.add(s);
    		}
    	}
    	
    	for (Shape s : rejectedShapes) {
    		shapeList.remove(s);
    	}
    	
    	return rejectedShapes;
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
