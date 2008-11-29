package scp.common.actionchain.actions;

import scp.common.*;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * highlight a Shape in the magazine
 * @author Benjamin Clauss
 */
public class HighlightMagazineShapeAction implements IAction {
	
	protected Shape s = null;
	
	/**
	 * @param s Shape to be highlighted
	 */
	public HighlightMagazineShapeAction(Shape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new UnhighlightMagazineShapeAction(s);
	}
}
