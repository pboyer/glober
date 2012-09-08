package android.peter.geometry.lights;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Light {

	static int lightCount = 0;
	int lightIndex = 0;

	int[] lightConstant = { GL10.GL_LIGHT0, GL10.GL_LIGHT1, GL10.GL_LIGHT2,
			GL10.GL_LIGHT3, GL10.GL_LIGHT4, GL10.GL_LIGHT5, GL10.GL_LIGHT6,
			GL10.GL_LIGHT7 };

	/* The buffers for our light values ( NEW ) */
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightSpecularBuffer;
	private FloatBuffer lightEmissiveBuffer;
	private FloatBuffer lightPositionBuffer;

	public Light() {
	}

	public void enable(GL10 gl) {
		if (lightAmbientBuffer != null)
			gl.glLightfv(lightConstant[lightIndex], GL10.GL_AMBIENT,
					lightAmbientBuffer);
		if (lightDiffuseBuffer != null)
			gl.glLightfv(lightConstant[lightIndex], GL10.GL_DIFFUSE,
					lightDiffuseBuffer);
		if (lightSpecularBuffer != null)
			gl.glLightfv(lightConstant[lightIndex], GL10.GL_SPECULAR,
					lightSpecularBuffer);
		if (lightEmissiveBuffer != null)
			gl.glLightfv(lightConstant[lightIndex], GL10.GL_EMISSION,
					lightEmissiveBuffer);
		if (lightPositionBuffer != null)
			gl.glLightfv(lightConstant[lightIndex], GL10.GL_POSITION,
					lightPositionBuffer);
		if (lightPositionBuffer != null)
			gl.glEnable(lightConstant[lightIndex]);
	}

	private Light(float[] lightAmbient, float[] lightDiffuse,
			float[] lightSpecular, float[] lightEmissive,
			float[] lightPosition, int lightIndex) {

		this.lightIndex = lightIndex;

		ByteBuffer byteBuf;

		if (lightAmbient != null) {
			byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			lightAmbientBuffer = byteBuf.asFloatBuffer();
			lightAmbientBuffer.put(lightAmbient);
			lightAmbientBuffer.position(0);
		}
		if (lightDiffuse != null) {
			byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			lightDiffuseBuffer = byteBuf.asFloatBuffer();
			lightDiffuseBuffer.put(lightDiffuse);
			lightDiffuseBuffer.position(0);
		}
		if (lightSpecular != null) {
			byteBuf = ByteBuffer.allocateDirect(lightSpecular.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			lightSpecularBuffer = byteBuf.asFloatBuffer();
			lightSpecularBuffer.put(lightDiffuse);
			lightSpecularBuffer.position(0);
		}
		if (lightEmissive != null) {
			byteBuf = ByteBuffer.allocateDirect(lightEmissive.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			lightEmissiveBuffer = byteBuf.asFloatBuffer();
			lightEmissiveBuffer.put(lightDiffuse);
			lightEmissiveBuffer.position(0);
		}
		if (lightPosition != null) {
			byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			lightPositionBuffer = byteBuf.asFloatBuffer();
			lightPositionBuffer.put(lightPosition);
			lightPositionBuffer.position(0);
		}
	}

	/*
	 * a factory method to create light objects. makes sure that no more than 8
	 * lights are created.
	 */
	public static Light CreateLight(float[] lightAmbient, float[] lightDiffuse,
			float[] lightSpecular, float[] lightEmissive, float[] lightPosition) {

		if (lightCount < 8) {
			Light l = new Light(lightAmbient, lightDiffuse, lightSpecular,
					lightEmissive, lightPosition, lightCount++);
			return l;
		} else {
			return null;
		}

	}

}
