/*
 * $Id: EditHostPanel.java,v 1.7 2004/04/16 12:27:11 gon23 Exp $
 */
package wol.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import wol.configuration.*;
import wol.resources.Messages;

/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class EditHostPanel extends JPanel {
	private javax.swing.JLabel nameLabel = null;
	private javax.swing.JTextField nameTextField = null;
	private javax.swing.JLabel hostLabel = null;
	private javax.swing.JTextField hostTextField = null;
	private javax.swing.JLabel portLabel = null;
	private JTextField portTextField = null;
	private javax.swing.JLabel ethernetAddressLabel = null;
	private JFormattedTextField ethernetAddressTextField = null;
	private javax.swing.JLabel commentLabel = null;
	private javax.swing.JScrollPane commentScrollPane = null;
	private javax.swing.JTextArea commentTextArea = null;
	private Host config;
	private JPanel editPanel;
	private JPanel buttonPanel;
	private JButton applyButton;
	private JButton revertButton;

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
		setLayout(new BorderLayout());
		add(getEditPanel(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		if (null == buttonPanel) {
			buttonPanel = new JPanel();
			//buttonPanel.setLayout(new BorderLayout());
			buttonPanel.add(getApplyButton(), null);
			buttonPanel.add(getRevertButton(), null);
		}
		
		return buttonPanel;
	}

	private JButton getRevertButton() {
		if (null == revertButton) {
			revertButton = new JButton();
			revertButton.setText(Messages.UI_MESSAGES.getString("button.revert.label"));
			revertButton.setToolTipText(Messages.UI_MESSAGES.getString("button.revert.tooltip"));
			revertButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							updateFromConfig();
						}
					});
				}
			});
			revertButton.setEnabled(false);
		}
		return revertButton;
	}

	private JButton getApplyButton() {
		if (null == applyButton) {
			applyButton = new JButton();
			applyButton.setText(Messages.UI_MESSAGES.getString("button.apply.label"));
			applyButton.setToolTipText(Messages.UI_MESSAGES.getString("button.apply.tooltip"));
			applyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							applyChanges();
						}
					});
				}
			});
			applyButton.setEnabled(false);		
		}
		
		return applyButton;
	}

	private JPanel getEditPanel() {
		if (null == editPanel) {
			editPanel = new JPanel();
		
			java.awt.GridBagConstraints nameLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints nameTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints hostTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints portTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints ethernetAddressTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints portLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints hostLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints commentLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints commentTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints ethernetAddressLabelConstraints = new java.awt.GridBagConstraints();
			Insets insets = new Insets(1, 2, 1, 2);
			
			nameLabelConstraints.gridx = 0;
			nameLabelConstraints.gridy = 0;
			nameLabelConstraints.anchor = java.awt.GridBagConstraints.WEST;
			nameLabelConstraints.insets = new Insets(0, 2, 0, 2);
		
			nameTextFieldConstraints.gridx = 1;
			nameTextFieldConstraints.gridy = 0;
			nameTextFieldConstraints.weightx = 1.0;
			nameTextFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
			nameTextFieldConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			nameTextFieldConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			nameTextFieldConstraints.insets = new Insets(0, 2, 0, 2);
			
			ethernetAddressLabelConstraints.gridx = 0;
			ethernetAddressLabelConstraints.gridy = 1;
			ethernetAddressLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
			ethernetAddressLabelConstraints.weighty = 0.4D;
			ethernetAddressLabelConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
			ethernetAddressLabelConstraints.insets = insets;
			
			ethernetAddressTextFieldConstraints.weightx = 1.0;
			ethernetAddressTextFieldConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			ethernetAddressTextFieldConstraints.gridx = 0;
			ethernetAddressTextFieldConstraints.gridy = 2;
			ethernetAddressTextFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
			ethernetAddressTextFieldConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			ethernetAddressTextFieldConstraints.insets = insets;
			
			hostLabelConstraints.gridx = 0;
			hostLabelConstraints.gridy = 3;
			hostLabelConstraints.gridwidth = 2;
			hostLabelConstraints.anchor = java.awt.GridBagConstraints.WEST;
			hostLabelConstraints.insets = insets;
			
			portLabelConstraints.gridx = 2;
			portLabelConstraints.gridy = 3;
			portLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
			portLabelConstraints.anchor = java.awt.GridBagConstraints.WEST;
			portLabelConstraints.insets = insets;
			
			hostTextFieldConstraints.weightx = 1.0;
			hostTextFieldConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			hostTextFieldConstraints.gridx = 0;
			hostTextFieldConstraints.gridy = 4;
			hostTextFieldConstraints.gridwidth = 2;
			hostTextFieldConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			hostTextFieldConstraints.insets = insets;
			
			portTextFieldConstraints.weightx = 0.0D;
			portTextFieldConstraints.gridx = 3;
			portTextFieldConstraints.gridy = 4;
			portTextFieldConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
			portTextFieldConstraints.insets = insets;
			
			commentLabelConstraints.gridx = 0;
			commentLabelConstraints.gridy = 5;
			commentLabelConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			commentLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
			commentLabelConstraints.insets = insets;
			
			commentTextFieldConstraints.weightx = 1.0D;
			commentTextFieldConstraints.weighty = 1.0D;
			commentTextFieldConstraints.fill = java.awt.GridBagConstraints.BOTH;
			commentTextFieldConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			commentTextFieldConstraints.gridy = 6;
			commentTextFieldConstraints.gridx = 0;
			commentTextFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
			commentTextFieldConstraints.insets = new Insets(0, 2, 0, 2);
			
			editPanel.setLayout(new java.awt.GridBagLayout());
			editPanel.add(getNameLabel(), nameLabelConstraints);
			editPanel.add(getNameTextField(), nameTextFieldConstraints);
			editPanel.add(getHostLabel(), hostLabelConstraints);
			editPanel.add(getHostTextField(), hostTextFieldConstraints);
			editPanel.add(getPortLabel(), portLabelConstraints);
			editPanel.add(getPortTextField(), portTextFieldConstraints);
			editPanel.add(getEthernetAddressLabel(), ethernetAddressLabelConstraints);
			editPanel.add(getEthernetAddressTextField(), ethernetAddressTextFieldConstraints);
			editPanel.add(getCommentLabel(), commentLabelConstraints);
			editPanel.add(getCommentScrollPane(), commentTextFieldConstraints);
		}
		
		return editPanel;
	}

	private javax.swing.JLabel getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new javax.swing.JLabel();
			nameLabel.setText(Messages.UI_MESSAGES.getString("name.label")); //$NON-NLS-1$
			nameLabel.setLabelFor(getNameTextField());
		}
		return nameLabel;
	}

	private javax.swing.JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new javax.swing.JTextField();
			nameTextField.setToolTipText(Messages.UI_MESSAGES.getString("name.tooltip"));
			nameTextField.setColumns(20);
			nameTextField.setEnabled(false);
			nameTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							checkButtonStates();
						}
					});
					
				}
			});
		}

		return nameTextField;
	}
	
	/**
	 * Returns the currently used configuration. This does not update
	 * the configuration. You need to update the configuration by calling 
	 * @link #updateConfig() first! 
	 * @return the currently used configuration
	 * @see #updateConfig()
	 */
	public Host getConfig() {
		return config;
	}
	
	/**
	 * Sets the configuration used by this Component.
	 * 
	 * @param config the configuration
	 */	
	public synchronized void setConfig(Host config) {
		if (hasChanges()) {
			if (JOptionPane.showConfirmDialog(this, Messages.ERROR_MESSAGES.getFormattedString("host.unsavedChanges.message", this.config.getName()), Messages.ERROR_MESSAGES.getString("host.unsavedChanges.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				applyChanges();
			}
		}
		
		this.config = config;
		updateFromConfig();
	}
	
	/**
	 * Updates the underlying configuration. This does nothing if there is no underlying 
	 * configuration.
	 * 
	 * @return the updated configuration. Null if there is no current configuration.
	 */
	public synchronized Host applyChanges() {
		if (null != config) {
			int port = -1;
			
			try {
				port = Integer.parseInt(getPortTextField().getText());
			} catch (NumberFormatException e) {
			}
			
			config.setPort(port);
			config.setHost(getHostTextField().getText());
			config.setEthernetAddress((String) getEthernetAddressTextField().getValue());
			config.setName(getNameTextField().getText());
			config.setComment(getCommentTextArea().getText());
			
			checkButtonStates();
		}		
		
		return config;
	}

	private javax.swing.JLabel getHostLabel() {
		if (hostLabel == null) {
			hostLabel = new javax.swing.JLabel();
			hostLabel.setText(Messages.UI_MESSAGES.getString("host.label")); //$NON-NLS-1$
			hostLabel.setLabelFor(getHostTextField());
		}
		return hostLabel;
	}

	private javax.swing.JTextField getHostTextField() {
		if (hostTextField == null) {
			hostTextField = new javax.swing.JTextField();
			hostTextField.setToolTipText(Messages.UI_MESSAGES.getString("host.tooltip"));
			hostTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							checkButtonStates();
						}
					});
					
				}
			});
			hostTextField.setEnabled(false);
		}

		return hostTextField;
	}

	private javax.swing.JLabel getPortLabel() {
		if (portLabel == null) {
			portLabel = new javax.swing.JLabel();
			portLabel.setText(Messages.UI_MESSAGES.getString("port.label")); //$NON-NLS-1$
			portLabel.setLabelFor(getPortTextField());
		}
		return portLabel;
	}

	private JTextField getPortTextField() {
		if (portTextField == null) {
			portTextField = new JTextField();
			portTextField.setColumns(4);
			portTextField.setHorizontalAlignment(JTextField.RIGHT);
			portTextField.setToolTipText(Messages.UI_MESSAGES.getString("port.tooltip"));
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
			portTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							checkButtonStates();
						}
					});
				}
			});
			portTextField.setEnabled(false);
		}

		return portTextField;
	}

	private javax.swing.JLabel getEthernetAddressLabel() {
		if (ethernetAddressLabel == null) {
			ethernetAddressLabel = new javax.swing.JLabel();
			ethernetAddressLabel.setText(Messages.UI_MESSAGES.getString("ethernetAddress.label")); //$NON-NLS-1$
			ethernetAddressLabel.setLabelFor(getEthernetAddressTextField());
		}
		return ethernetAddressLabel;
	}

	private JFormattedTextField getEthernetAddressTextField() {
		if (ethernetAddressTextField == null) {
			MaskFormatter formatter;
			
			try {
				formatter = new MaskFormatter("HH:HH:HH:HH:HH:HH");
			} catch (ParseException e) {
				//Should not happen, this is a valid mask!
				throw new RuntimeException("Should not happen", e);
			}
			
			formatter.setCommitsOnValidEdit(true);
			formatter.setPlaceholderCharacter('_');
			ethernetAddressTextField = new JFormattedTextField(formatter);
			ethernetAddressTextField.setToolTipText(Messages.UI_MESSAGES.getString("ethernetAddress.tooltip"));
			ethernetAddressTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							checkButtonStates();
						}
					});
					
				}
			});
			ethernetAddressTextField.setEnabled(false);
		}

		return ethernetAddressTextField;
	}

	private void updateFromConfig() {
		Host currentConfig = getConfig();
		boolean enabled = (null != currentConfig);

		if (enabled) {
			getHostTextField().setText(currentConfig.getHost());
			getNameTextField().setText(currentConfig.getName());
			getPortTextField().setText(currentConfig.getPort() > 0 ? String.valueOf(currentConfig.getPort()) : null);
			getEthernetAddressTextField().setValue(currentConfig.getEthernetAddress());
			getCommentTextArea().setText(currentConfig.getComment());
		} else {
			getHostTextField().setText(null);
			getNameTextField().setText(null);
			getPortTextField().setText(null);
			getEthernetAddressTextField().setValue(null);
			getCommentTextArea().setText(null);
		}

		getHostTextField().setEnabled(enabled);
		getNameTextField().setEnabled(enabled);
		getPortTextField().setEnabled(enabled);
		getEthernetAddressTextField().setEnabled(enabled);
		getCommentTextArea().setEnabled(enabled);
		
		checkButtonStates();
	}

	private javax.swing.JLabel getCommentLabel() {
		if (commentLabel == null) {
			commentLabel = new javax.swing.JLabel();
			commentLabel.setText(Messages.UI_MESSAGES.getString("comment.label")); //$NON-NLS-1$
			commentLabel.setLabelFor(getCommentTextArea());
		}
		return commentLabel;
	}

	private javax.swing.JScrollPane getCommentScrollPane() {
		if (commentScrollPane == null) {
			commentScrollPane = new javax.swing.JScrollPane();
			commentScrollPane.setViewportView(getCommentTextArea());
		}
		return commentScrollPane;
	}

	private javax.swing.JTextArea getCommentTextArea() {
		if (commentTextArea == null) {
			commentTextArea = new javax.swing.JTextArea();
			commentTextArea.setRows(15);
			commentTextArea.setWrapStyleWord(true);
			commentTextArea.setLineWrap(true);
			commentTextArea.setToolTipText(Messages.UI_MESSAGES.getString("comment.tooltip"));
			commentTextArea.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							checkButtonStates();
						}
					});
					
				}
			});
			commentTextArea.setEnabled(false);
			
		}

		return commentTextArea;
	}
	
	private void checkButtonStates() {
		boolean changed = hasChanges();
		
		getApplyButton().setEnabled(changed);
		getRevertButton().setEnabled(changed);
	}
	
	public boolean hasChanges() {
		if (null == config) {
			return false;
		}
		
		if (!getEthernetAddressTextField().isEditValid()) {
			return false;
		}
		
		if (!getNameTextField().getText().equals(config.getName())) {
			return true;
		}
		
		if (!getHostTextField().getText().equals(config.getHost())) {
			return true;
		}
		
		if (!getEthernetAddressTextField().getValue().equals(config.getEthernetAddress())) {
			return true;
		}
		
		if (!getCommentTextArea().getText().equals(config.getComment())) {
			return true;
		}
		
		int port = -1;
			
		try {
			port = Integer.parseInt(getPortTextField().getText());
		} catch (NumberFormatException e) {
		}
		
		if (port != config.getPort()) {
			return true;
		}
		
		return false;
	}

}

/*
 * $Log: EditHostPanel.java,v $
 * Revision 1.7  2004/04/16 12:27:11  gon23
 * *** empty log message ***
 *
 * Revision 1.6  2004/04/15 22:57:57  gon23
 * *** empty log message ***
 *
 * Revision 1.5  2004/04/15 10:21:36  gon23
 * New Resources handling
 *
 * Revision 1.4  2004/04/14 22:14:49  gon23
 * *** empty log message ***
 *
 * Revision 1.3  2004/04/14 21:52:30  gon23
 * *** empty log message ***
 *
 * Revision 1.2  2004/04/14 18:21:40  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/14 11:13:37  gon23
 * *** empty log message ***
 *
 */