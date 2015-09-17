
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
	q.push(4);

	while(!q.empty()) {
		cout << q.front() << endl;
		q.pop();
	}

```
