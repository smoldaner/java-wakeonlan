/*
 * $Id: EditHostPanel.java,v 1.3 2004/04/14 21:52:30 gon23 Exp $
 */
package wol.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import wol.Errors;
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
			revertButton.setText(Messages.getString("button.revert.label"));
			revertButton.setToolTipText(Messages.getString("button.revert.tooltip"));
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
			applyButton.setText(Messages.getString("button.apply.label"));
			applyButton.setToolTipText(Messages.getString("button.apply.tooltip"));
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
			java.awt.GridBagConstraints hwAdressTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints portLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints hostLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints commentLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints commentTextFieldConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints hwAdressLabelConstraints = new java.awt.GridBagConstraints();
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
			
			hwAdressLabelConstraints.gridx = 0;
			hwAdressLabelConstraints.gridy = 1;
			hwAdressLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
			hwAdressLabelConstraints.weighty = 0.4D;
			hwAdressLabelConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
			hwAdressLabelConstraints.insets = insets;
			
			hwAdressTextFieldConstraints.weightx = 1.0;
			hwAdressTextFieldConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			hwAdressTextFieldConstraints.gridx = 0;
			hwAdressTextFieldConstraints.gridy = 2;
			hwAdressTextFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
			hwAdressTextFieldConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			hwAdressTextFieldConstraints.insets = insets;
			
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
			editPanel.add(getHwAdressLabel(), hwAdressLabelConstraints);
			editPanel.add(getHwAdressTextField(), hwAdressTextFieldConstraints);
			editPanel.add(getCommentLabel(), commentLabelConstraints);
			editPanel.add(getCommentScrollPane(), commentTextFieldConstraints);
		}
		
		return editPanel;
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
			if (JOptionPane.showConfirmDialog(this, Errors.getFormattedString("host.unsavedChanges.message", this.config.getName()), Errors.getString("host.unsavedChanges.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
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
			config.setHwAdress(getHwAdressTextField().getText());
			config.setName(getNameTextField().getText());
			config.setComment(getCommentTextArea().getText());
			
			checkButtonStates();
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
			portLabel.setText(Messages.getString("port.label")); //$NON-NLS-1$
			portLabel.setLabelFor(getPortTextField());
		}
		return portLabel;
	}

	private JTextField getPortTextField() {
		if (portTextField == null) {
			portTextField = new JTextField();
			portTextField.setColumns(4);
			portTextField.setHorizontalAlignment(JTextField.RIGHT);
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
			hwAdressTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							checkButtonStates();
						}
					});
					
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
		
		checkButtonStates();
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
			commentTextArea.setToolTipText(Messages.getString("comment.tooltip"));
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
		
		if (!getNameTextField().getText().equals(config.getName())) {
			return true;
		}
		
		if (!getHostTextField().getText().equals(config.getHost())) {
			return true;
		}
		
		if (!getHwAdressTextField().getText().equals(config.getHwAdress())) {
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