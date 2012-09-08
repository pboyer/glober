package android.peter.geometry.mesh.primitives;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

//recursive data type Group

public class MeshGroup extends Mesh {

	private ArrayList<Mesh> children = new ArrayList<Mesh>();
	
	public MeshGroup() {	
	}
	
    @Override
    public void draw(GL10 gl) {
        for (Mesh m: children) {
        	m.draw(gl);
        }
    }
    
	public void add(Mesh child) {
		children.add(child);
	}
	
	public void delete(Mesh child) {
		children.remove(child);
	}
	
	public void clear() {
		children.clear();
	}
	
	public Mesh get(int index) {
		return children.get(index);		
	}
	
	public Mesh remove(int index) {
		return children.remove(index);
	}
	
	public int size() {
		return children.size();
	}
	
	public ListIterator<Mesh> listIterator() {
		return children.listIterator();
	}
	
}
