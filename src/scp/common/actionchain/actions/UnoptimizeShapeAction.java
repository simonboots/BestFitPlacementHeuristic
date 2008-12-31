package scp.common.actionchain.actions;

import java.util.List;

import scp.common.IPlaceableObject;
import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * unoptimize Shape on stockroll
 * @author Benjamin Clauss
 */
public class UnoptimizeShapeAction implements IAction {

	PlacedShape ops = null;
	List<IPlaceableObject> placedlist = null;

	/**
	 * @param s PlacedShape to be optimized
	 */
	public UnoptimizeShapeAction(PlacedShape ops, List<IPlaceableObject> placedlist) {
		this.ops = ops;
		this.placedlist = placedlist;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.removeShape(ops);
		placer.unhighlightAllGaps();
		placer.placeShape(getUnoptimizedShape(ops));
		placer.highlightShape(getUnoptimizedShape(ops));
	}

	public IAction getReverseAction() {
		return new OptimizeShapeAction(ops, placedlist);
	}

	private PlacedShape getUnoptimizedShape(PlacedShape ops) {
		PlacedShape uops = null;
		for (IPlaceableObject obj : placedlist) {
			if ((obj instanceof PlacedShape) && (obj.getId() == ops.getId())) {
				uops = ((PlacedShape) obj);
			}
		}
		return uops;
	}
}