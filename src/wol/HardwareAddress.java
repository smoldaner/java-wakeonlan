/*
 * $Id: HardwareAddress.java,v 1.1 2004/04/15 15:34:53 gon23 Exp $
 */
package wol;

import java.util.StringTokenizer;


public class HardwareAddress {
	private final static String HARDWARE_ADDRESS_DELIM = ":";
	private byte[] hwAdress;
	
	public HardwareAddress(String hwAdress) throws IllegalArgumentException {
		super();
		this.hwAdress = parseHardwareAddress(hwAdress);
	}
	
	/**
	 * Parses a String representing a hardware address into an byte array. The String must be a list 
	 * of hexadecimal bytes, sepeartet by a colon.<br><br> 
	 * Example: 00:50:95:10:95:F5
	 * 
	 * @param hwAddress a String representation of the hardware address. 
	 * @return a byte array containing the byte representation of the hardware address
	 */
	protected byte[] parseHardwareAddress(String hwAddress) throws IllegalArgumentException{
		StringTokenizer tokenizer = new StringTokenizer(hwAddress, HARDWARE_ADDRESS_DELIM);
		
		if (tokenizer.countTokens() != 6) {
			throw new IllegalArgumentException(hwAdress + " is not a hardwareaddress");
		}
		
		byte[] hwAddressBytes = new byte[tokenizer.countTokens()];
		
		try {
			for(int i=0; i < hwAddressBytes.length; i++) {
				hwAddressBytes[i] = (byte) Integer.parseInt(tokenizer.nextToken(), 16);
			}
		} catch (NumberFormatException e) {
			
		}
		
		return hwAddressBytes;
	}
}

/*
 * $Log: HardwareAddress.java,v $
 * Revision 1.1  2004/04/15 15:34:53  gon23
 * *** empty log message ***
 *
 */