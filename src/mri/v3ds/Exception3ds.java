
package mri.v3ds;


/**
 * Exception class thrown by the {@link mri.v3ds.Scene3ds Scene3ds} constructors.
 *
 * The exception is thrown in case of I/O and parsing problems. Use 
 * <code>getMessage()</code> to retreive the error message.
 * 
 * @author Mats Byggmästar
 * @version 0.1
 */
public class Exception3ds extends java.lang.Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Exception3ds(String s)
	{
		super(s);
	}
}

