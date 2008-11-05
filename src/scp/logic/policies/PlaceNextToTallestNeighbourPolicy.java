package scp.logic.policies;

import scp.common.PlacedShape;
import scp.common.Shape;
import scp.logic.Gap;

public class PlaceNextToTallestNeighbourPolicy implements INichePlacementPolicy {

	public PlacedShape placeShape(Shape s, Gap g) {
		int newX = 0;
		
		if (g.getLeftHeight() > g.getRightHeight()) {
			newX = g.getLocation();
		} else {
			newX = (g.getLocation() + g.getWidth() - s.getWidth());
		}
		return new PlacedShape(s, newX, g.getHeight());
	}

}
