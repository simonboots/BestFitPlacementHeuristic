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


package scp.common.actionchain;

import scp.common.Gap;
import scp.common.PlacedShape;

/**
 * Interface for ShapePlacer
 * @author Simon Stiefel
 *
 */

public interface IShapePlacer {
	
	/**
	 * Place shape on stock roll
	 * @param s shape to be placed
	 */
	public void placeShape(PlacedShape s);
	
	/**
	 * Remove shape from stock roll
	 * @param s shape to be removed
	 */
	public void removeShape(PlacedShape s);
	
	/**
	 * Highlight shape on stock roll
	 * @param s shape to be highlighted
	 */
	public void highlightShape(PlacedShape s);
	
	/**
	 * Unhighlight all shapes on stock roll
	 */
	public void unhighlightAllShapes();
	
	/**
	 * Highlight gap
	 * @param g gap to be highlighted
	 */
	public void highlightGap(Gap g);

	/**
	 * Unhighlights all gaps
	 */
	public void unhighlightAllGaps();
	
	/**
	 * Highlight skyline
	 */
	public void highlightSkyline();
	
	/**
	 * UnhighlightSkyline
	 */
	public void unhighlightSkyline();

}
