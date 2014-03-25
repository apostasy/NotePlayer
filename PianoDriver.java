package notes;

import javax.swing.*;

/**
 * 
 * @author Rob Vogel
 *
 */
public class PianoDriver 
{
	/**
	 * Starts the Piano object, a JFrame piano player
	 * @param a
	 * 	No arguments
	 */
	public static void main(String[] a) 
	{
		Piano piano = new Piano();

		piano.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
