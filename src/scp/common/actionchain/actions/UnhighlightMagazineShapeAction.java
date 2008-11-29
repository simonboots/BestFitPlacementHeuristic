package scp.common.actionchain.actions;

import scp.common.Shape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * unhighlight shape in the magazine
 * @author Benjamin Clauss
 */
public class UnhighlightMagazineShapeAction implements IAction {

	Shape s = null;
	
	/**
	 * @param s Shape to be unhighlighted
	 */
	public UnhighlightMagazineShapeAction(Shape s) {
		this.s = s;
	}
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.unhighlightAllShapes();
	}

	public IAction getReverseAction() {
		return new HighlightMagazineShapeAction(s);
	}

}
