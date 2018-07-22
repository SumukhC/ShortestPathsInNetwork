/**
 * Name: Sumukh Chitrashekar
 * UNCC #: 801020249
 * Email: schitras@uncc.edu
 */

package com.sumukh.algos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import com.sumukh.algos.Edge;
import com.sumukh.algos.Vertex;
import com.sumukh.algos.VertexNameComparator;
import com.sumukh.algos.NameComparator;
import com.sumukh.algos.MinHeapPriorityQueue;

public class Graph {
	
	private static Graph graph = new Graph();
	private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
	private Map<String, Map<String, Edge>> edgeMap = new HashMap<String, Map<String, Edge>>();
	private static Scanner graphInputFile;
	private static Scanner userInput;
	
	// Starting point for program
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			FileReader fileReader = new FileReader(args[0]);
			graphInputFile = new Scanner(fileReader);
			userInput = new Scanner(System.in);
			
			String fileInputLine;
			while (graphInputFile.hasNextLine()) {
				fileInputLine = graphInputFile.nextLine();
				StringTokenizer stringTokenizer = new StringTokenizer(fileInputLine);
				
				try {
					if (stringTokenizer.countTokens() != 3) {
						System.out.println("Wrong input encountered in input file. Skipping line");
						continue;
					}
					String sourceVertex = stringTokenizer.nextToken();
					String destinationVertex = stringTokenizer.nextToken();
					float pathCost = 0;
					pathCost = Float.parseFloat(stringTokenizer.nextToken());
					
					graph.addEdge(sourceVertex, destinationVertex, pathCost, true);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Issue parsing input line - " + fileInputLine);
					System.out.println("Skipping line...");
				}
			}
			
