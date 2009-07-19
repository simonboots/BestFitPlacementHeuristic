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

import scp.common.IPlaceableObject;

/**
 * highlights the highest point on stockroll after all shapes are placed
 * @author Benjamin Clauss
 */
public class Skyline implements IPlaceableObject {

	private int maxHeight;

	public Skyline(int maxHeight) {
		this.setMaxHeight(maxHeight);
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	//not used
	public int getHeight() {
		return 0;
	}

	//not used
	public int getId() {
		return 0;
	}

	//not used
	public int getWidth() {
		return 0;
	}

	//not used
	public int getX() {
		return 0;
	}

	//not used
	public int getY() {
		return 0;
	}
}