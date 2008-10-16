package scp.common;

import java.util.ArrayList;

public class ShapePool {
	
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
}
