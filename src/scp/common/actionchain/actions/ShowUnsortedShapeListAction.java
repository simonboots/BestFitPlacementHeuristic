package scp.common.actionchain.actions;

import java.util.List;

import scp.common.Shape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class ShowUnsortedShapeListAction implements IAction {

	private List<Shape> shapelist = null;
	private List<Shape> sortedlist = null;

	public ShowUnsortedShapeListAction(List<Shape> shapelist, List<Shape> sortedlist) {
		this.shapelist = shapelist;
		this.sortedlist = sortedlist;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.loadMagazine(shapelist);
	}

	public IAction getReverseAction() {
		return new ShowSortedShapeListAction(shapelist, sortedlist);
	}

}