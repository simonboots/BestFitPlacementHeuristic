package scp.logic;

public class Gap {
	
	private int location = 0;
	private int width = 0;
	private int height = 0;
	
	public Gap(int location, int width, int height) {
		super();
		this.location = location;
		this.width = width;
		this.height = height;
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
	
	public String toString() {
		return "L: " + location + ", W: " + width + ", H: " + height;
	}
}
