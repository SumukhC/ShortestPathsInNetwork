/**
 * Name: Sumukh Chitrashekar
 * UNCC #: 801020249
 * Email: schitras@uncc.edu
 */
package com.sumukh.algos;

import java.util.ArrayList;
import java.util.List;
import com.sumukh.algos.Vertex;

public class MinHeapPriorityQueue {
	private List<Vertex> minHeap;

	public MinHeapPriorityQueue() {
		minHeap = new ArrayList<Vertex>();
		minHeap.add(null);		// Adding a null to the beginning to emulate starting index at 1
	}

	// Get Min Vertex, remove the Min Vertex. Subsequent task of reconstructing heap.
	public Vertex getMinVertex() {

		if (minHeap.size() > 1) {
			Vertex verExtracted = minHeap.get(1);
			int index = minHeap.size() - 1;
			Vertex endVertex = minHeap.remove(index);
			if (index > 1) {
				minHeap.set(1, endVertex);
				reconstructHeap();
			}
			return verExtracted;
		} else
			return null;
	}

	// Reconstruct Heap after removing min element
	private void reconstructHeap() {

		Vertex rootVertex = minHeap.get(1);
		int rootIndex = 1;
		int lastIndex = minHeap.size() - 1;

		while (true) {
			int childVerIndex = rootIndex * 2;
			int rightChildIndex = (rootIndex * 2) + 1;

			if (childVerIndex > lastIndex) {
				break;
			}

			Vertex childVertex = getLeftChildVertex(rootIndex);

			if ((rightChildIndex) <= lastIndex && getRightChildVertex(rootIndex).getDistance() < childVertex.getDistance()) {
				childVerIndex = rightChildIndex;
				childVertex = getRightChildVertex(rootIndex);
			}

			if (childVertex.getDistance() > rootVertex.getDistance()) {
				break;
			}
			minHeap.set(rootIndex, childVertex);
			rootIndex = childVerIndex;
		}
		minHeap.set(rootIndex, rootVertex);
	}

	// Return left child
	private Vertex getLeftChildVertex(int verIndex) {
		return minHeap.get(2 * verIndex);
	}	

	// Return right child
	private Vertex getRightChildVertex(int verIndex) {
		return minHeap.get(2 * verIndex + 1);
	}
	// Check if min heap is empty
	public boolean isEmpty() {
		return minHeap.size() == 0;
	}

	// Return parent vertex
	private Vertex getParentVertex(int verIndex) {
		return minHeap.get(verIndex / 2);
	}

	// Add new vertex to heap
	public void addNewVertex(Vertex ver) {
		minHeap.add(null);
		int verIndex = minHeap.size() - 1;

		while ((verIndex > 1) && getParentVertex(verIndex).getDistance() > ver.getDistance()) {
			minHeap.set(verIndex, getParentVertex(verIndex));
			verIndex = verIndex / 2;
		}
		minHeap.set(verIndex, ver);
	}

	// Display Min Heap
	public void displayMinHeap() {
		List<Vertex> minHeapxList = minHeap.subList(1, minHeap.size());

		for (Vertex vertex : minHeapxList) {
			System.out.println(vertex.getDistance());
		}
	}


}
