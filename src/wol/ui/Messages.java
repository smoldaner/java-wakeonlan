/*
 * $Id: Messages.java,v 1.1 2004/04/08 22:10:06 gon23 Exp $
 */
package wol.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "wol.ui.Resources"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {

	}
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

/*
 * $Log: Messages.java,v $
 * Revision 1.1  2004/04/08 22:10:06  gon23
 * Initial
 *
 */