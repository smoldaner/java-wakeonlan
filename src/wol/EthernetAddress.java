/*
 * $Id: EthernetAddress.java,v 1.1 2004/04/15 22:57:57 gon23 Exp $
 */
package wol;

import java.util.StringTokenizer;

/**
 * This class represents a ethernet address.
 * <br>
 * <br>
 * An Ethernet Address ia a six octets (48 bits) number that uniquelly identifies every
 * network interface card (NIC). The first three octects (24 bits) are known as the
 * Organizationally Unique Identifier (OUI) and identifies its manufacturer.
 * 
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class EthernetAddress {
	private final static String ETHERNET_ADDRESS_DELIM = ":";
	private final byte[] bytes;
	
	/**
	 * Create a EthernetAddress based on the provided address. The address must be 6 hexadecimal values
	 * seperated by a colon.
	 * <br>
	 * <br>
	 * E.g.: 00:50:95:10:95:F5
	 * @param ethernetAddress the ethernet address
	 * @throws IllegalArgumentException if the ethernet address could not be parsed 
	 */
	public EthernetAddress(String ethernetAddress) throws IllegalEthernetAddressException {
		super();
		this.bytes = parseEthernetAddress(ethernetAddress);
	}
	
	/**
	 * Create a EthernetAddress based on the provided bytes. The byte array must have a length 
	 * of 6 representing the 48 bit ethernet address.
	 * 
	 * @param bytes the bytes. Must have length of 6!
	 * @throws IllegalEthernetAddressException if ethernet address is of illegal length or null
	 */
	public EthernetAddress(byte[] ethernetAddress) throws IllegalEthernetAddressException {
		super();
		
		if (null == ethernetAddress || ethernetAddress.length != 6) {
			throw new IllegalEthernetAddressException("An ethernet address must be 6 bytes");
		}
		
		this.bytes = ethernetAddress;
	}
	
	/**
	 * Parses a String representing an ethernet address into an byte array. The String must be a list 
	 * of 6 hexadecimal bytes, sepeartet by a colon.<br><br> 
	 * Example: 00:50:95:10:95:F5
	 * 
	 * @param ethernetAddress a String representation of the ethernet address. 
	 * @return a byte array containing the byte representation of the ethernet address
	 */
	protected byte[] parseEthernetAddress(String ethernetAddress) throws IllegalEthernetAddressException{
		StringTokenizer tokenizer = new StringTokenizer(ethernetAddress, ETHERNET_ADDRESS_DELIM);
		
		if (tokenizer.countTokens() != 6) {
			throw new IllegalEthernetAddressException(ethernetAddress + " is not a legal hardwareaddress");
		}
		
		byte[] ethernetAddressBytes = new byte[6];
		
		try {
			for(int i=0; i < 6; i++) {
				ethernetAddressBytes[i] = (byte) Integer.parseInt(tokenizer.nextToken(), 16);
			}
		} catch (NumberFormatException e) {
			throw new IllegalEthernetAddressException(ethernetAddress + " is not a legal hardwareaddress");
		}
		
		return ethernetAddressBytes;
	}
	
	/**
	 * Returns the byte representation of this ethernet address.
	 * 
	 * @return the bytes
	 */
	public byte[] toBytes() {
		return bytes;
	}
}

/*
 * $Log: EthernetAddress.java,v $
 * Revision 1.1  2004/04/15 22:57:57  gon23
 * *** empty log message ***
 *
 */