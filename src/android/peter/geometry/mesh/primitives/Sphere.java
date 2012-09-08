package android.peter.geometry.mesh.primitives;

import android.peter.geometry.utility.Vec3D;

public class Sphere extends Mesh {
	private final float[] vertices;
	private final float[] normals;
	private final short[] indices;

	// private float[] uvs;

	public Sphere(float radius, int resTheta, int resPhi) {

		normals = new float[3 * (2 + resTheta * (resPhi - 1))]; // top, strips,
																// bottom

		vertices = new float[3 * (2 + resTheta * (resPhi - 1))]; // top, strips,
																	// bottom

		indices = new short[3 * (2 * resTheta + resTheta * (resPhi - 2) * 2)];

		// top point
		vertices[0] = 0;
		vertices[1] = 0;
		vertices[2] = radius;

		normals[0] = 0;
		normals[1] = 0;
		normals[2] = 1;

		double x;
		double y;
		double z;

		// strips
		for (int u = 0; u < resTheta; u++) {
			for (int v = 1; v < resPhi; v++) {

				x = radius * Math.sin(Math.PI * (v / (double) resPhi))
						* Math.cos(2 * Math.PI * (u / (double) resTheta));
				y = radius * Math.sin(Math.PI * (v / (double) resPhi))
						* Math.sin(2 * Math.PI * (u / (double) resTheta));

				z = radius * Math.cos(Math.PI * (v / (double) resPhi));

				vertices[3 * (u * (resPhi - 1) + v)] = (float) x;
				vertices[3 * (u * (resPhi - 1) + v) + 1] = (float) y;
				vertices[3 * (u * (resPhi - 1) + v) + 2] = (float) z;

				Vec3D norm = new Vec3D((float) x, (float) y, (float) z)
						.normalize();

				normals[3 * (u * (resPhi - 1) + v)] = norm.x;
				normals[3 * (u * (resPhi - 1) + v) + 1] = norm.y;
				normals[3 * (u * (resPhi - 1) + v) + 2] = norm.z;
			}
		}

		// bottom point
		vertices[vertices.length - 3] = 0;
		vertices[vertices.length - 2] = 0;
		vertices[vertices.length - 1] = -radius;

		normals[vertices.length - 3] = 0;
		normals[vertices.length - 2] = 0;
		normals[vertices.length - 1] = -1;

		// add all the top polygons except for the last one
		for (int n = 0; n < resTheta - 1; n++) {
			indices[3 * n] = 0; // top
			indices[3 * n + 1] = (short) (1 + (resPhi - 1) * n);
			indices[3 * n + 2] = (short) (1 + (resPhi - 1) * (n + 1));
		}

		int offset = 3 * (resTheta - 1);

		indices[offset] = 0; // top
		indices[offset + 1] = (short) (1 + (resPhi - 1) * (resTheta - 1));
		indices[offset + 2] = 1;

		// bottom polygons

		offset = 3 * (resTheta);

		for (int n = 0; n < resTheta - 1; n++) {
			indices[offset + 3 * n] = (short) ((resPhi - 1) * resTheta + 1);
			indices[offset + 3 * n + 1] = (short) ((resPhi - 1) * (n + 2));
			indices[offset + 3 * n + 2] = (short) ((resPhi - 1) * (n + 1));
		}

		offset = offset + 3 * (resTheta - 1);

		// close the bottom cone
		indices[offset] = (short) ((resPhi - 1) * resTheta + 1);
		indices[offset + 1] = (short) (resPhi - 1);
		indices[offset + 2] = (short) ((resPhi - 1) * resTheta);

		offset = offset + 3;

		for (int u = 0; u < resTheta - 1; u++) {
			for (int v = 0; v < resPhi - 2; v++) {

				indices[offset + 6 * ((resPhi - 2) * u + v)] = (short) (u
						* (resPhi - 1) + v + 1);
				indices[offset + 6 * ((resPhi - 2) * u + v) + 1] = (short) (u
						* (resPhi - 1) + v + 2);
				indices[offset + 6 * ((resPhi - 2) * u + v) + 2] = (short) ((u + 1)
						* (resPhi - 1) + v + 1);

				indices[offset + 6 * ((resPhi - 2) * u + v) + 3] = (short) (u
						* (resPhi - 1) + v + 2);
				indices[offset + 6 * ((resPhi - 2) * u + v) + 4] = (short) ((u + 1)
						* (resPhi - 1) + v + 2);
				indices[offset + 6 * ((resPhi - 2) * u + v) + 5] = (short) ((u + 1)
						* (resPhi - 1) + v + 1);

			}
		}

		offset = offset + 6 * (resTheta - 1) * (resPhi - 2);

		for (int v = 0; v < resPhi - 2; v++) {

			indices[offset + 6 * v] = (short) ((resTheta - 1) * (resPhi - 1)
					+ v + 1);
			indices[offset + 6 * v + 1] = (short) ((resTheta - 1)
					* (resPhi - 1) + v + 2);
			indices[offset + 6 * v + 2] = (short) (v + 1);

			indices[offset + 6 * v + 3] = (short) ((resTheta - 1)
					* (resPhi - 1) + v + 2);
			indices[offset + 6 * v + 4] = (short) (v + 2);
			indices[offset + 6 * v + 5] = (short) (v + 1);

		}

		this.setVertices(vertices);
		this.setIndices(indices);
		this.setNormals(normals);
		// this.setUVs(UVs);

	}

	public void invertNormals() {
		if (normals != null) {
			for (int i = 0; i < normals.length; i++) {
				normals[i] *= -1;
			}
		}
	}

}
