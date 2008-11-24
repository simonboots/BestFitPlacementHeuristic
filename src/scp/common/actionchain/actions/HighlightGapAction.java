package scp.common.actionchain.actions;

import scp.common.Gap;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class HighlightGapAction implements IAction {

	protected Gap gap = null;

	public HighlightGapAction(Gap gap) {
		this.gap = gap;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.unhighlightAllShapes();
		placer.removeGap();
		placer.highlightGap(gap);
	}

	public IAction getReverseAction() {
		return new UnhighlightGapAction(gap);
	}
}
