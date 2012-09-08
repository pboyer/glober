package android.peter.geometry.lights;

import javax.microedition.khronos.opengles.GL10;

public class LightGroup extends Light {

	Light[] children = new Light[8];
	private boolean isShining;

	public LightGroup() {
	}

	@Override
	public void enable(GL10 gl) {
		if (isShining) {
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null) {
					children[i].enable(gl);
				}
			}
		}
	}

	public void add(Light a) {
		children[Light.lightCount - 1] = a;
	}

	public void setShining(boolean b) {
		this.isShining = b;
	}

	public boolean isShining() {
		return isShining;
	}

}
