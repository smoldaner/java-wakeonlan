/*
 * $Id: EditHostPanel.java,v 1.1 2004/04/14 11:13:37 gon23 Exp $
 */
package wol.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import wol.configuration.*;

public class EditHostPanel extends JPanel {
	private javax.swing.JLabel nameLabel = null;
	private javax.swing.JTextField nameTextField = null;
	private javax.swing.JLabel hostLabel = null;
	private javax.swing.JTextField hostTextField = null;
	private javax.swing.JLabel portLabel = null;
	private JTextField portTextField = null;
	private javax.swing.JLabel hwAdressLabel = null;
	private javax.swing.JTextField hwAdressTextField = null;
	private javax.swing.JLabel commentLabel = null;
	private javax.swing.JScrollPane commentScrollPane = null;
	private javax.swing.JTextArea commentTextArea = null;
	private Host config;

	public EditHostPanel() {
		super();
		initialize();
	}
	
	public EditHostPanel(Host config) {
		super();
		initialize();
		setConfig(config);
	}

	private void initialize() {
		java.awt.GridBagConstraints consGridBagConstraints2 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints3 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints4 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints5 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints7 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints6 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints21 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints31 = new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints8 = new java.awt.GridBagConstraints();
		consGridBagConstraints21.gridy = 3;
		consGridBagConstraints21.gridx = 0;
		consGridBagConstraints21.anchor = java.awt.GridBagConstraints.NORTHWEST;
		consGridBagConstraints21.weighty = 1.0D;
		consGridBagConstraints31.weightx = 1.0;
		consGridBagConstraints31.weighty = 1.0;
		consGridBagConstraints31.fill = java.awt.GridBagConstraints.BOTH;
		consGridBagConstraints31.gridy = 3;
		consGridBagConstraints31.gridx = 1;
		consGridBagConstraints31.gridwidth = 3;
		consGridBagConstraints3.weightx = 1.0;
		consGridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints3.gridx = 1;
		consGridBagConstraints3.gridy = 1;
		consGridBagConstraints4.weightx = 0.0D;
		consGridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints4.gridx = 3;
		consGridBagConstraints4.gridy = 1;
		consGridBagConstraints5.weightx = 1.0;
		consGridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints7.gridx = 2;
		consGridBagConstraints7.gridy = 1;
		consGridBagConstraints1.weightx = 1.0;
		consGridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints1.gridx = 1;
		consGridBagConstraints2.gridx = 0;
		consGridBagConstraints2.gridy = 0;
		consGridBagConstraints1.gridy = 0;
		consGridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		consGridBagConstraints1.gridwidth = 3;
		consGridBagConstraints6.gridx = 0;
		consGridBagConstraints6.gridy = 1;
		consGridBagConstraints8.gridx = 0;
		consGridBagConstraints8.gridy = 2;
		consGridBagConstraints5.gridx = 1;
		consGridBagConstraints5.gridy = 2;
		consGridBagConstraints5.gridwidth = 3;
		consGridBagConstraints5.anchor = java.awt.GridBagConstraints.NORTHWEST;
		consGridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
		consGridBagConstraints6.anchor = java.awt.GridBagConstraints.SOUTHWEST;
		consGridBagConstraints8.anchor = java.awt.GridBagConstraints.WEST;
		consGridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
		consGridBagConstraints4.anchor = java.awt.GridBagConstraints.SOUTHWEST;
		consGridBagConstraints2.weighty = 0.5D;
		consGridBagConstraints3.anchor = java.awt.GridBagConstraints.SOUTHWEST;
		setLayout(new java.awt.GridBagLayout());
		add(getNameLabel(), consGridBagConstraints2);
		add(getNameTextField(), consGridBagConstraints1);
		add(getHostLabel(), consGridBagConstraints6);
		add(getHostTextField(), consGridBagConstraints3);
		add(getPortLabel(), consGridBagConstraints7);
		add(getPortTextField(), consGridBagConstraints4);
		add(getHwAdressLabel(), consGridBagConstraints8);
		add(getHwAdressTextField(), consGridBagConstraints5);
		add(getCommentLabel(), consGridBagConstraints21);
		add(getCommentScrollPane(), consGridBagConstraints31);
	}

