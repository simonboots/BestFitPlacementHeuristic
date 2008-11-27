package scp.common;

import java.util.Comparator;

public class ShapeSortByIdComparator implements Comparator<Shape> {

	public int compare(Shape s1, Shape s2) {
		if (s1.getId() > s2.getId()) {
			return 1;
		} else if (s1.getId() < s2.getId()) {
			return -1;
		}
		
		return 0;
	}
}
