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


package scp.common;

/**
 * Gap on StockRoll
 * @author Simon Stiefel
 *
 */
public class Gap implements IPlaceableObject {

	private static int idcounter = -1;
	
	private int id = 0;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int leftheight = 0;
	private int rightheight = 0;
	
	
	/**
	 * Constructor for Gap
	 * 
	 * ID of gap is automatically generated
	 * 
	 * @param x location on x axis
	 * @param y location on y axis
	 * @param width width of gap
	 * @param leftheight height at the left edge
	 * @param rightheight height at the right edge
	 */
	public Gap(int x, int y, int width, int leftheight, int rightheight) {
		this.id = idcounter--;
		this.x = x;
		this.y = y;
		this.width = width;
		this.leftheight = leftheight;
		this.rightheight = rightheight;
	}
	
	/**
	 * Constructor for Gap
	 * 
	 * ID can be specified in constructor
	 * 
	 * @param id ID of Gap
	 * @param x location on x axis
	 * @param y location on y axis
	 * @param width width of gap
	 * @param leftheight height at the left edge
	 * @param rightheight height at the right edge
	 */
	public Gap(int id, int x, int y, int width, int leftheight, int rightheight) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.leftheight = leftheight;
		this.rightheight = rightheight;
	}
	
	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {		
		return getLeftHeight() > getRightHeight() ? getRightHeight() : getLeftHeight();
	}
	
	public int getLeftHeight() {
		return leftheight;
	}
	
	public int getRightHeight() {
		return rightheight;
	}
	
	/**
	 * Checks if gap can fit shape
	 * 
	 * This method only checks if the width of the shape fits into the width of the gap.
	 * This function does NOT rotate the shape!
	 * 
	 * @param s shape to check
	 * @return shape fits gap
	 */
	public boolean canFitShape(Shape s) {
		return this.width >= s.getWidth();
	}
	
	public String toString() {
		return "L: " + x + ", W: " + width + ", H: " + y + ", LH: " + leftheight + ", RH: " + rightheight;
	}
}
