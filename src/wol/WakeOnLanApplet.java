/*
 * $Id: WakeOnLanApplet.java,v 1.5 2004/05/18 13:55:52 gon23 Exp $
 */
package wol;

import java.awt.HeadlessException;

import javax.swing.JApplet;

import wol.configuration.Configuration;
import wol.ui.MainFrame;

/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
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
 * Revision 1.5  2004/05/18 13:55:52  gon23
 * *** empty log message ***
 *
 * Revision 1.4  2004/04/28 05:38:02  gon23
 * Initial
 *
 */