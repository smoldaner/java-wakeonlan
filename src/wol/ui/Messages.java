/*
 * $Id: Messages.java,v 1.2 2004/04/14 18:21:40 gon23 Exp $
 */
package wol.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Messages {
	private static final String BUNDLE_NAME = "wol.ui.Resources"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static final Logger LOG = Logger.getLogger(Messages.class.getName());
	
	private Messages() {

	}
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOG.warning("Missing resource: " + key);
			
			return '!' + key + '!';
		}
	}
	
	public static int getMnemonic(String key) {
		try {
			String charString =  RESOURCE_BUNDLE.getString(key);
			
			if (charString.length() != 1) {
				LOG.warning("Mnemonic for key " + key + " is not a char: " + charString);
			} else {
				return charString.charAt(0);
			}
		} catch (MissingResourceException e) {
			LOG.warning("Missing mnemonic resource for key: " + key);
		}
		
		return -1;
	}
}

/*
 * $Log: Messages.java,v $
 * Revision 1.2  2004/04/14 18:21:40  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */