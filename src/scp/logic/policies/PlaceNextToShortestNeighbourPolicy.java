package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

public class PlaceNextToShortestNeighbourPolicy implements INichePlacementPolicy {

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
