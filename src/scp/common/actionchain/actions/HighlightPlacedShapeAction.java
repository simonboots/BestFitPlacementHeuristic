package scp.common.actionchain.actions;

import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class HighlightPlacedShapeAction implements IAction {

	PlacedShape s = null;
	
	public HighlightPlacedShapeAction(PlacedShape s) {
		this.s = s;
	}
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new UnhighlightPlacedShapeAction(s);
	}

}
