package scp.common;

/**
 * @author sst
 *
 */
public class Shape implements Cloneable {

    private Integer id;
    private Integer height;
    private Integer width;
    
    private Boolean rotated;

    /**
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
     * @return area of shape
     */
    public int getArea() {
    	return getWidth() * getHeight();
    }

    /**
     * rotates shape by 90 degrees
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
        return (int)width > (int)height;
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
