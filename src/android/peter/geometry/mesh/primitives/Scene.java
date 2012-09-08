package android.peter.geometry.mesh.primitives;

import javax.microedition.khronos.opengles.GL10;

import android.peter.geometry.lights.LightGroup;

public class Scene {
	public MeshGroup meshGroup;
	public LightGroup lightGroup;

	public Scene() {
		meshGroup = new MeshGroup();
		lightGroup = new LightGroup();
	}

	public void drawMeshes(GL10 gl) {
		meshGroup.draw(gl);
	}

	public void enableLights(GL10 gl) {
		lightGroup.enable(gl);
	}

}
