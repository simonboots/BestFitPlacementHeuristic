package scp.gui;

import java.util.ArrayList;
import scp.common.Shape;
import scp.common.xml.IShapeCallback;

public class ShapeGetter implements IShapeCallback {

  private ArrayList<Shape> shapeList = new ArrayList<Shape>();

  public void newShape(Integer id, Integer width, Integer height) {
    shapeList.add(new Shape(id, height, width));    
  }

  public ArrayList<Shape> getShapeList() {
    return shapeList;
  }
}