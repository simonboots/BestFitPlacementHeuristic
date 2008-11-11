package scp.common.actionchain.actions;

import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class UnoptimizeShapeAction implements IAction {
	
	PlacedShape s = null;
	
	public UnoptimizeShapeAction(PlacedShape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		// TODO Auto-generated method stub

	}

	public IAction getReverseAction() {
		return new OptimizeShapeAction(s);
	}

}
