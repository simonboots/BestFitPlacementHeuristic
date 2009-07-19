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
 * Place next to shortest neighbour policy
 * 
 * Places shape next to shortest neighbour
 * 
 * @author Simon Stiefel
 *
 */
public class PlaceNextToShortestNeighbourPolicy implements INichePlacementPolicy {

	/* (non-Javadoc)
	 * @see scp.logic.policies.INichePlacementPolicy#placeShape(scp.common.Shape, scp.common.Gap)
	 */
	public PlacedShape placeShape(Shape s, Gap g) {
		int newX = 0;
		
		if (g.getLeftHeight() < g.getRightHeight()) {
			newX = g.getX();
		} else {
			newX = (g.getX() + g.getWidth() - s.getWidth());
		}
		return new PlacedShape(s, newX, g.getY());
	}
}
