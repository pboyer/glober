package android.peter.geometry.utility;

import mri.v3ds.Face3ds;
import mri.v3ds.Mesh3ds;
import mri.v3ds.Vertex3ds;

public class NormalsTranslator {

	//
	// Compute scene vertex normals
	//
	public static float[] computeNormals(Mesh3ds mesh) {
		// for each face in the array
		// add the normal of each face to the vertex
		// normalize each vector in the array

		Vec3D[] normalVecs = new Vec3D[mesh.vertices()];

		Vertex3ds[] vertices = mesh.vertexArray();

		Face3ds[] faces = mesh.faceArray();

		for (Face3ds f : faces) {
			Vertex3ds vertp1 = vertices[f.P1];
			Vertex3ds vertp0 = vertices[f.P0];
			Vertex3ds vertp2 = vertices[f.P2];

			Vec3D vecp1 = new Vec3D(vertp1.X, vertp1.Y, vertp1.Z);
			Vec3D vecp0 = new Vec3D(vertp0.X, vertp0.Y, vertp0.Z);
			Vec3D vecp2 = new Vec3D(vertp2.X, vertp2.Y, vertp2.Z);

			Vec3D vecp2p1 = vecp2.subtract(vecp1);
			Vec3D vecp0p1 = vecp0.subtract(vecp1);

			// TODO: this algorithm does not weight the normals
			// based on properties of the face
			// TODO: possible cross product is incorrect

			// TODO: this depends on vertex windings being counterclockwise

			if (normalVecs[f.P0] == null)
				normalVecs[f.P0] = vecp0p1.cross(vecp2p1);
			else
				normalVecs[f.P0].add(vecp0p1.cross(vecp2p1));

			if (normalVecs[f.P1] == null)
				normalVecs[f.P1] = vecp0p1.cross(vecp2p1);
			else
				normalVecs[f.P1].add(vecp0p1.cross(vecp2p1));

			if (normalVecs[f.P2] == null)
				normalVecs[f.P2] = vecp0p1.cross(vecp2p1);
			else
				normalVecs[f.P2].add(vecp0p1.cross(vecp2p1));
		}

		for (int i = 0; i < normalVecs.length; i++) {
			normalVecs[i] = normalVecs[i].normalize();
		}

		float[] normalVecsOut = new float[normalVecs.length * 3];

		for (int i = 0; i < normalVecs.length; i++) {
			normalVecsOut[3 * i] = normalVecs[i].x;
			normalVecsOut[3 * i + 1] = normalVecs[i].y;
			normalVecsOut[3 * i + 2] = normalVecs[i].z;
		}

		return normalVecsOut;

	}
}
