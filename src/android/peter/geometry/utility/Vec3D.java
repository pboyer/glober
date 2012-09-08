package android.peter.geometry.utility;

public class Vec3D {

	public float x;
	public float y;
	public float z;

	public Vec3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vec3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3D normalize() {

		float mag = this.magnitude();

		return this.multiply(1 / mag);
	}

	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public Vec3D cross(Vec3D o) {
		float xi = this.y * o.z - this.z * o.y;
		float yi = this.z * o.x - this.x * o.z;
		float zi = this.x * o.y - this.y * o.x;

		return new Vec3D(xi, yi, zi);
	}

	public float dot(Vec3D o) {
		float xi = this.x * o.x;
		float yi = this.y * o.y;
		float zi = this.z * o.z;

		return xi + yi + zi;
	}

	public Vec3D add(Vec3D o) {
		this.x += o.x;
		this.y += o.y;
		this.z += o.z;

		return new Vec3D(this.x, this.y, this.z);
	}

	public Vec3D subtract(Vec3D o) {
		this.x -= o.x;
		this.y -= o.y;
		this.z -= o.z;

		return new Vec3D(this.x, this.y, this.z);
	}

	public Vec3D multiply(float m) {
		float xi = this.x * m;
		float yi = this.y * m;
		float zi = this.z * m;

		return new Vec3D(xi, yi, zi);
	}

}
