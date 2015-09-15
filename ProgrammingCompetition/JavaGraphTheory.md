**Dijkstra:**

-Complexity: O(V log E)
-Find shortest path from a specified node to another specified node using a priority queue (or simply breadth first search if unweighted)
-Only works for non-negative weights
-Can work for multiple-source or multiple-destination by modifying original graph (by adding new node and adding edges of weight 0)

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