	private javax.swing.JLabel getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new javax.swing.JLabel();
			nameLabel.setText(Messages.getString("name.label")); //$NON-NLS-1$
			nameLabel.setLabelFor(getNameTextField());
		}
		return nameLabel;
	}

	private javax.swing.JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new javax.swing.JTextField();
			nameTextField.setToolTipText(Messages.getString("name.tooltip"));
			nameTextField.setColumns(20);
			nameTextField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getConfig().setName(nameTextField.getText());
				}

			});
			nameTextField.setEnabled(false);
		}

		return nameTextField;
	}
	
	/**
	 * Returns the currently used configuration. This does not update
	 * the configuration. You need to update the configuration by calling 
	 * @link #updateConfig() first! 
	 * @return the currently used configuration
	 * @see #updateConfig()
	 * @see #getConfig(boolean)
	 */
	public Host getConfig() {
		return config;
	}
	/**
	 * 
	 * @param update if <code>true</code> the current configuration will be updated
	 * 					before it is returned. 
	 * @return the current configuration after a call to @link #updateConfig()
	 * @see #updateConfig()
	 * @see #getConfig(boolean)
	 */
	public synchronized Host getConfig(boolean update) {
		updateConfig();
		
		return getConfig();
	}
	
	/**
	 * Sets the configuration used by this Component.
	 * 
	 * @param config the configuration
	 */	
	public synchronized void setConfig(Host config) {
		this.config = config;
		updateFromConfig();
	}
	
	/**
	 * Updates the underlying configuration. This does nothing if there is no underlying 
	 * configuration.
	 * 
	 * @return the updated configuration. Null if there is no current configuration.
	 */
	public synchronized Host updateConfig() {
		if (null != config) {
			int port = -1;
			
			try {
				port = Integer.parseInt(getPortTextField().getText());
			} catch (NumberFormatException e) {
			}
			
			config.setPort(port);
			config.setHost(getHostTextField().getText());
			config.setHwAdress(getHwAdressTextField().getText());
			config.setName(getNameTextField().getText());
			config.setComment(getCommentTextArea().getText());
		}
		
		
		return config;
	}

	private javax.swing.JLabel getHostLabel() {
		if (hostLabel == null) {
			hostLabel = new javax.swing.JLabel();
			hostLabel.setText(Messages.getString("host.label")); //$NON-NLS-1$
			hostLabel.setLabelFor(getHostTextField());
		}
		return hostLabel;
	}

	private javax.swing.JTextField getHostTextField() {
		if (hostTextField == null) {
			hostTextField = new javax.swing.JTextField();
			hostTextField.setToolTipText(Messages.getString("host.tooltip"));
			hostTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getConfig().setHost(hostTextField.getText());
				}
			});
			hostTextField.setEnabled(false);
		}

		return hostTextField;
	}

	private javax.swing.JLabel getPortLabel() {
		if (portLabel == null) {
			portLabel = new javax.swing.JLabel();
			portLabel.setText(Messages.getString("port.label")); //$NON-NLS-1$
			portLabel.setLabelFor(getPortTextField());
		}
		return portLabel;
	}

	private JTextField getPortTextField() {
		if (portTextField == null) {
			portTextField = new JTextField();
			portTextField.setColumns(4);
			portTextField.setToolTipText(Messages.getString("port.tooltip"));
			portTextField.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					String valueString = ((JTextField) input).getText();
					
					if (null == valueString || 0 == valueString.length()) {
						return true;
					}
					
					int port = Integer.MIN_VALUE;
					
					try {
						port = Integer.parseInt(valueString); 
					} catch (NumberFormatException e) {
					}
					
					return port > 0;
				}
			});
			portTextField.setEnabled(false);
		}

		return portTextField;
	}

	private javax.swing.JLabel getHwAdressLabel() {
		if (hwAdressLabel == null) {
			hwAdressLabel = new javax.swing.JLabel();
			hwAdressLabel.setText(Messages.getString("hwAdress.label")); //$NON-NLS-1$
			hwAdressLabel.setLabelFor(getHwAdressTextField());
		}
		return hwAdressLabel;
	}

	private javax.swing.JTextField getHwAdressTextField() {
		if (hwAdressTextField == null) {
			hwAdressTextField = new javax.swing.JTextField();
			hwAdressTextField.setToolTipText(Messages.getString("hwAdress.tooltip"));
			hwAdressTextField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getConfig().setHwAdress(hwAdressTextField.getText());
				}
			});
			hwAdressTextField.setEnabled(false);
		}

		return hwAdressTextField;
	}

	private void updateFromConfig() {
		Host currentConfig = getConfig();
		boolean enabled = (null != currentConfig);

		if (enabled) {
			getHostTextField().setText(currentConfig.getHost());
			getNameTextField().setText(currentConfig.getName());
			getPortTextField().setText(currentConfig.getPort() > 0 ? String.valueOf(currentConfig.getPort()) : null);
			getHwAdressTextField().setText(currentConfig.getHwAdress());
			getCommentTextArea().setText(currentConfig.getComment());
		} else {
			getHostTextField().setText(null);
			getNameTextField().setText(null);
			getPortTextField().setText(null);
			getHwAdressTextField().setText(null);
			getCommentTextArea().setText(null);
		}

		getHostTextField().setEnabled(enabled);
		getNameTextField().setEnabled(enabled);
		getPortTextField().setEnabled(enabled);
		getHwAdressTextField().setEnabled(enabled);
		getCommentTextArea().setEnabled(enabled);
	}

	private javax.swing.JLabel getCommentLabel() {
		if (commentLabel == null) {
			commentLabel = new javax.swing.JLabel();
			commentLabel.setText(Messages.getString("comment.label")); //$NON-NLS-1$
			commentLabel.setLabelFor(getCommentTextArea());
		}
		return commentLabel;
	}

	private javax.swing.JScrollPane getCommentScrollPane() {
		if (commentScrollPane == null) {
			commentScrollPane = new javax.swing.JScrollPane();
			//commentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			commentScrollPane.setViewportView(getCommentTextArea());
		}
		return commentScrollPane;
	}

	private javax.swing.JTextArea getCommentTextArea() {
		if (commentTextArea == null) {
			commentTextArea = new javax.swing.JTextArea();
			commentTextArea.setToolTipText(Messages.getString("comment.tooltip"));
			commentTextArea.setEnabled(false);
		}

		return commentTextArea;
	}

}

/*
 * $Log: EditHostPanel.java,v $
 * Revision 1.1  2004/04/14 11:13:37  gon23
 * *** empty log message ***
 *
 */