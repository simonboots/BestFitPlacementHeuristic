package scp.common.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

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
		ArrayList<scp.common.Shape> shapelist = (ArrayList<scp.common.Shape>) getShapeList();
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
	
	public List<scp.common.PlacedShape> getPlacedShapeList() throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Placements placements = getPlacements(solution);
		
		List<scp.common.PlacedShape> placedlist = new ArrayList<scp.common.PlacedShape>();
		Map<Integer, scp.common.Shape> shapemap = getSortedShapeMap();
		
		for (Placement placement : placements.getPlacement()) {
			scp.common.Shape shape = shapemap.get(new Integer(placement.getShapeid()));
			placedlist.add(new scp.common.PlacedShape(shape, placement.getCoords().getX(), placement.getCoords().getY()));
		}
		
		return placedlist;
	}

	public void setPlacedShapeList(List<scp.common.PlacedShape> placedList) throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Placements placements = getPlacements(solution);
		
		// Clear Placement list if exist
		List<Placement> placementlist = placements.getPlacement();
		if (placementlist.size() > 0) {
			placementlist.clear();
		}
		
		// Save placement list
		int counter = 1;
		for (scp.common.PlacedShape s : placedList) {
			Placement placement = new Placement();
			placement.setId(counter++);
			placement.setShapeid(s.getId());
			Coordinates coordinates = new Coordinates();
			coordinates.setX(s.getX());
			coordinates.setY(s.getY());
			placement.setCoords(coordinates);
			placementlist.add(placement);
		}
	}
	
	public List<scp.common.PlacedShape> getOptimizedShapeList() throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Optimizations optimizations = getOptimizations(solution);
		
		List<scp.common.PlacedShape> optimizelist = new ArrayList<scp.common.PlacedShape>();
		Map<Integer, scp.common.Shape> shapemap = getSortedShapeMap();
		
		for (Optimization optimization : optimizations.getOptimization()) {
			scp.common.Shape shape = shapemap.get(new Integer(optimization.getShapeid()));
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
			rootElement = new JAXBElement<Problem>(new QName("problem"), Problem.class, new Problem());
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
}
