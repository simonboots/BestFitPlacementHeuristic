package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

/**
 * Rightmost policy
 * 
 * Places shape at the rightmost location in gap
 * 
 * @author sst
 *
 */
public class PlaceAtRightmostPolicy implements INichePlacementPolicy {

	/* (non-Javadoc)
	 * @see scp.logic.policies.INichePlacementPolicy#placeShape(scp.common.Shape, scp.common.Gap)
	 */
	public PlacedShape placeShape(Shape s, Gap g) {
		return new PlacedShape(s, g.getX() + g.getWidth() - s.getWidth(), g.getY());
	}
}
