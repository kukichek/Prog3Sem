#include <iostream>
#include "ToString.h"

using namespace std;

int main() {
	int n = 17;
	double x = 6.75;
	cout << ToString("; ", 3.7, 25, n, x) << endl;
	int k = 0;
	cout << ToString("_/\\_", 0, 0, k, x) << endl;

	system("pause");

	return 0;
}
