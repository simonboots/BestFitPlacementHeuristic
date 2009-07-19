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

/**
 * Bridge to XML files
 * 
 * Prepares XML content to be accessible through lists
 * 
 * @author Simon Stiefel
 *
 */
public class XMLBridge {
	
	private JAXBContext jaxbContext = null;
	private JAXBElement<Problem> rootElement = null;
	
	/**
	 * @throws JAXBException
	 */
	public XMLBridge() throws JAXBException {
		jaxbContext = JAXBContext.newInstance("scp.common.xml.bindings");
	}
	
	/**
	 * Loads xml document
	 * @param document xml document to be loaded
	 * @throws JAXBException
	 */
	public void loadFile(File document) throws JAXBException {
		SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		try {
			schema = sf.newSchema(new File("src/StockCuttingProblem.xsd"));
		} catch (SAXException e) {
			e.printStackTrace();
		}
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setSchema(schema);
		rootElement = (JAXBElement<Problem>) unmarshaller.unmarshal(document);
	}
	
	/**
	 * Saves xml document
	 * @param document xml file to be saved in
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	public void saveFile(File document) throws FileNotFoundException, JAXBException {
		if (rootElement != null) {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.marshal(rootElement, document);
		}
	}
	
	/**
	 * All shapes
	 * 
	 * @return all shapes sorted by id
	 * @throws JAXBException
	 */
	public List<scp.common.Shape> getShapeList() throws JAXBException {
		Problem p = getProblem();
		Shapes shapes = getShapes(p);
		List<scp.common.Shape> shapeslist = new ArrayList<scp.common.Shape>();
		
		for (Shape s : shapes.getShape()) {
			shapeslist.add(new scp.common.Shape(s.getId(), s.getHeight(), s.getWidth()));
		}
		
		return shapeslist;
	}
	
	/**
	 * All shapes
	 * 
	 * @return all shapes
	 * @throws JAXBException
	 */
	public Map<Integer, scp.common.Shape> getShapeMap() throws JAXBException {
		ArrayList<scp.common.Shape> shapelist = (ArrayList<scp.common.Shape>) getShapeList();
		Map<Integer, scp.common.Shape> shapemap = new HashMap<Integer, scp.common.Shape>();
		for (scp.common.Shape shape : shapelist) {
			shapemap.put(new Integer(shape.getId()), shape);
		}
		
		return shapemap;
	}
	
	/**
	 * Set shape list
	 * @param shapeList shapes to be saved
	 * @throws JAXBException
	 */
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

	/**
	 * All shapes sorted by size
	 * 
	 * @return all shapes sorted by size
	 * @throws JAXBException
	 */
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
	
	/**
	 * All shapes in sorted state (with rotation)
	 * 
	 * @return all shapes in sorted state (with rotation)
	 * @throws JAXBException
	 */
	public Map<Integer, scp.common.Shape> getSortedShapeMap() throws JAXBException {
		ArrayList<scp.common.Shape> shapelist = (ArrayList<scp.common.Shape>) getSortedShapeList();
		Map<Integer, scp.common.Shape> shapemap = new HashMap<Integer, scp.common.Shape>();
		for (scp.common.Shape shape : shapelist) {
			shapemap.put(new Integer(shape.getId()), shape);
		}
		
		return shapemap;
	}
	
	/**
	 * Set sorted shapes
	 * 
	 * @param sortedList shapes in sorted order
	 * @throws JAXBException
	 */
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
	
	/**
	 * all placeable objects in placing order
	 * 
	 * @return all placable objects in pacing order
	 * @throws JAXBException
	 */
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
	
	/**
	 * All shapes in placing state
	 * 
	 * @return all shapes in placing state
	 * @throws JAXBException
	 */
	public Map<Integer, scp.common.IPlaceableObject> getPlacementsMap() throws JAXBException {
		ArrayList<scp.common.IPlaceableObject> placementlist = (ArrayList<scp.common.IPlaceableObject>) getPlacementsList();
		Map<Integer, scp.common.IPlaceableObject> placementmap = new HashMap<Integer, scp.common.IPlaceableObject>();
		for (scp.common.IPlaceableObject placement : placementlist) {
			placementmap.put(new Integer(placement.getId()), placement);
		}
		
		return placementmap;
	}

	/**
	 * Set placeable objects in placing order
	 * 
	 * @param placedList placable objects on placing order
	 * @throws JAXBException
	 */
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
	
	/**
	 * All optimizing steps
	 * 
	 * A shape is followed by one or more gaps.
	 * Gaps represent the gap search for the preceding shape.
	 * 
	 * @return optimizing steps
	 * @throws JAXBException
	 */
	public List<scp.common.IPlaceableObject> getOptimizeList() throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Optimizations optimizations = getOptimizations(solution);
		
		List<scp.common.IPlaceableObject> optimizelist = new ArrayList<scp.common.IPlaceableObject>();
		Map<Integer, scp.common.IPlaceableObject> placementmap = getPlacementsMap();
		
		for (Object optimization : optimizations.getOptimizationOrGap()) {
			// if object is shape
			if (optimization instanceof Optimization) {
				scp.common.Shape shape = (scp.common.Shape) placementmap.get(new Integer(((Optimization)optimization).getShapeid()));
				shape.rotate();
				optimizelist.add(new scp.common.PlacedShape(shape, ((Optimization)optimization).getCoodinates().getX(), ((Optimization)optimization).getCoodinates().getY()));
			}
			
			// if object is gap
			if (optimization instanceof Gap) {
				Coordinates c = ((Gap)optimization).getCoordinates();
				scp.common.Gap gap = new scp.common.Gap(((Gap) optimization).getId(), c.getX(), c.getY(), ((Gap) optimization).getWidth(), ((Gap) optimization).getLeftheight(), ((Gap) optimization).getRightheight());
				optimizelist.add(gap);
			}
		}
		
		return optimizelist;
	}

	/**
	 * set optimizing steps
	 * 
	 * @param optimizedList optimizing steps
	 * @throws JAXBException
	 */
	public void setOptimizedShapeList(List<scp.common.IPlaceableObject> optimizedList) throws JAXBException {
		Problem p = getProblem();
		Solution solution = getSolution(p);
		Optimizations optimizations = getOptimizations(solution);
		
		// Clear optimization list if exist
		List<Object> optimizationlist = optimizations.getOptimizationOrGap();
		if (optimizationlist.size() > 0) {
			optimizationlist.clear();
		}
		
		// Save optimization list
		int counter = 1;
		for (scp.common.IPlaceableObject po : optimizedList) {
			
			// if object is shape
			if (po instanceof scp.common.PlacedShape) {
				Optimization optimization = new Optimization();
				optimization.setId(counter++);
				optimization.setShapeid(po.getId());
				Coordinates coordinates = new Coordinates();
				coordinates.setX(po.getX());
				coordinates.setY(po.getY());
				optimization.setCoodinates(coordinates);
				optimizationlist.add(optimization);
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
				gap.setRightheight(((scp.common.Gap)po).getRightHeight());
				optimizationlist.add(gap);
			}
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
}
