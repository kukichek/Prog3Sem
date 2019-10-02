#include <iostream>
#include "DequeIteratorVisitor.h"

using namespace std;

int main() {
	Deque<int> deq{1, 2, 3, 4};
	Iterator<int> *it = deq.createIterator();
	while (!(it->isDone())) {
		cout << it->currentItem() << ' ';
		it->next();
	}
	cout << endl;
	deq.pushFront(0);
	deq.popBack();
	deq.popBack();
	deq.pushFront(-1);
	deq.pushFront(-2);
	deq.pushFront(-3);
	deq.pushFront(-4);

	Iterator<int> *it2 = deq.createIterator();
	while (!(it2->isDone())) {
		cout << it2->currentItem() << ' ';
		it2->next();
	}
	cout << endl;

	system("pause");

	return 0;
}