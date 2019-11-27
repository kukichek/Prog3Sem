#pragma once
#include <initializer_list>
#include <iostream>
#include <algorithm>
#include <cmath>

template<class T>
class Iterator;

template<class T>
class Set {
public:
	Set();
	Set(std::initializer_list<T>);
	Set(Set<T>&&);
	Set(const Set<T>&);
	~Set();

	int size() const;
	void clear();
	void swap(Set<T>&);

	void add(const T&);
	void remove(const T&);
	bool isContaining(T) const;

	T operator[] (int) const;

	Set operator+ (const Set<T>&);
	Set& operator+= (const Set<T>&);

	Set operator* (const Set<T>&);
	Set& operator*= (const Set<T>&);

	Set operator/ (const Set<T>&);
	Set& operator/= (const Set<T>&);

	bool operator==(const Set<T>&);
	bool operator!=(const Set<T>&);

	Set& operator=(const Set<T>&);
	Set& operator=(Set<T>&&);

	template<class T>
	friend std::istream& operator>>(std::istream&, Set<T>&);
	template<class T>
	friend std::ostream& operator<< (std::ostream&, Set<T>&);

	Iterator<T>* createIterator() const;

private:
	friend class Iterator<T>;

	T* ptr = nullptr;
	int size_s = 0;
	int capacity = 1;
};

template<class T>
class Iterator {
public:
	Iterator(const Set<T>* s);
	T first();
	bool isDone();
	void next();
	T& currentItem();
private:
	const Set<T> *set_;
	int index;
};

template<class T>
Set<T>::Set() {
	ptr = new T[capacity];
}

template<class T>
Set<T>::Set(std::initializer_list<T> l)
	: size_s(0), capacity(std::max(1, size_s)) {
	ptr = new T[capacity];
	for (int i = 0; i < l.size(); i++) {
		add(*(l.begin() + i));
	}
	std::sort(ptr, ptr + size_s);
}

template<class T>
Set<T>::Set(Set<T>&& other) : capacity(other.capacity), size_s(other.size_s), ptr(other.ptr) {
	other.ptr = nullptr;
	other.size_s = 0;
}

template<class T>
Set<T>::Set(const Set<T>& other) : capacity(other.capacity), size_s(other.size_s) {
	ptr = new T[capacity];

	for (int i = 0; i < size_s; ++i) {
		ptr[i] = other[i];
	}
}

template<class T>
Set<T>::~Set() {
	delete[] ptr;
}

template<class T>
int Set<T>::size() const {
	return size_s;
}

template<class T>
void Set<T>::clear() {
	size_s = 0;
}

template<class T>
void Set<T>::swap(Set<T>& other) {
	std::swap(capacity, other.capacity);
	std::swap(size_s, other.size_s);
	std::swap(ptr, other.ptr);
}

template<class T>
void Set<T>::add(const T& new_item) {
	if (isContaining(new_item)) {
		return;
	}

	if (size_s == capacity) {
		capacity = round(1.7 * capacity);
		T* new_ptr = new T[capacity];

		int i = 0;
		for (; i < size_s; i++) {
			if (ptr[i] > new_item) break;
			new_ptr[i] = ptr[i];
		}

		new_ptr[i] = new_item;

		for (; i < size_s; ++i) {
			new_ptr[i + 1] = ptr[i];
		}

		delete[] ptr;
		std::swap(ptr, new_ptr);
	}
	else {
		ptr[size_s] = new_item;
		std::sort(ptr, ptr + size_s + 1);
	}

	size_s++;
}

template<class T>
void Set<T>::remove(const T& item) {
	if (size_s == 0) {
		return;
	}

	if (!isContaining(item)) {
		return;
	}

	size_s--;
	if (size_s == 0) {
		size_s = 0;
		return;
	}

	int i = 0;
	while (ptr[i] != item) {
		i++;
	}

	for (; i < size_s; ++i) {
		ptr[i] = ptr[i + 1];
	}
}

