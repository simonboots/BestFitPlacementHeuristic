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
 * @author Simon Stiefel
 *
 */
public class Shape implements Cloneable {

    private Integer id;
    private Integer height;
    private Integer width;
    
    private Boolean rotated;

    /**
     * Constructor of Shape
     * 
     * @param id ID of shape
     * @param height height of shape
     * @param width width of shape
     */
    public Shape(Integer id, Integer height, Integer width) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.rotated = new Boolean(false);
    }

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    protected void setHeight(Integer newHeight) {
        this.height = newHeight;
    }

    public int getWidth() {
        return width;
    }

    protected void setWidth(Integer newWidth) {
        this.width = newWidth;
    }
    
    /**
     * @return Area of shape
     */
    public int getArea() {
    	return getWidth() * getHeight();
    }

    /**
     * Rotates shape by 90 degrees
     */
    public void rotate() {
        Integer oldWidth = this.getWidth();
        this.setWidth(this.getHeight());
        this.setHeight(oldWidth);
        this.rotated = ! this.rotated;
    }
    
    /**
     * Internal rotation state method
     * 
     * DO NOT USE!
     * @param rotated value of rotation state
     */
    protected void setRotated(boolean rotated) {
    	this.rotated = rotated;
    }
    
    public Boolean isRotated() {
    	return this.rotated;
    }

    /**
     * Checks if shape is wider than higher
     * 
     * @return is wider than higher
     */
    public boolean isWiderThanHigher() {
        return width.intValue() > height.intValue();
    }
    
    /**
     * Checks if shape is a square
     * 
     * @return shape is square
     */
    public boolean isSquare() {
    	return width.equals(height);
    }

    /**
     * Makes the shape wider than higher (by rotation)
     * 
     * @return (non-copied) rotated shape
     */
    public Shape makeWiderThanHigher() {
        if (!this.isWiderThanHigher()) {
            this.rotate();
        }
        return this;
    }
    
    public String toString() {
    	return "" + getId() + "<" + getHeight() + "," + getWidth() + ">";
    }
    
    public Object clone() {
    	Shape clone = new Shape(this.getId(), this.getHeight(), this.getWidth());
    	if (this.isRotated()) clone.setRotated(true);
    	
    	return clone;
    }
    
    public boolean equals(Object x) {
    	if (! x.getClass().equals(this.getClass())) return false;
    	return ((Shape) x).getId() == this.getId();
    }
}
