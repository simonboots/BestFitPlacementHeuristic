package scp.common.actionchain;

import java.util.List;
import scp.common.*;

/**
 * Interface for ShapeManagzine
 * @author sst
 *
 */
public interface IShapeMagazine {
	
	/**
	 * Loads magazine with shapes
	 * @param shapelist shapes to load
	 */
	public void loadMagazine(List<Shape> shapelist);
	
	/**
	 * Highlight shape
	 * @param s shape to highlight
	 */
	public void highlightShape(Shape s);
	
	/**
	 * Unhighlight all shapes 
	 */
	public void unhighlightAllShapes();
	
	/**
	 * Remove shape from magazine
	 * @param s shape to be removed
	 */
	public void removeShape(Shape s);
	
	/**
	 * Insert shape to magazine
	 * @param s shape to be added
	 */
	public void insertShape(Shape s);
}
