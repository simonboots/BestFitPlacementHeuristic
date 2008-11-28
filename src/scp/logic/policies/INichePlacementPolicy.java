package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

/**
 * Interface for niche placement policies
 * @author sst
 *
 */
public interface INichePlacementPolicy {
	/**
	 * Place shape
	 * @param s shape to be placed
	 * @param g gap in which shapewill be placed
	 * @return placed shape
	 */
	public PlacedShape placeShape(Shape s, Gap g);
}
