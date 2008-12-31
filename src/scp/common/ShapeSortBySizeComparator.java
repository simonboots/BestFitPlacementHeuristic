package scp.common;

import java.util.Comparator;

/**
 * Comparator to sort Shapes by size (big to small)
 * @author Simon Stiefel
 *
 */
public class ShapeSortBySizeComparator implements Comparator<Shape> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Shape s1, Shape s2) {
	       // Breite vergleichen
        if (s1.getWidth() > s2.getWidth()) {
            return -1;
        } else if (s1.getWidth() < s2.getWidth()) {
            return 1;
        } else {
            // Breite ist identisch; Höhe vergleichen
            if (s1.getHeight() > s2.getHeight()) {
                return -1;
            } else if (s1.getHeight() < s2.getHeight()) {
                return 1;
            } else {
                // Beide Shapes sind identisch
                return 0;
            }
        }
	}
}
