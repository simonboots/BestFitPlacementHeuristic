package scp.common.actionchain;

import java.util.ArrayList;
import java.util.List;

import scp.common.*;
import scp.common.actionchain.actions.*;

public class ActionGenerator {

	private List<Shape> shapelist = null;
	private List<Shape> sortedlist = null;
	private List<IPlaceableObject> placedlist = null;
	private List<PlacedShape> optimizedlist = null;
	private List<IAction> doQueue = null;

	public ActionGenerator(List<Shape> shapelist, List<Shape> sortedlist, List<IPlaceableObject> placedlist, List<PlacedShape> optimizedlist) {
		this.shapelist = shapelist;
		this.sortedlist = sortedlist;
		this.placedlist = placedlist;
		this.optimizedlist = optimizedlist;
	}

	public List<IAction> getDoQueue() {
		doQueue = new ArrayList<IAction>();
		// first step: load unorderd shapes
		doQueue.add(new ShowShapeListAction(shapelist));
		// second step: sort shapes by size
		doQueue.add(new ShowSortedShapeListAction(sortedlist));

		// add all objects to the doQueue
		for (IPlaceableObject po : placedlist) {
			if (po instanceof PlacedShape) {
				doQueue.add(new HighlightMagazineShapeAction((PlacedShape) po));
				doQueue.add(new PlaceShapeAction((PlacedShape) po));
			}

			if (po instanceof Gap) {
				doQueue.add(new HighlightGapAction((Gap) po));
			}
		}

		// add optimized shapes
		for (PlacedShape s : optimizedlist) {
			doQueue.add(new OptimizeShapeAction(s));
		}
		return doQueue;
	}
}