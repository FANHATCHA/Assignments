``` c++

int dijkstra( int **weights, int n, int start, int end ) {

	int dist[n];
	for (int i = 0; i < n; i++) dist[i] = INF;
	dist[start] = 0;

	priority_queue <Node> q;
	q.push( Node( start, 0 ) );

	while ( q.size() > 0 ) {

		Node node = q.top();
		q.pop();

		if (dist[node.index] < node.dist) 
			continue;

		if (node.index == end) 
			return node.dist;

		for (int i = 0; i < n; ++i) {
			if (weights[node.index][i] != INF)  {
				int newDist = dist[node.index] + weights[node.index][i];
				if (newDist < dist[i]) {
					dist[i] = newDist;
					q.push( Node(i, newDist) );
				}
			}
		}
	}
	return -1;
}

```
