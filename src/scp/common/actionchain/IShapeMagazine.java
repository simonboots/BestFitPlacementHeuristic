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

import java.util.List;
import scp.common.*;

/**
 * Interface for ShapeManagzine
 * @author Simon Stiefel
 *
 */
public interface IShapeMagazine {
	
	/**
	 * Loads magazine with shapes
	 * @param shapelist shapes to load
	 */
	public void loadMagazine(List<Shape> shapelist);
	
	/**
	 * Highlight shape
	 * @param s shape to highlight
	 */
	public void highlightShape(Shape s);
	
	/**
	 * Unhighlight all shapes 
	 */
	public void unhighlightAllShapes();
	
	/**
	 * Remove shape from magazine
	 * @param s shape to be removed
	 */
	public void removeShape(Shape s);
	
	/**
	 * Insert shape to magazine
	 * @param s shape to be added
	 */
	public void insertShape(Shape s);
}
