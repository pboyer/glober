package android.peter.geometry.utility;

import android.util.FloatMath;

public class Quaternion {

	public float x;
	public float y;
	public float z;
	public float w;

	Quaternion() {

	}

	/**
	 * Constructor, sets the four components of the quaternion.
	 * 
	 * @param x
	 *            The x-component
	 * @param y
	 *            The y-component
	 * @param z
	 *            The z-component
	 * @param w
	 *            The w-component
	 */
	public Quaternion(float x, float y, float z, float w) {
		this.set(x, y, z, w);
	}

	/**
	 * Sets the components of the quaternion
	 * 
	 * @param x
	 *            The x-component
	 * @param y
	 *            The y-component
	 * @param z
	 *            The z-component
	 * @param w
	 *            The w-component
	 * @return This quaternion for chaining
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * Sets the quaternion to the given euler angles.
	 * 
	 * @param yaw
	 *            the yaw in degrees
	 * @param pitch
	 *            the pitch in degress
	 * @param roll
	 *            the roll in degess
	 * @return this quaternion
	 */
	public Quaternion setEulerAngles(float yaw, float pitch, float roll) {
		float num9 = roll * 0.5f;
		float num6 = FloatMath.sin(num9);
		float num5 = FloatMath.cos(num9);
		float num8 = pitch * 0.5f;
		float num4 = FloatMath.sin(num8);
		float num3 = FloatMath.cos(num8);
		float num7 = yaw * 0.5f;
		float num2 = FloatMath.sin(num7);
		float num = FloatMath.cos(num7);
		x = ((num * num4) * num5) + ((num2 * num3) * num6);
		y = ((num2 * num3) * num5) - ((num * num4) * num6);
		z = ((num * num3) * num6) - ((num2 * num4) * num5);
		w = ((num * num3) * num5) + ((num2 * num4) * num6);
		return this;
	}

	/**
	 * Spherical linear interpolation between this quaternion and the other
	 * quaternion, based on the alpha value in the range [0,1]. Taken from.
	 * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/
	 * 
	 * @param end
	 *            the end quaternion
	 * @param alpha
	 *            alpha in the range [0,1]
	 * @return this quaternion for chaining
	 */
	public Quaternion slerp(Quaternion end, float alpha) {
		if (this.equals(end)) {
			return this;
		}

		float result = dot(end);

		if (result < 0.0) {
			// Negate the second quaternion and the result of the dot product
			end.mul(-1);
			result = -result;
		}

		// Set the first and second scale for the interpolation
		float scale0 = 1 - alpha;
		float scale1 = alpha;

		// Check if the angle between the 2 quaternions was big enough to
		// warrant such calculations
		if ((1 - result) > 0.1) {// Get the angle between the 2 quaternions,
			// and then store the sin() of that angle
			final double theta = Math.acos(result);
			final double invSinTheta = 1f / Math.sin(theta);

			// Calculate the scale for q1 and q2, according to the angle and
			// it's sine value
			scale0 = (float) (Math.sin((1 - alpha) * theta) * invSinTheta);
			scale1 = (float) (Math.sin((alpha * theta)) * invSinTheta);
		}

		// Calculate the x, y, z and w values for the quaternion by using a
		// special form of linear interpolation for quaternions.
		final float x = (scale0 * this.x) + (scale1 * end.x);
		final float y = (scale0 * this.y) + (scale1 * end.y);
		final float z = (scale0 * this.z) + (scale1 * end.z);
		final float w = (scale0 * this.w) + (scale1 * end.w);
		set(x, y, z, w);

		// Return the interpolated quaternion
		return this;
	}

	/**
	 * Dot product between this and the other quaternion.
	 * 
	 * @param other
	 *            the other quaternion.
	 * @return this quaternion for chaining.
	 */
	public float dot(Quaternion other) {
		return x * other.x + y * other.y + z * other.z + w * other.w;
	}

	/**
	 * Multiplies the components of this quaternion with the given scalar.
	 * 
	 * @param scalar
	 *            the scalar.
	 * @return this quaternion for chaining.
	 */
	public Quaternion mul(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		this.w *= scalar;
		return this;
	}

