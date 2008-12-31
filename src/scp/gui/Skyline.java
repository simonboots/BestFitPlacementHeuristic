package scp.gui;

import scp.common.IPlaceableObject;

/**
 * highlights the highest point on stockroll after all shapes are placed
 * @author Benjamin Clauss
 */
public class Skyline implements IPlaceableObject {

	private int maxHeight;

	public Skyline(int maxHeight) {
		this.setMaxHeight(maxHeight);
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	//not used
	public int getHeight() {
		return 0;
	}

	//not used
	public int getId() {
		return 0;
	}

	//not used
	public int getWidth() {
		return 0;
	}

	//not used
	public int getX() {
		return 0;
	}

	//not used
	public int getY() {
		return 0;
	}
}