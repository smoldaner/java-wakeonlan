/*
 * $Id: WakeOnLan.java,v 1.1 2003/09/01 07:10:41 gon23 Exp $
 */
package wol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * The only purpose of this class is to wake up a machine from the commandline
 * using {@link wol.WakeUpUtil WakeUpUtil} 
 * 
 * @see wol.WakeUpUtil 
 * @author <a href="mailto:s.moldaner@gmx.net">Steffen Moldaner</a>
 */
public class WakeOnLan {
	private final static String VERSION = "0.3.0";
	private final static String VERSIONSTRING = "wakeonlan version " + VERSION;

	private ResourceBundle bundle = ResourceBundle.getBundle("wol.Resources");
	private Options options;
	
	public WakeOnLan(String args[]) {
		super();
		
		createOptions();

		CommandLineParser parser = new PosixParser();
		String host = WakeUpUtil.DEFAULT_HOST;
		int port = WakeUpUtil.DEFAULT_PORT;
		boolean debug = false;
		
		try {
			CommandLine line = parser.parse(options, args);
			
			debug = line.hasOption('d'); 
			
			if (line.hasOption('h')) {
				printHelpAndExit();
			}
			
			if (line.hasOption('v')) {
				printVersionAndExit();
			}
			
			if (line.hasOption('p')) {
				port = Integer.parseInt(line.getOptionValue('p'));	
			}
			
			if (line.hasOption('i')) {
				host = line.getOptionValue('i');				
			}
			
			String[] hardwareAddresses; 
			
			if (line.hasOption('f')) {
				List hwAddressList = new ArrayList();
				String file = line.getOptionValue('f');
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					
					for (String address = reader.readLine(); null != address; address = reader.readLine()) {
						hwAddressList.add(address);
					}
				} catch (Exception e) {
					printErrorAndExit(MessageFormat.format(bundle.getString("error.inputfile"), new Object[]{file}), e, debug);
				}
				
				hardwareAddresses = (String[]) hwAddressList.toArray(new String[hwAddressList.size()]);
			} else {
				hardwareAddresses = line.getArgs();
			}
			
			if (null == hardwareAddresses || 0 == hardwareAddresses.length) {
				printErrorAndExit(bundle.getString("error.arguments.missing"), null, debug);
			}
			
			WakeUpUtil wakeOnLan = new WakeUpUtil(host, port);
			
			wakeOnLan.wakeUp(hardwareAddresses);				
		} catch (ParseException e) {
			printErrorAndExit(bundle.getString("error.parsingCommandline"), e, debug);
			System.exit(1);
		} catch (UnknownHostException e) {
			printErrorAndExit(MessageFormat.format(bundle.getString("error.unknownHost"), new Object[] {host}), e, debug);
		} catch (SocketException e) {
			printErrorAndExit(bundle.getString("error.socket"), e, debug);
		} catch (IOException e) {
			printErrorAndExit(MessageFormat.format(bundle.getString("error.io"), new Object[] {host, new Integer(port)}), e, debug);
		} catch (SecurityException e) {
			printErrorAndExit(bundle.getString("error.security"), e, debug);
		} catch (NumberFormatException e) {
			printErrorAndExit(MessageFormat.format(bundle.getString("error.portNaN"), new Object[]{new Integer(port)}),  e, debug);
		}
	}

	/**
	 * The WakeOnLan main method.
	 * 
	 * @param args an array containing the commandline arguments, if any
	 */
	public static void main(String[] args) {
		new WakeOnLan(args);
	}
	
	/**
	 * Prints the commandline help and exists with the given code.
	 * 
	 * @param exitCode the exit code
	 */
	protected void printHelpAndExit(int exitCode) {
		HelpFormatter formatter = new HelpFormatter();
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(bundle.getString("usage.cmdline.label"));
		buffer.append(' ');
		buffer.append(bundle.getString("usage.option.label"));
		buffer.append(' ');
		buffer.append(bundle.getString("usage.hardwareAddress.label"));
		
		formatter.printHelp(buffer.toString(), bundle.getString("help.header"), options, bundle.getString("help.footer"), false);
		System.exit(exitCode);
	}
	
	/**
	 * Prints an error and exits with code 1.
	 * 
	 * @param err an error message
	 * @param e the exception the caused the error. can be null
	 * @param debug if true, the exception will be printetd too
	 */
	protected void printErrorAndExit(String err, Exception e, boolean debug) {
		System.err.println(err);
		
		if (debug && null != e) {
			System.err.println("Exception:\n");
			System.err.println(e.getLocalizedMessage());
		}
		
		System.exit(1);
	}
	
	/**
	 * Print the WakeOnLan version and exit.
	 *
	 */
	protected void printVersionAndExit() {
		System.out.println(VERSIONSTRING);
		System.exit(0);
	}
	
	/**
	 * Prints the commandline help and exits with code 0.
	 *
	 */
	protected void printHelpAndExit() {
		printHelpAndExit(0);
	}
	
	/**
	 * Creates the commandline options
	 *
	 */
	private void createOptions() {
		options = new Options();
		OptionBuilder.withArgName(bundle.getString("cmdline.value.inet-address"));
		OptionBuilder.withLongOpt("inet-address");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(bundle.getString("cmdline.description.inet-address"));
		options.addOption(OptionBuilder.create('i'));
		
		OptionBuilder.withArgName(bundle.getString("cmdline.value.port"));
		OptionBuilder.withLongOpt("port");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(bundle.getString("cmdline.description.port"));
		options.addOption(OptionBuilder.create('p'));
		
		OptionBuilder.withArgName(bundle.getString("cmdline.value.file"));
		OptionBuilder.withLongOpt("file");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(bundle.getString("cmdline.description.file"));
		options.addOption(OptionBuilder.create('f'));
		
		options.addOption("v", "version", false, bundle.getString("cmdline.description.version"));
		options.addOption("h", "help", false, bundle.getString("cmdline.description.help"));
		options.addOption("d", "debug", false, bundle.getString("cmdline.description.debug"));
	}
}

/*
 * $Log: WakeOnLan.java,v $
 * Revision 1.1  2003/09/01 07:10:41  gon23
 * Initial
 *
 */