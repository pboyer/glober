
package mri.v3ds;

import java.lang.StringBuffer;


/**
 * Container class for storing text decode.
 */
public class TextDecode3ds
{
	StringBuffer mText = new StringBuffer(1024 * 4);

	/**
	 * Clear all text.
	 */
	public void clear()
	{
		mText.setLength(0);
	}

	/**
	 * Access the text decode.
	 *
	 * @return text string
	 */
	public String text()
	{
		return mText.toString();
	}
}

