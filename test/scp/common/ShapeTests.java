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
		assertTrue(shape.compareTo(smallerShape) == 1);
	}
	
	@Test
	public void compareToLargerShape() {
		Shape largerShape = new Shape(3, 20, 30);
		assertTrue(shape.compareTo(largerShape) == -1);
	}
	
	@Test
	public void compareToEqualShape() {
		Shape equalShape = new Shape(4, 10, 20);
		assertTrue(shape.compareTo(equalShape) == 0);
	}
	
	@Test
	public void compareToSmallerWidthShape() {
		Shape smallerWidthShape = new Shape(5, 10, 10);
		assertTrue(shape.compareTo(smallerWidthShape) == 1);
	}
	
	@Test
	public void compareToLargerWidthShape() {
		Shape largerWidthShape = new Shape(6, 10, 30);
		assertTrue(shape.compareTo(largerWidthShape) == -1);
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
