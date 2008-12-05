package scp.common.actionchain.actions;

import scp.common.Gap;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * unhighlight gap on the stockroll
 * @author Benjamin Clauss
 */
public class UnhighlightGapAction implements IAction {

	protected Gap gap = null;
	
	/**
	 * @param gap Gap to be unhighlighted
	 */
	public UnhighlightGapAction(Gap gap) {
		this.gap = gap;
	}
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.unhighlightAllGaps();
	}

	public IAction getReverseAction() {
		return new HighlightGapAction(gap);
	}
}
