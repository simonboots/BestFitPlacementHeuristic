package scp.common.actionchain.actions;

import scp.common.Shape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class UnhighlightMagazineShapeAction implements IAction {

	Shape s = null;
	
	public UnhighlightMagazineShapeAction(Shape s) {
		this.s = s;
	}
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		// TODO Auto-generated method stub
	}

	public IAction getReverseAction() {
		return new HighlightMagazineShapeAction(s);
	}

}
