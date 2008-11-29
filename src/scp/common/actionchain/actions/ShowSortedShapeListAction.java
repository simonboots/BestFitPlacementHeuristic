package scp.common.actionchain.actions;

import java.util.List;

import scp.common.Shape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * show list of sorted Shapes in the magazine
 * @author Benjamin Clauss
 */
public class ShowSortedShapeListAction implements IAction {

	private List<Shape> shapelist = null;
	private List<Shape> sortedlist = null;

	/**
	 * @param shapelist list of shapes sorted by id
	 * @param sortedlist list of shapes sorted by size
	 */
	public ShowSortedShapeListAction(List<Shape> shapelist, List<Shape> sortedlist) {
		this.shapelist = shapelist;
		this.sortedlist = sortedlist;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.loadMagazine(sortedlist);
	}

	public IAction getReverseAction() {
		return new ShowUnsortedShapeListAction(shapelist, sortedlist);
	}
}