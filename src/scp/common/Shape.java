package scp.common;

public class Shape implements Comparable<Shape> {

    private Integer id;
    private Integer height;
    private Integer width;
    private Integer x = null;
    private Integer y = null;

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

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
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
