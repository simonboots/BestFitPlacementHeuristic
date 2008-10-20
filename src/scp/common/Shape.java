package scp.common;

import java.util.Collection;
import java.util.Collections;

public class Shape implements Comparable<Shape> {

    private Integer id;
    private Integer height;
    private Integer width;

    public Shape(Integer id, Integer height, Integer width) {
        this.id = id;
        this.height = height;
        this.width = width;
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

    public void rotate() {
        Integer oldWidth = this.getWidth();
        this.setWidth(this.getHeight());
        this.setHeight(oldWidth);
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
            return 1;
        } else if (this.getWidth() < s.getWidth()) {
            return -1;
        } else {
            // Breite ist identisch; Höhe vergleichen
            if (this.getHeight() > s.getHeight()) {
                return 1;
            } else if (this.getHeight() < s.getHeight()) {
                return -1;
            } else {
                // Beide Shapes sind identisch
                return 0;
            }
        }
    }
}
