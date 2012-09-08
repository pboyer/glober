package android.peter.geometry.translators;

import mri.v3ds.Color3ds;
import mri.v3ds.Light3ds;
import mri.v3ds.Mesh3ds;
import mri.v3ds.Scene3ds;
import mri.v3ds.Vertex3ds;
import android.peter.geometry.lights.Light;
import android.peter.geometry.mesh.primitives.Mesh;
import android.peter.geometry.mesh.primitives.Scene;
import android.peter.geometry.utility.NormalsTranslator;

public class Translator3ds {

	public static void translate(Scene3ds s, Scene scene) {

		// load meshes
		for (int i = 0; i < s.meshes(); i++) {
			Mesh3ds m = s.mesh(i);
			Mesh mOut = new Mesh(); // the mesh we will export

			float[] verticesOut = new float[3 * m.vertices()];
			float[] texCoordsOut = null;
			if (m.texCoords() != 0)
				texCoordsOut = new float[2 * m.texCoords()];

			for (int j = 0; j < m.vertices(); j++) {
				verticesOut[3 * j + 0] = s.mesh(i).vertex(j).X;
				verticesOut[3 * j + 1] = s.mesh(i).vertex(j).Y;
				verticesOut[3 * j + 2] = s.mesh(i).vertex(j).Z;

				if (m.texCoords() != 0) {
					texCoordsOut[2 * j + 0] = s.mesh(i).texCoord(j).U;
					texCoordsOut[2 * j + 1] = s.mesh(i).texCoord(j).V;
				}
			}

			short[] indicesOut = new short[3 * m.faces()];

			for (int j = 0; j < m.faces(); j++) {
				indicesOut[3 * j + 0] = (short) s.mesh(i).face(j).P0;
				indicesOut[3 * j + 1] = (short) s.mesh(i).face(j).P1;
				indicesOut[3 * j + 2] = (short) s.mesh(i).face(j).P2;
			}

			mOut.setVertices(verticesOut);
			mOut.setIndices(indicesOut);
			mOut.setTextureCoordinates(texCoordsOut);
			mOut.setNormals(NormalsTranslator.computeNormals(m));

			// materials for every face!

			scene.meshGroup.add(mOut);
		}

		// load lights
		for (int i = 0; i < s.lights(); i++) {
			Light3ds light = s.light(i);

			Color3ds color = light.color();
			float[] colorArray = new float[] { color.red(), color.blue(),
					color.green() };

			Vertex3ds position = light.fixedPosition();
			float[] positionArray = new float[] { position.X, position.Y,
					position.Z };

			// TODO: stub -> translate between 3ds lights and opengl, not 1-1
			Light l = Light.CreateLight(colorArray, null, null, null,
					positionArray);

			scene.lightGroup.add(l);
		}

	}
}
