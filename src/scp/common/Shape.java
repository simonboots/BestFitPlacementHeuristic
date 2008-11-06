package scp.common;

import java.util.Collection;
import java.util.Collections;

public class Shape implements Comparable<Shape>, Cloneable {

    private Integer id;
    private Integer height;
    private Integer width;
    
    private Boolean rotated;

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
    
    public int getArea() {
    	return getWidth() * getHeight();
    }

    public void rotate() {
        Integer oldWidth = this.getWidth();
        this.setWidth(this.getHeight());
        this.setHeight(oldWidth);
        this.rotated = ! this.rotated;
    }
    
    protected void setRotated(boolean rotated) {
    	this.rotated = rotated;
    }
    
    public Boolean isRotated() {
    	return this.rotated;
    }

    public boolean isWiderThanHigher() {
        return width > height;
    }

    public Shape makeWiderThanHigher() {
        if (!this.isWiderThanHigher()) {
            this.rotate();
        }
        return this;
    }

    public int compareTo(Shape s) {
        // Breite vergleichen
        if (this.getWidth() > s.getWidth()) {
            return -1;
        } else if (this.getWidth() < s.getWidth()) {
            return 1;
        } else {
            // Breite ist identisch; Höhe vergleichen
            if (this.getHeight() > s.getHeight()) {
                return -1;
            } else if (this.getHeight() < s.getHeight()) {
                return 1;
            } else {
                // Beide Shapes sind identisch
                return 0;
            }
        }
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
