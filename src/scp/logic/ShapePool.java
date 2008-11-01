package scp.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import scp.common.Shape;

public class ShapePool implements Iterable<Shape> {
	
	ArrayList<Shape> shapeList = null;

	public ShapePool() {
		shapeList = new ArrayList<Shape>();
	}
	
	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}

	public void setShapeList(ArrayList<Shape> shapeList) {
		this.shapeList = shapeList;
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
