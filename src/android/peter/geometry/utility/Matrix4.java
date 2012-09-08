package android.peter.geometry.utility;

import android.util.FloatMath;

public class Matrix4 {

	public static final int M00 = 0;// 0;
	public static final int M01 = 4;// 1;
	public static final int M02 = 8;// 2;
	public static final int M03 = 12;// 3;
	public static final int M10 = 1;// 4;
	public static final int M11 = 5;// 5;
	public static final int M12 = 9;// 6;
	public static final int M13 = 13;// 7;
	public static final int M20 = 2;// 8;
	public static final int M21 = 6;// 9;
	public static final int M22 = 10;// 10;
	public static final int M23 = 14;// 11;
	public static final int M30 = 3;// 12;
	public static final int M31 = 7;// 13;
	public static final int M32 = 11;// 14;
	public static final int M33 = 15;// 15;

	public final float tmp[] = new float[16];
	public final float val[] = new float[16];

	static Quaternion quat = new Quaternion();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[" + val[M00] + "|" + val[M01] + "|" + val[M02] + "|"
				+ val[M03] + "]\n" + "[" + val[M10] + "|" + val[M11] + "|"
				+ val[M12] + "|" + val[M13] + "]\n" + "[" + val[M20] + "|"
				+ val[M21] + "|" + val[M22] + "|" + val[M23] + "]\n" + "["
				+ val[M30] + "|" + val[M31] + "|" + val[M32] + "|" + val[M33]
				+ "]\n";
	}

	/**
	 * Linearly interpolates between this matrix and the given matrix mixing by
	 * alpha
	 * 
	 * @param matrix
	 *            the matrix
	 * @param alpha
	 *            the alpha value in the range [0,1]
	 */
	public void lerp(Matrix4 matrix, float alpha) {
		for (int i = 0; i < 16; i++)
			this.val[i] = this.val[i] * (1 - alpha) + matrix.val[i] * alpha;
	}

	/**
	 * Sets this matrix to a rotation matrix from the given euler angles.
	 * 
	 * @param yaw
	 *            the yaw in degrees
	 * @param pitch
	 *            the pitch in degress
	 * @param roll
	 *            the roll in degrees
	 * @return this matrix
	 */
	public Matrix4 setFromEulerAngles(float yaw, float pitch, float roll) {
		idt();
		quat.setEulerAngles(yaw, pitch, roll);
		return this.set(quat);
	}

	/**
	 * Sets the matrix to a rotation matrix representing the quaternion.
	 * 
	 * @param quaternion
	 *            The quaternion
	 * @return This matrix for chaining
	 */
	public Matrix4 set(Quaternion quaternion) {
		// Compute quaternion factors
		float l_xx = quaternion.x * quaternion.x;
		float l_xy = quaternion.x * quaternion.y;
		float l_xz = quaternion.x * quaternion.z;
		float l_xw = quaternion.x * quaternion.w;
		float l_yy = quaternion.y * quaternion.y;
		float l_yz = quaternion.y * quaternion.z;
		float l_yw = quaternion.y * quaternion.w;
		float l_zz = quaternion.z * quaternion.z;
		float l_zw = quaternion.z * quaternion.w;
		// Set matrix from quaternion
		val[M00] = 1 - 2 * (l_yy + l_zz);
		val[M01] = 2 * (l_xy - l_zw);
		val[M02] = 2 * (l_xz + l_yw);
		val[M03] = 0;
		val[M10] = 2 * (l_xy + l_zw);
		val[M11] = 1 - 2 * (l_xx + l_zz);
		val[M12] = 2 * (l_yz - l_xw);
		val[M13] = 0;
		val[M20] = 2 * (l_xz - l_yw);
		val[M21] = 2 * (l_yz + l_xw);
		val[M22] = 1 - 2 * (l_xx + l_yy);
		val[M23] = 0;
		val[M30] = 0;
		val[M31] = 0;
		val[M32] = 0;
		val[M33] = 1;
		return this;
	}