template<class T>
bool Set<T>::isContaining(T item) const {
	Iterator<T> *it = createIterator();
	while (!(it->isDone())) {
		if (it->currentItem() == item) {
			return 1;
		}
		it->next();
	}
	return 0;
}

template<class T>
T Set<T>::operator[] (int index) const {
	return ptr[index];
}

template<class T>
Set<T> Set<T>::operator+ (const Set<T>& other) {
	Set<T> ans(*this);
	Iterator<T> *it = other.createIterator();

	while (!it->isDone()) {
		ans.add(it->currentItem());
		it->next();
	}

	return ans;
}

template<class T>
Set<T>& Set<T>::operator+= (const Set<T>& other) {
	Iterator<T> *it = other.createIterator();

	while (!it->isDone()) {
		add(it->currentItem());
		it->next();
	}

	return *this;
}

template<class T>
Set<T> Set<T>::operator* (const Set<T>& other) {
	Set<T> ans;
	Iterator<T> *it = other.createIterator();

	while (!it->isDone()) {
		if (isContaining(it->currentItem())) {
			ans.add(it->currentItem());
		}
		it->next();
	}

	return ans;
}

template<class T>
Set<T>& Set<T>::operator*= (const Set<T>& other) {
	for (int i = 0; i < size_s; ++i) {
		if (!other.isContaining(ptr[i])) {
			remove(ptr[i]);
			--i;
		}
	}

	return *this;
}

template<class T>
Set<T> Set<T>::operator/ (const Set<T>& other) {
	Set ans(*this);
	Iterator *it = other.createIterator();

	while (!it->isDone()) {
		ans.remove(it->currentItem());
		it->next();
	}

	return ans;
}

template<class T>
Set<T>& Set<T>::operator/= (const Set<T>& other) {
	Iterator<T> *it = other.createIterator();

	while (!it->isDone()) {
		if (isContaining(it->currentItem())) {
			remove(it->currentItem());
		}
		it->next();
	}

	return *this;
}

template<class T>
bool Set<T>::operator==(const Set<T>& other) {
	if (size_s != other.size_s) {
		return 0;
	}

	for (int i = 0; i < size_s; ++i) {
		if (ptr[i] != other.ptr[i]) {
			return 0;
		}
	}

	return 1;
}

template<class T>
bool Set<T>::operator!=(const Set<T>& other) {
	return !(operator==(other));
}

template<class T>
Set<T>& Set<T>::operator=(const Set<T>& other) {
	if (*this == other) {
		return *this;
	}

	size_s = other.size_s;
	capacity = other.capacity;
	T* new_ptr = new T[capacity];

	for (int i = 0; i < size_s; ++i) {
		new_ptr[i] = other[i];
	}

	delete[] ptr;
	ptr = new_ptr;

	return *this;
}

template<class T>
Set<T>& Set<T>::operator= (Set<T>&& other) {
	if (*this == other) {
		return *this;
	}

	delete[] ptr;
	ptr = other.ptr;
	other.ptr = nullptr;
	capacity = other.capacity;
	size_s = other.size_s;
	other.size_s = 0;

	return *this;
}

template<class T>
std::istream& operator>> (std::istream& in, Set<T>& set_) {
	int temp;
	set_.clear();

	while (in.peek() != '\n') {
		if (!(in >> temp)) {
			return in;
		}
		set_.add(temp);
	}

	return in;
}

template<class T>
std::ostream& operator<< (std::ostream& out, Set<T>& set_) {
	for (int i = 0; i < set_.size_s; ++i) {
		out << set_[i] << ' ';
	}

	return out;
}

template<class T>
Iterator<T>* Set<T>::createIterator() const {
	return new Iterator<T>(this);
}

template<class T>
Iterator<T>::Iterator(const Set<T>* s) : index(0), set_(s) {}

template<class T>
T Iterator<T>::first() {
	index = 0;
	return set_->ptr[index];
}

template<class T>
bool Iterator<T>::isDone() {
	return (index >= set_->size_s);
}

template<class T>
void Iterator<T>::next() {
	index++;
}

template<class T>
T& Iterator<T>::currentItem() {
	return set_->ptr[index];
}
