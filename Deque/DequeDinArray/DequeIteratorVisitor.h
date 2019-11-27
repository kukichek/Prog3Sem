#pragma once
#include<initializer_list>
#include<iostream>
#include<cmath>
#include<string>
#include<algorithm>

template<class T>
class Iterator;

template<class T>
class ReverseIterator;

template<class T>
class Visitor;

template<class T>
class Deque {
public:
	Deque();
	Deque(Deque<T>&& x);
	Deque(std::initializer_list<T> l);
	~Deque();

	bool isEmpty() const;
	int size() const;
	void clear();

	T front() const;
	T back() const;

	void pushFront(T new_item);
	void pushBack(T new_item);
	T popFront();
	T popBack();
	void swap(Deque<T> &x);

	Deque<T>& operator= (Deque<T>&& x);
	template<class T> friend std::istream& operator>> (std::istream &in, Deque<T>& q);
	template<class T> friend std::ostream& operator<< (std::ostream &out, const Deque<T> &q);

	Deque<T> operator+ (const Deque<T>& q);
	Deque<T>& operator+= (const Deque<T>& q);

	bool operator== (const Deque<T>& q) const;
	bool operator!= (const Deque<T> &q) const;

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

	Deque(int _capacity);
};

template<class T>
Deque<T>::Deque() : q_size(0), capacity(1), head_index(0), tail_index(0) {
	ptr = new T[capacity];
}

template<class T>
Deque<T>::Deque(Deque<T>&& x) : ptr(x.ptr), q_size(x.q_size), capacity(x.capacity), head_index(x.head_index), tail_index(x.tail_index) {
	x.ptr = nullptr;
	x.q_size = 0;
}

template<class T>
Deque<T>::Deque(std::initializer_list<T> l) 
	: q_size(l.size()), capacity(std::max(1, q_size)), head_index(0), tail_index(q_size - 1) {
	ptr = new T[capacity];
	for (int i = 0; i < q_size; i++) {
		ptr[i] = *(l.begin() + i);
	}
}

template<class T>
Deque<T>::Deque(int _capacity) : q_size(0), capacity(_capacity), head_index(0), tail_index(0) {
	ptr = new T[capacity];
}

template<class T>
Deque<T>::~Deque() {
	delete[] ptr;
}

template<class T>
bool Deque<T>::isEmpty() const {
	return (q_size == 0);
}

template<class T>
int Deque<T>::size() const {
	return q_size;
}

template<class T>
void Deque<T>::clear() {
	q_size = 0;
	head_index = 0;
	tail_index = 0;
}

template<class T>
T Deque<T>::front() const {
	return ptr[head_index];
}

template<class T>
T Deque<T>::back() const {
	return ptr[tail_index];
}

template<class T>
void Deque<T>::pushFront(T new_item) {
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
		head_index--;
		if (head_index < 0) {
			head_index += capacity;
		}
	}
	ptr[head_index] = new_item;
	++q_size;
}

template<class T>
void Deque<T>::pushBack(T new_item) {
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
T Deque<T>::popFront() {
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
T Deque<T>::popBack() {
	q_size--;
	if (q_size > 0) {
		int temp_index = tail_index;
		tail_index--;
		if (tail_index < 0) {
			tail_index += capacity;
		}
		return ptr[temp_index];
	}
	return ptr[tail_index];
}

template<class T>
void Deque<T>::swap(Deque<T> &x) {
	std::swap(ptr, x.ptr);
	std::swap(q_size, x.q_size);
	std::swap(capacity, x.capacity);
	std::swap(head_index, x.head_index);
	std::swap(tail_index, x.tail_index);
}

template<class T>
Deque<T>& Deque<T>::operator= (Deque<T>&& x) {
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
std::istream& operator>> (std::istream &in, Deque<T>& q) {
	T temp_T;
	q.clear();
	while (in.peek() != '\n') {
		if (!(in >> temp_T)) {
			return in;
		}
		q.pushBack(temp_T);
	}
	return in;
}

template<class T>
std::ostream& operator<< (std::ostream &out, const Deque<T> &q) {
	for (int i = 0, k = q.head_index; i < q.q_size; ++i, ++k) {
		out << q.ptr[k % q.capacity] << ' ';
	}
	out << std::endl;
	return out;
}

template<class T>
Deque<T> Deque<T>::operator+ (const Deque<T>& q) {
	Deque<T> new_q(this->q_size + q.q_size);
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
Deque<T>& Deque<T>::operator+= (const Deque<T>& q) {
	for (int i = 0, k = q.head_index; i < q.q_size; ++i, ++k) {
		pushBack(q.ptr[k % q.capacity]);
	}
	return *this;
}

template<class T>
bool Deque<T>::operator== (const Deque<T>& q) const {
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
bool Deque<T>::operator!= (const Deque<T> &q) const {
	return !(this->operator==(q));
}

template<class T>
class Iterator {
public:
	Iterator(const Deque<T> *q);
	T first();
	bool isDone();
	void next();
	T& currentItem();
private:
	template<class U>
	friend class Reverser;
	const Deque<T> *deque;
	int index;
};

template<class T>
class ReverseIterator {
public:
	ReverseIterator(const Deque<T> *q);
	T first();
	bool isDone();
	void next();
	T& currentItem();
private:
	template<class U>
	friend class Reverser;
	const Deque<T> *deque;
	int index;
};

template<class T>
Iterator<T>* Deque<T>::createIterator() {
	return new Iterator<T>(this);
}

template<class T>
ReverseIterator<T>* Deque<T>::createReverseIterator() {
	return new ReverseIterator<T>(this);
}

template<class T>
Iterator<T>::Iterator(const Deque<T> *q) : deque(q), index(0) {}

template<class T>
T Iterator<T>::first() {
	index = 0;
	return deque->ptr[deque->head_index];
}

template<class T>
bool Iterator<T>::isDone() {
	return index >= deque->q_size;
}

template<class T>
void Iterator<T>::next() {
	index++;
}

template<class T>
T& Iterator<T>::currentItem() {
	return deque->ptr[(deque->head_index + index) % deque->capacity];
}

template<class T>
ReverseIterator<T>::ReverseIterator(const Deque<T> *q) : deque(q), index(deque->q_size - 1) {}

template<class T>
T ReverseIterator<T>::first() {
	index = deque->q_size - 1;
	return deque->ptr[deque->tail_index];
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
	int temp_real_index = deque->head_index + index;
	if (temp_real_index < 0) {
		temp_real_index += deque->q_size;
	}
	return deque->ptr[temp_real_index];
}

template<class T>
void Deque<T>::accept(Visitor<T> &visitor) {
	Iterator<T> *it = createIterator();
	while (!(it->isDone())) {
		visitor.visitDeque(*it);
		it->next();
	}
}

template<class T>
class Visitor {
public:
	virtual void visitDeque(T el) = 0;
};

template<class T>
class Printer : public Visitor<T> {
private:
	std::string spacer = ' ';
public:
	void visitDeque(T el) override;
};

template<class T>
void Printer<T>::visitDeque(T el) {
	std::cout << el << spacer;
}

