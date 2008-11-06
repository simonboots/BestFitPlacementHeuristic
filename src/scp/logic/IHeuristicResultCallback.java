package scp.logic;

import scp.common.*;

public interface IHeuristicResultCallback {
	public void sortedShapeCallback(Shape s);
	public void placedShapeCallback(PlacedShape ps);
	public void optimizedPlacedShapeCallback(PlacedShape ps);
}
