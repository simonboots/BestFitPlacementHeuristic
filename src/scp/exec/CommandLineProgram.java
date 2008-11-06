package scp.exec;

import org.apache.commons.cli.*;

public class CommandLineProgram {

	private void configure(String[] args) {
		CommandLineParser clparser = new GnuParser();
		Options options = generateOptions();
		
		
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("scp-logic INPUTFILE [options]", options);		
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
		
		return options;
	}

	public static void main(String[] args) {
		CommandLineProgram clp = new CommandLineProgram();
		clp.configure(args);
	}
}
