package scp.logic;

public class Gap {
	
	private int location = 0;
	private int width = 0;
	private int height = 0;
	private int leftheight = 0;
	private int rightheight = 0;
	
	public Gap(int location, int width, int height, int leftheight, int rightheight) {
		super();
		this.location = location;
		this.width = width;
		this.height = height;
		this.leftheight = leftheight;
		this.rightheight = rightheight;
	}

	public int getLocation() {
		return location;
	}
	
	public int getWidth() {
		return width;
	}	
	
	public int getHeight() {
		return height;
	}
	
	public int getLeftHeight() {
		return leftheight;
	}
	
	public int getRightHeight() {
		return rightheight;
	}
	
	public String toString() {
		return "L: " + location + ", W: " + width + ", H: " + height + ", LH: " + leftheight + ", RH: " + rightheight;
	}
}
