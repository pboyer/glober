package android.peter.geometry.mesh.primitives;

import java.util.ArrayList;

import android.peter.geometry.utility.Vec3D;

public class Globe extends Mesh {
	private final float[] vertices;
	private final float[] normals;
	private final short[] indices;
	private final float[] uvs;

	public Globe(float radius, int resTheta, int resPhi) {

		normals = new float[3 * ((resTheta + 1) * (resPhi + 1))];
		ArrayList<Float> normalList = new ArrayList<Float>();

		vertices = new float[3 * ((resTheta + 1) * (resPhi + 1))];
		ArrayList<Float> vertexList = new ArrayList<Float>();

		indices = new short[6 * (resTheta * resPhi)];
		ArrayList<Short> indexList = new ArrayList<Short>();

		uvs = new float[3 * ((resTheta + 1) * (resPhi + 1))];
		ArrayList<Float> uvList = new ArrayList<Float>();

		for (int v = 0; v <= resPhi; v++) {
			for (int u = 0; u <= resTheta; u++) {

				x = (float) (radius * Math.sin(Math.PI * (v / (double) resPhi)) * Math
						.cos(2 * Math.PI * (u / (double) resTheta)));
				y = (float) (radius * Math.sin(Math.PI * (v / (double) resPhi)) * Math
						.sin(2 * Math.PI * (u / (double) resTheta)));

				z = radius
						+ (float) (radius * Math.cos(Math.PI
								* (v / (float) resPhi)));

				vertexList.add(x);
				vertexList.add(y);
				vertexList.add(z);

				Vec3D norm = new Vec3D(x, y, z).normalize();

				normalList.add(-norm.x);
				normalList.add(-norm.y);
				normalList.add(-norm.z);

				float U = (float) u / (float) resTheta;
				float V = (float) v / (float) resPhi;

				uvList.add(U);
				uvList.add(V);

			}
		}

		for (int u = 0; u < resTheta; u++) {
			for (int v = 0; v < resPhi; v++) {

				indexList.add((short) ((v + 1) * (resTheta + 1) + u));
				indexList.add((short) (v * (resTheta + 1) + u));
				indexList.add((short) (v * (resTheta + 1) + u + 1));

				indexList.add((short) ((v + 1) * (resTheta + 1) + u + 1));
				indexList.add((short) ((v + 1) * (resTheta + 1) + u));
				indexList.add((short) (v * (resTheta + 1) + u + 1));

			}
		}

		int c = 0;
		for (Short s : indexList) {
			indices[c++] = s;
		}

		c = 0;
		for (Float s : vertexList) {
			vertices[c++] = s;
		}

		c = 0;
		for (Float s : normalList) {
			normals[c++] = s;
		}

		c = 0;
		for (Float s : uvList) {
			uvs[c++] = s;
		}

		this.setVertices(vertices);
		this.setIndices(indices);
		this.setNormals(normals);
		this.setTextureCoordinates(uvs);

	}

	public void invertNormals() {
		if (normals != null) {
			for (int i = 0; i < normals.length; i++) {
				normals[i] *= -1;
			}
		}
	}

}
