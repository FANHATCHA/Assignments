**Dijkstra:**

-Finds shortest path from a specified node to another specified node using a priority queue (or simply breadth first search if unweighted)

-Complexity: O(V log E) using priority queue

-Only works with non-negative weights. Works on both directed and undirected graphs.

-Can work for multiple-source or multiple-destination by modifying original graph (by adding new node and adding edges of weight 0)

``` java
// UNTESTED
static int dijkstra(Integer[][] weights, int n, int start, int end) {

    int[] dist = new int[n];
    Arrays.fill(dist, INFINITY);
    dist[start] = 0;

	PriorityQueue<Node> q = new PriorityQueue<Node>();
	q.offer(new Node(start, 0));

	while (q.size() > 0) {

		Node node = q.poll();

		// Check to see if its stale
		if (dist[node.index] < node.dist)
			continue;

		// Reached destination
		if (node.index == end)
			return node.dist;

        // Check neighbours
		for (int i = 0; i < n; i++) {
			if (factors[node.index][i] != null) {
				double newDist = dist[node.index] + weights[node.index][i];
				if (newDist < dist[i]) {
					dist[i] = newDist;
					q.offer(new Node(i, newDist));
				}

			}
		}

	}

    // Does not connect
	return -1;

}

class Node implements Comparable<Node> {

    int index;
    int dist;

    public Node(int index, int dist) {
        this.index = index;
        this.dist = dist;
    }

    @Override public int compareTo(Node other) {
        return ((Integer) dist).compareTo(other.dist);
    } 

}

```

**Bellman-Ford:**

-Complexity: O(V × E)

-Works on negative weights

-Can be used to detect negative-weight cycles

**Floyd-Warshall:**

-Complexity: O(V^3)

-Computes all-pairs shortest paths

-Works with negative weights (acyclic). Works on both directed and undirected graphs.

-Can be modified to detect negative-weight cycles (if running the algorithm on it a second time would result in anything being changed, then there is at least one negative-weight cycle)

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

**Bridge finder:**

Get alg from algo.is

**Eularian Path/Cycle:**


**Prim's algorithm:**

Minimum spanning tree

**Ford-Fulkerson:**

-Solves maximum flow problems.

-Can be used to solve problems with more than one source or sink by modifying the original graph (adding a new node and edges with capacities that will not inhibit the flow). Can also be used to solve problems where nodes have a capacity (split nodes in half and add a new edge between them with the desired capacity).


