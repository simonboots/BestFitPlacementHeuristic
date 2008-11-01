package scp.logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import scp.common.*;

public class StockRoll {
	
	private int width = 0;
	private Integer[] skyline = null;
	private List<PlacedShape>[] shapes = null;
	
	public StockRoll(int width) {
		this.width = width;
		skyline = new Integer[width];
		shapes = (ArrayList<PlacedShape>[]) Array.newInstance(ArrayList.class, width);  // workaround for generic arrays
		for (int i = 0; i < this.width; i++) {
			skyline[i] = 0;
			shapes[i] = new ArrayList<PlacedShape>();
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public Gap getLowestGap() {
		int location = 0;
		int width = 1;
		int height = skyline[0];
		boolean interrupted = false;
		
		for (int i = 1; i < this.width; i++) {
			if (skyline[i] < height) {
				height = skyline[i];
				location = i;
				width = 1;
			} else if (skyline[i] == height) {
				if (interrupted) {
					width = 1;
					location = i;
					interrupted = false;
				} else {
					width++;
				}
			} else if (skyline[i] > height) {
				interrupted = true;
			}
		}
		
		return new Gap(location, width, height);
	}
	
	public void raiseGap(Gap gap) throws WrongPlacementException {
		int raise = 0;
		// Find next level (left or right)
		if (gap.getLocation() > 0) {
			raise = skyline[gap.getLocation() - 1] - gap.getHeight();
		}
		
		if (gap.getLocation() + gap.getWidth() < this.width) {
			int rightraise = skyline[gap.getLocation() + gap.getWidth() + 1] - gap.getHeight();
			if (raise == 0 || rightraise < raise) raise = rightraise;
		}
		
		// raise gap
		WasterShape wasterShape = new WasterShape(raise, gap.getWidth(), gap.getLocation(), gap.getHeight());
		this.placeShape(wasterShape);
	}
	
	public void placeShape(PlacedShape shape) throws WrongPlacementException {
		int begin = shape.getX();
		int height = skyline[begin];
		
		// Check if shape fits width
		if (shape.getX() + shape.getWidth() > this.width) {
			throw new WrongPlacementException("Cannot place shape here: shape does not fit width");
		}
		
		// Check if skyline is flat
		for (int i = 1; i < shape.getWidth(); i++) {
			if (skyline[begin + i] != height) {
				throw new WrongPlacementException("Cannot place shape here: skyline not flat");
			}
		}
		
		// Raise skyline
		for (int i = 0; i < shape.getWidth(); i++) {
			skyline[begin + i] += shape.getHeight();
			shapes[begin + i].add(shape);
		}
	}
	
	public void removeShape(PlacedShape shape) throws WrongRemovalException {
		int begin = shape.getX();
		boolean isTopShape = true;
		
		for (int i = 0; i < shape.getWidth(); i++) {
			if (! shapes[begin + i].get(shapes[begin + i].size() -1).equals(shape)) {
				isTopShape = false;
			}
		}
		
		if (isTopShape) {
			for (int i = 0; i < shape.getWidth(); i++) {
				// remove from skyline
				skyline[begin + i] -= shape.getHeight();
				
				// remove from shapes list
				shapes[begin + i].remove(shapes[begin + i].size() - 1);
			}

		} else {
			throw new WrongRemovalException("Cannot remove shape: is not top level shape");
		}
		
		if (! shape.getClass().equals(WasterShape.class)) {
			// Remove top level gaps which may have been added before shape was added
			this.removeTopLevelWasterShapes();
		}
	}
	
	public PlacedShape getTopShapeAt(int location) {
		if (location >= this.width) {
			return null;
		}
		
		if (shapes[location].size() > 0) {
			return shapes[location].get(shapes[location].size() - 1);
		}
		
		return null;
	}
	
	public String skylineToString() {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < this.width; i++) {
			sb.append(skyline[i]);
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	public String topShapesToString() {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < this.width; i++) {
			PlacedShape shape = getTopShapeAt(i);
			if (shape == null) {
				sb.append("0");
			} else {
				sb.append(shape.getId());
			}
			
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	private void removeTopLevelWasterShapes() throws WrongRemovalException {
		boolean removedShapes = false;
		
		do {
			removedShapes = false;
			for (int i = 0; i < this.width; i++) {
				PlacedShape shape = this.getTopShapeAt(i);
				if (shape != null && shape.getClass().equals(WasterShape.class)) {
					this.removeShape(shape);
					removedShapes = true;
				}
			}
			
		} while (removedShapes);
	}
}
