/*
 * $Id: WakeUpUtil.java,v 1.4 2004/04/15 22:57:57 gon23 Exp $
 */
package wol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A class to wake up wake-on-lan enabled machines.
 * 
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class WakeUpUtil {
	/**
	 * The default wakeup host: 255.255.255.255 (limited broadcast address).
	 */
	public final static InetAddress DEFAULT_HOST;
	
	//this is ugly
	static {
		InetAddress tmpInetAddress = null;
		
		try {
			tmpInetAddress = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			 //should not happen, because this _is_ a valid IP!
		}
		
		DEFAULT_HOST = tmpInetAddress;
	}
	
	/**
	 * The default wakeup port: 9
	 */
	public final static int DEFAULT_PORT = 9;
	
	private final static byte[] SYNCHRONIZATION_STREAM_BYTES = new byte[] {
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff, 
		(byte) 0xff 
	};
	
	private WakeUpUtil() {
		super();
	}
	
	/**
	 * Wakes up the machine with the provided eternet address, using the default port and host.
	 * 
	 * @param ethernetAddress the ethernet address to wake up
	 * @throws IOException if an I/O error occurs
	 * @see #DEFAULT_HOST
	 * @see #DEFAULT_PORT
	 */
	public static void wakeup(EthernetAddress ethernetAddress) throws IOException {
		WakeUpUtil.wakeup(new EthernetAddress[]{ ethernetAddress });
	}
	/**
	 * Wakes up the machines with the provided ethernet addresses, using the default port and host.
	 * 
	 * @param ethernetAddresses the ethernet addresses to wake up
	 * @throws IOException if an I/O error occurs
	 * @see #DEFAULT_HOST
	 * @see #DEFAULT_PORT
	 */
	public static void wakeup(EthernetAddress[] ethernetAddresses) throws IOException {
		WakeUpUtil.wakeup(ethernetAddresses, DEFAULT_HOST);
	}
	
	/**
	 * Wakes up the machine with the provided ethernet addresses, using the default port.
	 * 
	 * @param ethernetAddress the ethernet address to wake up
	 * @param host the host, the magic sequence will be send to
	 * @throws IOException if an I/O error occurs
	 * @see #DEFAULT_PORT
	 */
	public static void wakeup(EthernetAddress ethernetAddress, InetAddress host) throws IOException {
		WakeUpUtil.wakeup(new EthernetAddress[]{ ethernetAddress }, host);
	}
	
	/**
	 * 
	 * @param ethernetAddresses the ethernet addresses to wake up
	 * @param host the host, the magic sequence will be send to
	 * @throws IOException if an I/O error occurs
	 * @see #DEFAULT_PORT
	 */
	public static void wakeup(EthernetAddress[] ethernetAddresses, InetAddress host) throws IOException {
			WakeUpUtil.wakeup(ethernetAddresses, host, DEFAULT_PORT);	
	}
	
	/**
	 * 
	 * @param ethernetAddress the ethernet address to wake up
	 * @param host the host, the magic sequence will be send to
	 * @param port the port number
	 * @throws IOException if an I/O error occurs
	 */
	public static void wakeup(EthernetAddress ethernetAddress, InetAddress host, int port) throws IOException {
		WakeUpUtil.wakeup(new EthernetAddress[]{ ethernetAddress }, host, port);		
	}
	
	/**
	 * 
	 * @param ethernetAddresses the ethernet addresses to wake up
	 * @param host the host, the magic sequence will be send to
	 * @param port the port number
	 * @throws IOException if an I/O error occurs
	 */
	public static void wakeup(EthernetAddress[] ethernetAddresses, InetAddress host, int port) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		
		for (int i = 0; i < ethernetAddresses.length; i++) {
			byte[] ethernetAddressBytes = ethernetAddresses[i].toBytes();
			byte[] magicBytes = new byte[6 + 16 * ethernetAddressBytes.length];
			
			System.arraycopy(SYNCHRONIZATION_STREAM_BYTES, 0, magicBytes, 0, SYNCHRONIZATION_STREAM_BYTES.length);
	        
			for (int j = 6; j < magicBytes.length; j += ethernetAddressBytes.length) {
				System.arraycopy(ethernetAddressBytes, 0, magicBytes, j, ethernetAddressBytes.length);
			}
			
			DatagramPacket packet = new DatagramPacket(magicBytes, magicBytes.length, host, port);
			
			socket.send(packet);
		}
	}
}

/*
 * $Log: WakeUpUtil.java,v $
 * Revision 1.4  2004/04/15 22:57:57  gon23
 * *** empty log message ***
 *
 */