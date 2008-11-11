package scp.common;

import java.util.Comparator;

public class ShapeSortBySizeComparator implements Comparator<Shape> {

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
