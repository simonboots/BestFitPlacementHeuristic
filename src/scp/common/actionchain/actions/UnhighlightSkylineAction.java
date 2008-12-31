package scp.common.actionchain.actions;

import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class UnhighlightSkylineAction implements IAction {

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.unhighlightSkyline();
	}

	public IAction getReverseAction() {
		return new HighlightSkylineAction();
	}

}
