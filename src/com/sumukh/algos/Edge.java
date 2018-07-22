/**
 * Name: Sumukh Chitrashekar
 * UNCC #: 801020249
 * Email: schitras@uncc.edu
 */
package com.sumukh.algos;

import com.sumukh.algos.Vertex;

public class Edge {
	
	private Vertex startVertex;
	private Vertex endVertex;
	private float edgeWeight;
	private boolean isUp;

	// Constructor
	public Edge(Vertex startVertex, Vertex endVertex, float edgeWeight) {
		super();
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.edgeWeight = edgeWeight;
		this.isUp = true;
	}
	
	// Sets of getters and setters for the variables
	
	public boolean isUp() {
		return isUp;
	}

	public void setIsUp(boolean isUp) {
		this.isUp = isUp;
	}

	public float getEdgeWeight() {
		return edgeWeight;
	}

	public void setEdgeWeight(float edgeWeight) {
		this.edgeWeight = edgeWeight;
	}

	public Vertex getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(Vertex startVertex) {
		this.startVertex = startVertex;
	}

	public Vertex getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(Vertex endVertex) {
		this.endVertex = endVertex;
	}
}
