package scp.common.actionchain.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import scp.common.Shape;
import scp.common.ShapeSortByIdComparator;
import scp.common.actionchain.IAction;

public class ShowSortedShapeListAction extends ShowShapeListAction implements
		IAction {

	public ShowSortedShapeListAction(List<Shape> shapelist) {
		super(shapelist);
	}

	public IAction getReverseAction() {
		List<Shape> newList = (List<Shape>) ((ArrayList<Shape>)shapelist).clone();
		Collections.sort(newList, new ShapeSortByIdComparator());
		
		return new ShowUnsortedShapeListAction(newList);
	}

}
