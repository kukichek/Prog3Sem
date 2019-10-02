//2.	Написать функцию, которая вычисляет сумму элементов списка аргументов произвольной длины с разными типами элементов списка.
//Код на стороне пользователя может выглядеть так :
//
//short m = 13;
//int n1 = 17, n2 = 23;
//double x = 12.8;
//auto xSum = FSums(n1, 4.7, m, 1.5, 10, n2, ‘a’);
//
//std::cout << " xSum=" << xSum << std::endl;

#include <iostream>
#include "SumArgs.h"

using namespace std;

int main() {
	short m = 13;
	int n1 = 17, n2 = 23;
	double x = 12.8;
	//double xSum = sumArgs(n1, 4.7, 1.5, 10, n2);
	double xSum = (x);

	std::cout << "xSum = " << xSum << std::endl;

	system("pause");

	return 0;
}
