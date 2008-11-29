package scp.common.actionchain.actions;

import scp.common.Gap;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class UnhighlightGapAction implements IAction {

	protected Gap gap = null;
	
	public UnhighlightGapAction(Gap gap) {
		this.gap = gap;
	}
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.unhighlightGap(gap);
	}

	public IAction getReverseAction() {
		return new HighlightGapAction(gap);
	}
}
