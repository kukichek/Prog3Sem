#include <iostream>
#include "Set.h"

using namespace std;

int main() {
	Set s1{4, 5, 6, 7}, s2{ 1, 2, 3 }, s3{ 4, 6, 5, 7};
	s1 /= s3;
	cout << s1;

	system("pause");

	return 0;
}