/*
 * $Id: MainPanel.java,v 1.3 2004/04/14 11:13:39 gon23 Exp $
 */
package wol.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.text.NumberFormatter;

import wol.configuration.*;


public class MainPanel extends JPanel {
	private EditHostPanel editHostPanel;
	private javax.swing.JPanel buttonPanel = null;
	private javax.swing.JButton wakeupButton = null;
	private javax.swing.JPanel centerPanel = null;
	private javax.swing.JScrollPane configurationsScrollPane = null;
	private javax.swing.JList configurationsList = null;

	private javax.swing.JPanel configurationsPanel = null;
	private javax.swing.JLabel configurationsLabel = null;
	private javax.swing.JButton newButton = null;
	private javax.swing.JButton deleteButton = null;
	private javax.swing.JPanel modifyPanel = null;
	private javax.swing.JPanel wakeupPanel = null;
	private HostsModel configurationsModel;
	private Configuration config;

	public MainPanel() {
		super();
		ArrayList list = new ArrayList();
		Host currentConfig = new Host(Messages.getString("defaultConfigurationName"));
		
		list.add(currentConfig);
		this.configurationsModel = new HostsModel(new Host[0]);
		initialize();
	}
	
	public MainPanel(Configuration config) {
		super();
		this.config = config;
		this.configurationsModel = new HostsModel(config.getHosts());
		initialize();
	}

	private void initialize() {
		this.setLayout(new java.awt.BorderLayout());
		this.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
		this.add(getCenterPanel(), java.awt.BorderLayout.CENTER);
	}
	
	private Host getCurrentConfig() {
		return (Host) getConfigurationsList().getSelectedValue();
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
			centerPanel.add(getEditHostPanel(), java.awt.BorderLayout.EAST);
			centerPanel.add(getConfigurationsPanel(), java.awt.BorderLayout.CENTER);
		}
		return centerPanel;
	}
	
	private EditHostPanel getEditHostPanel() {
		if (null == editHostPanel) {
			editHostPanel = new EditHostPanel();
			editHostPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		}
		
		return editHostPanel;
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
			configurationsList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			configurationsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() { 
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								getDeleteButton().setEnabled(!configurationsList.isSelectionEmpty());
								
								Host oldConfig = getEditHostPanel().updateConfig();
								Host newConfig = null;
								Object[] selectedHosts = configurationsList.getSelectedValues();
								
								if (selectedHosts.length == 1) {
									newConfig = (Host) selectedHosts[0];
								} 

								getEditHostPanel().setConfig(newConfig);
							}
						});   
					}
				}
			});
		}
		
		return configurationsList;
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
			configurationsLabel.setText(Messages.getString("configurations.label")); //$NON-NLS-1$
		}
		return configurationsLabel;
	}

	private javax.swing.JButton getNewButton() {
		if(newButton == null) {
			newButton = new javax.swing.JButton();
			newButton.setText(Messages.getString("button.new")); //$NON-NLS-1$
			newButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					int index = configurationsModel.newHost();
					getConfigurationsList().setSelectedIndex(index);
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
					int[] indices = getConfigurationsList().getSelectedIndices();
					
					for (int i = 0; i < indices.length; i++) {
						int index = indices[i];
						configurationsModel.deleteHost(index-i);
					}
					
				}
			});
		}
		return deleteButton;
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
	
	private class HostsModel extends  AbstractListModel implements PropertyChangeListener {
		private List hosts;
		private Comparator hostComparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				String hostname1 = (null == o1 ? null : ((Host) o1).getName());
				String hostname2 = (null == o2 ? null : ((Host) o2).getName());
				
				if (null == hostname1) {
					hostname1 = "";
				}
				
				if (null == hostname2) {
					hostname2 = "";
				}
				
				return hostname1.compareToIgnoreCase(hostname2);
			}
		};
		
		public HostsModel(Host[] hosts) {
			this.hosts = new ArrayList();
			
			for (int i = 0; i < hosts.length; i++) {
				Host host = hosts[i];
				
				host.addPropertyChangeListener(this);
				this.hosts.add(host);
			}
			
			Collections.sort(this.hosts, hostComparator);
		}

		public Object getElementAt(int index) {
			return hosts.get(index);
		}
		
		public int getSize() {
			return hosts.size();
		}
		
		public Host deleteHost(int index) {
			Host oldHost = (Host) hosts.remove(index);
			
			oldHost.removePropertyChangeListener(this);
			fireIntervalRemoved(this, index, index);
			
			return oldHost;
		}

		public int newHost() {
			Host newHost = new Host(getNewName(Messages.getString("defaultConfigurationName")));
			
			newHost.addPropertyChangeListener(this);
			
			for (int i = 0; i < hosts.size(); i++) {
				Host host = (Host) hosts.get(i);
				
				if (hostComparator.compare(newHost, host) < 0) {
					
				}
			}			
			hosts.add(newHost);
			
			
			int index = hosts.indexOf(newHost);
			
			fireIntervalAdded(this, index, index);
			
			return index;
		}
		
		protected String getNewName(String defaultName) {
			for (Iterator iter = hosts.iterator(); iter.hasNext();) {
				Host host = (Host) iter.next();
				
				if (defaultName.equalsIgnoreCase(host.getName())) {
					return getNewName(defaultName, 1);
				}
			}
			
			return defaultName;
		}
		
		protected String getNewName(String defaultName, int counter) {
			String newName = defaultName  + " ("  + counter + ')';
			
			for (Iterator iter = hosts.iterator(); iter.hasNext();) {
				Host host = (Host) iter.next();
				
				if (newName.equalsIgnoreCase(host.getName())) {
					return getNewName(defaultName, ++counter);
				}
			}
			
			return newName;
		}

		public void propertyChange(PropertyChangeEvent event) {
			int index = hosts.indexOf(event.getSource());
			
			if (-1 != index && "name".equals(event.getPropertyName())) {
				Collections.sort(hosts, hostComparator);
				fireContentsChanged(this, 0, hosts.size()-1);
			}
		}
		
		public List getHosts() {
			return hosts;
		}

	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"



/*
 * $Log: MainPanel.java,v $
 * Revision 1.3  2004/04/14 11:13:39  gon23
 * *** empty log message ***
 *
 * Revision 1.2  2004/04/08 22:31:27  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */