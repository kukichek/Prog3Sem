#include "funcsLambda.h"

void outLambda(std::list<int> l) {
	for_each(l.begin(), l.end(), [](int n) { std::cout << n << ' ';  });
}

void sortLamda(std::list<int>& l) {
	l.sort([](int a, int b) { return abs(a) < abs(b); });
}

int firtsEven(std::list<int> l) {
	return *find_if(l.begin(), l.end(), [](int n) { return ((n % 2) == 0); });
}

int sumEven(std::list<int> l) {
	int sum = 0;
	for_each(l.begin(), l.end(), [&sum](int n) {if ((n % 2) == 0) { sum++; }});
	return sum;
}

void replaceNegatives(std::list<int> &l) {
	for_each(l.begin(), l.end(), [](int &n) { if (n < 0) n = 0; });
}