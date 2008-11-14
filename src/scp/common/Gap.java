package scp.common;

public class Gap implements IPlaceableObject {

	private static int idcounter = -1;
	
	private int id = 0;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int leftheight = 0;
	private int rightheight = 0;
	
	public Gap(int x, int y, int width, int leftheight, int rightheight) {
		this.id = idcounter--;
		this.x = x;
		this.y = y;
		this.width = width;
		this.leftheight = leftheight;
		this.rightheight = rightheight;
	}
	
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
		return getLeftHeight() > getRightHeight() ? getLeftHeight() : getRightHeight();
	}
	
	public int getLeftHeight() {
		return leftheight;
	}
	
	public int getRightHeight() {
		return rightheight;
	}
	
	public String toString() {
		return "L: " + x + ", W: " + width + ", H: " + y + ", LH: " + leftheight + ", RH: " + rightheight;
	}
}
