/*
 * $Id: WakeUpUtil.java,v 1.1 2003/09/01 07:10:41 gon23 Exp $
 */
package wol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A class to wake up wake-on-lan enabled machines.
  * <br>
 * <br>
 * Examples:
 * <blockqoute>
 * <pre>
 * try {
 *    //Creates a new WakeUpUtil instance using the local subnet broadcast address
 *    WakeUpUtil wakeUpUtil = new WakeUpUtil("192.168.0.255");
 * 		
 *    //wakes up the machine with the hardware address '00:50:95:10:95:F5', listening on port 9 in the local subnet
 *    wakeUpUtil.wakeup("00:50:95:10:95:F5");
 * } catch(Exception e) {
 *	   System.err.println("Ups..."); 
 * }
 * </pre>
 * </blockqoute>
 * <br>
 * 
 * @author <a href="mailto:s.moldaner@gmx.net">Steffen Moldaner</a>
  */
public class WakeUpUtil {
	/**
	 * The default host: 255.255.255.255 (limited broadcast address).
	 */
	public final static String DEFAULT_HOST = "255.255.255.255";
	/**
	 * The default port: 9.
	 */
	public final static int DEFAULT_PORT = 9;
	
	
	private final static String HARDWARE_ADDRESS_DELIM = ":";
	private final static Log log = LogFactory.getLog(WakeUpUtil.class);
	
	private ResourceBundle bundle = ResourceBundle.getBundle("wol.Resources");
	
	private final static byte[] STATIC_BYTES = new byte[] {
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff 
	};
	
	private InetAddress host;
	private int port;
	
	/**
	 * Creates a new Instance of WakeUpUtil, using the default host and port.
	 * @throws UnknownHostException if the default host is unknown
	 * @see #DEFAULT_HOST
	 * @see #DEFAULT_PORT
	 */	
	public WakeUpUtil() throws UnknownHostException {
		this(DEFAULT_HOST, DEFAULT_PORT);
	}
	
	/**
	 * Creates a new Instance of WakeUpUtil, using the default port.
	 * 
	 * @param host the host, the 'magic packets' wil be send
	 * @throws UnknownHostException if the host is unknown
	 * @see #DEFAULT_PORT
	 */
	public WakeUpUtil(String host) throws UnknownHostException {
		this(host, DEFAULT_PORT);
	}
	
	/**
	 * Creates a new Instance of WakeUpUtil, using the default host.
	 * 
	 * @param port the port the 'magic packets' wil be send
	 * @throws UnknownHostException if the default host is unknown
	 * @see #DEFAULT_HOST 
	 */
	public WakeUpUtil(int port) throws UnknownHostException {
		this(DEFAULT_HOST, port);
	}
	
	/**
	 * Creates a new Instance of WakeUpUtil with the given host and port.
	 * 
	 * @param host the host, the 'magic packets' wil be send 
	 * @param port the port the 'magic packets' wil be send
	 * @throws UnknownHostException if the host is unknown
	 */
	public WakeUpUtil(String host, int port) throws UnknownHostException {
		this(InetAddress.getByName(host), port);
	}
	
	/**
	 * Creates a new instance of WakeUpUtil  using the given <code>InetAddress</code>.
	 * 
	 * @param host an <code>InetAddress</code> representing the host, the 'magic packets' wil be send
	 * @see #DEFAULT_PORT
	 */
	public WakeUpUtil(InetAddress host) {
		this(host, DEFAULT_PORT);
	}
	
	
	/**
	 * Creates a new instance of WakeUpUtil  using the given <code>InetAddress</code> and port.
	 * 
	 * @param host an <code>InetAddress</code> representing the host, the 'magic packets' wil be send
	 * @param port the port the 'magic packets' wil be send
	 */
	public WakeUpUtil(InetAddress host, int port) {
		super();
		
		if (log.isDebugEnabled()) {
			log.debug("Creating new WakeUpUtil instance; host=" + host.toString() + ", port=" + port);
		}
		
		this.host = host;
		this.port = port;
	}
	
	/**
	 * Wakes up the machines with the given hardware addresses.
	 * 
	 * @param hwAddresses an array containg the hardware addresses of the machines
	 * @throws UnknownHostException if the host does not exist
	 * @throws IOException if an exception occures opening the socket or writing the 'magic packet'
	 */
	public void wakeUp(String[] hwAddresses) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		
		for (int i = 0; i < hwAddresses.length; i++) {
			byte[] hwAddressBytes = parseHardwareAddress(hwAddresses[i]);
			byte[] magicBytes = new byte[6 + 16 * hwAddressBytes.length];
			
			System.arraycopy(STATIC_BYTES, 0, magicBytes, 0, STATIC_BYTES.length);
	        
			for (int j = 6; j < magicBytes.length; j += hwAddressBytes.length) {
				System.arraycopy(hwAddressBytes, 0, magicBytes, j, hwAddressBytes.length);
			}
			
			DatagramPacket packet = new DatagramPacket(magicBytes, magicBytes.length, host, port);
			
			if (log.isInfoEnabled()) {
				log.info(MessageFormat.format(bundle.getString("magicPacket.send"), new Object[]{hwAddresses[i]}));
			}
			
			socket.send(packet);
		}
		
		if (log.isInfoEnabled()) {
			log.info(MessageFormat.format(bundle.getString("magicPacket.finished"), new Object[]{new Integer(hwAddresses.length)}));
		}
		
		socket.close();
	}
	
	
	
	/**
	 * Wakes up the machine with the given hardware address.
	 * 
	 * @param hwAddress the hardware address of the machine
	 * @throws UnknownHostException if the host does not exist
	 * @throws IOException if an exception occures opening the socket or writing the 'magic packet'
	 */
	public void wakeUp(String hwAddress) throws IOException {
		wakeUp(new String[] {hwAddress});
	}
	
	/**
	 * Parses a String representing a hardware address into an byte array. The String must be a list 
	 * of hexadecimal bytes, sepeartet by a colon.<br><br> 
	 * Example: 00:50:95:10:95:F5
	 * 
	 * @param hwAddress a String representation of the hardware address. 
	 * @return a byte array containing the byte representation of the hardware address
	 */
	protected byte[] parseHardwareAddress(String hwAddress) {
		StringTokenizer tokenizer = new StringTokenizer(hwAddress, HARDWARE_ADDRESS_DELIM);
		
		byte[] hwAddressBytes = new byte[tokenizer.countTokens()];
		
		for(int i=0; i < hwAddressBytes.length; i++) {
			hwAddressBytes[i] = (byte) Integer.parseInt(tokenizer.nextToken(), 16);
		}
		
		return hwAddressBytes;
	}
}

/*
 * $Log: WakeUpUtil.java,v $
 * Revision 1.1  2003/09/01 07:10:41  gon23
 * Initial
 *
 */