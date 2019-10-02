//1.	Ќаписать функцию ToString, котора€ список своих разнотипных 
//аргументов преобразует в строковое значение(типа std::string).
//ƒлина списка произвольна€.Ќапример, дл€
//int n = 17;
//double x = 6.75;
//ToString(У; Ф, 25, 3.7, n, x);
//
//где У; Ф Ц разделитель между элементами, 
//получим строковое значение У25; 3.7; 17; 6.75Ф;

#include <iostream>
#include "ToString.h"

using namespace std;

int main() {
	int n = 17;
	double x = 6.75;
	cout << ToString("; ", 3.7, 25/*, n, x*/) << endl;

	system("pause");

	return 0;
}
