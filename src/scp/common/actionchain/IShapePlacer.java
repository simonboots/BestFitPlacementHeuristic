package scp.common.actionchain;

import scp.common.Gap;
import scp.common.PlacedShape;

public interface IShapePlacer {
	
	public void placeShape(PlacedShape s);
	public void removeShape(PlacedShape s);
	public void highlightShape(PlacedShape s);
	public void unhighlightAllShapes();
	public void highlightGap(Gap g);
	public void removeGap();
}
