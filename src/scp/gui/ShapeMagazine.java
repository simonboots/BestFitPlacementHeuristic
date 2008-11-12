package scp.gui;

import java.util.List;
import scp.common.Shape;
import scp.common.actionchain.IShapeMagazine;

class ShapeMagazine implements IShapeMagazine {

  private StockCutterGUI gui;

  public ShapeMagazine(StockCutterGUI gui) {
    this.gui = gui;
  }

  public void loadMagazine(List<Shape> shapelist) {
    gui.loadMagazine(shapelist);
  }

  public void highlightShape(Shape s) {
    gui.highlightColoredShape(s);
  }

  public void unhighlightAllShapes() {
    gui.unhighlightColoredShapes();
  }

  public void removeShape(Shape s) {
    gui.removeColoredShape(s);
  }

  public void insertShape(Shape s) {
    gui.insertColoredShape(s);
  }
}