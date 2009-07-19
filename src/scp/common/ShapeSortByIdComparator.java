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


package scp.common;

import java.util.Comparator;

/**
 * Comparator to sort Shapes by id (lower to higher)
 * @author Simon Stiefel
 *
 */
public class ShapeSortByIdComparator implements Comparator<Shape> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Shape s1, Shape s2) {
		if (s1.getId() > s2.getId()) {
			return 1;
		} else if (s1.getId() < s2.getId()) {
			return -1;
		}
		
		return 0;
	}
}
