/*
 * $Id: Host.java,v 1.1 2004/04/14 11:13:08 gon23 Exp $
 */
package wol.configuration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class Host {
	private String comment;
	private String name;
	private String host;
	private String hwAdress;
	private int port;
	private PropertyChangeSupport pcs;
	
	public Host() {
		super();
		pcs = new PropertyChangeSupport(this);
	}
	
	public Host(String name) {
		this();
		setName(name);
	}

	public String getHost() {
		return host;
	}

	public String getHwAdress() {
		return hwAdress;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		String oldValue = this.host;
		
		this.host = host;
		pcs.firePropertyChange("host", oldValue, host);
	}

	public void setHwAdress(String hwAdress) {
		String oldValue = this.hwAdress;
		
		this.hwAdress = hwAdress;
		pcs.firePropertyChange("hwAdress", oldValue, hwAdress);
	}

	public void setName(String name) {
		String oldValue = this.name;
		
		this.name = name;
		pcs.firePropertyChange("name", oldValue, name);
	}

	public void setPort(int port) {
		int oldValue = this.port;
		
		this.port = port;
		pcs.firePropertyChange("port", oldValue, port);
	}

	public String toString() {
		return getName();
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		String oldValue = this.comment;
		
		this.comment = comment;
		pcs.firePropertyChange("comment", oldValue, comment);
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

}

/*
 * $Log: Host.java,v $
 * Revision 1.1  2004/04/14 11:13:08  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */