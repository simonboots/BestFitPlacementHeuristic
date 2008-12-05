package scp.common.actionchain.actions;

import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * unoptimize Shape
 * @author Benjamin Clauss
 */
public class UnoptimizeShapeAction implements IAction {

	PlacedShape s = null;

	/**
	 * @param s PlacedShape to be optimized
	 */
	public UnoptimizeShapeAction(PlacedShape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.removeShape(s);
		placer.unhighlightAllGaps();
		placer.placeShape(s);
		placer.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new OptimizeShapeAction(s);
	}

}
