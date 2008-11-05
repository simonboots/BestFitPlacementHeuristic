package scp.logic.policies;

import scp.common.PlacedShape;
import scp.common.Shape;
import scp.logic.Gap;

public class PlaceAtLeftmostPolicy implements INichePlacementPolicy {

	public PlacedShape placeShape(Shape s, Gap g) {
		return new PlacedShape(s, g.getLocation(), g.getHeight());
	}

}
