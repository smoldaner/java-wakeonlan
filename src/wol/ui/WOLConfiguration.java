/*
 * $Id: WOLConfiguration.java,v 1.1 2004/04/08 22:10:06 gon23 Exp $
 */
package wol.ui;


public class WOLConfiguration {
	private String name;
	private String host;
	private String hwAdress;
	private int port;
	
	public WOLConfiguration() {
		super();
	}
	
	public WOLConfiguration(String name) {
		this();
		this.name = name;
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

	public void setHost(String string) {
		host = string;
	}

	public void setHwAdress(String string) {
		hwAdress = string;
	}

	public void setName(String string) {
		name = string;
	}

	public void setPort(int i) {
		port = i;
	}

	public String toString() {
		return getName();
	}

}

/*
 * $Log: WOLConfiguration.java,v $
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */