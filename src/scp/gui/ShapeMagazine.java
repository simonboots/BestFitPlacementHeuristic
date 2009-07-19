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

import java.util.List;
import scp.common.Shape;
import scp.common.actionchain.IShapeMagazine;

/**
 * ShapeMagazin
 * 
 * @author Benjamin Clauss
 */
class ShapeMagazine implements IShapeMagazine {

	private StockCutterGUI gui;

	/**
	 * @param gui instance of the main-class to get access to the methods
	 */
	public ShapeMagazine(StockCutterGUI gui) {
		this.gui = gui;
	}

	public void loadMagazine(List<Shape> shapelist) {
		gui.loadMagazine(shapelist);
	}

	public void highlightShape(Shape s) {
		gui.highlightMagazineShape(s);
	}

	public void unhighlightAllShapes() {
		gui.unhighlightAllMagazineShapes();
	}

	public void removeShape(Shape s) {
		gui.removeShapeFromMagazine(s);
	}

	public void insertShape(Shape s) {
		gui.insertShapeIntoMagazine(s);
	}
}