	/**
	 * Multiplies this matrix with the given matrix, storing the result in this
	 * matrix.
	 * 
	 * @param matrix
	 *            The other matrix
	 * @return This matrix for chaining.
	 */
	public Matrix4 mul(Matrix4 matrix) {
		tmp[M00] = val[M00] * matrix.val[M00] + val[M01] * matrix.val[M10]
				+ val[M02] * matrix.val[M20] + val[M03] * matrix.val[M30];
		tmp[M01] = val[M00] * matrix.val[M01] + val[M01] * matrix.val[M11]
				+ val[M02] * matrix.val[M21] + val[M03] * matrix.val[M31];
		tmp[M02] = val[M00] * matrix.val[M02] + val[M01] * matrix.val[M12]
				+ val[M02] * matrix.val[M22] + val[M03] * matrix.val[M32];
		tmp[M03] = val[M00] * matrix.val[M03] + val[M01] * matrix.val[M13]
				+ val[M02] * matrix.val[M23] + val[M03] * matrix.val[M33];
		tmp[M10] = val[M10] * matrix.val[M00] + val[M11] * matrix.val[M10]
				+ val[M12] * matrix.val[M20] + val[M13] * matrix.val[M30];
		tmp[M11] = val[M10] * matrix.val[M01] + val[M11] * matrix.val[M11]
				+ val[M12] * matrix.val[M21] + val[M13] * matrix.val[M31];
		tmp[M12] = val[M10] * matrix.val[M02] + val[M11] * matrix.val[M12]
				+ val[M12] * matrix.val[M22] + val[M13] * matrix.val[M32];
		tmp[M13] = val[M10] * matrix.val[M03] + val[M11] * matrix.val[M13]
				+ val[M12] * matrix.val[M23] + val[M13] * matrix.val[M33];
		tmp[M20] = val[M20] * matrix.val[M00] + val[M21] * matrix.val[M10]
				+ val[M22] * matrix.val[M20] + val[M23] * matrix.val[M30];
		tmp[M21] = val[M20] * matrix.val[M01] + val[M21] * matrix.val[M11]
				+ val[M22] * matrix.val[M21] + val[M23] * matrix.val[M31];
		tmp[M22] = val[M20] * matrix.val[M02] + val[M21] * matrix.val[M12]
				+ val[M22] * matrix.val[M22] + val[M23] * matrix.val[M32];
		tmp[M23] = val[M20] * matrix.val[M03] + val[M21] * matrix.val[M13]
				+ val[M22] * matrix.val[M23] + val[M23] * matrix.val[M33];
		tmp[M30] = val[M30] * matrix.val[M00] + val[M31] * matrix.val[M10]
				+ val[M32] * matrix.val[M20] + val[M33] * matrix.val[M30];
		tmp[M31] = val[M30] * matrix.val[M01] + val[M31] * matrix.val[M11]
				+ val[M32] * matrix.val[M21] + val[M33] * matrix.val[M31];
		tmp[M32] = val[M30] * matrix.val[M02] + val[M31] * matrix.val[M12]
				+ val[M32] * matrix.val[M22] + val[M33] * matrix.val[M32];
		tmp[M33] = val[M30] * matrix.val[M03] + val[M31] * matrix.val[M13]
				+ val[M32] * matrix.val[M23] + val[M33] * matrix.val[M33];
		return this.set(tmp);
	}

	/**
	 * Sets the matrix to an identity matrix
	 * 
	 * @return This matrix for chaining
	 */
	public Matrix4 idt() {
		val[M00] = 1;
		val[M01] = 0;
		val[M02] = 0;
		val[M03] = 0;
		val[M10] = 0;
		val[M11] = 1;
		val[M12] = 0;
		val[M13] = 0;
		val[M20] = 0;
		val[M21] = 0;
		val[M22] = 1;
		val[M23] = 0;
		val[M30] = 0;
		val[M31] = 0;
		val[M32] = 0;
		val[M33] = 1;
		return this;
	}

	/**
	 * Sets the matrix to the given matrix.
	 * 
	 * @param matrix
	 *            The matrix
	 * @return This matrix for chaining
	 */
	public Matrix4 set(Matrix4 matrix) {
		return this.set(matrix.val);
	}

	/**
	 * Sets the matrix to the given matrix as a float array. The float array
	 * must have at least 16 elements.
	 * 
	 * @param values
	 *            The matrix
	 * @return This matrix for chaining
	 */
	public Matrix4 set(float[] values) {
		val[M00] = values[M00];
		val[M10] = values[M10];
		val[M20] = values[M20];
		val[M30] = values[M30];
		val[M01] = values[M01];
		val[M11] = values[M11];
		val[M21] = values[M21];
		val[M31] = values[M31];
		val[M02] = values[M02];
		val[M12] = values[M12];
		val[M22] = values[M22];
		val[M32] = values[M32];
		val[M03] = values[M03];
		val[M13] = values[M13];
		val[M23] = values[M23];
		val[M33] = values[M33];
		return this;
	}

	public float[] getEulerAngles() {
		float angle_x;
		float angle_z;
		float angle_y = (float) Math.asin(val[2]); /* Calculate Y-axis angle */
		float C = FloatMath.cos(angle_y);

		if (Math.abs(C) > 0.005) {
			float tx = val[10] / C; /* No, so get X-axis angle */
			float ty = -val[6] / C;
			angle_x = (float) Math.atan2(ty, tx);
			tx = val[0] / C; /* Get Z-axis angle */
			ty = -val[1] / C;
			angle_z = (float) Math.atan2(ty, tx);
		} else /* Gimball lock has occurred */
		{
			angle_x = 0; /* Set X-axis angle to zero */
			float tx = val[5]; /* And calculate Z-axis angle */
			float ty = val[4];
			angle_z = (float) Math.atan2(ty, tx);
		}

		return new float[] { angle_x, angle_y, angle_z };

		// Quaternion a = new Quaternion();
		// a.setFromMatrix(mat);
		// return Quaternion.getEulerAngles(a);
	}
}
