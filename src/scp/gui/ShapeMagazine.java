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
	  gui.highlightMagazineShape(s);
  }

  public void unhighlightAllShapes() {
	  gui.unhighlightAllMagazineShapes();
  }

  public void removeShape(Shape s) {
	  gui.removeShapeFromMagazine(s);
  }

  public void insertShape(Shape s) {
	  gui.insertShapeIntoMagazine(s);
  }
}