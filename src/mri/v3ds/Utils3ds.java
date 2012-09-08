
package mri.v3ds;

import java.text.DecimalFormat;
import java.lang.Integer;


class Utils3ds
{
	static DecimalFormat mDF = new DecimalFormat("0.0000");

	static String mSpaces = "                                ";
	static String mZeroes = "00000000000000000000000000000000";


	public static String floatToString(float val, int width)
	{
		String str = mDF.format(val);
		if(str.length() >= width) {
			return str;
		}
		return mSpaces.substring(0, width-str.length()) + str;
	}

	public static String intToString(int val, int width)
	{
		String str = new String("" + val);
		if(str.length() >= width) {
			return str;
		}
		return mSpaces.substring(0, width-str.length()) + str;
	}

	public static String intToHexString(int val, int width)
	{
		String str = Integer.toHexString(val);
		if(str.length() >= width) {
			return str;
		}
		return mZeroes.substring(0, width-str.length()) + str;
	}

	public static String intToBinString(int val, int width)
	{
		String str = Integer.toBinaryString(val);
		if(str.length() >= width) {
			return str;
		}
		return mZeroes.substring(0, width-str.length()) + str;
	}
}


