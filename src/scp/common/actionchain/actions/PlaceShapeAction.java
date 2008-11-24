package scp.common.actionchain.actions;

import scp.common.PlacedShape;
import scp.common.actionchain.IAction;
import scp.common.actionchain.IShapeMagazine;
import scp.common.actionchain.IShapePlacer;

public class PlaceShapeAction implements IAction {

	protected PlacedShape s;

	public PlaceShapeAction(PlacedShape s) {
		this.s = s;
	}

	public void execute(IShapeMagazine magazine, IShapePlacer placer) {
		magazine.removeShape(s);
		placer.unhighlightAllShapes();
		placer.removeGap();
		placer.placeShape(s);
		placer.highlightShape(s);
	}

	public IAction getReverseAction() {
		return new PlaceBackShapeAction(s);
	}
}