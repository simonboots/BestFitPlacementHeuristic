package scp.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ShapePool implements Iterable<Shape> {
	
	ArrayList<Shape> shapeList = null;

	public ShapePool() {
		shapeList = new ArrayList<Shape>();
	}
	
	public void add(Shape s) {
		shapeList.add(s);
	}
	
	public int size() {
		return shapeList.size();
	}

	public Iterator<Shape> iterator() {
		return shapeList.iterator();
	}
	
	private void rotateIfNeeded() {
		for (Shape s: shapeList) {
			s.makeWiderThanHigher();
		}
	}
	
    public void sort() {
    	rotateIfNeeded();
    	Collections.sort(shapeList);
    }
}
