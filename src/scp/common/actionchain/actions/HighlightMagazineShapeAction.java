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

import scp.common.*;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * highlight a Shape in the magazine
 * @author Benjamin Clauss
 */
public class HighlightMagazineShapeAction implements IAction {
	
	protected Shape s = null;
	
	/**
	 * @param s Shape to be highlighted
	 */
	public HighlightMagazineShapeAction(Shape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new UnhighlightMagazineShapeAction(s);
	}
}
