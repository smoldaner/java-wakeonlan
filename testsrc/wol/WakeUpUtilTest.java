/*
 * $Id: WakeUpUtilTest.java,v 1.1 2004/04/16 09:24:53 gon23 Exp $
 */
package wol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import junit.framework.TestCase;


public class WakeUpUtilTest extends TestCase {
	private final static String NIC = "01:02:03:04:05:06";
	private final static byte[] WAKEUP_FRAME = new byte[]{(byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05,
			(byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
			(byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x03,
			(byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x02,
			(byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x01,
			(byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06,
			(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05,
			(byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
			(byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x03,
			(byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x02,
			(byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x01,
			(byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06,
			(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05,
			(byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
			(byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x02, (byte) 0x03,
			(byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x02,
			(byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x01,
			(byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06,
			(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05,
			(byte) 0x06};
	
	private class Lock {
		private boolean locked = false;
		
		public boolean isLocked() {
			return locked;
		}
		
		public void setLocked(boolean b) {
			this.locked = b;
		}
	}
	
	public void testCreateWakeupFrame() throws IllegalEthernetAddressException {
		EthernetAddress nic = new EthernetAddress(NIC);
		byte[] wakeupFrame = WakeUpUtil.createWakeupFrame(nic);
		assertNotNull(wakeupFrame);
		assertTrue(Arrays.equals(wakeupFrame, WAKEUP_FRAME));
	}
	
	/*
	 * Class to test for void wakeup(EthernetAddress[], InetAddress, int)
	 */
	public void testWakeupEthernetAddressArrayInetAddressint() throws IOException, IllegalEthernetAddressException, InterruptedException {
		InetAddress host = InetAddress.getLocalHost();
		int port = 2710;
		final ServerSocket serverSocket = new ServerSocket(port);
		final Lock lock = new Lock();
		
		serverSocket.setSoTimeout(5000);
		new Thread(new Runnable() {
			public void run() {
				try {
					Socket socket = serverSocket.accept();
					
					InputStream in = socket.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					
					for (int b = in.read(); -1 != b; b = in.read()) {
						out.write(b);
					}
					
					in.close();
					
					byte[] wakeupFrame = out.toByteArray();
					
					out.close();
					
					assertTrue("Wakeup frames must equal", Arrays.equals(wakeupFrame, WAKEUP_FRAME));
				} catch (SocketTimeoutException e) {
					fail("Timeout. No wakeup frame recieved");
				} catch (IOException e) {
					fail("IO exception");
				} catch (Throwable t) {
					fail(t.toString());
				} finally {
					lock.setLocked(false);
				}
			}
		}).start();
		
		Thread.sleep(2000);
		
		WakeUpUtil.wakeup(new EthernetAddress[]{ new EthernetAddress(NIC) }, host, port);
		
		for(;lock.isLocked();){}
	}
}

/*
 * $Log: WakeUpUtilTest.java,v $
 * Revision 1.1  2004/04/16 09:24:53  gon23
 * Initial test sources
 *
 */