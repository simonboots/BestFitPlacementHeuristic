package scp.common.actionchain.actions;

import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * highlight PlacedShape on the stockroll
 * @author Benjamin Clauss
 */
public class HighlightPlacedShapeAction implements IAction {

	PlacedShape s = null;
	
	/**
	 * @param s PlacedShape to be highlighted
	 */
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
