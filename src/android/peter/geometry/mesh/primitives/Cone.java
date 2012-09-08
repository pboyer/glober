package android.peter.geometry.mesh.primitives;

/** 
 * 
 * @author Peter Boyer
 * 
 * creates a simple cone with center at base center point at 0,0,0
 * 
 */
public class Cone extends Mesh {

	float[] vertices; //top, bottom, circle of res vertices
	
	short[] indices;
	
	//TODO:  needs to be refactored to incorporate mesh normals
	
	public Cone(float radius, float height, int res) {
		vertices = new float[3*(res+2)]; //bottom, top, circle of res vertices
		
		indices = new short[6*(res)];
		
		vertices[0] = 0.0f;
		vertices[1] = 0.0f;
		vertices[2] = height;
		
		vertices[3] = 0.0f;
		vertices[4] = 0.0f;
		vertices[5] = 0.0f;
		
		double x;
		double y;
		
		//res vertices in total
		for (int i = 0; i < res; i++) {
			x = radius * Math.cos(2*Math.PI*i/(double) res);
			y = radius * Math.sin(2*Math.PI*i/(double) res);
			
			vertices[6+3*i] = (float) x;
			vertices[7+3*i] = (float) y;
			vertices[8+3*i] = 0.0f;
		}
		
		//add all the polygons except for the last one
		for (int i = 0; i < res; i++) {
			//System.out.println(i);
			indices[6*i]	= (short) 0;  		//top
			indices[6*i+1]	= (short) (i + 2);
			indices[6*i+2]	= (short) (i + 3);
			        
			indices[6*i+3]	= (short) 1;		//bottom
			indices[6*i+4]	= (short) (i + 3);
			indices[6*i+5]	= (short) (i + 2);
		}
		
		//close the cone
		indices[indices.length-6] = 0;
		indices[indices.length-5] = (short) (res+1);
		indices[indices.length-4] = 2;
			
		indices[indices.length-3] = 1;
		indices[indices.length-2] = 2;
		indices[indices.length-1] = (short) (res+1);
		

//			
		this.setVertices(vertices);
		this.setIndices(indices);
	}
}
