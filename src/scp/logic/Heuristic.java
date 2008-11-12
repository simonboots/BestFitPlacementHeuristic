package scp.logic;

import java.util.ArrayList;
import java.util.List;
import scp.common.*;
import scp.logic.policies.*;

public class Heuristic {

	private int stockrollwidth = 0;
	private ShapePool pool = null;
	public StockRoll stockRoll = null;
	private INichePlacementPolicy policy = null;
	private IHeuristicResultCallback callback = null;

	public Heuristic(int stockrollwidth) {
		this.stockrollwidth = stockrollwidth;
		policy = new PlaceAtLeftmostPolicy();
		init();
	}

	private void init() {
		pool = new ShapePool();
		stockRoll = new StockRoll(this.stockrollwidth);
	}

	public void setResultCallback(IHeuristicResultCallback cb) {
		this.callback = cb;
	}

	public void setShapeList(List<Shape> shapelist) {
		init();
		pool.setShapeList(shapelist);
	}

	public void setPlacementPolicy(INichePlacementPolicy policy) {
		this.policy = policy;
	}

	public void run() throws WrongPlacementException {

		// Sortiere pool
		pool.sort();

		// sortierte Shapes zuruecksenden
		if (callback != null) {
			for (Shape s : pool.getShapeList()) {
				callback.sortedShapeCallback((Shape) s.clone());
			}
		}

		while (pool.size() > 0) {
			Gap gap = stockRoll.getLowestGap();
			Shape shape = pool.findBestShapeforWidth(gap.getWidth());

			// Kein geeignetes Shape gefunden
			if (shape == null) {
				stockRoll.raiseGap(gap);
				continue;
			}

			// Platziere Shape
			PlacedShape placedShape = policy.placeShape(shape, gap);
			stockRoll.placeShape(placedShape);

			// platziertes Shape zuruecksenden
			if (callback != null) callback.placedShapeCallback((PlacedShape) placedShape.clone());
		}

		// Optimiere
		// TODO ...
	}
}
