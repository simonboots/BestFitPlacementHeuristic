package scp.common.actionchain.actions;

import java.util.List;

import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;
import scp.common.*;

/**
 * show unsorted list of Shapes in the magazine
 * @author Benjamin Clauss
 */
public class ShowShapeListAction implements IAction {
	
	protected List<Shape> shapelist = null;

	/**
	 * @param shapelist list of shapes sorted by id
	 */
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
