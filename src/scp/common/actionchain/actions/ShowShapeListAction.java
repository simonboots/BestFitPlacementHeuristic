package scp.common.actionchain.actions;

import java.util.List;

import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;
import scp.common.*;

public class ShowShapeListAction implements IAction {
	
	protected List<Shape> shapelist = null;

	public ShowShapeListAction(List<Shape> shapelist) {
		this.shapelist = shapelist;
	}
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.loadMagazine(shapelist);
	}

	public IAction getReverseAction() {
		return null;
	}

}
