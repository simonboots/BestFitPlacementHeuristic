package scp.common.xml;

public class ShapeNullCallback implements IShapeCallback {

	public void newShape(Integer id, Integer width, Integer height) {
		System.out.println("ShapeNullCallback: " + "ID: " + id + ", Width: " + width + ", Height: " + height);
	}

}
