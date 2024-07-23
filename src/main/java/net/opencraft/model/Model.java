package net.opencraft.model;

import java.util.function.Consumer;

public class Model {

	private float[] vertices;
	private float size;
	private Consumer<Model> loader;

	public Model(float[] vertices, float size, Consumer<Model> loader) {
		this.vertices = vertices;
		this.size = size;
		this.loader = loader;
	}
	
	public float[] getVertices() {
		return vertices;
	}
	
	public float getSize() {
		return size;
	}
	
	public float[] getSizedVertices() {
		float[] newVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			newVertices[i] = vertices[i] * size;
		}
		
		return newVertices;
	}
	
	public void load() {
		loader.accept(this);
	}
	
}
