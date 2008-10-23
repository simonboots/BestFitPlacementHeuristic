package scp.gui;

import java.util.Vector;
import scp.common.Shape;
import scp.common.xml.IShapeCallback;

public class ShapeGetter implements IShapeCallback {

  private Vector<Shape> shapeList = new Vector<Shape>();

  public void newShape(Integer id, Integer width, Integer height) {
    shapeList.add(new Shape(id, height, width));    
  }

  public Vector<Shape> getShapeList() {
    return shapeList;
  }
}