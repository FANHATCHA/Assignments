**Dijkstra:**

-Complexity: O(V log E) using priority queue

-Find shortest path from a specified node to another specified node using a priority queue (or simply breadth first search if unweighted)

-Only works for non-negative weights

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

-Can be used to detect negative-weight cycles?????????????

-IS THE FOLLOWING CODE CORRECT?
``` java
for (int k = 0; k < n; k++)
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
```
