package scp.common;

public class PlacedShape extends Shape {
	
    private Integer x = null;
    private Integer y = null;

	public PlacedShape(Integer id, Integer height, Integer width, Integer x, Integer y) {
		super(id, height, width);
		this.setX(x);
		this.setY(y);
	}
	
	public PlacedShape(Shape shape, Integer x, Integer y) {
		super(shape.getId(), shape.getHeight(), shape.getWidth());
		if (shape.isRotated()) this.setRotated(true);
		this.setX(x);
		this.setY(y);
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
    
    public Object clone() {
    	PlacedShape clone = new PlacedShape(this, this.getX(), this.getY());
    	if (this.isRotated()) clone.setRotated(true);
    	
    	return clone;
    }
    
    public boolean equals(Object x) {
    	if (! x.getClass().equals(this.getClass())) return false;
    	return ((PlacedShape) x).getId() == this.getId();
    }
}
