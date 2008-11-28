package scp.common;

/**
 * Gap on StockRoll
 * @author sst
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
