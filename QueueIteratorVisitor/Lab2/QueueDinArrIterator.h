#pragma once
#include<initializer_list>
#include<iostream>
#include<cmath>
#include<iterator>
#include<string>
#include<algorithm>
#include"MyException.h"

template<class T>
class Iterator;

template<class T>
class ReverseIterator;

template<class T>
class Visitor;

template<class T>
class Queue {
public:
	Queue();
	Queue(Queue<T>&& x);
	Queue(std::initializer_list<T> l);
	~Queue();

	bool isEmpty() const;
	int size() const;
	void clear();

	T front() const;
	T back() const;

	void push(T new_item);
	T pop();
	void swap(Queue<T> &x);

	Queue<T>& operator= (Queue<T>&& x);
	template<class T> friend std::istream& operator>> (std::istream &in, Queue<T>& q);
	template<class T> friend std::ostream& operator<< (std::ostream &out, const Queue<T> &q);

	Queue<T> operator+ (const Queue<T>& q);
	Queue<T>& operator+= (const Queue<T>& q);

	bool operator== (const Queue<T>& q) const;
	bool operator!= (const Queue<T> &q) const;

	Iterator<T>* createIterator();
	ReverseIterator<T>* createReverseIterator();
	void accept(Visitor<T> &visitor);

private:
	template<class T>
	friend class Iterator;
	template<class T>
	friend class ReverseIterator;

	T* ptr;
	int q_size;
	int capacity;
	int head_index;
	int tail_index;

	Queue(int _capacity);
};

template<class T>
Queue<T>::Queue() : q_size(0), capacity(1), head_index(0), tail_index(0) {
	ptr = new T[capacity];
}

template<class T>
Queue<T>::Queue(Queue<T>&& x) : ptr(x.ptr), q_size(x.q_size), capacity(x.capacity), head_index(x.head_index), tail_index(x.tail_index) {
	x.ptr = nullptr;
	x.q_size = 0;
}

template<class T>
Queue<T>::Queue(std::initializer_list<T> l) 
	: q_size(l.size()), capacity(std::max(1, q_size)), head_index(0), tail_index(q_size - 1) {
	ptr = new T[capacity];
	for (int i = 0; i < q_size; i++) {
		ptr[i] = *(l.begin() + i);
	}
}

template<class T>
Queue<T>::Queue(int _capacity) : q_size(0), capacity(_capacity), head_index(0), tail_index(0) {
	ptr = new T[capacity];
}

template<class T>
Queue<T>::~Queue() {
	delete[] ptr;
}

template<class T>
bool Queue<T>::isEmpty() const {
	return (q_size == 0);
}

template<class T>
int Queue<T>::size() const {
	return q_size;
}

template<class T>
void Queue<T>::clear() {
	q_size = 0;
	head_index = 0;
	tail_index = 0;
}

template<class T>
T Queue<T>::front() const {
	if (isEmpty()) throw MyException(std::string("Empty queue"), std::string("Queue::front()"));
	return ptr[head_index];
}

template<class T>
T Queue<T>::back() const {
	if (isEmpty()) throw MyException(std::string("Empty queue"), std::string("Queue::back()"));
	return ptr[tail_index];
}

template<class T>
void Queue<T>::push(T new_item) {
	if (q_size == capacity) {
		capacity = round(1.7 * capacity);
		T* new_ptr = new T[capacity];
		for (int i = 0, k = head_index; i < q_size; ++i, ++k) {
			new_ptr[i] = ptr[k % q_size];
		}
		head_index = 0;
		tail_index = q_size - 1;
		delete[] ptr;
		ptr = new_ptr;
		new_ptr = nullptr;
	}
	if (q_size > 0) {
		tail_index++;
		tail_index %= capacity;
	}
	ptr[tail_index] = new_item;
	++q_size;
}

template<class T>
T Queue<T>::pop() {
	if (q_size == 0) {
		throw MyException(std::string("Removal from the empty queue"), std::string("Queue::pop()"));
	}
	q_size--;
	if (q_size > 0) {
		int temp_index = head_index;
		head_index++;
		head_index %= capacity;
		return ptr[temp_index];
	}
	return ptr[head_index];
}

template<class T>
void Queue<T>::swap(Queue<T> &x) {
	std::swap(ptr, x.ptr);
	std::swap(q_size, x.q_size);
	std::swap(capacity, x.capacity);
	std::swap(head_index, x.head_index);
	std::swap(tail_index, x.tail_index);
}

template<class T>
Queue<T>& Queue<T>::operator= (Queue<T>&& x) {
	if (*this == x) {
		return *this;
	}
	delete[] ptr;
	q_size = x.size();
	capacity = x.capacity;
	head_index = x.head_index;
	tail_index = x.tail_index;
	ptr = x.ptr;
	x.ptr = nullptr;
	x.q_size = 0;
	return *this;
}

