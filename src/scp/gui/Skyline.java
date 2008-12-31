package scp.gui;

import scp.common.IPlaceableObject;

public class Skyline implements IPlaceableObject {
	
	private int maxHeight;

	public Skyline(int maxHeight) {
		this.setMaxHeight(maxHeight);
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxHeight() {
		return maxHeight;
	}
}