/*
 * $Id: Errors.java,v 1.1 2004/04/14 18:21:40 gon23 Exp $
 */
package wol;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Errors {
	private static final String BUNDLE_NAME = "wol.Errors"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static final Logger LOG = Logger.getLogger(Errors.class.getName());
	
	private Errors() {

	}
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOG.warning("Missing resource:" + key);
			
			return '!' + key + '!';
		}
	}
	
		public static String getFormattedString(String key, Object[] arguments) {
		String message = Errors.getString(key);
		 
		return MessageFormat.format(message, arguments);
	}
	
	public static String getFormattedString(String key, Object argument) {
		return Errors.getFormattedString(key, new Object[]{argument});
	}
	
	public static String getFormattedString(String key, Object argument1, Object argument2) {
		return Errors.getFormattedString(key, new Object[]{argument1, argument2});
	}
	
	public static String getFormattedString(String key, Object argument1, Object argument2, Object argument3) {
		return Errors.getFormattedString(key, new Object[]{argument1, argument2, argument3});
	}
	
	public static String getFormattedString(String key, Object argument1, Object argument2, Object argument3, Object argument4) {
		return Errors.getFormattedString(key, new Object[]{argument1, argument2, argument3, argument4});
	}
	

}

/*
 * $Log: Errors.java,v $
 * Revision 1.1  2004/04/14 18:21:40  gon23
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */