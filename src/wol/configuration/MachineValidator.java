/*
 * $Id: MachineValidator.java,v 1.1 2004/05/17 21:58:59 gon23 Exp $
 */
package wol.configuration;


public interface MachineValidator {
	public boolean nameIsValid(String name);
	public boolean portIsValid(String port);
	public boolean hostIsValid(String host);
	public boolean ethernetAddressIsValid(String ethernetAddress);
	public boolean commentIsValid(String comment);
}

/*
 * $Log: MachineValidator.java,v $
 * Revision 1.1  2004/05/17 21:58:59  gon23
 * javadoc
 *
 */