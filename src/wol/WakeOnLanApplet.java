/*
 * $Id: WakeOnLanApplet.java,v 1.4 2004/04/28 05:38:02 gon23 Exp $
 */
package wol;

import java.awt.HeadlessException;

import javax.swing.JApplet;

import wol.configuration.Configuration;
import wol.ui.MainFrame;

public class WakeOnLanApplet extends JApplet {
	private MainFrame mainFrame;
	
	public WakeOnLanApplet() throws HeadlessException {
		super();
		init();
	}

	public static void main(String[] args) {
		WakeOnLanApplet applet = new WakeOnLanApplet();
		
		applet.start();
	}
	
	public void init() {
		super.init();
		Configuration config = new Configuration();
		mainFrame = new MainFrame(config);
	}

	public void start() {
		super.start();
		mainFrame.show();
	}

	public void stop() {
		super.stop();
		mainFrame.dispose();
	}

}

/*
 * $Log: WakeOnLanApplet.java,v $
 * Revision 1.4  2004/04/28 05:38:02  gon23
 * Initial
 *
 */