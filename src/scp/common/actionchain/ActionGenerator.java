package scp.common.actionchain;

import java.util.ArrayList;
import java.util.List;

import scp.common.*;
import scp.common.actionchain.actions.*;

/**
 * Generates Actions for doQueue
 * @author Benjamin Clauss
 */
public class ActionGenerator {

	private List<Shape> shapelist = null;
	private List<Shape> sortedlist = null;
	private List<IPlaceableObject> placedlist = null;
	private List<IPlaceableObject> optimizedlist = null;
	private List<IAction> doQueue = null;

	/**
	 * 
	 * @param shapelist list of Shapes sorted by id
	 * @param sortedlist list of Shapes sorted by size
	 * @param placedlist list of PlacedShapes
	 * @param optimizedlist list of optimized PlacedShapes
	 */
	public ActionGenerator(List<Shape> shapelist, List<Shape> sortedlist, List<IPlaceableObject> placedlist, List<IPlaceableObject> optimizedlist) {
		this.shapelist = shapelist;
		this.sortedlist = sortedlist;
		this.placedlist = placedlist;
		this.optimizedlist = optimizedlist;
	}

	/**
	 * creates an ordered list of all todo-actions
	 * @return list of all todo-actions
	 */
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
			}
			if (po instanceof PlacedShape) {
				doQueue.add(new HighlightMagazineShapeAction(((PlacedShape) po)));
				doQueue.add(new PlaceShapeAction((PlacedShape) po));
				doQueue.add(new UnhighlightPlacedShapeAction((PlacedShape) po));
			}
		}

		// add optimized shapes

		PlacedShape optimizedShape = null;

		for (IPlaceableObject po : optimizedlist) {

			if (po instanceof Gap) {
				doQueue.add(new HighlightGapAction((Gap) po));
			}
			if (po instanceof PlacedShape) {
				if (optimizedShape != null) {
					doQueue.add(new HighlightPlacedShapeAction(optimizedShape));
					doQueue.add(new OptimizeShapeAction(optimizedShape, placedlist));
					doQueue.add(new UnhighlightPlacedShapeAction(optimizedShape));

					optimizedShape = null;
				}
				optimizedShape = (PlacedShape) po;
			}
		}

		// insert last optimizedShape
		if (optimizedShape != null) {
			doQueue.add(new HighlightPlacedShapeAction(optimizedShape));
			doQueue.add(new OptimizeShapeAction(optimizedShape, placedlist));
			doQueue.add(new UnhighlightPlacedShapeAction(optimizedShape));

			optimizedShape = null;
		}

		// highlight skyline
		doQueue.add(new HighlightSkylineAction());

		return doQueue;
	}
}