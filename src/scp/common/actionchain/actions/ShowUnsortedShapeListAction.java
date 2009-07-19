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

import scp.common.Shape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * show unsorted list of Shapes in the magazine
 * @author Benjamin Clauss
 */
public class ShowUnsortedShapeListAction implements IAction {

	private List<Shape> shapelist = null;
	private List<Shape> sortedlist = null;

	/**
	 * @param shapelist list of shapes sorted by id
	 * @param sortedlist list of shapes sorted by size
	 */
	public ShowUnsortedShapeListAction(List<Shape> shapelist, List<Shape> sortedlist) {
		this.shapelist = shapelist;
		this.sortedlist = sortedlist;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.loadMagazine(shapelist);
	}

	public IAction getReverseAction() {
		return new ShowSortedShapeListAction(shapelist, sortedlist);
	}

}