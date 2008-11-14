package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

public class PlaceAtRightmostPolicy implements INichePlacementPolicy {

	public PlacedShape placeShape(Shape s, Gap g) {
		return new PlacedShape(s, g.getX() + g.getWidth() - s.getWidth(), g.getY());
	}
}
