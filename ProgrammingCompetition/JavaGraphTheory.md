**Dijkstra:** Finds shortest path from a specified node to another specified node using a priority queue (or simply breadth first search if unweighted). Complexity: O(V*log(E)) using priority queue. Only works with non-negative weights. Works on both directed and undirected graphs. Can work for multiple-source or multiple-destination by modifying original graph (by adding new node and adding edges of weight 0).

``` java
// Using adjacency matrix
static int dijkstra(Integer[][] weights, int n, int start, int end) {
    int[] dist = new int[n];
    Arrays.fill(dist, INFINITY);
    dist[start] = 0;
	PriorityQueue<Node> q = new PriorityQueue<Node>();
	q.offer(new Node(start, 0));
	while (q.size() > 0) {
		Node node = q.poll();
		if (dist[node.index] < node.dist) continue; // Check to see if its stale
		if (node.index == end) return node.dist; // Reached destination
		for (int i = 0; i < n; i++)
			if (weights[node.index][i] != null) {
				int newDist = dist[node.index] + weights[node.index][i];
				if (newDist < dist[i]) {
					dist[i] = newDist;
					q.offer(new Node(i, newDist));
				}
			}
	}
    return -1; // Does not connect
}
class Node implements Comparable<Node> {
    int index, dist;
    public Node(int index, int dist) {
        this.index = index; this.dist = dist;
    }
    @Override public int compareTo(Node other) {
        return ((Integer) dist).compareTo(other.dist);
    } 
}
```

**Floyd-Warshall:** Complexity: O(V^3). Computes all-pairs shortest paths. Works with negative weights (acyclic). Works on both directed and undirected graphs. Can be modified to detect negative-weight cycles (if running the algorithm on it a second time would result in anything being changed, then there is at least one negative-weight cycle).

