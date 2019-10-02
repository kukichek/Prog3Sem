#include <iostream>
#include <algorithm>
#include <list>
#include "funcsLambda.h"

using namespace std;

int main() {
	list<int> l{1, 3, 56, 14, 0, 7, -3, -29, 17, 1, 8, 6};

	cout << "List<int>:\n";
	outLambda(l);
	cout << endl;

	cout << "First even number in list: " << firtsEven(l) << endl;
	cout << "Amount of even numbers: " << sumEven(l) << endl;

	cout << "Sorted list:\n";
	sortLamda(l);
	outLambda(l);
	cout << endl;

	cout << "List witout negative numbers:\n";
	replaceNegatives(l);
	outLambda(l);
	cout << endl;

	system("pause");

	return 0;
}