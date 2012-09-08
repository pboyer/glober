package android.peter.geometry.mesh.primitives;

public class Cylinder extends Mesh {

	float[] vertices; //top, bottom, circle of res vertices
	
	short[] indices;
	
	public Cylinder(float radius, float height, int res) {
		vertices = new float[6*(res)]; //top, bottom, top, bottom, etc.
		
		indices = new short[12*(res-1)];
		
		double x;
		double y;
		
		//TODO:  needs to be refactored to incorporate mesh normals
		
		//res vertices in total
		for (int i = 0; i < res; i++) {
			
			x = radius*((float) Math.cos(2*Math.PI*((float) i)/((float) res)));
			y = radius*((float) Math.sin(2*Math.PI*((float) i)/((float) res)));
			
			vertices[6*i] = (float) x;
			vertices[6*i+1] = (float) y;
			vertices[6*i+2] = height;
			
			vertices[6*i+3] = (float) x;
			vertices[6*i+4] = (float) y;
			vertices[6*i+5] = 0;
		}
		
		//top one side is drawn in the wrong direction
		//bottom neither top or bottom appears to be drawn
		//sides appear to be double drawn (but only in some cases)
		
		//make the top using a triangle fan
		for (int i = 0; i < res-2; i++) {
			indices[3*i]	= 0;  		//top
			indices[3*i+1]	= (short) (2*i + 2);
			indices[3*i+2]	= (short) (2*i + 4);
		}
		
		//  6  4
		//  0  2
		
		//   2
		// 4   0
		//   6
		

		//make the bottom using a triangle fan
		int offset = 3*(res-2);
		
		for (int i = 0; i < res-2; i++) {
			indices[offset+3*i]		= 1;  		
			indices[offset+3*i+1]	= (short) (2*i + 5);
			indices[offset+3*i+2]	= (short) (2*i + 3);
		}

		//make all the side except for the last one
		offset = 6*(res-2);
		
		for (int i = 0; i < res-1; i++) {
			indices[offset+6*i]		= (short) (2 * i);  		
			indices[offset+6*i+1]	= (short) (2 * i + 1);
			indices[offset+6*i+2]	= (short) (2 * i + 2);        
			
			indices[offset+6*i+3]	= (short) (2 * i + 1);
			indices[offset+6*i+4]	= (short) (2 * i + 3);
			indices[offset+6*i+5]	= (short) (2 * i + 2);
		}
		
	
		//close the cylinder
		offset = 6*(res-2) + 6*(res-1);
		
		indices[offset]		= 0;  	
		indices[offset+1]	= (short) (2*(res-1));
		indices[offset+2]	= (short) (2*(res-1)+1);    
		
		indices[offset+3]	= 0;
		indices[offset+4]	= (short) (2*(res-1)+1);
		indices[offset+5]	= 1;
			
		this.setVertices(vertices);
		this.setIndices(indices);
		
	}

}