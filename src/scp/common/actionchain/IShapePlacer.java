package scp.common.actionchain;

import scp.common.Gap;
import scp.common.PlacedShape;

/**
 * Interface for ShapePlacer
 * @author sst
 *
 */

public interface IShapePlacer {
	
	/**
	 * Place shape on stock roll
	 * @param s shape to be placed
	 */
	public void placeShape(PlacedShape s);
	
	/**
	 * Remove shape from stock roll
	 * @param s shape to be removed
	 */
	public void removeShape(PlacedShape s);
	
	/**
	 * Highlight shape on stock roll
	 * @param s shape to be highlighted
	 */
	public void highlightShape(PlacedShape s);
	
	/**
	 * Unhighlight all shapes on stock roll
	 */
	public void unhighlightAllShapes();
	
	/**
	 * Highlight gap
	 * @param g gap to be highlighted
	 */
	public void highlightGap(Gap g);

	/**
	 * Unhighlights all gaps
	 */
	public void unhighlightAllGaps();

}