			String query;
			while (userInput.hasNextLine()) {
				query = userInput.nextLine().trim();
				if (query.equalsIgnoreCase("quit")) {
					return;
				} else {
					graph.processInputLine(query);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	// Add a new edge to the graph. If vertices don't exist, creates new vertices, and then adds the edges.
	private void addEdge(String sourceVertexName, String destinationVertexName, float pathCost, boolean isBidirectional) {
		// TODO Auto-generated method stub
		Vertex sourceVertex = getVertex(sourceVertexName);
		Vertex destinationVertex = getVertex(destinationVertexName);
		
		if (sourceVertex.getAdjacentVertices().indexOf(destinationVertex) == -1) {
			sourceVertex.getAdjacentVertices().add(destinationVertex);
		}
		
		if (edgeMap.containsKey(sourceVertex.getVertexName())) {
			edgeMap.get(sourceVertex.getVertexName()).put(destinationVertex.getVertexName(), 
					new Edge(sourceVertex, destinationVertex, pathCost));
		} else {
			Map<String, Edge> temp = new HashMap<String, Edge>();
			temp.put(destinationVertex.getVertexName(), new Edge(sourceVertex, destinationVertex, pathCost));
			edgeMap.put(sourceVertex.getVertexName(), temp);
		}
		
		if (isBidirectional) {
			if (destinationVertex.getAdjacentVertices().indexOf(sourceVertex) == -1) {
				destinationVertex.getAdjacentVertices().add(sourceVertex);
			}
			
			if (edgeMap.containsKey(destinationVertex.getVertexName())) {
				edgeMap.get(destinationVertex.getVertexName()).put(sourceVertex.getVertexName(), 
						new Edge(destinationVertex, sourceVertex, pathCost));
			} else {
				Map<String, Edge> temp = new HashMap<>();
				temp.put(sourceVertex.getVertexName(), new Edge(destinationVertex, sourceVertex, pathCost));
				edgeMap.put(destinationVertex.getVertexName(), temp);
			}
		}
	}
	
	// Returns the Vertex object - either new object, or fetch from vertexMap
	private Vertex getVertex(String sourceVertexName) {
		// TODO Auto-generated method stub
		Vertex temp = vertexMap.get(sourceVertexName);
		if (temp == null) {
			temp = new Vertex(sourceVertexName);
			vertexMap.put(sourceVertexName, temp);
		}
		return temp;
	}
	
	// Process user input line and take the appropriate action
	private void processInputLine(String query) {
		// TODO Auto-generated method stub
		StringTokenizer tokenizer = new StringTokenizer(query);
		
		switch (tokenizer.countTokens()) {
		case 1:
			switch (tokenizer.nextToken().trim()) {
			
			case "print":
				printGraph();
				break;
				
			case "reachable":
				doReachableComputation();
				break;
			
			default:
				System.out.println("Invalid query.");
				break;
			}
			
			break;
			
		case 2:
			switch (tokenizer.nextToken().trim()) {
			
			case "vertexdown":
				vertexDown(tokenizer);
				break;
			
			case "vertexup":
				vertexUp(tokenizer);
				break;
			
			default:
				System.out.println("Invalid query");
				break;
			}
			
			break;
			
		case 3:
			switch (tokenizer.nextToken().trim()) {
			
			case "path":
				findShortestPath(tokenizer);
				break;

			case "deleteedge":
				deleteEdge(tokenizer);
				break;

			case "edgedown":
				edgeDown(tokenizer);
				break;

			case "edgeup":
				edgeUp(tokenizer);
				break;

			default:
				System.out.println("Invalid query");
				break;
			}
			
			break;
		
		case 4:
			switch (tokenizer.nextToken().trim()) {
			
			case "addedge":
				addEdge(tokenizer);
				break;
			
			case "default":
				System.out.println("Invalid query");
				break;	
			}
			
			break;
		}
		
	}
	
	// Add edge
	private void addEdge(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String sourceVertexName;
		String destinationVertexName;
		float cost;
		sourceVertexName = tokenizer.nextToken().trim();
		destinationVertexName = tokenizer.nextToken().trim();
		cost = Float.parseFloat(tokenizer.nextToken().trim());
		
		if (!sourceVertexName.isEmpty() && !destinationVertexName.isEmpty()) {
			addEdge(sourceVertexName, destinationVertexName, cost, false);
		} else {
			System.out.println("There was an error processing your request - " + tokenizer.toString());
		}
	}
	
	// Make an edge's status as UP
	private void edgeUp(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String firstVertex;
		String secondVertex;
		
		firstVertex = tokenizer.nextToken().trim();
		secondVertex = tokenizer.nextToken().trim();
		
		if (edgeMap.containsKey(firstVertex) && edgeMap.get(firstVertex).containsKey(secondVertex)) {
			edgeMap.get(firstVertex).get(secondVertex).setIsUp(true);
		} else {
			System.out.println("No Edge Exists Between Pair Of Vertices " + firstVertex + " " + secondVertex);
		}
		
	}
	
	// Make an edge's status as DOWN
	private void edgeDown(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String firstVertex;
		String secondVertex;
		
		firstVertex = tokenizer.nextToken().trim();
		secondVertex = tokenizer.nextToken().trim();
		
		if (edgeMap.containsKey(firstVertex) && edgeMap.get(firstVertex).containsKey(secondVertex)) {
			edgeMap.get(firstVertex).get(secondVertex).setIsUp(false);
		} else {
			System.out.println("No Edge Exists Between Pair Of Vertices " + firstVertex + " " + secondVertex);
		}
	}

	// Completely delete an edge from the graph, permanently
	private void deleteEdge(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String firstVertex;
		String secondVertex;
		
		firstVertex = tokenizer.nextToken().trim();
		secondVertex = tokenizer.nextToken().trim();
		
		if (edgeMap.containsKey(firstVertex) && edgeMap.get(firstVertex).containsKey(secondVertex)) {
			edgeMap.get(firstVertex).remove(secondVertex);
			getVertex(firstVertex).getAdjacentVertices().remove(getVertex(secondVertex));
		} else {
			System.out.println("No Edge Exists Between Pair Of Vertices " + firstVertex + " " + secondVertex);
		}
		
	}
	
	// Find the shortest path
	private void findShortestPath(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String sourceVertex;
		String destinationVertex;
		
		sourceVertex = tokenizer.nextToken().trim();
		destinationVertex = tokenizer.nextToken().trim();
		
		if (!vertexMap.containsKey(sourceVertex) || !vertexMap.containsKey(destinationVertex)) {
			System.out.println("Either " + sourceVertex + " or " + 
					destinationVertex + " don't exist, or both don't exist");
			return;
		}
		
		performDjikstrasForShortestPath(sourceVertex, destinationVertex);
		printPathFromSourceToDestination(destinationVertex);
		
		
	}
	
	// Prints the path from the source to the destination
	private void printPathFromSourceToDestination(String destinationVertex) {
		// TODO Auto-generated method stub
		Vertex endVertex = vertexMap.get(destinationVertex);
		
		if (endVertex == null) {
			System.out.println("Destination vertex doesn't exist!");
		} else if (endVertex.getDistance() == Float.POSITIVE_INFINITY) {
			System.out.println("Path to destination doesn't exist!");
		} else {
			printPath(endVertex);
			System.out.println(" " + endVertex.getDistance());
		}
	}
	
	// A recursive call to print the vertices 
	private void printPath(Vertex endVertex) {
		// TODO Auto-generated method stub
		if (endVertex.getPrevious() != null) {
			printPath(endVertex.getPrevious());
			System.out.print(" ");
		}
		System.out.print(endVertex.getVertexName());
	}
	
	// Implementation of the Djikstra's algorithm for finding the shortest path
	private void performDjikstrasForShortestPath(String sourceVertex, String destinationVertex) {
		// TODO Auto-generated method stub
		resetValues();
		if (!vertexMap.containsKey(sourceVertex) || !vertexMap.containsKey(destinationVertex)) {
			System.out.println("Either " + sourceVertex + " or " 
					+ destinationVertex + " doesn't exist, or both don't exist!");
			return;
		}
		Vertex startVertex = vertexMap.get(sourceVertex);
		Vertex endVertex = vertexMap.get(destinationVertex);
		if (!startVertex.isUp() || !endVertex.isUp()) {
			System.out.println(startVertex.getVertexName() + " or " + endVertex.getVertexName() + " is down.");
			return;
		}
		
		MinHeapPriorityQueue priorityQueue = new MinHeapPriorityQueue();
		startVertex.setDistance(0);
		priorityQueue.addNewVertex(startVertex);
		
		while (!priorityQueue.isEmpty()) {
			Vertex minVertex = priorityQueue.getMinVertex();
			
			if (minVertex.getVertexName().equals(endVertex.getVertexName())) {
				return;
			}
			
			for (Vertex adjVertex : minVertex.getAdjacentVertices()) {
				if (adjVertex.isUp() && (getCost(minVertex, adjVertex) > 0) 
						&& (adjVertex.getDistance() > minVertex.getDistance() + getCost(minVertex, adjVertex))
						&& (doesEdgeExist(minVertex, adjVertex))
						&& edgeMap.get(minVertex.getVertexName()).get(adjVertex.getVertexName()).isUp()) {
					
					adjVertex.setDistance(minVertex.getDistance() + getCost(minVertex, adjVertex));
					adjVertex.setPrevious(minVertex);
					priorityQueue.addNewVertex(adjVertex);
				}
			}
			
		}
	}
	
	// Call this function to reset the vertices values
	private void resetValues() {
		// TODO Auto-generated method stub
		for (Vertex vertex : vertexMap.values()) {
			vertex.reset();
		}
	}
	
	// Check if edge exists between a pair of vertices
	private boolean doesEdgeExist(Vertex minVertex, Vertex adjVertex) {
		// TODO Auto-generated method stub
		if (edgeMap.containsKey(minVertex.getVertexName()) 
				&& edgeMap.get(minVertex.getVertexName()).containsKey(adjVertex.getVertexName())) {
			return true;
		}
		return false;
	}

	// Return the cost between a pair of vertices
	private float getCost(Vertex startVertex, Vertex endVertex) {
		// TODO Auto-generated method stub
		float cost = 0;
		if (edgeMap.containsKey(startVertex.getVertexName()) 
				&& edgeMap.get(startVertex.getVertexName()).containsKey(endVertex.getVertexName())) {
			cost = edgeMap.get(startVertex.getVertexName()).get(endVertex.getVertexName()).getEdgeWeight();
		} else {
			System.out.println("Edge doesn't exist between " + startVertex.getVertexName() 
							+ " " + endVertex.getVertexName());
			return Float.NEGATIVE_INFINITY;
		}
		return cost;
	}
	
	// Set the vertex status as UP
	private void vertexUp(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String vertexName;
		vertexName = tokenizer.nextToken().trim();
		
		if (vertexMap.containsKey(vertexName)) {
			getVertex(vertexName).setIsUp(true);
		} else {
			System.out.println("No Such Vertex Exists - " + vertexName);
		}
	}
	
	// Set the vertex status as DOWN
	private void vertexDown(StringTokenizer tokenizer) {
		// TODO Auto-generated method stub
		String vertexName;
		vertexName = tokenizer.nextToken().trim();
		
		if (vertexMap.containsKey(vertexName)) {
			getVertex(vertexName).setIsUp(false);
		} else {
			System.out.println("No Such Vertex Exists - " + vertexName);
		}	
	}
	
	/* The function to handle the 'reachable' query. 
	 * This function is basically a modification of BFS to get the set of reachable vertices.
	 * In this particular function, for each UP vertex, compute the reachable vertices
	 * So, for BFS, the time complexity is O(|V| + |E|)
	 * As this computes for UP vertices, assuming N vertices to be up, where N <= |V|, then time complexity is O (N (|V| + |E|))*/
	private void doReachableComputation() {
		// TODO Auto-generated method stub
		List<Vertex> vertexList = new ArrayList<Vertex>(vertexMap.values());
		vertexList.sort(new VertexNameComparator());
        System.out.println();
		for (Vertex vertex : vertexList) {
			if (vertex.isUp()) {
				System.out.println(vertex.getVertexName());
				ArrayList<Vertex> visitedVertices = new ArrayList<>();
				ArrayList<Vertex> reachableVertices = new ArrayList<>();
				visitedVertices.add(vertex);
				List<Vertex> listOfReachableVertices = getReachableVertices(vertex, visitedVertices, reachableVertices);
				listOfReachableVertices.sort(new VertexNameComparator());
				for (Vertex reachableVertex : listOfReachableVertices) {
					System.out.println("  " + reachableVertex.getVertexName());
				}
			}
		}
	}
	
	// The basic BFS function whose time complexity is O (|V| + |E|)
	private ArrayList<Vertex> getReachableVertices(Vertex vertex, ArrayList<Vertex> visitedVertices, ArrayList<Vertex> reachableVertices) {
		// TODO Auto-generated method stub
		List<Vertex> listOfAdjVertices = vertex.getAdjacentVertices();
		listOfAdjVertices.sort(new VertexNameComparator());
		Iterator<Vertex> iterator = listOfAdjVertices.iterator();
		while (iterator.hasNext()) {
			Vertex adjacentVertex = iterator.next();
			if (visitedVertices.indexOf(adjacentVertex) == -1) {
				if (adjacentVertex.isUp() 
						&& (edgeMap.containsKey(vertex.getVertexName()) && edgeMap.get(vertex.getVertexName()).containsKey(adjacentVertex.getVertexName()))
						&& edgeMap.get(vertex.getVertexName()).get(adjacentVertex.getVertexName()).isUp()) {
					visitedVertices.add(adjacentVertex);
					reachableVertices.add(adjacentVertex);
					getReachableVertices(adjacentVertex, visitedVertices, reachableVertices);
				}
			}
			
		}
		return reachableVertices;
	}
	
	// Prints the graph vertices and their costs
	private void printGraph() {
		// TODO Auto-generated method stub
		System.out.println("");
		Set<String> vertices = graph.vertexMap.keySet();
		List<String> myList = new ArrayList<String>();
		myList.addAll(vertices);
		myList.sort(new NameComparator());

		for (String vertex : myList) {
			Boolean verStatus = graph.vertexMap.get(vertex).isUp();
			if (verStatus != null && !verStatus) {
				System.out.print(vertex + "  DOWN\n");
			} else {
				System.out.print(vertex + "\n");
			}

			List<Vertex> adjList = getVertex(vertex).getAdjacentVertices();
			adjList.sort(new VertexNameComparator());
			for (Vertex innerVertex : adjList) {
				Boolean edgeStatus = null;
				Edge edge = graph.edgeMap.get(vertex).get(innerVertex.getVertexName());
				if (edge != null) {
					edgeStatus = edge.isUp();
				}
				if (edgeStatus != null && !edgeStatus) {
					System.out.println("  " + innerVertex.getVertexName() + "  " + edge.getEdgeWeight() + "  DOWN");
				} else {
					System.out.println("  " + innerVertex.getVertexName() + "  " + edge.getEdgeWeight());
				}
			}
		}
		
	}
}
