package scp.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import scp.common.Shape;
import scp.common.xml.XMLBridge;

public class Generator {
	
	protected int maxDimension = 100;
	protected int numOfShapes = 100;
	protected List<Shape> shapeList = null;
	protected XMLBridge bridge = null;
	protected File outputFile = null;
	public Options options = null;
	
	public Generator() throws JAXBException {
		this.shapeList = new ArrayList<Shape>();
		this.bridge = new XMLBridge();
	}
	
	private void configure(String[] args) throws ParseException, NoArgumentsException, NoFileSpecifiedException {
		CommandLineParser clparser = new GnuParser();
		options = generateOptions();
	
		CommandLine commandLine = clparser.parse(options, args);
		
		if (commandLine.hasOption('h')) {
			throw new NoArgumentsException("");
		}
		
		// Check if file is specified
		if (commandLine.getArgList().size() < 1) {
			throw new NoFileSpecifiedException("Please specify an output file");
		}
		
		outputFile = new File((String) commandLine.getArgList().get(0));
		
		if (commandLine.hasOption('n')) {
			this.numOfShapes = new Integer(commandLine.getOptionValue('n'));
		}
		
		if (commandLine.hasOption('m')) {
			this.maxDimension = new Integer(commandLine.getOptionValue('m'));
		}
	}

	public void generate() {
		Random generator = new Random(4711);
		
		for (int i = 0; i < numOfShapes; i++) {
			int height = generator.nextInt(maxDimension + 1);
			int width = generator.nextInt(maxDimension + 1);
			shapeList.add(new Shape(i, height, width));
		}
	}
	
	public void save() throws JAXBException, FileNotFoundException {
		bridge.setShapeList(shapeList);
		bridge.saveFile(outputFile);
	}
	
	private Options generateOptions() {
		Options options = new Options();
		options.addOption("m", "max", true, "maximal dimension (height and width). Default: 100");
		options.addOption("n", "num", true, "amount of shapes to generate. Default: 100");
		options.addOption("h", "help", false, "show this help");
		
		return options;
	}

	public static void main(String args[]) {
		Generator clg = null;
		try {
			clg = new Generator();
			clg.configure(args);
		} catch (JAXBException e) {
			System.out.println("Error while parsing XML: " + e.getMessage());
			return;
		} catch (ParseException e) {
			System.out.println("Error while parsing arguments: " + e.getMessage());
			return;
		} catch (NoArgumentsException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -cp scp.jar scp.exec.CommandLineGenerator OUTPUTFILE [options]", clg.options);
			return;
		} catch (NoFileSpecifiedException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		clg.generate();
		
		try {
			clg.save();
		} catch (FileNotFoundException e) {
			System.out.println("Error while saving file: " + e.getMessage());
			return;
		} catch (JAXBException e) {
			System.out.println("Error while generating XML: " + e.getMessage());
			return;
		}
	}
}
