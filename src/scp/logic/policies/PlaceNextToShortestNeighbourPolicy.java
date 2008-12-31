package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

/**
 * Place next to shortest neighbour policy
 * 
 * Places shape next to shortest neighbour
 * 
 * @author Simon Stiefel
 *
 */
public class PlaceNextToShortestNeighbourPolicy implements INichePlacementPolicy {

	/* (non-Javadoc)
	 * @see scp.logic.policies.INichePlacementPolicy#placeShape(scp.common.Shape, scp.common.Gap)
	 */
	public PlacedShape placeShape(Shape s, Gap g) {
		int newX = 0;
		
		if (g.getLeftHeight() < g.getRightHeight()) {
			newX = g.getX();
		} else {
			newX = (g.getX() + g.getWidth() - s.getWidth());
		}
		return new PlacedShape(s, newX, g.getY());
	}
}
