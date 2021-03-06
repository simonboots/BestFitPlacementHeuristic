/**
 *     Copyright (C) 2008 Benjamin Clauss, Simon Stiefel 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package scp.logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import scp.common.*;

/**
 * Stock Roll
 * 
 * @author Simon Stiefel
 *
 */
public class StockRoll {
	
	private int width = 0;
	private Integer[] skyline = null;
	private List<IPlaceableObject>[] placeableObjects = null;
	
	/**
	 * @param width width of stock roll
	 */
	public StockRoll(int width) {
		this.width = width;
		skyline = new Integer[width];
		placeableObjects = (ArrayList<IPlaceableObject>[]) Array.newInstance(ArrayList.class, width);  // workaround for generic arrays
		for (int i = 0; i < this.width; i++) {
			skyline[i] = 0;
			placeableObjects[i] = new ArrayList<IPlaceableObject>();
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Calculates lowest gap
	 * @return lowest gap
	 */
	public Gap getLowestGap() {
		int x = 0;
		int width = 1;
		int y = skyline[0];
		boolean interrupted = false;
		
		for (int i = 1; i < this.width; i++) {
			if (skyline[i] < y) {
				interrupted = false;
				y = skyline[i];
				x = i;
				width = 1;
			} else if (skyline[i] == y) {
				if (interrupted) {
					width = 1;
					x = i;
					interrupted = false;
				} else {
					width++;
				}
			} else if (skyline[i] > y) {
				interrupted = true;
			}
		}
				
		// Check left and right height
		// left height
		int leftheight = 0;
		if (x != 0) leftheight = skyline[x - 1] - skyline[x]; 
		
		// right height
		int rightheight = 0;
		if (x + width < this.width) rightheight = skyline[x + width] - skyline[x];
		
		// if gap touches edge set height to opposite height
		// Gap touches left edge
		if (x == 0) leftheight = rightheight;
		
		// Gap touches right edge
		if ((x + width) == this.width) rightheight = leftheight;
		
		return new Gap(x, y, width, leftheight, rightheight);
	}
	
	/**
	 * Places object on stock roll
	 * @param po object to be placed
	 * @throws WrongPlacementException
	 */
	public void placeObject(IPlaceableObject po) throws WrongPlacementException {
		int begin = po.getX();
		int height = skyline[begin];
		
		// Check if object fits width
		if (po.getX() + po.getWidth() > this.width) {
			throw new WrongPlacementException("Cannot place object here: object does not fit width");
		}
		
		// Check if skyline is flat
		for (int i = 1; i < po.getWidth(); i++) {
			if (skyline[begin + i] != height) {
				throw new WrongPlacementException("Cannot place object here: skyline not flat");
			}
		}
		
		// Raise skyline
		for (int i = 0; i < po.getWidth(); i++) {
			skyline[begin + i] += po.getHeight();
			placeableObjects[begin + i].add(po);
		}
	}
	
	/**
	 * Removed object from stock roll
	 * @param object object to removed
	 * @throws WrongRemovalException
	 */
	public void removeObject(IPlaceableObject object) throws WrongRemovalException {
		int begin = object.getX();
		boolean isTopObject = true;
		
		for (int i = 0; i < object.getWidth(); i++) {
			if (! placeableObjects[begin + i].get(placeableObjects[begin + i].size() -1).equals(object)) {
				isTopObject = false;
			}
		}
		
		if (isTopObject) {
			for (int i = 0; i < object.getWidth(); i++) {
				// remove from skyline
				skyline[begin + i] -= object.getHeight();
				
				// remove from shapes list
				placeableObjects[begin + i].remove(placeableObjects[begin + i].size() - 1);
			}
		} else {
			throw new WrongRemovalException("Cannot remove object: is not top level object");
		}
		
		if (! object.getClass().equals(Gap.class)) {
			// Remove top level gaps which may have been added before shape was added
			this.removeTopLevelGaps();
		}
	}
	
	/**
	 * Finds top object at specified location
	 * @param location location (x axis) on stock roll 
	 * @return top object
	 */
	public IPlaceableObject getTopObjectAt(int location) {
		if (location >= this.width) {
			return null;
		}
		
		if (placeableObjects[location].size() > 0) {
			return placeableObjects[location].get(placeableObjects[location].size() - 1);
		}
		
		return null;
	}
	
	/**
	 * Find top shape
	 * @return top shape
	 */
	public PlacedShape getTopShape() {
		IPlaceableObject topShape = getTopObjectAt(maxHeightIndex());
		
		if (topShape == null || topShape instanceof Gap) {
			return null;
		}
		
		return (PlacedShape)topShape;
	}
	
	/**
	 * Calculates location (x-axis) of the maximum height of stock roll
	 * @return location (on x-axis) of maximum height
	 */
	public int maxHeightIndex() {
		int maxheightindex = 0;
		int maxheight = skyline[maxheightindex];
		
		for (int i = 1; i < width; i++) {
			if (skyline[i] > maxheight) {
				maxheight = skyline[i];
				maxheightindex = i;
			}
		}
		
		return maxheightindex;
	}
	
	/**
	 * Calculates the maximum height of stock roll
	 * @return maximum height of stock roll
	 */
	public int maxHeight() {
		return skyline[maxHeightIndex()];
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
	
	public String topObjectsToString() {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < this.width; i++) {
			IPlaceableObject object = getTopObjectAt(i);
			if (object == null) {
				sb.append("0");
			} else {
				sb.append(object.getId());
			}
			
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	/**
	 * Removes all top level gaps
	 */
	public void removeTopLevelGaps() {
		boolean removedShapes = false;
		
		do {
			removedShapes = false;
			for (int i = 0; i < this.width; i++) {
				IPlaceableObject object = this.getTopObjectAt(i);
				if (object != null && object.getClass().equals(Gap.class)) {
					try {
						this.removeObject(object);
						removedShapes = true;
					} catch (WrongRemovalException e) {
						// ignore and move on
					}
				}
			}
			
		} while (removedShapes);
	}
}
