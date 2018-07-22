/**
 * Name: Sumukh Chitrashekar
 * UNCC #: 801020249
 * Email: schitras@uncc.edu
 */
package com.sumukh.algos;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private String vertexName;
	private List<Vertex> adjacentVertices;
	private Vertex previous;
	private float distance;
	private boolean isUp;

	// Constructor
	public Vertex(String name) {
		this.vertexName = name;
		adjacentVertices = new ArrayList<Vertex>();
		isUp = true;
		reset();
	}

	// Reset vertex values in case there is a change in the network topology
	public void reset() {
		distance = Float.POSITIVE_INFINITY;
		previous = null;

	}
	
	// Sets of getters and setters
	
	public boolean isUp() {
		return isUp;
	}

	public void setIsUp(boolean isUp) {
		this.isUp = isUp;
	}

	public String getVertexName() {
		return vertexName;
	}

	public void setVertexName(String vertexName) {
		this.vertexName = vertexName;
	}

	public List<Vertex> getAdjacentVertices() {
		return adjacentVertices;
	}

	public void setAdjacentVertices(List<Vertex> adjacentVertices) {
		this.adjacentVertices = adjacentVertices;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

}