	public static float[] getEulerAngles(Quaternion q1) {
		double sqw = q1.w * q1.w;
		double sqx = q1.x * q1.x;
		double sqy = q1.y * q1.y;
		double sqz = q1.z * q1.z;
		double heading;
		double attitude;
		double bank;
		double unit = sqx + sqy + sqz + sqw; // if normalised is one, otherwise
												// is correction factor
		double test = q1.x * q1.y + q1.z * q1.w;
		if (test > 0.499 * unit) { // singularity at north pole
			heading = 2 * Math.atan2(q1.x, q1.w);
			attitude = (float) (Math.PI / 2);
			bank = 0;
			return new float[] { (float) heading, (float) attitude,
					(float) bank };
		}
		if (test < -0.499 * unit) { // singularity at south pole
			heading = -2 * Math.atan2(q1.x, q1.w);
			attitude = (float) (-Math.PI / 2);
			bank = 0;
			return new float[] { (float) heading, (float) attitude,
					(float) bank };
		}
		heading = Math.atan2(2 * q1.y * q1.w - 2 * q1.x * q1.z, sqx - sqy - sqz
				+ sqw);
		attitude = Math.asin(2 * test / unit);
		bank = Math.atan2(2 * q1.x * q1.w - 2 * q1.y * q1.z, -sqx + sqy - sqz
				+ sqw);

		return new float[] { (float) heading, (float) attitude, (float) bank };

	}

	public Quaternion setFromMatrix(Matrix4 matrix) {
		return setFromAxes(matrix.val[Matrix4.M00], matrix.val[Matrix4.M01],
				matrix.val[Matrix4.M02], matrix.val[Matrix4.M10],
				matrix.val[Matrix4.M11], matrix.val[Matrix4.M12],
				matrix.val[Matrix4.M20], matrix.val[Matrix4.M21],
				matrix.val[Matrix4.M22]);
	}

	/**
	 * <p>
	 * Sets the Quaternion from the given x-, y- and z-axis which have to be
	 * orthonormal.
	 * </p>
	 * 
	 * <p>
	 * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/
	 * which in turn took it from Graphics Gem code at
	 * ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z.
	 * </p>
	 * 
	 * @param xx
	 *            x-axis x-coordinate
	 * @param xy
	 *            x-axis y-coordinate
	 * @param xz
	 *            x-axis z-coordinate
	 * @param yx
	 *            y-axis x-coordinate
	 * @param yy
	 *            y-axis y-coordinate
	 * @param yz
	 *            y-axis z-coordinate
	 * @param zx
	 *            z-axis x-coordinate
	 * @param zy
	 *            z-axis y-coordinate
	 * @param zz
	 *            z-axis z-coordinate
	 */
	public Quaternion setFromAxes(float xx, float xy, float xz, float yx,
			float yy, float yz, float zx, float zy, float zz) {
		// the trace is the sum of the diagonal elements; see
		// http://mathworld.wolfram.com/MatrixTrace.html
		final float m00 = xx, m01 = yx, m02 = zx;
		final float m10 = xy, m11 = yy, m12 = zy;
		final float m20 = xz, m21 = yz, m22 = zz;
		final float t = m00 + m11 + m22;

		// we protect the division by s by ensuring that s>=1
		double x, y, z, w;
		if (t >= 0) { // |w| >= .5
			double s = Math.sqrt(t + 1); // |s|>=1 ...
			w = 0.5 * s;
			s = 0.5 / s; // so this division isn't bad
			x = (m21 - m12) * s;
			y = (m02 - m20) * s;
			z = (m10 - m01) * s;
		} else if ((m00 > m11) && (m00 > m22)) {
			double s = Math.sqrt(1.0 + m00 - m11 - m22); // |s|>=1
			x = s * 0.5; // |x| >= .5
			s = 0.5 / s;
			y = (m10 + m01) * s;
			z = (m02 + m20) * s;
			w = (m21 - m12) * s;
		} else if (m11 > m22) {
			double s = Math.sqrt(1.0 + m11 - m00 - m22); // |s|>=1
			y = s * 0.5; // |y| >= .5
			s = 0.5 / s;
			x = (m10 + m01) * s;
			z = (m21 + m12) * s;
			w = (m02 - m20) * s;
		} else {
			double s = Math.sqrt(1.0 + m22 - m00 - m11); // |s|>=1
			z = s * 0.5; // |z| >= .5
			s = 0.5 / s;
			x = (m02 + m20) * s;
			y = (m21 + m12) * s;
			w = (m10 - m01) * s;
		}

		return set((float) x, (float) y, (float) z, (float) w);
	}

}
