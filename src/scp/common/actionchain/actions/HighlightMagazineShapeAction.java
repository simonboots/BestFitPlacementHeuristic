package scp.common.actionchain.actions;

import scp.common.*;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class HighlightMagazineShapeAction implements IAction {
	
	protected Shape s = null;
	
	public HighlightMagazineShapeAction(Shape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		// TODO Auto-generated method stub
	}

	public IAction getReverseAction() {
		return new UnhighlightMagazineShapeAction(s);
	}
}
