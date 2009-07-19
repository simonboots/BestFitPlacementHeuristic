/**
 *     Copyright (C) 2008 Benjamin Clauss, Simon Stiefel 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


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