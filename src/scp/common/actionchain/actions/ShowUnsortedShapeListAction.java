package scp.common.actionchain.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scp.common.Shape;
import scp.common.ShapeSortBySizeComparator;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class ShowUnsortedShapeListAction extends ShowShapeListAction implements IAction {

	public ShowUnsortedShapeListAction(List<Shape> shapelist) {
		super(shapelist);
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		// TODO Auto-generated method stub

	}

	public IAction getReverseAction() {
		List<Shape> sortedList = (List<Shape>) ((ArrayList<Shape>) shapelist).clone();
		Collections.sort(sortedList, new ShapeSortBySizeComparator());

		return new ShowSortedShapeListAction(sortedList);
	}

}
