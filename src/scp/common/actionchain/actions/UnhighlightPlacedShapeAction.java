package scp.common.actionchain.actions;

import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class UnhighlightPlacedShapeAction implements IAction {
	
	PlacedShape s = null;
	
	public UnhighlightPlacedShapeAction(PlacedShape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.unhighlightAllShapes();
	}

	public IAction getReverseAction() {
		return new HighlightPlacedShapeAction(s);
	}

}
