package scp.logic;

import scp.common.*;

/**
 * Interface to Heuristic callback methods
 * @author sst
 *
 */
public interface IHeuristicResultCallback {
	/**
	 * Gets called for every sorted shape
	 * @param s shape
	 */
	public void sortedShapeCallback(Shape s);
	
	/**
	 * Gets called for every placement step
	 * @param po placeable object
	 */
	public void placementsCallback(IPlaceableObject po);
	
	/**
	 * Gets called for every optimization step
	 * @param po placeable object
	 */
	public void optimizedPlacementsCallback(IPlaceableObject po);
}
