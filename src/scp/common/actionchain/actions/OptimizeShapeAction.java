package scp.common.actionchain.actions;

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
	
	/**
	 * @param s PlacedShape to be optimized
	 */
	public OptimizeShapeAction(PlacedShape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.removeShape(s);
		placer.placeShape(s);
		placer.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new UnoptimizeShapeAction(s);
	}
}