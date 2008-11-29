package scp.gui;

import java.awt.Color;
import scp.common.Shape;

/**
 * a ColoredShape extends a Shape and has an additional color
 * @author Benjamin Clauss
 */
public class ColoredShape extends Shape {

	private Color color;

	/**
	 * @param s Shape that should be a ColoredShape
	 */
	public ColoredShape(Shape s) {
		super(s.getId(), s.getHeight(), s.getWidth());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}