``` java
static void floydWarshall(Double[][] dist, int n) {
	for (int k = 0; k < n; k++)
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (dist[i][k] != null && dist[k][j] != null)
					if (dist[i][j] == null || dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
}
````

**Eularian Path/Circuit:**

* Difference:  An Euler path starts and ends at different vertices. An Euler circuit starts and ends at the same vertex.
* Task: Given an undirected or a directed graph, find a path or circuit that passes through each edge exactly once.
* Complexity: O(V + E)

Conditions for an undirected graph:
* An undirected graph has an eulerian circuit if and only if it is connected and each vertex has an even degree (degree is the number of edges that are adjacent to that vertex).
* An undirected graph has an eulerian path if and only if it is connected and all vertices except 2 have even degree. One of those 2 vertices that have an odd degree must be the start vertex, and the other one must be the end vertex.

Conditions for a directed graph:
* A directed graph has an eulerian circuit if and only if it is connected and each vertex has the same in-degree as out-degree.
* A directed graph has an eulerian path if and only if it is connected and each vertex except 2 have the same in-degree as out-degree, and one of those 2 vertices has out-degree with one greater than in-degree (this is the start vertex), and the other vertex has in-degree with one greater than out-degree (this is the end vertex).

Algorithm for undirected graphs:
* Start with an empty stack and an empty circuit (eulerian path).
  * If all vertices have even degree - choose any of them.
  * If there are exactly 2 vertices having an odd degree - choose one of them.
  * Otherwise no euler circuit or path exists.
* If current vertex has no neighbors - add it to circuit, remove the last vertex from the stack and set it as the current one. Otherwise (in case it has neighbors) - add the vertex to the stack, take any of its neighbors, remove the edge between selected neighbor and that vertex, and set that neighbor as the current vertex.
* Repeat step 2 until the current vertex has no more neighbors and the stack is empty.

Note that obtained circuit will be in reverse order - from end vertex to start vertex.

Algorithm for directed graphs:
* Start with an empty stack and an empty circuit (eulerian path).
    * If all vertices have same out-degrees as in-degrees - choose any of them.
    * If all but 2 vertices have same out-degree as in-degree, and one of those 2 vertices has out-degree with one greater than its in-degree, and the other has in-degree with one greater than its out-degree - then choose the vertex that has its out-degree with one greater than its in-degree.
    * Otherwise no euler circuit or path exists.
* If current vertex has no out-going edges (i.e. neighbors) - add it to circuit, remove the last vertex from the stack and set it as the current one. Otherwise (in case it has out-going edges, i.e. neighbors) - add the vertex to the stack, take any of its neighbors, remove the edge between that vertex and selected neighbor, and set that neighbor as the current vertex.
* Repeat step 2 until the current vertex has no more out-going edges (neighbors) and the stack is empty.

Note that obtained circuit will be in reverse order - from end vertex to start vertex.

**Prim's algorithm:** Finds the minimum spanning tree of an undirected graph. Can be modified to find the minimum spanning forest. O(n^2) using adjacency matrix, however the naive implementation is O(n^3). NOTE: If we use a priority queue we might be able to get this to O(n*log(m)).

``` java
// PARTIALLY TESTED
static long prims(int[][] dist) {
	long total = 0;
	boolean[] isConnected = new boolean[n];
	isConnected[0] = true;
	// Initialize the minimum distances from the starting node
	int[] minDist = new int[n];
	for (int i = 1; i < n; i++)
		minDist[i] = dist[0][i];
	// Greedily add shortest edge from connected part to disconnect part each time
	for (int nConnected = 1; nConnected < n; nConnected++) {
		// Find smallest distance
		int smallest = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < n; i++)
			if (!isConnected[i] && minDist[i] < smallest) {
				smallest = minDist[i];
				index = i;
			}
		// Connect to selected node
		isConnected[index] = true;
		total += smallest;
		// Update minimum distances
		for (int i = 0; i < n; i++)
			if (!isConnected[i])
				minDist[i] = Math.min(minDist[i], dist[index][i]);
	}
	return total;
}
```

**Ford-Fulkerson:** Solves maximum flow problems. Can be used to solve problems with more than one source or sink by modifying the original graph (adding a new node and edges with capacities that will not inhibit the flow). Can also be used to solve problems where nodes have a capacity (split nodes in half and add a new edge between them with the desired capacity). Can also be used to find the minimum cut by modifying a few lines (see below).

``` java
static long fordFulkerson(Node source, Node target, int nNodes) {
  long maxFlow = 0;
  boolean pathWasFound = true;
  while (pathWasFound) {
    pathWasFound = false;
    Long bottleneck = getBottleNeck(source, target, new boolean[nNodes], Long.MAX_VALUE);
    if (bottleneck != null && bottleneck > 0) {
      pathWasFound = true;
      maxFlow += bottleneck;
    }
  }
  return maxFlow;
}
static Long getBottleNeck(Node current, Node target, boolean[] visited, long currentBottleNeck) {
  if (visited[current.index]) return null;
  if (current.index == target.index) return currentBottleNeck;
  visited[current.index] = true;
  for (Edge edge : current.adj)
    if (edge.capacityLeft > 0) {
      Long bottleneck = getBottleNeck(edge.target, target, visited, Math.min(currentBottleNeck, edge.capacityLeft));
      if (bottleneck != null) {
        edge.capacityLeft -= bottleneck;
        edge.opposite.capacityLeft += bottleneck;
        return bottleneck;
      }
    }
  return null; // Dead-end
}
class Node {
  List<Edge> adj = new ArrayList<Edge>();
  int index;
  public Node(int index) { this.index = index; }
  public static void addEdge(Node initial, Node target, long capacity) {
    Edge edge = new Edge(initial, target, capacity);
    edge.originalCapacity = capacity;
    initial.adj.add(edge);
    Edge reversed = new Edge(target, initial, 0);
    target.adj.add(reversed);
    edge.opposite = reversed;
    reversed.opposite = edge;
  }
}
class Edge {
  Edge opposite = null;
  Node initial, target;
  // Residual edges should have originalCapacity=null 
  // so that this can be used to determine which nodes are the originals
  Long originalCapacity = null;
  long capacityLeft;
  public Edge(Node initial, Node target, long capacity) {
    this.initial = initial; this.target = target; this.capacityLeft = capacity;
  }
}
```

This algorithm can easily be modified to return the minimum cut:

``` java
// NOTE: This only passed 12/49 tests on Kattis (Minimum Cut problem), but failed due to a time-out
static boolean[] fordFulkerson(Node source, Node target, int n) {
    boolean pathWasFound = true;
    boolean[] minCut = new boolean[n];
    while (pathWasFound) {
      pathWasFound = false;
      Arrays.fill(minCut, false);
      Long bottleneck = getBottleNeck(source, target, Long.MAX_VALUE, minCut);
      if (bottleneck != null && bottleneck > 0) pathWasFound = true;
    }
    return minCut;
}
```

**Find Strongly Connected Components:**

The complexity of this algorithm (which we came up with) is O(m*(n+m)) to remove the "bridges". After the "bridges" have been removed, we are left with connected components (indicating those which were originally strongly connected), and we can simply find the components in O(n+m).

NOTE: It may be possible to improve our algorithm by removing all bridges at once, instead of one at a time.
NOTE: Our use of the word "bridge" is slightly different than the normal definition. We're using it to refer to any edge that when followed, you would never be able to return to the starting spot (so we are dealing with directed graphs).

``` java
// Remove all bridges
boolean removed = true;
while (removed) {
	removed = false;
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			if (arr[i][j] && isBridge(arr, j, i, new boolean[n])) {
				arr[i][j] = false;
				removed = true;
			}
}

// Find largest connected component
int max = 0;
boolean[] visited = new boolean[n];
for (int i = 0; i < n; i++)
	if (!visited[i])  {
		int size = findSize(arr, i, visited);
		max = Math.max(max, size);
	}

static int findSize(boolean[][] arr, int cur, boolean[] visited) {
	int total = 0;
	visited[cur] = true;
	for (int i = 0; i < arr.length; i++)
		if (!visited[i] && arr[cur][i]) total += findSize(arr, i, visited);
	return total + 1;
}
static boolean isBridge(boolean[][] arr, int cur, int target, boolean[] visited) {
	if (cur == target) return false;
	visited[cur] = true;
	for (int i = 0; i < arr.length; i++)
		if (!visited[i] && arr[cur][i]) {
			boolean result = isBridge(arr, i, target, visited);
			if (!result) return false;
		}
	return true;
}
```
