package scp.gui;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.actionchain.IShapePlacer;

class ShapePlacer implements IShapePlacer {

	private StockCutterGUI gui;

	public ShapePlacer(StockCutterGUI gui) {
		this.gui = gui;
	}

	public void placeShape(PlacedShape s) {
		gui.placeShape(s);
		gui.printToLogger("placed shape with ID " + s.getId() + " @ " + s.getX() + "," + s.getY() + " (" + s.getHeight() + "x" + s.getWidth() + ")");
	}

	public void removeShape(PlacedShape s) {
		gui.removePlacedShape(s);
		gui.printToLogger("removed shape with ID " + s.getId() + " @ " + s.getX() + "," + s.getY() + " (" + s.getHeight() + "x" + s.getWidth() + ")");
	}

	public void highlightShape(PlacedShape s) {
		gui.highlightPlacedShape(s);
	}

	public void unhighlightAllShapes() {
		gui.unhighlightAllPlacedShapes();
	}

	public void highlightGap(Gap g) {
		gui.highlightGap(g);
	}

	public void removeGap() {
		gui.removeGap();
	}
}