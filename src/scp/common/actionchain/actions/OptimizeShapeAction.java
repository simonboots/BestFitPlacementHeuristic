package scp.common.actionchain.actions;

import java.util.List;

import scp.common.IPlaceableObject;
import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * optimize PlacedShape
 * @author Benjamin Clauss
 */
public class OptimizeShapeAction implements IAction {
	
	PlacedShape s = null;
	List<IPlaceableObject> placedlist = null;
	
	/**
	 * @param s PlacedShape to be optimized
	 */
	public OptimizeShapeAction(PlacedShape s, List<IPlaceableObject> placedlist) {
		this.s = s;
		this.placedlist = placedlist;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.removeShape(s);
		placer.unhighlightAllGaps();
		placer.placeShape(s);
		placer.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new UnoptimizeShapeAction(s, placedlist);
	}
}