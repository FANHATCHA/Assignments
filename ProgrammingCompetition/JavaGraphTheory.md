**Dijkstra:**

-Complexity: O(V log E)
-Find shortest path from a specified node using a priority queue (or simply breadth first search if unweighted)
-Only works for non-negative weights

**Bellman-Ford:**

-Complexity: O(V × E)
-Can be used to detect negative-weight cycles

**Floyd-Warshall:**

-Complexity: O(V^3)
-Computes all-pairs shortest paths
``` java
// Initialize
for (int i = 0; i < n; i++)
  for (int j = 0; j < n; j++)
    dist[i][j] = i == j ? 0 : weight[i][j];

// Compute
for (int k = 0; k < n; k++)
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
```
