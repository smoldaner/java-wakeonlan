/*
 * $Id: Configuration.java,v 1.1 2004/04/14 11:13:08 gon23 Exp $
 */
package wol.configuration;

import java.io.File;
import java.util.List;

public class Configuration {
	private Host[] hosts;
		
	public Configuration() {
		super();
	}
	
	public Host[] getHosts() {
		return null == hosts ? new Host[0] : hosts;
	}

	public void setHosts(Host[] hosts) {
		this.hosts = hosts;
	}
}

/*
 * $Log: Configuration.java,v $
 * Revision 1.1  2004/04/14 11:13:08  gon23
 * *** empty log message ***
 *
 */