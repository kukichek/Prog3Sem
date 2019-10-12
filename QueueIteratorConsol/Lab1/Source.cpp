#include<iostream>
#include"QueueDinArrIterator.h"
#include"MyException.h"

using namespace std;

int main() {
	//Queue<int> a{ 1, 2, 3 };
	//Queue<int> b(std::move(a));
	//a.isEmpty() ? cout << "Move constructor has executed\n" : cout << "Move constructor hasn't executed\n";

	//Queue<string> b{"sigareta", "shlang", "karas'"};
	//cout << b;
	//cin >> b;
	//cout << "Size of entered queue is " << b.size() << endl;
	//cout << "First item of queue is \"" << b.front() << "\"\n";
	//cout << "Last item of queue is \"" << b.back() << "\"\n";
	//cout << "Queue: " << b << endl;
	
	//Queue<char> q;
	//q.push('t');
	//q.push('s');
	//q.push('a');
	//q.push('r');
	//q.push('\'');
	//for (auto it : q) {
	//	cout << it;
	//}
	//cout << endl;

	//Queue<int> a{1, 2, 3};
	//a.clear();
	//try {
	//	cout << a.front();
	//}
	//catch (MyException &exception) {
	//	cout << "Error: " << exception.getErr() << "\nIn method: " << exception.getMethod() << endl;
	//}

	//Queue<char> a{ 'a', 'b', 'c' };
	//Queue<char> b{ 'a', 'b', 'c' };
	//a == b ? cout << "Queues are equal\n" : cout << "Queues are not equal\n";
	//cout << a.pop() << ' ' << a.pop() << endl;
	//cout << a;
	//a != b ? cout << "Queues are not equal\n" : cout << "Queues are equal\n";
	//a.swap(b);
	//cout << a;

	//Queue<int> a{ 1, 3, 5 };
	//Queue<int> b{ 2, 4, 6 };
	//a = move(b);
	//try {
	//	b.pop();
	//}
	//catch (MyException &exception) {
	//	cout << "Error: " << exception.getErr() << "\nIn method: " << exception.getMethod() << endl;
	//}

	Queue<char> a;
	Queue<char> b{'s', 'o'};
	Queue<char> c{'b', 'a'};
	Queue<char> d{'k', 'a'};
	a = b + c;
	a += d;
	for (auto it : a) {
		cout << it;
	}
	cout << endl;

	system("pause");

	return 0;
}