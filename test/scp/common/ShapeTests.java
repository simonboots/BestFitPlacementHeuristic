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


package scp.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShapeTests {
	
	Shape shape = null;

	@Before
	public void setUp() throws Exception {
		shape = new Shape(1, 10, 20);
	}

	@Test
	public void widerThanHigher() {
		assertTrue(shape.isWiderThanHigher());
	}
	
	@Test
	public void rotation() {
		shape.rotate();
		assertFalse(shape.isWiderThanHigher());
	}
	
	@Test
	public void rotateIfHigherThanWider() {
		shape.rotate();
		shape.makeWiderThanHigher();
		assertTrue(shape.isWiderThanHigher());
	}
	
	@Test
	public void compareToSmallerShape() {
		Shape smallerShape = new Shape(2, 5, 20);
		assertTrue(new ShapeSortBySizeComparator().compare(shape, smallerShape) == -1);
	}
	
	@Test
	public void compareToLargerShape() {
		Shape largerShape = new Shape(3, 20, 30);
		assertTrue(new ShapeSortBySizeComparator().compare(shape, largerShape) == 1);
	}
	
	@Test
	public void compareToEqualShape() {
		Shape equalShape = new Shape(4, 10, 20);
		assertTrue(new ShapeSortBySizeComparator().compare(shape, equalShape) == 0);
	}
	
	@Test
	public void compareToSmallerWidthShape() {
		Shape smallerWidthShape = new Shape(5, 10, 10);
		assertTrue(new ShapeSortBySizeComparator().compare(shape, smallerWidthShape) == -1);
	}
	
	@Test
	public void compareToLargerWidthShape() {
		Shape largerWidthShape = new Shape(6, 10, 30);
		assertTrue(new ShapeSortBySizeComparator().compare(shape, largerWidthShape) == 1);
	}
	
	@Test
	public void rotationFlag() {
		Shape rotatedShape = new Shape(7, 10, 30);
		assertFalse(rotatedShape.isRotated());
		rotatedShape.rotate();
		assertTrue(rotatedShape.isRotated());
		rotatedShape.rotate();
		assertFalse(rotatedShape.isRotated());
	}
	

}
