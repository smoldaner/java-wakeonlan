/*
 * $Id: Configuration.java,v 1.4 2004/04/15 22:50:39 gon23 Exp $
 */
package wol.configuration;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class Configuration {
	private final static Logger LOG = Logger.getLogger(Configuration.class.getName());
	private Host[] hosts;
	private File file;
	
	public Configuration(String path) {
		this(new File(path));
	}
	
	public Configuration(File file) {
		super();
		this.file = file;
		
		if (file.exists()) {
			try {
				loadConfig();
			} catch (FileNotFoundException e) {
				String errMsg = "Could not load configuration";
				
				if (LOG.isLoggable(Level.FINE)) {
					LOG.log(Level.WARNING, errMsg, e);
				} else {
					LOG.warning(errMsg);
				}
				
			}
		} else {
			this.file = file;
		}
	}
	
	public Host[] getHosts() {
		return null == hosts ? new Host[0] : hosts;
	}

	public void setHosts(Host[] hosts) {
		this.hosts = hosts;
	}
	
	public void loadConfig() throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
		
		try {
			this.hosts = (Host[]) decoder.readObject();
		} catch (Throwable t) {
			String errMsg = "Could not load configuration";
			
			LOG.log(Level.SEVERE, errMsg, t);
		}
		
		decoder.close();
	}
	
	public void saveConfig() throws FileNotFoundException {
		saveConfig(this.file);
	}
	
	public void saveConfig(File file) throws FileNotFoundException {
		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file));
		
		encoder.writeObject(this.hosts);
		encoder.close();
		this.file = file;
	}
	public File getFile() {
		return file;
	}
}

/*
 * $Log: Configuration.java,v $
 * Revision 1.4  2004/04/15 22:50:39  gon23
 * New Constructor
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
 */