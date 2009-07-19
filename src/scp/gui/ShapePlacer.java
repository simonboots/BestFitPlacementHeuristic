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


package scp.gui;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.actionchain.IShapePlacer;

/**
 * StockRoll
 * 
 * @author Benjamin Clauss
 */
class ShapePlacer implements IShapePlacer {

	private StockCutterGUI gui;

	/**
	 * @param gui instance of the main-class to get access to the methods
	 */
	public ShapePlacer(StockCutterGUI gui) {
		this.gui = gui;
	}

	public void placeShape(PlacedShape s) {
		gui.placeShape(s);
		gui.printToLogger("placed\tshape\tID " + s.getId() + "\t@ " + s.getX() + "," + s.getY() + "\t(h: " + s.getHeight() + ", w: " + s.getWidth() + ")");
	}

	public void removeShape(PlacedShape s) {
		gui.removePlacedShape(s);
		gui.printToLogger("removed\tshape\tID " + s.getId() + "\t@ " + s.getX() + "," + s.getY() + "\t(h: " + s.getHeight() + ", w: " + s.getWidth() + ")");
	}

	public void highlightShape(PlacedShape s) {
		gui.highlightPlacedShape(s);
	}

	public void unhighlightAllShapes() {
		gui.unhighlightAllPlacedShapes();
	}

	public void highlightGap(Gap g) {
		gui.highlightGap(g);
		gui.printToLogger("highlighted\tgap\tID " + g.getId() + "\t@ " + g.getX() + "," + g.getY() + "\t(lh: " + g.getLeftHeight() + ", w: " + g.getWidth() + ", rh: " + g.getRightHeight() + ")");
	}

	public void unhighlightAllGaps() {
		gui.unhighlightAllGaps();
	}

	public void highlightSkyline() {
		gui.highlightSkyline();
	}

	public void unhighlightSkyline() {
		gui.unhighlightSkyline();
	}
}