template<class T>
std::istream& operator>> (std::istream &in, Queue<T>& q) {
	T temp_T;
	q.clear();
	while (in.peek() != '\n') {
		if (!(in >> temp_T)) {
			return in;
		}
		q.push(temp_T);
	}
	return in;
}

template<class T>
std::ostream& operator<< (std::ostream &out, const Queue<T> &q) {
	for (int i = 0, k = q.head_index; i < q.q_size; ++i, ++k) {
		out << q.ptr[k % q.capacity] << ' ';
	}
	out << std::endl;
	return out;
}

template<class T>
Queue<T> Queue<T>::operator+ (const Queue<T>& q) {
	Queue<T> new_q(this->q_size + q.q_size);
	int i = 0;
	for (int k = head_index; i < q_size; ++i, ++k) {
		new_q.ptr[i] = ptr[k % capacity];
	}
	for (int k = q.head_index; i < new_q.capacity; ++i, ++k) {
		new_q.ptr[i] = q.ptr[k % q.capacity];
	}
	new_q.q_size = new_q.capacity;
	new_q.tail_index = new_q.q_size - 1;
	return new_q;
}

template<class T>
Queue<T>& Queue<T>::operator+= (const Queue<T>& q) {
	for (int i = 0, k = q.head_index; i < q.q_size; ++i, ++k) {
		push(q.ptr[k % q.capacity]);
	}
	return *this;
}

template<class T>
bool Queue<T>::operator== (const Queue<T>& q) const {
	if (q_size != q.size()) {
		return 0;
	}
	for (int i = head_index, k = q.head_index; i < q_size; ++i, ++k) { // и тут через итераторы!!!
		if (ptr[i % capacity] != q.ptr[k % capacity]) {
			return 0;
		}
	}
	return 1;
}

template<class T>
bool Queue<T>::operator!= (const Queue<T> &q) const {
	return !(this->operator==(q));
}

template<class T>
class Iterator {
public:
	Iterator(const Queue<T> *q);
	T first();
	bool isDone();
	void next();
	T& currentItem();
private:
	template<class U>
	friend class Reverser;
	const Queue<T> *queue;
	int index;
};

template<class T>
class ReverseIterator {
public:
	ReverseIterator(const Queue<T> *q);
	T first();
	bool isDone();
	void next();
	T& currentItem();
private:
	template<class U>
	friend class Reverser;
	const Queue<T> *queue;
	int index;
};

template<class T>
Iterator<T>* Queue<T>::createIterator() {
	return new Iterator<T>(this);
}

template<class T>
ReverseIterator<T>* Queue<T>::createReverseIterator() {
	return new ReverseIterator<T>(this);
}

template<class T>
Iterator<T>::Iterator(const Queue<T> *q) : queue(q), index(0) {}

template<class T>
T Iterator<T>::first() {
	index = 0;
	return queue->ptr[queue->head_index];
}

template<class T>
bool Iterator<T>::isDone() {
	return index >= queue->q_size;
}

template<class T>
void Iterator<T>::next() {
	index++;
}

template<class T>
T& Iterator<T>::currentItem() {
	return queue->ptr[(queue->head_index + index) % queue->capacity];
}

template<class T>
ReverseIterator<T>::ReverseIterator(const Queue<T> *q) : queue(q), index(queue->q_size - 1) {}

template<class T>
T ReverseIterator<T>::first() {
	index = queue->q_size - 1;
	return queue->ptr[queue->tail_index];
}

template<class T>
bool ReverseIterator<T>::isDone() {
	return index < 0;
}

template<class T>
void ReverseIterator<T>::next() {
	index--;
}

template<class T>
T& ReverseIterator<T>::currentItem() {
	int temp_real_index = queue->head_index + index;
	if (temp_real_index < 0) {
		temp_real_index += queue->q_size;
	}
	return queue->ptr[temp_real_index];
}

template<class T>
void Queue<T>::accept(Visitor<T> &visitor) {
	visitor.visitQueue(*this);
}

template<class T>
class Visitor {
public:
	virtual void visitQueue(Queue<T> &queue) = 0;
};

template<class T>
class Printer : public Visitor<T> {
public:
	void visitQueue(Queue<T> &queue) override;
};

template<class T>
void Printer<T>::visitQueue(Queue<T> &queue) {
	Iterator<T> *it = queue.createIterator();
	while (!(it->isDone())) {
		std::cout << it->currentItem() << ' ';
		it->next();
	}
	std::cout << std::endl;
}

template<class T>
class Reverser : public Visitor<T> {
public:
	void visitQueue(Queue<T> &queue) override;
};

template<class T>
void Reverser<T>::visitQueue(Queue<T> &queue) {
	Iterator<T> *it = queue.createIterator();
	ReverseIterator<T> *rIt = queue.createReverseIterator();
	while (it->index < rIt->index) {
		std::swap(it->currentItem(), rIt->currentItem());
		it->next();
		rIt->next();
	}
}

