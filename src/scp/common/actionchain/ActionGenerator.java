package scp.common.actionchain;

import java.util.ArrayList;
import java.util.List;

import scp.common.*;
import scp.common.actionchain.actions.*;

public class ActionGenerator {

	private List<Shape> shapelist = null;
	private List<Shape> sortedlist = null;
	private List<IPlaceableObject> placedlist = null;
	private List<IPlaceableObject> optimizedlist = null;
	private List<IAction> doQueue = null;

	public ActionGenerator(List<Shape> shapelist, List<Shape> sortedlist, List<IPlaceableObject> placedlist, List<IPlaceableObject> optimizedlist) {
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
		doQueue.add(new ShowSortedShapeListAction(shapelist, sortedlist));

		// add all objects to the doQueue
		for (IPlaceableObject po : placedlist) {
			if (po instanceof Gap) {
				doQueue.add(new HighlightGapAction((Gap) po));
				doQueue.add(new UnhighlightGapAction((Gap) po));
			}
			if (po instanceof PlacedShape) {
				doQueue.add(new HighlightMagazineShapeAction((PlacedShape) po));
				doQueue.add(new PlaceShapeAction((PlacedShape) po));
				doQueue.add(new UnhighlightPlacedShapeAction((PlacedShape) po));
			}
		}

		// add optimized shapes
		for (IPlaceableObject po : optimizedlist) {
			if (po instanceof Gap) {
				doQueue.add(new HighlightGapAction((Gap) po));
				doQueue.add(new UnhighlightGapAction((Gap) po));
			}
			if (po instanceof PlacedShape) {
				doQueue.add(new HighlightMagazineShapeAction((PlacedShape) po));
				doQueue.add(new OptimizeShapeAction((PlacedShape) po));
				doQueue.add(new UnhighlightPlacedShapeAction((PlacedShape) po));
			}
		}

		return doQueue;
	}
}