#pragma once
#include<initializer_list>
#include<iostream>
#include<cmath>
#include<iterator>
#include<algorithm>
#include"MyException.h"

class Queue {
public:
	class Iterator : public std::iterator<std::bidirectional_iterator_tag, std::string> {
	public:
		std::string& operator* () const;

		Iterator& operator++ ();
		Iterator operator++ (int);
		Iterator& operator-- ();
		Iterator operator-- (int);

		bool operator== (const Iterator& it) const;
		bool operator!= (const Iterator& it) const;

		bool isDone() const;

	private:
		friend class Queue;

		const Queue* const queue;
		int index;

		int getRealIndex() const;
		Iterator(const Queue* const _q, int _index);
	};

	class ConstIterator : public std::iterator<std::bidirectional_iterator_tag, std::string> {
	public:
		const std::string& operator* () const;

		ConstIterator& operator++ ();
		ConstIterator operator++ (int);
		ConstIterator& operator-- ();
		ConstIterator operator-- (int);

		bool operator== (const ConstIterator& it) const;
		bool operator!= (const ConstIterator& it) const;
		
		bool isDone() const;

	private:
		friend class Queue;

		const Queue* const queue;
		int index;

		int getRealIndex() const;
		ConstIterator(const Queue* const _q, int _index);
	};

	Queue();
	Queue(Queue&& x);
	Queue(std::initializer_list<std::string> l);
	~Queue();

	bool isEmpty() const;
	int size() const;
	void clear();

	std::string front() const;
	std::string back() const;

	Iterator begin();
	Iterator end();
	ConstIterator begin() const;
	ConstIterator end() const;

	void push(std::string new_item);
	std::string pop();
	void swap(Queue &x);

	Queue& operator= (Queue&& x);
	friend std::istream& operator>> (std::istream &in, Queue& q);
	friend std::ostream& operator<< (std::ostream &out, const Queue &q);

	Queue operator+ (const Queue& q);
	Queue& operator+= (const Queue& q);

	bool operator== (const Queue& q) const;
	bool operator!= (const Queue &q) const;

	std::string getModelString();

private:
	std::string* ptr;
	int q_size;
	int capacity;
	int head_index;
	int tail_index;

	Queue(int _capacity);
};