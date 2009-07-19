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


package scp.common.actionchain.actions;

import java.util.List;

import scp.common.IPlaceableObject;
import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * optimize PlacedShape on stockroll
 * @author Benjamin Clauss
 */
public class OptimizeShapeAction implements IAction {
	
	PlacedShape s = null;
	List<IPlaceableObject> placedlist = null;
	
	/**
	 * @param s PlacedShape to be optimized
	 */
	public OptimizeShapeAction(PlacedShape s, List<IPlaceableObject> placedlist) {
		this.s = s;
		this.placedlist = placedlist;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.removeShape(s);
		placer.unhighlightAllGaps();
		placer.placeShape(s);
		placer.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new UnoptimizeShapeAction(s, placedlist);
	}
}