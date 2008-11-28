package scp.common;

/**
 * Placed Shape (Shape with coordinates)
 * @author sst
 *
 */
public class PlacedShape extends Shape implements IPlaceableObject {
	
    private Integer x = null;
    private Integer y = null;

	/**
	 * @param id ID of placed shape
	 * @param height height of placed shape
	 * @param width width of placed shape
	 * @param x location on x axis
	 * @param y location on y axis
	 */
	public PlacedShape(Integer id, Integer height, Integer width, Integer x, Integer y) {
		super(id, height, width);
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * @param shape (non-placed) template shape
	 * @param x location on x axis
	 * @param y location on y axis
	 */
	public PlacedShape(Shape shape, int x, int y) {
		super(shape.getId(), shape.getHeight(), shape.getWidth());
		if (shape.isRotated()) this.setRotated(true);
		this.setX(x);
		this.setY(y);
	}
	
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
