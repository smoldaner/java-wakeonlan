/*
 * $Id: EthernetAddressTest.java,v 1.1 2004/05/18 12:57:37 gon23 Exp $
 */
package wol.configuration;

import java.util.Arrays;

import junit.framework.TestCase;


public class EthernetAddressTest extends TestCase {
	private final static String NIC = "01:02:03:04:05:06";
	private final static byte[] NIC_BYTES = new byte[]{ 1, 2, 3, 4, 5, 6 };
	/*
	 * Class to test for void EthernetAddress(String)
	 */
	public void testEthernetAddressString() throws IllegalEthernetAddressException {
		EthernetAddress nic = new EthernetAddress(NIC);
		
		assertTrue("Bytes must equal", Arrays.equals(NIC_BYTES, nic.toBytes()));
		byte[] bytes = nic.toBytes();
		bytes[0] = 17;
		assertTrue("Bytes should be final", Arrays.equals(NIC_BYTES, nic.toBytes()));
		
		try {
			String nullString = null;
			new EthernetAddress(nullString);
			fail(NullPointerException.class.getName() + " expected");
			
		} catch (NullPointerException e) {}
		
		try {
			String s = "01:02:03:04:05:06:07";
			new EthernetAddress(s);
			fail("address too long. " + IllegalEthernetAddressException.class.getName() + " expected");
			
		} catch (IllegalEthernetAddressException e) {}
		
		try {
			String s = "01:02:03:04:05:";
			new EthernetAddress(s);
			fail("address too short. " + IllegalEthernetAddressException.class.getName() + " expected");
			
		} catch (IllegalEthernetAddressException e) {}
		
		try {
			String s = "01:zzs:03:04:05:06";
			new EthernetAddress(s);
			fail("illegal address. " + IllegalEthernetAddressException.class.getName() + " expected");
			
		} catch (IllegalEthernetAddressException e) {}
		
	}
	/*
	 * Class to test for void EthernetAddress(byte[])
	 */
	public void testEthernetAddressbyteArray() throws IllegalEthernetAddressException {
		EthernetAddress nic = new EthernetAddress(NIC_BYTES);
		
		assertTrue("bytes must be equal", Arrays.equals(NIC_BYTES, nic.toBytes()));
		assertNotSame(NIC_BYTES, nic.toBytes());
		
		try {
			new EthernetAddress(new byte[5]);
			fail("byte array must by 6 bytes long. " + IllegalEthernetAddressException.class.getName() + " expected");
		} catch (IllegalEthernetAddressException e) {}
		
		try {
			new EthernetAddress(new byte[7]);
			fail("byte array must by 6 bytes long. " + IllegalEthernetAddressException.class.getName() + " expected");
		} catch (IllegalEthernetAddressException e) {}
	}
	
	public void testToString() throws IllegalEthernetAddressException {
		EthernetAddress nic = new EthernetAddress(NIC_BYTES);
		
		assertEquals(NIC, nic.toString());
	}
	
	public void testEquals() throws IllegalEthernetAddressException {
		EthernetAddress nic = new EthernetAddress(NIC);
		EthernetAddress equalNic = new EthernetAddress(NIC_BYTES);
		EthernetAddress thirdNic = new EthernetAddress("06:05:04:03:02:01");
		
		
		assertTrue(nic + " must equal " + equalNic, nic.equals(equalNic));
		assertTrue(equalNic + " must equal " + nic, equalNic.equals(nic));
		assertFalse(nic + " must not equal " + thirdNic, nic.equals(thirdNic));
	} 
}

/*
 * $Log: EthernetAddressTest.java,v $
 * Revision 1.1  2004/05/18 12:57:37  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/16 09:24:53  gon23
 * Initial test sources
 *
 */