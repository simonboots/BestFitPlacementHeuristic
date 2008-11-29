package scp.gui;

import java.awt.Color;
import scp.common.PlacedShape;

/**
 * a ColoredPlacedShape extends a PlacedShape and has an additional color
 * @author Benjamin Clauss
 */
public class ColoredPlacedShape extends PlacedShape {

	private Color color;

	/**
	 * @param s PlacedShape that should be a ColoredPlacedShape
	 */
	public ColoredPlacedShape(PlacedShape s) {
		super(s, s.getX(), s.getY());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}