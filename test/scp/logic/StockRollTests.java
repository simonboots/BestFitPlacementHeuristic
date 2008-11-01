package scp.logic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import scp.common.PlacedShape;

public class StockRollTests {

	private StockRoll stockRoll = null;
	
	@Before
	public void setUp() throws Exception {
		stockRoll = new StockRoll(10);
	}
	
	@Test
	public void testEmpty() {
		assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.skylineToString());
		assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.topShapesToString());
	}
	
	@Test
	public void OneShapeTest() {
		PlacedShape shape = new PlacedShape(1, 5, 3, 3, 0);
		try {
			stockRoll.placeShape(shape);
		} catch (WrongPlacementException e) {
			e.printStackTrace();
		}
		
		assertEquals("0 0 0 5 5 5 0 0 0 0", stockRoll.skylineToString());
		assertEquals("0 0 0 1 1 1 0 0 0 0", stockRoll.topShapesToString());
		
		Gap gap = stockRoll.getLowestGap();
		assertEquals(6, gap.getLocation());
		assertEquals(4, gap.getWidth());
		assertEquals(0, gap.getHeight());
	}
	
	@Test
	public void wrongPlacementTest() {
		boolean success = false;
		PlacedShape shape = new PlacedShape(1, 20, 23, 0, 0);
		
		try {
			stockRoll.placeShape(shape);
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
			stockRoll.placeShape(shape);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gap gap = stockRoll.getLowestGap();
		assertEquals(6, gap.getLocation());
		assertEquals(4, gap.getWidth());
		assertEquals(0, gap.getHeight());
				
		try {
			stockRoll.raiseGap(gap);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("0 0 0 2 2 2 2 2 2 2", stockRoll.skylineToString());
		
		gap = stockRoll.getLowestGap();
		assertEquals(0, gap.getLocation());
		assertEquals(3, gap.getWidth());
		assertEquals(0, gap.getHeight());
		
		try {
			stockRoll.raiseGap(gap);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("2 2 2 2 2 2 2 2 2 2", stockRoll.skylineToString());
		
		gap = stockRoll.getLowestGap();
		assertEquals(0, gap.getLocation());
		assertEquals(10, gap.getWidth());
		assertEquals(2, gap.getHeight());
		
		try {
			stockRoll.raiseGap(gap);
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
			stockRoll.placeShape(shape1);
			stockRoll.placeShape(shape2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("5 5 5 5 0 2 2 2 0 0", stockRoll.skylineToString());
		assertEquals("1 1 1 1 0 2 2 2 0 0", stockRoll.topShapesToString());
		
		try {
			stockRoll.removeShape(shape1);
			assertEquals("0 0 0 0 0 2 2 2 0 0", stockRoll.skylineToString());
			assertEquals("0 0 0 0 0 2 2 2 0 0", stockRoll.topShapesToString());
			
			stockRoll.removeShape(shape2);
			assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.skylineToString());
			assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.topShapesToString());
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
			stockRoll.placeShape(shape1);
			stockRoll.placeShape(shape2);
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("2 2 5 5 5 5 2 0 0 0", stockRoll.skylineToString());
		assertEquals("1 1 2 2 2 2 1 0 0 0", stockRoll.topShapesToString());
		
		try {
			stockRoll.removeShape(shape2);
		} catch (WrongRemovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("2 2 2 2 2 2 2 0 0 0", stockRoll.skylineToString());
		assertEquals("1 1 1 1 1 1 1 0 0 0", stockRoll.topShapesToString());

		try {
			stockRoll.raiseGap(stockRoll.getLowestGap());
		} catch (WrongPlacementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		assertEquals("2 2 2 2 2 2 2 2 2 2", stockRoll.skylineToString());
		
		try {
			stockRoll.removeShape(shape1);
		} catch (WrongRemovalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("0 0 0 0 0 0 0 0 0 0", stockRoll.skylineToString());
	}
}
