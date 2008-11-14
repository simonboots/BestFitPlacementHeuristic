package scp.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.commons.cli.*;
import org.xml.sax.SAXException;

import scp.common.*;
import scp.common.xml.XMLBridge;
import scp.logic.*;
import scp.logic.policies.*;

public class Solver implements IHeuristicResultCallback {
	
	protected final static int STOCKROLL_WIDTH = 200;
	protected Heuristic heuristic = null;
	protected XMLBridge bridge = null;
	protected List<Shape> sortedShapeList = null;
	protected List<IPlaceableObject> placementList = null;
	protected List<PlacedShape> optimizedShapeList = null;
	protected File outputFile = null;
	public Options options = null;
	
	public Solver() throws JAXBException {
		heuristic = new Heuristic(STOCKROLL_WIDTH);
		bridge = new XMLBridge();
		sortedShapeList = new ArrayList<Shape>();
		placementList = new ArrayList<IPlaceableObject>();
		optimizedShapeList = new ArrayList<PlacedShape>();
	}

	private void configure(String[] args) throws NoFileSpecifiedException, ParseException, JAXBException, NoArgumentsException {
		CommandLineParser clparser = new GnuParser();
		options = generateOptions();

		CommandLine commandLine = clparser.parse(options, args);
		
		if (commandLine.hasOption('h')) {
			throw new NoArgumentsException("");
		}
		
		// Check if file is specified
		if (commandLine.getArgList().size() < 1) {
			throw new NoFileSpecifiedException("Please specify an input file");
		}
		
		File source = new File((String) commandLine.getArgList().get(0));
		
		// Check if file exists
		if (! source.exists()) {
			throw new NoFileSpecifiedException(source.toString() + " does not exist!");
		}
		
		// save output file
		if (commandLine.hasOption('o')) {
			outputFile = new File(commandLine.getOptionValue('o'));
		} else {
			outputFile = source; 
		}
		
		// find placement policy
		if (commandLine.hasOption('p')) {
			heuristic.setPlacementPolicy(findPlacementPolicy(commandLine.getOptionValue('p')));
		}
		
		// prepare heuristic
		bridge.loadFile(source);
		heuristic.setResultCallback(this);
		heuristic.setShapeList(bridge.getShapeList());
	}
	
	private void run() throws WrongPlacementException {
		heuristic.run();
	}

	private void save() throws JAXBException, FileNotFoundException {
		bridge.setSortedShapeList(sortedShapeList);
		bridge.setPlacementsList(placementList);
		bridge.setOptimizedShapeList(optimizedShapeList);
		
		bridge.saveFile(outputFile);
	}

	private Options generateOptions() {
		Options options = new Options();
		options.addOption("p", "policy", true, "use placement policy (leftmost (default), rightmost, shortest, tallest)");
		options.addOption(OptionBuilder.withArgName("file")
				                       .hasArg()
				                       .withDescription("Output file (will use stdout if not set)")
				                       .isRequired(false)
				                       .withLongOpt("output")
				                       .create("o"));
		options.addOption("h", "help", false, "show this help");
		
		return options;
	}

	private INichePlacementPolicy findPlacementPolicy(String optionValue) {
		if (optionValue.equals("rightmost")) {
			return new PlaceAtRightmostPolicy();
		}
		
		if (optionValue.equals("shortest")) {
			return new PlaceNextToShortestNeighbourPolicy();
		}
		
		if (optionValue.equals("tallest")) {
			return new PlaceNextToTallestNeighbourPolicy();
		}
		
		System.out.println("Placement policy " + optionValue + " not found. Defaulting to leftmost.");
		return new PlaceAtLeftmostPolicy();
	}

	public void optimizedPlacedShapeCallback(PlacedShape ps) {
		optimizedShapeList.add(ps);
	}

	public void placementsCallback(IPlaceableObject po) {
		placementList.add(po);
	}

	public void sortedShapeCallback(Shape s) {
		sortedShapeList.add(s);
	}

	public static void main(String[] args) {
		
		Solver clh = null;
		
		try {
			clh = new Solver();
			clh.configure(args);
		} catch (JAXBException e) {
			System.out.println("Error while parsing XML: " + e.getMessage());
			return;
		} catch (ParseException e) {
			System.out.println("Error while parsing arguments: " + e.getMessage());
			return;
		} catch (NoFileSpecifiedException e) {
			System.out.println(e.getMessage());
			return;
		} catch (NoArgumentsException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -cp scp.jar scp.exec.CommandLineHeuristic INPUTFILE [options]", clh.options);
			return;
		}
		
		try {
			clh.run();
		} catch (WrongPlacementException e) {
			System.out.println("Error during placement: " + e.getMessage());
			return;
		}
		
		try {
			clh.save();
		} catch (FileNotFoundException e) {
			System.out.println("Error while saving file: " + e.getMessage());
			return;
		} catch (JAXBException e) {
			System.out.println("Error while generating XML: " + e.getMessage());
			return;
		}
	}
}
