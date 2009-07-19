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