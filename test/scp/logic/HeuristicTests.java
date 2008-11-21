package scp.logic;

import java.util.ArrayList;
import java.util.List;

import scp.common.*;
import scp.logic.policies.PlaceAtRightmostPolicy;
import scp.logic.policies.PlaceNextToShortestNeighbourPolicy;
import scp.logic.policies.PlaceNextToTallestNeighbourPolicy;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HeuristicTests implements IHeuristicResultCallback {
	
	private Heuristic h = null;
	List<Shape> shapelist = new ArrayList<Shape>();
	List<IPlaceableObject> placeableList = new ArrayList<IPlaceableObject>();
	List<IPlaceableObject> optimizedList = new ArrayList<IPlaceableObject>();

	@Before
	public void setUp() throws Exception {
		h = new Heuristic(20);
		shapelist.add(new Shape(1, 12, 11));
		shapelist.add(new Shape(2, 18, 2));
		shapelist.add(new Shape(3, 2, 2));
		
		h.setShapeList(shapelist);
		h.setResultCallback(this);
	}
	
	@Test
	public void LeftmostTest() {
		placeableList.clear();
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topObjectsToString());
		
		assertEquals(18, placeableList.get(2).getX());
		assertEquals(2, placeableList.get(4).getY());		
	}
	
	@Test
	public void RightmostTest() {
		placeableList.clear();
		
		h.setPlacementPolicy(new PlaceAtRightmostPolicy());
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topObjectsToString());
		
		assertEquals(2, placeableList.get(1).getX());
		assertEquals(8, placeableList.get(5).getX());
	}
	
	@Test
	public void ShortestTest() {
		placeableList.clear();
		
		h.setPlacementPolicy(new PlaceNextToShortestNeighbourPolicy());
		
		shapelist.clear();
		shapelist.add(new Shape(1, 5, 5));
		shapelist.add(new Shape(2, 9, 2));
		shapelist.add(new Shape(3, 4, 3));
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topObjectsToString());
		
		assertEquals(7, placeableList.get(5).getX());
		assertEquals(0, placeableList.get(3).getX());
	}
	
	@Test
	public void TallestTest() {
		placeableList.clear();
		
		h.setPlacementPolicy(new PlaceNextToTallestNeighbourPolicy());
		
		shapelist.clear();
		shapelist.add(new Shape(1, 5, 5));
		shapelist.add(new Shape(2, 9, 2));
		shapelist.add(new Shape(3, 4, 3));
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topObjectsToString());
		
		assertEquals(6, placeableList.get(3).getX());
		assertEquals(2, placeableList.get(5).getX());
	}
	
	@Test
	public void OptimizeTest() {
		optimizedList.clear();
		shapelist.clear();
		shapelist.add(new Shape(1, 1, 18));
		shapelist.add(new Shape(2, 1, 18));
		shapelist.add(new Shape(3, 1, 18));
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topObjectsToString());
		System.out.println(optimizedList);
		
		assertTrue(new Integer(h.stockRoll.topObjectsToString().split(" ")[18]) < 0);
		assertTrue(new Integer(h.stockRoll.topObjectsToString().split(" ")[19]) < 0);
		assertTrue(optimizedList.get(2) instanceof PlacedShape);
		assertTrue(optimizedList.get(5) instanceof PlacedShape);
	}

	public void optimizedPlacementsCallback(IPlaceableObject po) {
		optimizedList.add(po);
	}

	public void sortedShapeCallback(Shape s) {
		// nothing...
	}

	public void placementsCallback(IPlaceableObject po) {
		placeableList.add(po);		
	}

}
