/*
 * $Id: MainFrame.java,v 1.1 2004/04/28 05:38:35 gon23 Exp $
 */
package wol.ui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import wol.configuration.Configuration;
import wol.resources.Messages;


public class MainFrame extends JFrame {
	private final static Logger LOG = Logger.getLogger(MainFrame.class.getName());

	public MainFrame(Configuration config) throws HeadlessException {
		super();
		initialize(config);
	}
	
	private void initialize(Configuration config) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOG.info("Could not set system Look and Feel");
		}
		
		final MainPanel mainPanel = new MainPanel(config);
		setDefaultLookAndFeelDecorated(true);
		setIconImage(new ImageIcon(getClass().getResource("/wol/images/wakeonlan16x16.gif")).getImage());
		setContentPane(mainPanel);
		setTitle(Messages.UI_MESSAGES.getString("frame.title"));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					mainPanel.saveConfig();
				} catch (FileNotFoundException e1) {
					LOG.warning(Messages.ERROR_MESSAGES.getString("save.fileNotFound.message"));
				}
				System.exit(0);
			}
		});
		pack();
	}
}

/*
 * $Log: MainFrame.java,v $
 * Revision 1.1  2004/04/28 05:38:35  gon23
 * Added Statusbar
 *
 */