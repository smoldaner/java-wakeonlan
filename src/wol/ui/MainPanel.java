/*
 * $Id: MainPanel.java,v 1.2 2004/04/08 22:31:27 gon23 Exp $
 */
package wol.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.text.NumberFormatter;


public class MainPanel extends JPanel {

	private javax.swing.JPanel buttonPanel = null;
	private javax.swing.JButton wakeupButton = null;
	private javax.swing.JPanel centerPanel = null;
	private javax.swing.JScrollPane configurationsScrollPane = null;
	private javax.swing.JList configurationsList = null;
	private javax.swing.JPanel currentConfigPanel = null;
	private javax.swing.JLabel nameLabel = null;
	private javax.swing.JTextField nameTextField = null;
	private javax.swing.JLabel hostLabel = null;
	private javax.swing.JTextField hostTextField = null;
	private javax.swing.JLabel portLabel = null;
	private JFormattedTextField portTextField = null;
	private javax.swing.JLabel hwAdressLabel = null;
	private javax.swing.JTextField hwAdressTextField = null;
	private javax.swing.JPanel configurationsPanel = null;
	private javax.swing.JLabel configurationsLabel = null;
	private javax.swing.JButton newButton = null;
	private javax.swing.JButton deleteButton = null;
	private javax.swing.JLabel commentLabel = null;
	private javax.swing.JScrollPane commentScrollPane = null;
	private javax.swing.JTextArea commentTextArea = null;
	private javax.swing.JPanel modifyPanel = null;
	private javax.swing.JPanel wakeupPanel = null;
	private WOLConfiguration currentConfig;
	private ListListModel configurationsModel;

	public MainPanel() {
		super();
		ArrayList list = new ArrayList();
		WOLConfiguration currentConfig = new WOLConfiguration(Messages.getString("defaultConfigurationName"));
		
		list.add(currentConfig);
		this.configurationsModel = new ListListModel(list);
		initialize();
		initialize(currentConfig);
	}
	
	public MainPanel(WOLConfiguration config, List configurations) {
		super();
		this.configurationsModel = new ListListModel(configurations);
		initialize();
		initialize(config);
	}

	private void initialize() {
		this.setLayout(new java.awt.BorderLayout());
		this.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
		this.add(getCenterPanel(), java.awt.BorderLayout.CENTER);
	}
	
	private void initialize(WOLConfiguration configuration) {
		this.currentConfig = configuration;
		getHostTextField().setText(currentConfig.getHost());
		getNameTextField().setText(currentConfig.getName());
		getPortTextField().setValue(currentConfig.getPort() > 0 ? new Integer(currentConfig.getPort()) : null);
		getHwAdressTextField().setText(currentConfig.getHwAdress());
	}

	private javax.swing.JPanel getButtonPanel() {
		if(buttonPanel == null) {
			buttonPanel = new javax.swing.JPanel();
			buttonPanel.setLayout(new java.awt.BorderLayout());
			buttonPanel.add(getModifyPanel(), java.awt.BorderLayout.WEST);
			buttonPanel.add(getWakeupPanel(), java.awt.BorderLayout.EAST);
		}
		return buttonPanel;
	}

