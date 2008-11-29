package scp.common.actionchain.actions;

import scp.common.Gap;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * highlight a Gap on the stockroll
 * @author Benjamin Clauss
 */
public class HighlightGapAction implements IAction {

	protected Gap gap = null;

	/**
	 * @param gap Gap to be highlighted
	 */
	public HighlightGapAction(Gap gap) {
		this.gap = gap;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.highlightGap(gap);
	}

	public IAction getReverseAction() {
		return new UnhighlightGapAction(gap);
	}
}
