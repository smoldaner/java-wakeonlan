/*
 * $Id: WakeOnLan.java,v 1.6 2004/04/15 22:57:57 gon23 Exp $
 */
package wol;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;
import com.martiansoftware.jsap.stringparsers.InetAddressStringParser;
import com.martiansoftware.jsap.stringparsers.IntegerStringParser;
import com.martiansoftware.jsap.stringparsers.StringStringParser;

import wol.configuration.Configuration;
import wol.configuration.Host;
import wol.resources.Messages;
import wol.ui.MainPanel;

/**
 * The only purpose of this class is to wake up a machine from the commandline
 * using {@link wol.WakeUpUtil WakeUpUtil} 
 * 
 * @see wol.WakeUpUtil 
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class WakeOnLan {
	private final static String VERSION = "0.3.0";
	private final static String VERSIONSTRING = "wakeonlan version " + VERSION;
	private final static Logger LOG = Logger.getLogger(WakeOnLan.class.getName());

	private static final String CMD_INET_ADDRESS = "inet-address";
	private static final String CMD_PORT = "port";
	private static final String CMD_CONFIG_FILE = "config";
	private static final String CMD_VERSION = "verbose";
	private static final String CMD_HELP = "help";
	private static final String CMD_DEBUG = "debug";
	private static final String CMD_ADDRESSES = "addresses";
	
	public WakeOnLan(JSAPResult cmdConfig) throws JSAPException, IOException {
		super();
		
		if (!cmdConfig.success()) {
			printHelpAndExit();
		}
		
		if (cmdConfig.getBoolean(CMD_HELP)) {
			printHelpAndExit();
		}
		
		if (cmdConfig.getBoolean(CMD_VERSION)) {
			printVersionAndExit();
		}
		
		String[] machines = cmdConfig.getStringArray(CMD_ADDRESSES); 
		String configPath = cmdConfig.getString(CMD_CONFIG_FILE);
		Configuration config = new Configuration(configPath);
		
		if (null == machines || 0 == machines.length) {
			showGUI(config);
		} else {
		 	InetAddress host = cmdConfig.getInetAddress(CMD_INET_ADDRESS);
		 	int port = cmdConfig.getInt(CMD_PORT);
		 	
			wakeup(machines, config, host, port);
		}
	}
	
	private void wakeup(String[] names, Configuration config, InetAddress host, int port) {
		Host[] hosts = config.getHosts();
		HashMap hostMap = new HashMap(hosts.length);
		
		for (int i = 0; i < hosts.length; i++) {
			Host hostConfig = hosts[i];
			hostMap.put(hostConfig.getName(), hostConfig);
		}
		
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Host hostConfig = (Host) hostMap.get(name);
				if (null != host) {
					wakeupConfig(hostConfig);
				} else {
					try {
						wakeupEthernetAddresses(name, host, port);
					} catch (IllegalEthernetAddressException e) {
						Messages.ERROR_MESSAGES.getFormattedString(
								"wakeup.notAHostOrEthernetAddress",
								name);
					}
				}
		}
	}
	
	private void wakeupConfig(Host host) {
		try {
			EthernetAddress ethernetAddress = new EthernetAddress(host.getEthernetAddress());
			InetAddress hostAddress = InetAddress.getByName(host.getHost());
			WakeUpUtil.wakeup(ethernetAddress, hostAddress, host.getPort());
		} catch (IOException e) {
			System.err.println(
					Messages.ERROR_MESSAGES.getFormattedString("wakeup.io", host.getHost(), String.valueOf(host.getPort())));
		} catch (IllegalEthernetAddressException e) {
			//should not happen
			System.err.println(
					Messages.ERROR_MESSAGES.getFormattedString("wakeup.invalidEthernetAddress", host.getHost(), String.valueOf(host.getPort())));
		}
	}
	
	private void wakeupEthernetAddresses(String address, InetAddress host, int port) throws IllegalEthernetAddressException {
		try {
			EthernetAddress ethernetAddress = new EthernetAddress(address);
			WakeUpUtil.wakeup(ethernetAddress, host, port);
		} catch (IOException e) {
			System.err.println(
					Messages.ERROR_MESSAGES.getFormattedString("wakeup.io", host.toString(), String.valueOf(port)));
		}
	}
	
	private void showGUI(Configuration config) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOG.info("Could not set system Look and Feel");
		}
		
		JFrame frame = new JFrame("Wakeonlan");
		final MainPanel mainPanel = new MainPanel(config);
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		frame.setContentPane(mainPanel);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.show();
	}
	
	private static String getConfigFilePath() {
		StringBuffer buffer = new StringBuffer(System.getProperty("user.home"));
		
		buffer.append(File.separatorChar);
		buffer.append(".wakeonlan.hosts");
		
		return buffer.toString();
	}
	
	private static File getConfigFile() {
		return new File(getConfigFilePath());
	}
	
	/**
	 * The WakeOnLan main method.
	 * 
	 * @param args an array containing the commandline arguments, if any
	 */
	public static void main(String[] args) throws JSAPException, IOException {
		JSAP jsap = getOptions();
		JSAPResult config = jsap.parse(args);

		new WakeOnLan(config);
	}
	
	/**
	 * Prints the commandline help and exists with the given code.
	 * 
	 * @param exitCode the exit code
	 */
	protected void printHelpAndExit(int exitCode) throws JSAPException {
		JSAP jsap = getOptions();
		
		System.out.println();
		System.out.println("Usage: " + WakeOnLan.class.getName());
		System.out.println(jsap.getUsage());
		System.out.println();
		System.out.println(jsap.getHelp());
		System.exit(exitCode);
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
	protected void printHelpAndExit() throws JSAPException {
		printHelpAndExit(0);
	}
	
	protected static JSAP getOptions() throws JSAPException {
		JSAP jsap = new JSAP();
		
		FlaggedOption inetAddress = new FlaggedOption(CMD_INET_ADDRESS)
			.setStringParser(new InetAddressStringParser())
			.setDefault("255.255.255.255")
			.setRequired(false)
			.setShortFlag('i')
			.setLongFlag("inet-address");
		
		inetAddress.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.inet-address"));
		jsap.registerParameter(inetAddress);
		
		FlaggedOption port = new FlaggedOption(CMD_PORT)
			.setStringParser(new IntegerStringParser())
			.setDefault("9")
			.setRequired(false)
			.setShortFlag('p')
			.setLongFlag("port");
		
		port.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.port"));
		jsap.registerParameter(port);
		
		FlaggedOption config = new FlaggedOption(CMD_CONFIG_FILE)
			.setStringParser(new StringStringParser())
			.setRequired(false)
			.setDefault(getConfigFilePath())
			.setShortFlag('c')
			.setLongFlag("config");
		
		config.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.config"));
		jsap.registerParameter(config);
		
		Switch version = new Switch(CMD_VERSION)
			.setShortFlag('v')
			.setLongFlag("version");
		
		version.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.version"));
		jsap.registerParameter(version);
		
		Switch help = new Switch(CMD_HELP)
			.setShortFlag('h')
			.setLongFlag("help");
		
		help.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.help"));
		jsap.registerParameter(help);
		
		Switch debug = new Switch(CMD_DEBUG)
			.setShortFlag('d')
			.setLongFlag("debug");
		
		debug.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.debug"));
		jsap.registerParameter(debug);
		
		UnflaggedOption addresses = new UnflaggedOption(CMD_ADDRESSES)
			.setRequired(false)
			.setGreedy(true);
		
		addresses.setHelp(Messages.CMD_MESSAGES.getString("cmdline.description.addresses"));
		jsap.registerParameter(addresses);
	
	return jsap;
	}
}

/*
 * $Log: WakeOnLan.java,v $
 * Revision 1.6  2004/04/15 22:57:57  gon23
 * *** empty log message ***
 *
 * Revision 1.5  2004/04/15 10:22:06  gon23
 * Replaced commons-cli with JSAP
 *
 * Revision 1.4  2004/04/14 18:21:40  gon23
 * *** empty log message ***
 *
 * Revision 1.3  2004/04/14 11:13:08  gon23
 * *** empty log message ***
 *
 * Revision 1.2  2003/09/24 15:30:18  gon23
 * javadoc email spamblock
 *
 * Revision 1.1  2003/09/01 07:10:41  gon23
 * Initial
 *
 */