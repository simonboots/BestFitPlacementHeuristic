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
		gui.printToLogger("placed\tshape\tID " + s.getId() + "\t@ " + s.getX() + "," + s.getY() + "\t(h: " + s.getHeight() + ", w: " + s.getWidth() + ")");
	}

	public void removeShape(PlacedShape s) {
		gui.removePlacedShape(s);
		gui.printToLogger("removed\tshape\tID " + s.getId() + "\t@ " + s.getX() + "," + s.getY() + "\t(h: " + s.getHeight() + ", w: " + s.getWidth() + ")");
	}

	public void highlightShape(PlacedShape s) {
		gui.highlightPlacedShape(s);
	}

	public void unhighlightAllShapes() {
		gui.unhighlightAllPlacedShapes();
	}

	public void highlightGap(Gap g) {
		gui.highlightGap(g);
		gui.printToLogger("highlighted\tgap\tID " + g.getId() + "\t@ " + g.getX() + "," + g.getY() + "\t(lh: " + g.getLeftHeight() + ", w: " + g.getWidth() + ", rh: " + g.getRightHeight() + ")");
	}

	public void unhighlightGap(Gap g) {
		gui.unhighlightGap(g);
	}
}