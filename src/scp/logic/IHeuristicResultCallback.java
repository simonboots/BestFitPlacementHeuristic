package scp.logic;

import scp.common.*;

public interface IHeuristicResultCallback {
	public void sortedShapeCallback(Shape s);
	public void placementsCallback(IPlaceableObject po);
	public void optimizedPlacementsCallback(IPlaceableObject po);
}
