/**
 *     Copyright (C) 2008 Benjamin Clauss, Simon Stiefel 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package scp.logic;

import java.util.ArrayList;
import java.util.List;
import scp.common.*;
import scp.logic.policies.*;

/**
 * Implemtation of Best-Fit Heuristic
 * @author Simon Stiefel
 *
 */
public class Heuristic {

	private int stockrollwidth = 0;
	private ShapePool pool = null;
	public StockRoll stockRoll = null;
	private INichePlacementPolicy policy = null;
	private IHeuristicResultCallback callback = null;

	/**
	 * Constructor of Heuristic
	 * 
	 * @param stockrollwidth width of stockroll
	 */
	public Heuristic(int stockrollwidth) {
		this.stockrollwidth = stockrollwidth;
		policy = new PlaceAtLeftmostPolicy();
		init();
	}

	private void init() {
		pool = new ShapePool();
		stockRoll = new StockRoll(this.stockrollwidth);
	}

	/**
	 * Set callback object
	 * @param cb callback object
	 */
	public void setResultCallback(IHeuristicResultCallback cb) {
		this.callback = cb;
	}

	/**
	 * sets shapes to process
	 * 
	 * @param shapelist shapes to process
	 */
	public void setShapeList(List<Shape> shapelist) {
		init();
		pool.setShapeList(shapelist);
	}

	/**
	 * sets placement policy
	 * @param policy placement policy
	 */
	public void setPlacementPolicy(INichePlacementPolicy policy) {
		this.policy = policy;
	}

	/**
	 * Starts heuristic calculations
	 * @throws WrongPlacementException
	 */
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

			// gefundenes Gap zur�cksenden
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
