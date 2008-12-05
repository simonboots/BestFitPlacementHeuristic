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

/**
 * Command Line Solver
 * @author sst
 *
 */
public class Solver implements IHeuristicResultCallback {
	
	protected final static int STOCKROLL_WIDTH = 400;
	protected Heuristic heuristic = null;
	protected XMLBridge bridge = null;
	protected List<Shape> sortedShapeList = null;
	protected List<IPlaceableObject> placementList = null;
	protected List<IPlaceableObject> optimizedPlacementList = null;
	protected File outputFile = null;
	public Options options = null;
	
	
	/**
	 * @throws JAXBException
	 */
	public Solver() throws JAXBException {
		heuristic = new Heuristic(STOCKROLL_WIDTH);
		bridge = new XMLBridge();
		sortedShapeList = new ArrayList<Shape>();
		placementList = new ArrayList<IPlaceableObject>();
		optimizedPlacementList = new ArrayList<IPlaceableObject>();
	}

	/**
	 * Configures Solver according to command line options
	 * 
	 * Allowed arguments are:
	 * -p or --policy = use placement policy (leftmost (default), rightmost, shortest, tallest)
	 * -o or --output = Output file (will use stdout if not set)
	 * -h or --help = show help
	 * 
	 * @param args
	 * @throws NoFileSpecifiedException
	 * @throws ParseException
	 * @throws JAXBException
	 * @throws NoArgumentsException
	 */
	public void configure(String[] args) throws NoFileSpecifiedException, ParseException, JAXBException, NoArgumentsException {
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
	
	
	/**
	 * Starts solver
	 * 
	 * @throws WrongPlacementException
	 */
	private void run() throws WrongPlacementException {
		heuristic.run();
	}

	/**
	 * Saves result to file specified in configure()
	 * 
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private void save() throws JAXBException, FileNotFoundException {
		bridge.setSortedShapeList(sortedShapeList);
		bridge.setPlacementsList(placementList);
		bridge.setOptimizedShapeList(optimizedPlacementList);
		
		bridge.saveFile(outputFile);
	}

	/**
	 * Generates options usable in configure()
	 * 
	 * @return generated Options
	 */
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

	
	/**
	 * Helper method to find correct niche placement policy according to command line option
	 * 
	 * @param optionValue placement policy to search for
	 * @return placement policy which was found
	 */
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

	/* (non-Javadoc)
	 * @see scp.logic.IHeuristicResultCallback#optimizedPlacementsCallback(scp.common.IPlaceableObject)
	 */
	public void optimizedPlacementsCallback(IPlaceableObject po) {
		optimizedPlacementList.add(po);
	}

	/* (non-Javadoc)
	 * @see scp.logic.IHeuristicResultCallback#placementsCallback(scp.common.IPlaceableObject)
	 */
	public void placementsCallback(IPlaceableObject po) {
		placementList.add(po);
	}

	/* (non-Javadoc)
	 * @see scp.logic.IHeuristicResultCallback#sortedShapeCallback(scp.common.Shape)
	 */
	public void sortedShapeCallback(Shape s) {
		sortedShapeList.add(s);
	}

	/**
	 * Entry point for command line solver
	 * 
	 * @param args command line arguments
	 */
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
			formatter.printHelp("java -cp scp.jar scp.cli.CommandLineHeuristic INPUTFILE [options]", clh.options);
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
