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

		// ##### Schritt 0: Ungeeignete Shapes aussortieren #####
		List<Shape> rejected = pool.forceSizeConstraints(1, this.stockrollwidth);
		if (rejected.size() > 0) {	
			System.out.println("The following shapes have been rmoved from the pool due to size constraints:");
			for (Shape s : rejected) {
				System.out.println(s);
			}
		}
		
		// ##### Schritt 1: Sortiere pool ##### 
		pool.sort();

		// sortierte Shapes zuruecksenden
		if (callback != null) {
			for (Shape s : pool.getShapeList()) {
				callback.sortedShapeCallback((Shape) s.clone());
			}
		}

		// ##### Schritt 2: Shapes aus Pool platzieren ##### 
		while (pool.size() > 0) {
			Gap gap = stockRoll.getLowestGap();

			// gefundenes Gap zurŸcksenden
			if (callback != null)
				callback.placementsCallback(gap);

			Shape shape = pool.findBestShapeforWidth(gap.getWidth());

			// Kein geeignetes Shape gefunden
			if (shape == null) {
				stockRoll.placeObject(gap);
				continue;
			}

			// Platziere Shape
			PlacedShape placedShape = policy.placeShape(shape, gap);
			stockRoll.placeObject(placedShape);

			// platziertes Shape zuruecksenden
			if (callback != null)
				callback.placementsCallback((PlacedShape) placedShape.clone());
		}

		// ##### Schritt 3: Optimiere ##### 
		
		int currentheight = stockRoll.maxHeight();
		boolean isReverse = false;
		
		stockRoll.removeTopLevelGaps();

		try {
			PlacedShape topLevelShape = stockRoll.getTopShape();
			while ((!topLevelShape.isWiderThanHigher()
					&& !topLevelShape.isSquare()) || isReverse) {
				
				// check if shape height is bigger than stockroll
				if (topLevelShape.getHeight() > stockrollwidth) break;
				
				// clean stockroll
				stockRoll.removeObject(topLevelShape);
				stockRoll.removeTopLevelGaps();
				
				// rotate shape
				topLevelShape.rotate();
				
				// find gap that can fit shape
				List<Gap> gapSteps = new ArrayList<Gap>();
				Gap fittingGap = findGapForShape(topLevelShape, gapSteps);
				
				// re-place shape
				topLevelShape = policy.placeShape(topLevelShape, fittingGap);
				stockRoll.placeObject(topLevelShape);
				
				// if pass was reverse pass, exit loop
				if (isReverse) break;
				
				// if new height is higher than old, reverse
				if (stockRoll.maxHeight() > currentheight) {
					topLevelShape.rotate();
					isReverse = true;
					continue;
				} else {
					if (callback != null) {
						
						// Save Shape
						callback.optimizedPlacementsCallback(topLevelShape);
						
						// Save Gap steps
						for (Gap gap : gapSteps) {
							callback.optimizedPlacementsCallback(gap);
						}
					}
					
					// new current height
					currentheight = stockRoll.maxHeight();
				}
				
				// find next topLevelShape
				topLevelShape = stockRoll.getTopShape();
			}	
		} catch (WrongRemovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Gap findGapForShape(Shape s, List<Gap> gapSteps) throws WrongPlacementException {
		Gap fittingGap = null;
		
		for (;;) {
			fittingGap = stockRoll.getLowestGap();
			gapSteps.add(fittingGap);
			if (! fittingGap.canFitShape(s)) {
				stockRoll.placeObject(fittingGap);
			} else {
				break;
			}
		}
		
		return fittingGap;
	}
}
