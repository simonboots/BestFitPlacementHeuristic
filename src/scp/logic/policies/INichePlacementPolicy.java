package scp.logic.policies;

import scp.common.PlacedShape;
import scp.common.Shape;
import scp.logic.Gap;

public interface INichePlacementPolicy {
	public PlacedShape placeShape(Shape s, Gap g);
}
