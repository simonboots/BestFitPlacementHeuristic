package scp.common.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.SchemaFactoryLoader;

import org.xml.sax.SAXException;

import scp.common.xml.bindings.*;

public class XMLBridge {
	
	private JAXBContext jaxbContext = null;
	private JAXBElement<Problem> rootElement = null;
	
	public XMLBridge() throws JAXBException {
		jaxbContext = JAXBContext.newInstance("scp.common.xml.bindings");
	}
	
	public void loadFile(File document) throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		rootElement = (JAXBElement<Problem>) unmarshaller.unmarshal(document);
	}
	
	public void saveFile(File document) throws FileNotFoundException, JAXBException {
		if (rootElement != null) {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.marshal(rootElement, document);
		}
	}
	
	public List<scp.common.Shape> getShapeList() throws JAXBException {
		Problem p = getProblem();
		Shapes shapes = getShapes(p);
		List<scp.common.Shape> shapeslist = new ArrayList<scp.common.Shape>();
		
		for (Shape s : shapes.getShape()) {
			shapeslist.add(new scp.common.Shape(s.getId(), s.getHeight(), s.getWidth()));
		}
		
		return shapeslist;
	}
	
	public Map<Integer, scp.common.Shape> getShapeMap() throws JAXBException {
		ArrayList<scp.common.Shape> shapelist = (ArrayList<scp.common.Shape>) getShapeList();
		Map<Integer, scp.common.Shape> shapemap = new HashMap<Integer, scp.common.Shape>();
		for (scp.common.Shape shape : shapelist) {
			shapemap.put(new Integer(shape.getId()), shape);
		}
		
		return shapemap;
	}
	
	public void setShapeList(List<scp.common.Shape> shapeList) throws JAXBException {
		Problem p = getProblem();
		Shapes shapes = getShapes(p);
		
		// Clear Shape list if exist
		List<scp.common.xml.bindings.Shape> shapelist = shapes.getShape();
		if (shapelist.size() > 0) {
			shapelist.clear();
		}
		
		// Save shape list
		for (scp.common.Shape s : shapeList) {
			Shape shape = new Shape();
			shape.setId(s.getId());
			shape.setHeight(s.getHeight());
			shape.setWidth(s.getWidth());
			shapelist.add(shape);
		}
	}

	public List<scp.common.Shape> getSortedShapeList() throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Sorting sorting = getSorting(solution);
		
		List<scp.common.Shape> sortedlist = new ArrayList<scp.common.Shape>();
		Map<Integer, scp.common.Shape> shapemap = getShapeMap();
		
		for (Sort s : sorting.getSort()) {
			scp.common.Shape shape = shapemap.get(new Integer(s.getShapeid()));
			if (s.isRotated()) shape.rotate();
			sortedlist.add(shape);
		}
		
		return sortedlist;
	}
	
	public Map<Integer, scp.common.Shape> getSortedShapeMap() throws JAXBException {
		ArrayList<scp.common.Shape> shapelist = (ArrayList<scp.common.Shape>) getSortedShapeList();
		Map<Integer, scp.common.Shape> shapemap = new HashMap<Integer, scp.common.Shape>();
		for (scp.common.Shape shape : shapelist) {
			shapemap.put(new Integer(shape.getId()), shape);
		}
		
		return shapemap;
	}
	
	public void setSortedShapeList(List<scp.common.Shape> sortedList) throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Sorting sorting = getSorting(solution);
		
		// Clear Sort list if exist
		List<Sort> sortlist = sorting.getSort();
		if (sortlist.size() > 0) {
			sortlist.clear();
		}
		
		// Save sorted list
		int counter = 1;
		for (scp.common.Shape s : sortedList) {
			Sort sort = new Sort();
			sort.setId(counter++);
			sort.setShapeid(s.getId());
			sort.setRotated(s.isRotated());
			sortlist.add(sort);
		}
	}
	
	public List<scp.common.IPlaceableObject> getPlacementsList() throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Placements placements = getPlacements(solution);
		
		List<scp.common.IPlaceableObject> placementlist = new ArrayList<scp.common.IPlaceableObject>();
		Map<Integer, scp.common.Shape> shapemap = getShapeMap();
		
		for (Object placement : placements.getPlacementOrGap()) {
			// if object is shape
			if (placement instanceof Placement) {
				scp.common.Shape shape = shapemap.get(new Integer(((Placement) placement).getShapeid()));
				scp.common.PlacedShape placedShape = new scp.common.PlacedShape(shape, ((Placement) placement).getCoordinates().getX(), ((Placement) placement).getCoordinates().getY());
				if (((Placement)placement).isRotated()) placedShape.rotate();
				placementlist.add(placedShape);
			}
			
			// if object is gap
			if (placement instanceof Gap) {
				placement = (Gap)placement;
				
				int id = ((Gap)placement).getId();
				int width = ((Gap)placement).getWidth();
				int leftheight = ((Gap)placement).getLeftheight();
				int rightheight = ((Gap)placement).getRightheight();
				int x = ((Gap)placement).getCoordinates().getX();
				int y = ((Gap)placement).getCoordinates().getY();
				placementlist.add(new scp.common.Gap(id, x, y, width, leftheight, rightheight));
			}
		}
		
		return placementlist;
	}
	
	public Map<Integer, scp.common.IPlaceableObject> getPlacementsMap() throws JAXBException {
		ArrayList<scp.common.IPlaceableObject> placementlist = (ArrayList<scp.common.IPlaceableObject>) getPlacementsList();
		Map<Integer, scp.common.IPlaceableObject> placementmap = new HashMap<Integer, scp.common.IPlaceableObject>();
		for (scp.common.IPlaceableObject placement : placementlist) {
			placementmap.put(new Integer(placement.getId()), placement);
		}
		
		return placementmap;
	}

	public void setPlacementsList(List<scp.common.IPlaceableObject> placedList) throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Placements placements = getPlacements(solution);
		
		// Clear Placement list if exist
		List<Object> placementlist = placements.getPlacementOrGap();
		if (placementlist.size() > 0) {
			placementlist.clear();
		}
		
		// Save placement list
		int counter = 1;
		for (scp.common.IPlaceableObject po : placedList) {
			
			// if object is shape
			if (po instanceof scp.common.PlacedShape) {
				Placement placement = new Placement();
				placement.setId(counter++);
				placement.setShapeid(po.getId());
				placement.setRotated(((scp.common.Shape) po).isRotated());
				Coordinates coordinates = new Coordinates();
				coordinates.setX(po.getX());
				coordinates.setY(po.getY());
				placement.setCoordinates(coordinates);
				placementlist.add(placement);	
			}
			
			// if object is gap
			if (po instanceof scp.common.Gap) {
				Gap gap = new Gap();
				gap.setId(counter++);
				Coordinates coordinates = new Coordinates();
				coordinates.setX(po.getX());
				coordinates.setY(po.getY());
				gap.setCoordinates(coordinates);
				gap.setWidth(po.getWidth());
				gap.setLeftheight(((scp.common.Gap)po).getLeftHeight());
				gap.setRightheight(((scp.common.Gap) po).getRightHeight());
				placementlist.add(gap);
			}	
		}
	}
	
	public List<scp.common.PlacedShape> getOptimizedShapeList() throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Optimizations optimizations = getOptimizations(solution);
		
		List<scp.common.PlacedShape> optimizelist = new ArrayList<scp.common.PlacedShape>();
		Map<Integer, scp.common.IPlaceableObject> placementmap = getPlacementsMap();
		
		for (Optimization optimization : optimizations.getOptimization()) {
			scp.common.Shape shape = (scp.common.Shape) placementmap.get(new Integer(optimization.getShapeid()));
			shape.rotate();
			optimizelist.add(new scp.common.PlacedShape(shape, optimization.getCoodinates().getX(), optimization.getCoodinates().getY()));
		}
		
		return optimizelist;
	}

	public void setOptimizedShapeList(List<scp.common.PlacedShape> optimizedList) throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Optimizations optimizations = getOptimizations(solution);
		
		// Clear optimization list if exist
		List<Optimization> optimizationlist = optimizations.getOptimization();
		if (optimizationlist.size() > 0) {
			optimizationlist.clear();
		}
		
		// Save optimization list
		int counter = 1;
		for (scp.common.PlacedShape s : optimizedList) {
			Optimization optimization = new Optimization();
			optimization.setId(counter++);
			optimization.setShapeid(s.getId());
			Coordinates coordinates = new Coordinates();
			coordinates.setX(s.getX());
			coordinates.setY(s.getY());
			optimization.setCoodinates(coordinates);
			optimizationlist.add(optimization);
		}
	}
	
	private Problem getProblem() throws JAXBException {
		// Create Problem if it does not exist
		if (rootElement == null) {
			rootElement = new JAXBElement<Problem>(new QName("ns2:problem"), Problem.class, new Problem());
			//rootElement = new JAXBElement<Problem>()
		}
		return rootElement.getValue();
	}
	
	private Solution getSolution(Problem p) {
		// Create Solution if it does not exist
		Solution solution = p.getSolution();
		if (solution == null) {
			p.setSolution(new Solution());
			solution = p.getSolution();
		}
		
		return solution;
	}
	
	private Placements getPlacements(Solution s) {
		// Create Placements if it does not exist
		Placements placements = s.getPlacements();
		if (placements == null) {
			s.setPlacements(new Placements());
			placements = s.getPlacements();
		}
		
		return placements;
	}
	
	private Optimizations getOptimizations(Solution s) {
		// Create Optimizations if it does not exist
		Optimizations optimizations = s.getOptimize();
		if (optimizations == null) {
			s.setOptimize(new Optimizations());
			optimizations = s.getOptimize();
		}
		
		return optimizations;
	}
	
	private Shapes getShapes(Problem p) {
		// Create Shapes if it does not exist
		Shapes shapes = p.getShapes();
		if (shapes == null) {
			p.setShapes(new Shapes());
			shapes = p.getShapes();
		}
		
		return shapes;
	}
		
	private Sorting getSorting(Solution s) {
		// Create Sorting if it does not exist
		Sorting sorting = s.getSorting();
		if (sorting == null) {
			s.setSorting(new Sorting());
			sorting = s.getSorting();
		}
		
		return sorting;
	}
	
//	class SortById implements Comparator<Shape> {
//
//		public int compare(Shape s1, Shape s2) {
//			if (s1.getId() < s2.getId()) {
//				return 1;
//			} else if (s1.getId() > s2.getId()) {
//				return -1;
//			}
//			
//			return 0;
//		}
//	}
}
