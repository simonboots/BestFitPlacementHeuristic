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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import scp.common.Shape;
import scp.common.Gap;
import scp.logic.ShapePool;

public class ShapePoolTests {
	
	ShapePool sp = new ShapePool();

	@Before
	public void setUp() throws Exception {
		sp.add(new Shape(0, 20, 20));
		sp.add(new Shape(1, 40, 20));
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
		
		System.out.println(sp.toString());
		
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testGaps() {
		Gap gap = new Gap(0, 0, 6, 0, 0);
		
		List<Shape> shapelist = new ArrayList<Shape>();
		shapelist.add(new Shape(1, 5, 3));
		shapelist.add(new Shape(2, 2, 5));
		shapelist.add(new Shape(3, 1, 1));
		shapelist.add(new Shape(4, 3, 7));
		shapelist.add(new Shape(5, 2, 1));
		
		ShapePool sp = new ShapePool();
		sp.setShapeList(shapelist);
		
		sp.sort();
		
		Shape bestFit = sp.findBestShapeforWidth(gap.getWidth());
		System.out.println(sp.toString());
		
		assertEquals(1, bestFit.getId());
		
		gap = new Gap(0, 0, 2, 0, 0);
		
		bestFit = sp.findBestShapeforWidth(gap.getWidth());
		
		assertEquals(2, bestFit.getId());
		
	}
}
