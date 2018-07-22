/**
 * Name: Sumukh Chitrashekar
 * UNCC #: 801020249
 * Email: schitras@uncc.edu
 */
package com.sumukh.algos;

import java.util.Comparator;
import com.sumukh.algos.Vertex;

// A comparator class to sort the vertices based on the vertices' names
public class VertexNameComparator implements Comparator<Vertex>{

	@Override
	public int compare(Vertex vertex1, Vertex vertex2) {
		// TODO Auto-generated method stub
		return vertex1.getVertexName().compareTo(vertex2.getVertexName());
	}
}
