/*
 * $Id: MainPanel.java,v 1.6 2004/04/14 22:14:49 gon23 Exp $
 */
package wol.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import wol.Errors;
import wol.configuration.*;

/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class MainPanel extends JPanel {
	private final static Logger LOG = Logger.getLogger(MainPanel.class.getName());
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
	private javax.swing.JPanel wakeupPanel = null;
	private HostsModel configurationsModel;
	private Configuration config;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newConfigMenuItem;
	private JMenu newMenu;
	private JMenuItem newHostMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem exitMenuItem;
	private JPanel configurationsButtonPanel;

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
	
	public void setConfiguration(Configuration config) {
		try {
			saveConfig();
		} catch (FileNotFoundException e1) {
			LOG.log(Level.SEVERE, "Can't write config", e1);
			JOptionPane.showMessageDialog(this, Errors.getString("save.fileNotFound.message"), Errors.getString("save.fileNotFound.title"), JOptionPane.ERROR_MESSAGE);
		}
		
		this.config = config;
		this.configurationsModel = new HostsModel(config.getHosts());
		getConfigurationsList().setModel(configurationsModel);
	}

	private void initialize() {
		this.setLayout(new java.awt.BorderLayout());
		this.add(getMenuBar(), BorderLayout.NORTH);
		this.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
		this.add(getCenterPanel(), java.awt.BorderLayout.CENTER);
	}
	
	private JMenuBar getMenuBar() {
		if (null == menuBar) {
			menuBar = new JMenuBar();
			menuBar.add(getFileMenu());
		}
		
		return menuBar;
	}
	
	private JMenu getFileMenu() {
		if (null == fileMenu) {
			fileMenu = new JMenu(Messages.getString("menu.file.label"));
			fileMenu.setMnemonic(Messages.getMnemonic("menu.file.mnemonic"));
			fileMenu.add(getNewMenu());
			fileMenu.addSeparator();
			fileMenu.add(getOpenMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getSaveAsMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getExitMenuItem());
		}
		
		return fileMenu;
	}
	
	private JMenuItem getOpenMenuItem() {
		if (null == openMenuItem) {
			openMenuItem = new JMenuItem(Messages.getString("menu.file.open.label"));
			openMenuItem.setMnemonic(Messages.getMnemonic("menu.file.open.mnemonic"));
			openMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser(config.getFile());
					
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					if (fileChooser.showOpenDialog(MainPanel.this) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						Configuration newConfig = new Configuration(file);
						
						setConfiguration(newConfig);
					}
				}
			});
		}
		
		return openMenuItem;
	}
	
	private JMenuItem getSaveMenuItem() {
		if (null == saveMenuItem) {
			saveMenuItem = new JMenuItem(Messages.getString("menu.file.save.label"));
			saveMenuItem.setMnemonic(Messages.getMnemonic("menu.file.save.mnemonic"));
			saveMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							getEditHostPanel().applyChanges();

							try {
								saveConfig();
							} catch (FileNotFoundException e1) {
								LOG.log(Level.SEVERE, "Can't write config", e1);
								JOptionPane.showMessageDialog(MainPanel.this, Errors.getString("save.fileNotFound.message"), Errors.getString("save.fileNotFound.title"), JOptionPane.ERROR_MESSAGE);
							}
						}
					});
					
				}
			});
		}
		
		return saveMenuItem;
	}
	
	public void updateConfig() {
		config.setHosts(configurationsModel.getHosts());
	}
	
	private void saveConfig(File file) throws FileNotFoundException {
		updateConfig();
		config.saveConfig(file);
	}
	
	private void saveConfig() throws FileNotFoundException {
		updateConfig();
		config.saveConfig();
	}
	
	private JMenuItem getSaveAsMenuItem() {
		if (null == saveAsMenuItem) {
			saveAsMenuItem = new JMenuItem(Messages.getString("menu.file.saveAs.label"));
			saveAsMenuItem.setMnemonic(Messages.getMnemonic("menu.file.saveAs.mnemonic"));
			saveAsMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser(config.getFile());
					
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					
					if (fileChooser.showSaveDialog(MainPanel.this) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						
						if (file.exists()) {
							if (JOptionPane.showConfirmDialog(MainPanel.this, 
								Errors.getFormattedString("save.fileExists.message", file.getAbsoluteFile()), 
								Errors.getFormattedString("save.fileExists.title", file.getAbsoluteFile()), JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
									return;
							}
						}
						
						try {
							saveConfig(file);
						} catch (FileNotFoundException e1) {
							LOG.log(Level.SEVERE, "Can't write config", e1);
							JOptionPane
									.showMessageDialog(
											MainPanel.this,
											Errors.getString("save.fileNotFound.message"),
											Errors.getString("save.fileNotFound.title"),
											JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			});
		}
		
		return saveAsMenuItem;
	}
	
	private JMenu getNewMenu() {
		if (null == newMenu) {
			newMenu = new JMenu(Messages.getString("menu.file.new.label"));
			newMenu.setMnemonic(Messages.getMnemonic("menu.file.new.mnemonic"));
			newMenu.add(getNewConfigMenuItem());
			newMenu.add(getNewHostMenuItem());
			
		}
		
		return newMenu;
	}
	
	private JMenuItem getNewConfigMenuItem() {
		if (null == newConfigMenuItem) {
			newConfigMenuItem = new JMenuItem(Messages.getString("menu.file.new.config.label"));
			newConfigMenuItem.setMnemonic(Messages.getMnemonic("menu.file.new.config.mnemonic"));
			newConfigMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JFileChooser fileChooser = new JFileChooser(config.getFile());
							
							fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
							if (fileChooser.showOpenDialog(MainPanel.this) == JFileChooser.APPROVE_OPTION) {
								File file = fileChooser.getSelectedFile();
								
								if (file.exists()) {
									if (JOptionPane.showConfirmDialog(MainPanel.this, 
										Errors.getFormattedString("save.fileExists.message", file.getAbsoluteFile()), 
										Errors.getFormattedString("save.fileExists.title", file.getAbsoluteFile()), JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
											return;
									}
								}
								
								setConfiguration(new Configuration(file));
							}
						}
					});
				}
			});
		}
		
		return newConfigMenuItem;
	}
	
	private JMenuItem getNewHostMenuItem() {
		if (null == newHostMenuItem) {
			newHostMenuItem = new JMenuItem(Messages.getString("menu.file.new.host.label"));
			newHostMenuItem.setMnemonic(Messages.getMnemonic("menu.file.new.host.mnemonic"));
			newHostMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							newHost();
						}
					});
				}
			});
		}
		
		return newHostMenuItem;
	}
	
	private JMenuItem getExitMenuItem() {
		if (null == exitMenuItem) {
			exitMenuItem = new JMenuItem(Messages.getString("menu.file.exit.label"));
			exitMenuItem.setMnemonic(Messages.getMnemonic("menu.file.exit.mnemonic"));
		}
		
		return exitMenuItem;
	}

	private javax.swing.JPanel getButtonPanel() {
		if(buttonPanel == null) {
			buttonPanel = new javax.swing.JPanel();
			buttonPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(15, 0, 0, 0), BorderFactory
							.createMatteBorder(1, 0, 0, 0, getBackground()
									.brighter())));
			buttonPanel.setLayout(new java.awt.BorderLayout());
			buttonPanel.add(getWakeupPanel(), java.awt.BorderLayout.EAST);
		}
		return buttonPanel;
	}
	
	private javax.swing.JButton getWakeupButton() {
		if(wakeupButton == null) {
			wakeupButton = new javax.swing.JButton();
			wakeupButton.setText(Messages.getString("button.wakeup.label")); //$NON-NLS-1$
			wakeupButton.setToolTipText(Messages.getString("button.wakeup.tooltip"));
			wakeupButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getEditHostPanel().applyChanges();
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
			wakeupButton.setEnabled(false);
		}
		return wakeupButton;
	}

	private javax.swing.JPanel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new javax.swing.JPanel();
			centerPanel.setLayout(new GridBagLayout());
			GridBagConstraints editHostConstraints = new GridBagConstraints();
			GridBagConstraints configurationsConstraints = new GridBagConstraints();
			Insets insets = new Insets(2, 2, 2, 2);
			editHostConstraints.gridx = 1;
			editHostConstraints.gridy = 0;
			editHostConstraints.weightx = 0.3D;
			editHostConstraints.weighty = 1.0D;
			editHostConstraints.fill = GridBagConstraints.BOTH;
			editHostConstraints.insets = insets;
			
			configurationsConstraints.gridx = 0;
			configurationsConstraints.gridy = 0;
			configurationsConstraints.weightx = 0.7D;
			configurationsConstraints.weighty = 1.0D;
			configurationsConstraints.fill = GridBagConstraints.BOTH;
			configurationsConstraints.insets = insets;
			
			centerPanel.add(getEditHostPanel(), editHostConstraints);
			centerPanel.add(getConfigurationsPanel(), configurationsConstraints);
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
								getWakeupButton().setEnabled(!configurationsList.isSelectionEmpty());
								
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
			configurationsPanel.add(getConfigurationsButtonPanel(), BorderLayout.SOUTH);
		}
		return configurationsPanel;
	}
	
	private JPanel getConfigurationsButtonPanel() {
		if (null == configurationsButtonPanel) {
			configurationsButtonPanel = new JPanel();
			configurationsButtonPanel.add(getNewButton());
			configurationsButtonPanel.add(getDeleteButton());
		}
		
		return configurationsButtonPanel;
	}

	private javax.swing.JLabel getConfigurationsLabel() {
		if(configurationsLabel == null) {
			configurationsLabel = new javax.swing.JLabel();
			configurationsLabel.setText(Messages.getString("configurations.label")); //$NON-NLS-1$
			configurationsLabel.setLabelFor(getConfigurationsList());
		}
		return configurationsLabel;
	}

	private javax.swing.JButton getNewButton() {
		if(newButton == null) {
			newButton = new javax.swing.JButton();
			newButton.setText(Messages.getString("button.new.label")); //$NON-NLS-1$
			newButton.setToolTipText(Messages.getString("button.new.tooltip"));
			newButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					newHost();
				}
			});
		}
		return newButton;
	}
	
	private void newHost() {
		int index = configurationsModel.newHost();
		getConfigurationsList().setSelectedIndex(index);
	}

	private javax.swing.JButton getDeleteButton() {
		if(deleteButton == null) {
			deleteButton = new javax.swing.JButton();
			deleteButton.setText(Messages.getString("button.delete.label")); //$NON-NLS-1$
			deleteButton.setToolTipText(Messages.getString("button.delete.tooltip"));
			deleteButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					int[] indices = getConfigurationsList().getSelectedIndices();
					
					for (int i = 0; i < indices.length; i++) {
						int index = indices[i];
						configurationsModel.deleteHost(index-i);
					}
					
				}
			});
			deleteButton.setEnabled(false);
		}
		return deleteButton;
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
			synchronized(hosts) {
				return hosts.get(index);
			}
		}
		
		public int getSize() {
			synchronized(hosts) {
				return hosts.size();
			}
		}
		
		public Host deleteHost(int index) {
			Host oldHost;
			
			synchronized(hosts) {
				oldHost = (Host) hosts.remove(index);
			}
			
			oldHost.removePropertyChangeListener(this);
			fireIntervalRemoved(this, index, index);
			
			return oldHost;
		}

		public int newHost() {
			Host newHost = new Host(getNewName(Messages.getString("defaultConfigurationName")));
			
			newHost.addPropertyChangeListener(this);
			int index;
				
			synchronized(hosts) {
				hosts.add(newHost);
				index = hosts.size()-1;
			}
			
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
				fireContentsChanged(this, 0, hosts.size()-1);
			}
		}
		
		public Host[] getHosts() {
			Host[] retHosts = new Host[hosts.size()];
			
			return (Host[]) hosts.toArray(retHosts);
		}

	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"



/*
 * $Log: MainPanel.java,v $
 * Revision 1.6  2004/04/14 22:14:49  gon23
 * *** empty log message ***
 *
 * Revision 1.5  2004/04/14 21:52:30  gon23
 * *** empty log message ***
 *
 * Revision 1.4  2004/04/14 18:21:40  gon23
 * *** empty log message ***
 *
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