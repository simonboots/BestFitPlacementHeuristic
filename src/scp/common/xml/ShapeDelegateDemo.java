package scp.common.xml;

import scp.common.Shape;

public class ShapeDelegateDemo implements IShapeCallback {

	public void newShape(Integer id, Integer width, Integer height) {
		Shape s = new Shape(id, height, width);
		System.out.println("id: " + id + ", width: " + width + ", height: " + height + ", s: " + s);
	}

}
