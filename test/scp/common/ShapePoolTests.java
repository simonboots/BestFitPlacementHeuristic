package scp.common;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShapePoolTests {
	
	ShapePool sp = new ShapePool();

	@Before
	public void setUp() throws Exception {
		sp.add(new Shape(0, 40, 20));
		sp.add(new Shape(1, 20, 20));
	}
	
	@Test
	public void testUnordered() {
		Iterator<Shape> iterator = sp.iterator();
		Shape s = null;
		int[] result = new int[2];
		int[] expected = {0, 1};
		int counter = 0;
		
		while(iterator.hasNext()) {
			s = iterator.next();
			result[counter++] = s.getId();
		}
		
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testOrdered() {
		sp.sort();
		Iterator<Shape> iterator = sp.iterator();
		Shape s = null;
		int[] result = new int[2];
		int[] expected = {1, 0};
		int counter = 0;
		
		while(iterator.hasNext()) {
			s = iterator.next();
			result[counter++] = s.getId();
		}
		
		assertArrayEquals(expected, result);
	}
}
