/*
 * $Id: StatusBar.java,v 1.2 2004/05/18 13:55:52 gon23 Exp $
 */
package wol.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import wol.resources.Messages;

/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class StatusBar extends JPanel {

	private javax.swing.JPanel statusPanel = null;
	private javax.swing.JPanel progressPanel = null;
	private javax.swing.JProgressBar progressBar = null;
	private javax.swing.JLabel statusLabel = null;

	public StatusBar() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(new java.awt.BorderLayout());
		this.add(getStatusPanel(), java.awt.BorderLayout.CENTER);
		this.add(getProgressPanel(), java.awt.BorderLayout.EAST);
	}

	private javax.swing.JPanel getStatusPanel() {
		if(statusPanel == null) {
			statusPanel = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			layGridLayout2.setRows(1);
			statusPanel.setLayout(layGridLayout2);
			statusPanel.add(getStatusLabel(), null);
			statusPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.LOWERED));
		}
		return statusPanel;
	}

	private javax.swing.JPanel getProgressPanel() {
		if(progressPanel == null) {
			progressPanel = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(1);
			progressPanel.setLayout(layGridLayout1);
			progressPanel.add(getProgressBar(), null);
			progressPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.LOWERED));
		}
		return progressPanel;
	}

	private javax.swing.JProgressBar getProgressBar() {
		if(progressBar == null) {
			progressBar = new javax.swing.JProgressBar();
			progressBar.setBorder(null);
			progressBar.setForeground(Color.GREEN);
			Dimension size = progressBar.getPreferredSize();
			
			size.width = 50;
			
			progressBar.setPreferredSize(size);
			progressBar.setMaximumSize(size);
			progressBar.setMaximumSize(size);
		}
		
		return progressBar;
	}

	private javax.swing.JLabel getStatusLabel() {
		if(statusLabel == null) {
			statusLabel = new javax.swing.JLabel();
			statusLabel.setText(Messages.UI_MESSAGES.getString("status.default"));
		}
		return statusLabel;
	}
	
	public void setProgressMaximum(int n) {
		progressBar.setMaximum(n);
	}

	public void setProgressMinimum(int n) {
		progressBar.setMinimum(n);
	}

	public void setProgressValue(int n) {
		progressBar.setValue(n);
	}

	public void setProgressVisible(boolean b) {
		progressBar.setVisible(b);
	}

	public void setStatusText(String text) {
		statusLabel.setText(text);
	}
	public void setProgressColor(Color c) {
		progressBar.setForeground(c);
	}

}

/*
 * $Log: StatusBar.java,v $
 * Revision 1.2  2004/05/18 13:55:52  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/28 05:38:35  gon23
 * Added Statusbar
 *
 */