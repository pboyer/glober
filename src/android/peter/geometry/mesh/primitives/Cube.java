package android.peter.geometry.mesh.primitives;

public class Cube extends Mesh {

	public Cube(float width, float height, float depth) {

		width /= 2;
		height /= 2;
		depth /= 2;

		float vertices[] = {
				// Vertices according to faces
				-width,
				-height,
				depth, // top
				width,
				-height,
				depth, // v1
				-width,
				width,
				depth, // v2
				width,
				height,
				depth, // v3

				width,
				-height,
				depth, // right side
				width, -height, -depth, width, height, depth, width,
				height,
				-depth,

				width,
				-height,
				-depth, // bottom
				-width, -height, -depth, width, height, -depth, -width, height,
				-depth,

				-width,
				-height,
				-depth, // left side
				-width, -height, depth, -width, height, -depth, -width, height,
				depth,

				-width,
				-height,
				-depth, // front
				width, -height, -depth, -width, -height, depth, width, -height,
				depth,

				-width, height,
				depth, // back
				width, height, depth, -width, height, -depth, width, height,
				-depth, };

		float normals[] = {
				// Normals
				0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 1.0f,

				1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 0.0f,

				0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
				0.0f, -1.0f,

				-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
				0.0f, 0.0f,

				0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f,

				0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
				1.0f, 0.0f };

		short indices[] = { 0, 1, 3, 0, 3,
				2, // Face front
				4, 5, 7, 4, 7,
				6, // Face right
				8, 9, 11, 8, 11,
				10, // ...
				12, 13, 15, 12, 15, 14, 16, 17, 19, 16, 19, 18, 20, 21, 23, 20,
				23, 22, };

		setIndices(indices);
		setVertices(vertices);
		setNormals(normals);
	}

}