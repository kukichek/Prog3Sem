#pragma once
#include <list>
#include <algorithm>
#include <iostream>

//�	������ ��������� ������;
void outLambda(std::list<int> l);

//�	���������� ��������� ������ �� ������ ��������;
void sortLamda(std::list<int> &l);

//�	������ ������� ������� �����;
int firtsEven(std::list<int> l);

//�	���������� ���������� ������ ����� � �������������� for_each;
int sumEven(std::list<int> l);

//�	������ ������� �������������� ����� �� ����.
void replaceNegatives(std::list<int> &l);