#pragma once
#include <list>
#include <algorithm>
#include <iostream>

//Х	вывода элементов списка;
void outLambda(std::list<int> l);

//Х	сортировки элементов списка по модулю значений;
void sortLamda(std::list<int> &l);

//Х	поиска первого четного числа;
int firtsEven(std::list<int> l);

//Х	вычислени€ количества четных чисел с использованием for_each;
int sumEven(std::list<int> l);

//Х	замены каждого отрицательного числа на ноль.
void replaceNegatives(std::list<int> &l);