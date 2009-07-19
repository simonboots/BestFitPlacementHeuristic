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


package scp.logic.policies;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;

/**
 * Interface for niche placement policies
 * 
 * @author Simon Stiefel
 *
 */
public interface INichePlacementPolicy {
	/**
	 * Place shape
	 * @param s shape to be placed
	 * @param g gap in which shape will be placed
	 * @return placed shape
	 */
	public PlacedShape placeShape(Shape s, Gap g);
}
