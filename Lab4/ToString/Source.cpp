//1.	�������� ������� ToString, ������� ������ ����� ����������� 
//���������� ����������� � ��������� ��������(���� std::string).
//����� ������ ������������.��������, ���
//int n = 17;
//double x = 6.75;
//ToString(�; �, 25, 3.7, n, x);
//
//��� �; � � ����������� ����� ����������, 
//������� ��������� �������� �25; 3.7; 17; 6.75�;

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
