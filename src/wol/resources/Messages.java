/*
 * $Id: Messages.java,v 1.1 2004/04/15 10:20:19 gon23 Exp $
 */
package wol.resources;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author <a href="&#109;&#97;&#105;&#108;&#116;&#111;&#58;&#115;&#46;&#109;&#111;&#108;&#100;&#97;&#110;&#101;&#114;&#64;&#103;&#109;&#120;&#46;&#110;&#101;&#116;">Steffen Moldaner</a>
 */
public class Messages {
	private static final Logger LOG = Logger.getLogger(Messages.class.getName());
	private ResourceBundle bundle;
	
	public final static Messages ERROR_MESSAGES = new Messages(ResourceBundle.getBundle("wol.resources.ErrorResources"));
	public final static Messages CMD_MESSAGES = new Messages(ResourceBundle.getBundle("wol.resources.CMDResources"));
	public final static Messages UI_MESSAGES = new Messages(ResourceBundle.getBundle("wol.resources.UIResources"));
	
	private Messages(ResourceBundle bundle) {
		super();
		this.bundle = bundle;
	}
	
	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			LOG.warning("Missing resource:" + key);
			
			return '!' + key + '!';
		}
	}
	
	public int getChar(String key) {
		try {
			String charString =  bundle.getString(key);
			
			if (charString.length() != 1) {
				LOG.warning(key + " is not a char: " + charString);
			} else {
				return charString.charAt(0);
			}
		} catch (MissingResourceException e) {
			LOG.warning("Missing resource: " + key);
		}
		
		return -1;
	}
	
	public String getFormattedString(String key, Object[] arguments) {
		String message = getString(key);
		 
		return MessageFormat.format(message, arguments);
	}
	
	public String getFormattedString(String key, Object argument) {
		return getFormattedString(key, new Object[]{argument});
	}
	
	public String getFormattedString(String key, Object argument1, Object argument2) {
		return getFormattedString(key, new Object[]{argument1, argument2});
	}
	
	public String getFormattedString(String key, Object argument1, Object argument2, Object argument3) {
		return getFormattedString(key, new Object[]{argument1, argument2, argument3});
	}
	
	public String getFormattedString(String key, Object argument1, Object argument2, Object argument3, Object argument4) {
		return getFormattedString(key, new Object[]{argument1, argument2, argument3, argument4});
	}
}

/*
 * $Log: Messages.java,v $
 * Revision 1.1  2004/04/15 10:20:19  gon23
 * Initial
 *
 */