#pragma once
#include<initializer_list>
#include<iostream>
#include<cmath>
#include<iterator>
#include<string>
#include"MyException.h"

template<class T>
class Queue {
public:
	class Iterator : public std::iterator<std::bidirectional_iterator_tag, T> {
	public:
		T& operator* () const;
		//T* operator-> () const;

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

	class ConstIterator : public std::iterator<std::bidirectional_iterator_tag, T> {
	public:
		const T& operator* () const;
		//const T* operator-> () const;

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
	Queue(Queue<T>&& x);
	Queue(std::initializer_list<T> l);
	~Queue();

	bool isEmpty() const;
	int size() const;
	void clear();

	T front() const;
	T back() const;

	Iterator begin();
	Iterator end();
	ConstIterator begin() const;
	ConstIterator end() const;

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

private:
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
	: q_size(l.size()), capacity(max(1, q_size)), head_index(0), tail_index(q_size - 1) {
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
typename Queue<T>::Iterator Queue<T>::begin() {
	return Iterator(this, 0);
}

template<class T>
typename Queue<T>::Iterator Queue<T>::end() {
	return Iterator(this, q_size);
}

template<class T>
typename Queue<T>::ConstIterator Queue<T>::begin() const {
	return ConstIterator(this, 0);
}

template<class T>
typename Queue<T>::ConstIterator Queue<T>::end() const {
	return ConstIterator(this, q_size);
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
T& Queue<T>::Iterator::operator* () const {
	if (isDone()) throw MyException(std::string("Out of bounds"), std::string("Queue::Iterator::operator*()"));
	return *(queue->ptr + getRealIndex());
}

//template<class T>
//T* Queue<T>::Iterator::operator-> () const {
//	return (queue->ptr + getRealIndex());
//}

template<class T>
typename Queue<T>::Iterator& Queue<T>::Iterator::operator++ () {
	index++;
	return *this;
}

template<class T>
typename Queue<T>::Iterator Queue<T>::Iterator::operator++ (int) {
	Iterator temp = *this;
	index++;
	return temp;
}

template<class T>
typename Queue<T>::Iterator& Queue<T>::Iterator::operator-- () {
	index--;
	return *this;
}

template<class T>
typename Queue<T>::Iterator Queue<T>::Iterator::operator-- (int) {
	Iterator temp = *this;
	index--;
	return temp;
}

template<class T>
bool Queue<T>::Iterator::operator== (const Iterator& it) const {
	if (queue != it.queue) {
		return 0;
	}
	return (index == it.index);
}

template<class T>
bool Queue<T>::Iterator::operator!= (const Iterator& it) const {
	return !(this->operator==(it));
}

template<class T>
bool Queue<T>::Iterator::isDone() const {
	return (index >= queue->q_size || index < 0);
}

template<class T>
int Queue<T>::Iterator::getRealIndex() const {
	return ((queue->head_index + index) % queue->capacity);
}

template<class T>
Queue<T>::Iterator::Iterator(const Queue* const _q, int _index) :queue(_q), index(_index) {

}

template<class T>
const T& Queue<T>::ConstIterator::operator* () const {
	if (isDone()) throw MyException(std::string("Out of bounds"), std::string("Queue::ConstIterator::operator*()"));
	return *(queue->ptr + getRealIndex());
}

//template<class T>
//const T* Queue<T>::ConstIterator::operator-> () const {
//	return (queue->ptr + getRealIndex());
//}

template<class T>
typename Queue<T>::ConstIterator& Queue<T>::ConstIterator::operator++ () {
	index++;
	return *this;
}

template<class T>
typename Queue<T>::ConstIterator Queue<T>::ConstIterator::operator++ (int) {
	auto temp = *this;
	index++;
	return temp;
}

template<class T>
typename Queue<T>::ConstIterator& Queue<T>::ConstIterator::operator-- () {
	index--;
	return *this;
}

template<class T>
typename Queue<T>::ConstIterator Queue<T>::ConstIterator::operator-- (int) {
	auto temp = *this;
	index--;
	return temp;
}

template<class T>
bool Queue<T>::ConstIterator::operator== (const ConstIterator& it) const {
	if (queue != it.queue) {
		return 0;
	}
	return (index == it.index);
}

template<class T>
bool Queue<T>::ConstIterator::operator!= (const ConstIterator& it) const {
	return !(this->operator==(it));
}

template<class T>
bool Queue<T>::ConstIterator::isDone() const {
	return (index >= queue->q_size || index < 0);
}

template<class T>
int Queue<T>::ConstIterator::getRealIndex() const {
	return ((queue->head_index + index) % queue->capacity);
}

template<class T>
Queue<T>::ConstIterator::ConstIterator(const Queue* const _q, int _index) : queue(_q), index(_index) {

}