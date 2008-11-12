package scp.gui;

import scp.common.PlacedShape;
import scp.common.actionchain.IShapePlacer;

class ShapePlacer implements IShapePlacer {

  private StockCutterGUI gui;

  public ShapePlacer(StockCutterGUI gui) {
    this.gui = gui;
  }

  public void placeShape(PlacedShape s) {
    gui.placeColoredPlacedShape(s);
  }

  public void removeShape(PlacedShape s) {
    gui.removeColoredPlacedShape(s);
  }

  public void highlightShape(PlacedShape s) {
    gui.highlightColoredPlacedShape(s);
  }

  public void unhighlightAllShapes() {
    gui.unhighlightColoredPlacedShapes();
  }
}