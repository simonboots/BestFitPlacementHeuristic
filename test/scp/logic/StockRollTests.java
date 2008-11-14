package scp.logic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import scp.common.Gap;
import scp.common.PlacedShape;
import scp.common.Shape;
import scp.logic.policies.INichePlacementPolicy;
import scp.logic.policies.PlaceAtLeftmostPolicy;
import scp.logic.policies.PlaceAtRightmostPolicy;
import scp.logic.policies.PlaceNextToShortestNeighbourPolicy;
import scp.logic.policies.PlaceNextToTallestNeighbourPolicy;

public class StockRollTests {

	private StockRoll stockRoll = null;
	
	@Before
	public void setUp() throws Exception {
		stockRoll = new StockRoll(10);
	}
	
	@Test
	public void testEmpty() {
		assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.skylineToString());
		assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.topObjectsToString());
	}
	
	@Test
	public void OneShapeTest() {
		PlacedShape shape = new PlacedShape(1, 5, 3, 3, 0);
		try {
			stockRoll.placeObject(shape);
		} catch (WrongPlacementException e) {
			e.printStackTrace();
		}
		
		assertEquals("0 0 0 5 5 5 0 0 0 0", stockRoll.skylineToString());
		assertEquals("0 0 0 1 1 1 0 0 0 0", stockRoll.topObjectsToString());
		
		Gap gap = stockRoll.getLowestGap();
		assertEquals(6, gap.getX());
		assertEquals(4, gap.getWidth());
		assertEquals(0, gap.getY());
	}
	
	@Test
	public void wrongPlacementTest() {
		boolean success = false;
		PlacedShape shape = new PlacedShape(1, 20, 23, 0, 0);
		
		try {
			stockRoll.placeObject(shape);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			success = true;
		}
		
		assertEquals(true, success);
	}
	
	@Test
	public void raiseGapTest() {
		PlacedShape shape = new PlacedShape(1, 2, 3, 3, 0);
		
		try {
			stockRoll.placeObject(shape);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap gap = stockRoll.getLowestGap();
		assertEquals(6, gap.getX());
		assertEquals(4, gap.getWidth());
		assertEquals(0, gap.getY());
				
		try {
			stockRoll.placeObject(gap);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("0 0 0 2 2 2 2 2 2 2", stockRoll.skylineToString());
		
		gap = stockRoll.getLowestGap();
		assertEquals(0, gap.getX());
		assertEquals(3, gap.getWidth());
		assertEquals(0, gap.getY());
		
		try {
			stockRoll.placeObject(gap);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("2 2 2 2 2 2 2 2 2 2", stockRoll.skylineToString());
		
		gap = stockRoll.getLowestGap();
		assertEquals(0, gap.getX());
		assertEquals(10, gap.getWidth());
		assertEquals(2, gap.getY());
		
		try {
			stockRoll.placeObject(gap);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("2 2 2 2 2 2 2 2 2 2", stockRoll.skylineToString());
	}
	
	@Test
	public void twoShapesTest() {
		PlacedShape shape1 = new PlacedShape(1, 5, 4, 0, 0);
		PlacedShape shape2 = new PlacedShape(2, 2, 3, 5, 0);
		
		try {
			stockRoll.placeObject(shape1);
			stockRoll.placeObject(shape2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("5 5 5 5 0 2 2 2 0 0", stockRoll.skylineToString());
		assertEquals("1 1 1 1 0 2 2 2 0 0", stockRoll.topObjectsToString());
		
		try {
			stockRoll.removeObject(shape1);
			assertEquals("0 0 0 0 0 2 2 2 0 0", stockRoll.skylineToString());
			assertEquals("0 0 0 0 0 2 2 2 0 0", stockRoll.topObjectsToString());
			
			stockRoll.removeObject(shape2);
			assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.skylineToString());
			assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.topObjectsToString());
		} catch (WrongRemovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void twoShapesOnTopTest() {
		PlacedShape shape1 = new PlacedShape(1, 2, 7, 0, 0);
		PlacedShape shape2 = new PlacedShape(2, 3, 4, 2, 2);
		
		try {
			stockRoll.placeObject(shape1);
			stockRoll.placeObject(shape2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("2 2 5 5 5 5 2 0 0 0", stockRoll.skylineToString());
		assertEquals("1 1 2 2 2 2 1 0 0 0", stockRoll.topObjectsToString());
		
		try {
			stockRoll.removeObject(shape2);
		} catch (WrongRemovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("2 2 2 2 2 2 2 0 0 0", stockRoll.skylineToString());
		assertEquals("1 1 1 1 1 1 1 0 0 0", stockRoll.topObjectsToString());

		try {
			stockRoll.placeObject(stockRoll.getLowestGap());
		} catch (WrongPlacementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertEquals("2 2 2 2 2 2 2 2 2 2", stockRoll.skylineToString());
		
		try {
			stockRoll.removeObject(shape1);
		} catch (WrongRemovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.skylineToString());
	}
	
	@Test
	public void gapHeightsTest() {
		PlacedShape s1 = new PlacedShape(1, 3, 2, 0, 0);
		PlacedShape s2 = new PlacedShape(2, 4, 5, 5, 0);
		
		try {
			stockRoll.placeObject(s1);
			stockRoll.placeObject(s2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap g = stockRoll.getLowestGap();
		
		assertEquals(3, g.getLeftHeight());
		assertEquals(4, g.getRightHeight());
	}
	
	@Test
	public void leftmostPolicyTest() {
		PlacedShape s1 = new PlacedShape(1, 3, 2, 0, 0);
		PlacedShape s2 = new PlacedShape(2, 4, 5, 5, 0);
		
		try {
			stockRoll.placeObject(s1);
			stockRoll.placeObject(s2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap g = stockRoll.getLowestGap();
		INichePlacementPolicy pp = new PlaceAtLeftmostPolicy();
		
		Shape s = new Shape(3, 5, 1);
		PlacedShape newPlacedShape = pp.placeShape(s, g);
		
		assertEquals(2, newPlacedShape.getX());
	}
	
	@Test
	public void rightmostPolicyTest() {
		PlacedShape s1 = new PlacedShape(1, 3, 2, 0, 0);
		PlacedShape s2 = new PlacedShape(2, 4, 5, 5, 0);
		
		try {
			stockRoll.placeObject(s1);
			stockRoll.placeObject(s2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap g = stockRoll.getLowestGap();
		INichePlacementPolicy pp = new PlaceAtRightmostPolicy();
		
		Shape s = new Shape(3, 5, 1);
		PlacedShape newPlacedShape = pp.placeShape(s, g);
		
		assertEquals(4, newPlacedShape.getX());
	}
	
	@Test
	public void tallestNeighbourPolicyTest() {
		PlacedShape s1 = new PlacedShape(1, 3, 2, 0, 0);
		PlacedShape s2 = new PlacedShape(2, 4, 5, 5, 0);
		
		try {
			stockRoll.placeObject(s1);
			stockRoll.placeObject(s2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap g = stockRoll.getLowestGap();
		INichePlacementPolicy pp = new PlaceNextToTallestNeighbourPolicy();
		
		Shape s = new Shape(3, 5, 1);
		PlacedShape newPlacedShape = pp.placeShape(s, g);
		
		assertEquals(4, newPlacedShape.getX());
	}
	
	@Test
	public void shortestNeighbourPolicyTest() {
		PlacedShape s1 = new PlacedShape(1, 3, 2, 0, 0);
		PlacedShape s2 = new PlacedShape(2, 4, 5, 5, 0);
		
		try {
			stockRoll.placeObject(s1);
			stockRoll.placeObject(s2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap g = stockRoll.getLowestGap();
		INichePlacementPolicy pp = new PlaceNextToShortestNeighbourPolicy();
		
		Shape s = new Shape(3, 5, 1);
		PlacedShape newPlacedShape = pp.placeShape(s, g);
		
		assertEquals(2, newPlacedShape.getX());
	}
}
