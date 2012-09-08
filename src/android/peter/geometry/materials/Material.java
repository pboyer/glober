package android.peter.geometry.materials;

public class Material {

	private float[] diffuse;
	private float[] ambient;
	private float[] specular;
	private float[] emissive;
	private float[] shininess;
	private String textureURI;

	public Material() {
		diffuse = new float[] { 0f, 0f, 0f };
	}

	public String getTextureURI() {
		return textureURI;
	}

	public void setTextureURI(String textureURI) {
		this.textureURI = textureURI;
	}

	public float[] getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(float[] diffuse) {
		this.diffuse = diffuse;
	}

	public float[] getAmbient() {
		return ambient;
	}

	public void setAmbient(float[] ambient) {
		this.ambient = ambient;
	}

	public float[] getSpecular() {
		return specular;
	}

	public void setSpecular(float[] specular) {
		this.specular = specular;
	}

	public float[] getEmissive() {
		return emissive;
	}

	public void setEmissive(float[] emissive) {
		this.emissive = emissive;
	}

	public float[] getShininess() {
		return shininess;
	}

	public void setShininess(float[] shininess) {
		this.shininess = shininess;
	}

}
