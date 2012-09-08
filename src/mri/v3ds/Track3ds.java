
package mri.v3ds;


/**
 * Base class for all keyframer tracks.
 */
public class Track3ds
{
	/**
	 * Track runs one and stops.
	 */
	public static final int SINGLE = 0;

	/**
	 * Track repeats.
	 */
	public static final int REPEAT = 2;

	/**
	 * Track loops.
	 */
	public static final int LOOP = 3;

	public static final int LOCK_X = 0x008;
	public static final int LOCK_Y = 0x010;
	public static final int LOCK_Z = 0x020;

	public static final int UNLINK_X = 0x080;
	public static final int UNLINK_Y = 0x100;
	public static final int UNLINK_Z = 0x200;


	// Flags
	int mFlags = 0;


	/**
	 * Get loop type.
	 *
	 * @return loop type (SINGLE, LOOP, REPEAT)
	 */
	public int loopType()
	{
		return mFlags & 0x3;
	}

	/**
	 * Get track flags. Use the constants LOCK_X, LOCK_Y, LOCK_Z, UNLINK_X,
	 * UNLINK_Y and UNLINK_Z as bitmasks to see if the flags are set.
	 *
	 * @return track flags
	 */
	public int flags()
	{
		return mFlags;
	}
}

