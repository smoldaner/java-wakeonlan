/*
 * $Id: Host.java,v 1.4 2004/04/15 22:57:44 gon23 Exp $
 */
package wol.configuration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class Host {
	private String comment = "";
	private String name = "";
	private String host = "255.255.255.255";
	private String ethernetAddress ="";
	private int port = 9;
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

	public String getEthernetAddress() {
		return ethernetAddress;
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

	public void setEthernetAddress(String ethernetAddress) {
		String oldValue = this.ethernetAddress;
		
		this.ethernetAddress = ethernetAddress;
		pcs.firePropertyChange("ethernetAddress", oldValue, ethernetAddress);
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

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (null == obj) {
			return false;
		}
		
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Host other = (Host) obj;
		
		if (null != this.name) {
			if (!this.name.equals(other.name)) {
				return false;
			}
		} else {
			if (null != other.name) {
				return false;
			}
		}
		
		if (null != this.host) {
			if (!this.host.equals(other.host)) {
				return false;
			}
		} else {
			if (null != other.host) {
				return false;
			}
		}
		
		if (null != this.ethernetAddress) {
			if (!this.ethernetAddress.equals(other.ethernetAddress)) {
				return false;
			}
		} else {
			if (null != other.ethernetAddress) {
				return false;
			}
		}
		
		if (null != this.comment) {
			if (!this.comment.equals(other.comment)) {
				return false;
			}
		} else {
			if (null != other.comment) {
				return false;
			}
		}
		
		if (this.port != other.port) {
			return false;
		}
		
		return true;
	}
}

/*
 * $Log: Host.java,v $
 * Revision 1.4  2004/04/15 22:57:44  gon23
 * *** empty log message ***
 *
 * Revision 1.3  2004/04/14 22:14:49  gon23
 * *** empty log message ***
 *
 * Revision 1.2  2004/04/14 18:21:39  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/14 11:13:08  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */