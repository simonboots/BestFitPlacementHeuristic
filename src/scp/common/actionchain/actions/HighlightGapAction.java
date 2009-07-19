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

import scp.common.Gap;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

/**
 * highlight a Gap on the stockroll
 * @author Benjamin Clauss
 */
public class HighlightGapAction implements IAction {

	protected Gap gap = null;

	/**
	 * @param gap Gap to be highlighted
	 */
	public HighlightGapAction(Gap gap) {
		this.gap = gap;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		placer.unhighlightAllGaps();
		placer.highlightGap(gap);
	}

	public IAction getReverseAction() {
		return new UnhighlightGapAction(gap);
	}
}