	private javax.swing.JButton getWakeupButton() {
		if(wakeupButton == null) {
			wakeupButton = new javax.swing.JButton();
			wakeupButton.setText(Messages.getString("button.wakeup")); //$NON-NLS-1$
			wakeupButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return wakeupButton;
	}

	private javax.swing.JPanel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new javax.swing.JPanel();
			centerPanel.setLayout(new java.awt.BorderLayout());
			centerPanel.add(getCurrentConfigPanel(), java.awt.BorderLayout.EAST);
			centerPanel.add(getConfigurationsPanel(), java.awt.BorderLayout.CENTER);
		}
		return centerPanel;
	}

	private javax.swing.JScrollPane getConfigurationsScrollPane() {
		if(configurationsScrollPane == null) {
			configurationsScrollPane = new javax.swing.JScrollPane();
			configurationsScrollPane.setViewportView(getConfigurationsList());
			configurationsScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,0,0,0));
		}
		return configurationsScrollPane;
	}

	private javax.swing.JList getConfigurationsList() {
		if(configurationsList == null) {
			configurationsList = new javax.swing.JList(configurationsModel);
			configurationsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			configurationsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() { 
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								WOLConfiguration config = (WOLConfiguration) configurationsList.getSelectedValue();
								
								if (null != config) {
									initialize(config);
									getDeleteButton().setEnabled(true);
								} else {
									getDeleteButton().setEnabled(false);
								}
							}
						});   
					}
				}
			});
		}
		return configurationsList;
	}

	private javax.swing.JPanel getCurrentConfigPanel() {
		if(currentConfigPanel == null) {
			currentConfigPanel = new javax.swing.JPanel();
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
			currentConfigPanel.setLayout(new java.awt.GridBagLayout());
			currentConfigPanel.add(getNameLabel(), consGridBagConstraints2);
			currentConfigPanel.add(getNameTextField(), consGridBagConstraints1);
			currentConfigPanel.add(getHostLabel(), consGridBagConstraints6);
			currentConfigPanel.add(getHostTextField(), consGridBagConstraints3);
			currentConfigPanel.add(getPortLabel(), consGridBagConstraints7);
			currentConfigPanel.add(getPortTextField(), consGridBagConstraints4);
			currentConfigPanel.add(getHwAdressLabel(), consGridBagConstraints8);
			currentConfigPanel.add(getHwAdressTextField(), consGridBagConstraints5);
			currentConfigPanel.add(getCommentLabel(), consGridBagConstraints21);
			currentConfigPanel.add(getCommentScrollPane(), consGridBagConstraints31);
			currentConfigPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,15,0,0));
		}
		return currentConfigPanel;
	}

	private javax.swing.JLabel getNameLabel() {
		if(nameLabel == null) {
			nameLabel = new javax.swing.JLabel();
			nameLabel.setText(Messages.getString("label.name")); //$NON-NLS-1$
		}
		return nameLabel;
	}

	private javax.swing.JTextField getNameTextField() {
		if(nameTextField == null) {
			nameTextField = new javax.swing.JTextField();
			nameTextField.setColumns(20);
			nameTextField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					currentConfig.setName(nameTextField.getText());
				}
			});
		}
		return nameTextField;
	}

	private javax.swing.JLabel getHostLabel() {
		if(hostLabel == null) {
			hostLabel = new javax.swing.JLabel();
			hostLabel.setText(Messages.getString("label.host")); //$NON-NLS-1$
		}
		return hostLabel;
	}

	private javax.swing.JTextField getHostTextField() {
		if(hostTextField == null) {
			hostTextField = new javax.swing.JTextField();
			hostTextField.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					currentConfig.setHost(hostTextField.getText());
				}
			});
		}
		return hostTextField;
	}

	private javax.swing.JLabel getPortLabel() {
		if(portLabel == null) {
			portLabel = new javax.swing.JLabel();
			portLabel.setText(Messages.getString("label.port")); //$NON-NLS-1$
		}
		return portLabel;
	}

	private JFormattedTextField getPortTextField() {
		if(portTextField == null) {
			portTextField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
			portTextField.setColumns(4);
			portTextField.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					Integer port = (Integer) portTextField.getValue();
					currentConfig.setPort(null == port ? -1 : port.intValue());
				}
			});
		}
		return portTextField;
	}

	private javax.swing.JLabel getHwAdressLabel() {
		if(hwAdressLabel == null) {
			hwAdressLabel = new javax.swing.JLabel();
			hwAdressLabel.setText(Messages.getString("label.hwAdress")); //$NON-NLS-1$
		}
		return hwAdressLabel;
	}

	private javax.swing.JTextField getHwAdressTextField() {
		if(hwAdressTextField == null) {
			hwAdressTextField = new javax.swing.JTextField();
			hwAdressTextField.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					currentConfig.setHwAdress(hwAdressTextField.getText());
				}
			});
		}
		return hwAdressTextField;
	}

	private javax.swing.JPanel getConfigurationsPanel() {
		if(configurationsPanel == null) {
			configurationsPanel = new javax.swing.JPanel();
			configurationsPanel.setLayout(new java.awt.BorderLayout());
			configurationsPanel.add(getConfigurationsScrollPane(), java.awt.BorderLayout.CENTER);
			configurationsPanel.add(getConfigurationsLabel(), java.awt.BorderLayout.NORTH);
		}
		return configurationsPanel;
	}

	private javax.swing.JLabel getConfigurationsLabel() {
		if(configurationsLabel == null) {
			configurationsLabel = new javax.swing.JLabel();
			configurationsLabel.setText(Messages.getString("label.configurations")); //$NON-NLS-1$
		}
		return configurationsLabel;
	}

	private javax.swing.JButton getNewButton() {
		if(newButton == null) {
			newButton = new javax.swing.JButton();
			newButton.setText(Messages.getString("button.new")); //$NON-NLS-1$
			newButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					WOLConfiguration newConfig = new WOLConfiguration(Messages.getString("defaultConfigurationName"));
					
					configurationsModel.add(newConfig);
				}
			});
		}
		return newButton;
	}

	private javax.swing.JButton getDeleteButton() {
		if(deleteButton == null) {
			deleteButton = new javax.swing.JButton();
			deleteButton.setText(Messages.getString("button.delete")); //$NON-NLS-1$
			deleteButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					int index = getConfigurationsList().getSelectedIndex();
					
					configurationsModel.remove(index);
				}
			});
		}
		return deleteButton;
	}

	private javax.swing.JLabel getCommentLabel() {
		if(commentLabel == null) {
			commentLabel = new javax.swing.JLabel();
			commentLabel.setText(Messages.getString("label.comment")); //$NON-NLS-1$
		}
		return commentLabel;
	}

	private javax.swing.JScrollPane getCommentScrollPane() {
		if(commentScrollPane == null) {
			commentScrollPane = new javax.swing.JScrollPane();
			commentScrollPane.setViewportView(getCommentTextArea());
		}
		return commentScrollPane;
	}

	private javax.swing.JTextArea getCommentTextArea() {
		if(commentTextArea == null) {
			commentTextArea = new javax.swing.JTextArea();
		}
		return commentTextArea;
	}

	private javax.swing.JPanel getModifyPanel() {
		if(modifyPanel == null) {
			modifyPanel = new javax.swing.JPanel();
			modifyPanel.add(getNewButton(), null);
			modifyPanel.add(getDeleteButton(), null);
		}
		return modifyPanel;
	}

	private javax.swing.JPanel getWakeupPanel() {
		if(wakeupPanel == null) {
			wakeupPanel = new javax.swing.JPanel();
			wakeupPanel.add(getWakeupButton(), null);
		}
		return wakeupPanel;
	}
	
	private class ListListModel extends AbstractListModel {
			private List list;
			
			public ListListModel(List list) {
				this.list = list;
			}
			
			public Object getElementAt(int index) {
				return list.get(index);
			}
	
			public int getSize() {
				return list.size();
			}
			
			public boolean add(Object object) {
				if (list.add(object)) {
					fireIntervalAdded(this, list.size() - 1, list.size() - 1);
					return true;
				} else {
					return false;
				}
			}
			
			public Object remove (int index) {
				Object retVal = list.remove(index);
				
				fireIntervalRemoved(this, index, index);
				
				return retVal;
				
			}
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"



/*
 * $Log: MainPanel.java,v $
 * Revision 1.2  2004/04/08 22:31:27  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */