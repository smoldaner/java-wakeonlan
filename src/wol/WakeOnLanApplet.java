/*
 * $Id: WakeOnLanApplet.java,v 1.1 2004/04/08 22:10:06 gon23 Exp $
 */
package wol;

import javax.swing.JApplet;

import wol.ui.*;


public class WakeOnLanApplet extends JApplet {

	private javax.swing.JPanel jContentPane = null;

	public static void main(String[] args) {
	}
	/**
	 * This is the default constructor
	 */
	public WakeOnLanApplet() {
		super();
		init();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new MainPanel();
			jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,3,3,3));
		}
		return jContentPane;
	}
}

/*
 * $Log: WakeOnLanApplet.java,v $
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */