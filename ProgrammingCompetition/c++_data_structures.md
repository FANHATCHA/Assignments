
**Queue**
Core Method:
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

**Stack**
Core Method:
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


