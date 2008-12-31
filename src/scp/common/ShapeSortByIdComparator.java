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
