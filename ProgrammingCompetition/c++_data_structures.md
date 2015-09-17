
#### Queue

Core Methods:
* push()   
* empty()  - Bool to check if Q is empty
* front()  - Gets, but does not remove front element in Q
* pop()    - Removes the front element in Q
* size()

``` c++
  	#include <queue>
	queue <int> q;
	q.push(1);
	q.push(2);
	q.push(3);
	// Print 1, 2, 3
	while(!q.empty()) {
		cout << q.front() << endl;
		q.pop();
	}
```

#### Stack

Core Methods:
* push()   
* empty()  - Bool to check if Stack is empty
* top()    - Gets, but does not remove top element in Stack
* pop()    - Removes the front element in Stack
* size()
``` c++
	#include <stack>
	stack <string> s;
	s.push("Drinkin");
	s.push("all");
	s.push("dat tea");
	// Prints, "dat tea", "all", "Drinkin"
	while(!s.empty()) {
		cout << s.top() << endl;
		s.pop();
	}
```

#### Min/Max Heap (as Priority Queue)

Core Methods:
* empty() - Bool to check if PQ is empty
* size()
* top() - Gets, but does not remove top element in PQ
* push() 
* pop() - Removes the top element in PQ
``` c++
	#include <queue>

	// Max Heap (uses std::less<type> by default)
	priority_queue<string> max_heap;

	// Min Heap, here we need to change the default setting to use: std::greater<type>
	priority_queue<int, vector<int>, greater<int> > min_heap;

	array <string, 6> strs = {"a", "e", "b", "f", "c", "d"};
	array <int, 6> vals = {77, 8, 0, 4, -7, 2};

	for (int i = 0; i < strs.size(); ++i) {
		max_heap.push(strs[i]);
		min_heap.push(vals[i]);
	}
	// Prints f,e,d,c,b,a
	while(!max_heap.empty()) {
		cout << max_heap.top() << endl;
		max_heap.pop();
	}
	// Prints -7, 0, 2, 4, 8, 77
	while (!min_heap.empty()) {
		cout << min_heap.top() << endl;
		min_heap.pop();
	}
```

