package scp.logic.policies;

import scp.common.PlacedShape;
import scp.common.Shape;
import scp.logic.Gap;

public class PlaceAtRightmostPolicy implements INichePlacementPolicy {

	public PlacedShape placeShape(Shape s, Gap g) {
		return new PlacedShape(s, g.getLocation() + g.getWidth() - 1, g.getHeight());
	}
}
