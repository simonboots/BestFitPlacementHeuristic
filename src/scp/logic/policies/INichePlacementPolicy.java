package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

public interface INichePlacementPolicy {
	public PlacedShape placeShape(Shape s, Gap g);
}
