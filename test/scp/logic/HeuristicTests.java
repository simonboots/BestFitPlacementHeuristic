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
	List<PlacedShape> placedShapeList = new ArrayList<PlacedShape>();

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
		placedShapeList.clear();
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topShapesToString());
		
		assertEquals(18, placedShapeList.get(1).getX());
		assertEquals(2, placedShapeList.get(2).getY());		
	}
	
	@Test
	public void RightmostTest() {
		placedShapeList.clear();
		
		h.setPlacementPolicy(new PlaceAtRightmostPolicy());
		
		try {
			h.run();
		} catch (WrongPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(h.stockRoll.skylineToString());
		System.out.println(h.stockRoll.topShapesToString());
		
		assertEquals(2, placedShapeList.get(0).getX());
		assertEquals(8, placedShapeList.get(2).getX());
	}
	
	@Test
	public void ShortestTest() {
		placedShapeList.clear();
		
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
		System.out.println(h.stockRoll.topShapesToString());
		
		assertEquals(7, placedShapeList.get(2).getX());
		assertEquals(0, placedShapeList.get(1).getX());
	}
	
	@Test
	public void TallestTest() {
		placedShapeList.clear();
		
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
		System.out.println(h.stockRoll.topShapesToString());
		
		assertEquals(6, placedShapeList.get(1).getX());
		assertEquals(2, placedShapeList.get(2).getX());
	}

	public void optimizedPlacedShapeCallback(PlacedShape ps) {
		// nothing...
	}

	public void placedShapeCallback(PlacedShape ps) {
		placedShapeList.add(ps);
	}

	public void sortedShapeCallback(Shape s) {
		// nothing...
	}

}
