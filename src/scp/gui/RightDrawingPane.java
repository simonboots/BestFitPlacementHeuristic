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
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import scp.common.Gap;
import scp.common.IPlaceableObject;
import scp.common.PlacedShape;

/**
 * StockRoll-Panel
 * 
 * @author Benjamin Clauss
 */
@SuppressWarnings("serial")
public class RightDrawingPane extends JPanel {

	private static final int TOPGAP = 50;

	private ArrayList<IPlaceableObject> rightList = new ArrayList<IPlaceableObject>();

	/**
	 * @param rightList list of PlaceableObjects for the stockroll
	 */
	public RightDrawingPane(ArrayList<IPlaceableObject> rightList) {
		this.rightList = rightList;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int maxY = 0;
		int yCoord = 446;

		for (IPlaceableObject obj : rightList) {
			if ((obj.getHeight() + obj.getY()) > maxY) {
				maxY = obj.getHeight() + obj.getY();
			}
		}

		if (maxY > yCoord) {
			yCoord += maxY - yCoord + TOPGAP;
		}

		setPreferredSize(new Dimension(0, maxY + TOPGAP));

		for (IPlaceableObject obj : rightList) {

			if (obj instanceof PlacedShape) {
				g.setColor(((ColoredPlacedShape) obj).getColor());
				g.fillRect(obj.getX(), yCoord - obj.getHeight() - obj.getY(), obj.getWidth(), obj.getHeight());
				g.setColor(Color.black);
				g.drawRect(obj.getX(), yCoord - obj.getHeight() - obj.getY(), obj.getWidth() - 1, obj.getHeight() - 1);
			} else if (obj instanceof Gap) {
				g.setColor(Color.red);
				int[] xPoints = { obj.getX(), obj.getX(), obj.getX() + ((Gap) obj).getWidth() - 1, obj.getX() + ((Gap) obj).getWidth() - 1 };
				int[] yPoints = { yCoord - obj.getY() - ((Gap) obj).getLeftHeight() - 1, yCoord - obj.getY() - 1, yCoord - obj.getY() - 1, yCoord - obj.getY() - ((Gap) obj).getRightHeight() - 1 };
				g.drawPolyline(xPoints, yPoints, 4);
			} else if (obj instanceof Skyline) {
				g.setColor(Color.red);
				g.drawLine(0, yCoord - ((Skyline) obj).getMaxHeight() - 1, 400, yCoord - ((Skyline) obj).getMaxHeight() - 1);
				g.drawLine(0, yCoord - ((Skyline) obj).getMaxHeight() - 2, 400, yCoord - ((Skyline) obj).getMaxHeight() - 2);
			}
		}

		revalidate();
	}
}