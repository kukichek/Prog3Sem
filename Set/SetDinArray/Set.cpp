#include "Set.h"

Set::Set() {
	ptr = new int[capacity];
}

Set::Set(std::initializer_list<int> l)
	: size_s(0), capacity(std::max(1, size_s)) {
		ptr = new int [capacity];
		for (int i = 0; i < l.size(); i++) {
			add(*(l.begin() + i));
		}
		std::sort(ptr, ptr + size_s);
}

Set::Set(Set&& other) : capacity(other.capacity), size_s(other.size_s), ptr(other.ptr) {
	other.ptr = nullptr;
	other.size_s = 0;
}

Set::Set(const Set& other) : capacity(other.capacity), size_s(other.size_s) {
	ptr = new int[capacity];

	for (int i = 0; i < size_s; ++i) {
		ptr[i] = other[i];
	}
}

Set::~Set() {
	delete[] ptr;
}

int Set::size() const {
	return size_s;
}

void Set::clear() {
	size_s = 0;
}

void Set::swap(Set& other) {
	std::swap(capacity, other.capacity);
	std::swap(size_s, other.size_s);
	std::swap(ptr, other.ptr);
}

void Set::add(const int& new_item) {
	if (isContaining(new_item)) {
		return;
	}

	if (size_s == capacity) {
		capacity = round(1.7 * capacity);
		int* new_ptr = new int[capacity];

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

void Set::remove(const int& item) {
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

bool Set::isContaining(int item) const {
	Iterator *it = createIterator();
	while (!(it->isDone())) {
		if (it->currentItem() == item) {
			return 1;
		}
		it->next();
	}
	return 0;
}

int Set::operator[] (int index) const {
	return ptr[index];
}

Set Set::operator+ (const Set& other) {
	Set ans(*this);
	Iterator *it = other.createIterator();

	while (!it->isDone()) {
		ans.add(it->currentItem());
		it->next();
	}

	return ans;
}

Set& Set::operator+= (const Set& other) {
	Iterator *it = other.createIterator();

	while (!it->isDone()) {
		add(it->currentItem());
		it->next();
	}

	return *this;
}

Set Set::operator* (const Set& other) {
	Set ans;
	Iterator *it = other.createIterator();

	while (!it->isDone()) {
		if (isContaining(it->currentItem())) {
			ans.add(it->currentItem());
		}
		it->next();
	}

	return ans;
}

Set& Set::operator*= (const Set& other) {
	for (int i = 0; i < size_s; ++i) {
		if (!other.isContaining(ptr[i])) {
			remove(ptr[i]);
			--i;
		}
	}

	return *this;
}

Set Set::operator/ (const Set& other) {
	Set ans(*this);
	Iterator *it = other.createIterator();

	while (!it->isDone()) {
		ans.remove(it->currentItem());
		it->next();
	}

	return ans;
}

Set& Set::operator/= (const Set& other) {
	Iterator *it = other.createIterator();

	while (!it->isDone()) {
		if (isContaining(it->currentItem())) {
			remove(it->currentItem());
		}
		it->next();
	}

	return *this;
}

bool Set::operator==(const Set& other) {
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

bool Set::operator!=(const Set& other) {
	return !(operator==(other));
}

Set& Set::operator=(const Set& other) {
	if (*this == other) {
		return *this;
	}

	size_s = other.size_s;
	capacity = other.capacity;
	int* new_ptr = new int[capacity];

	for (int i = 0; i < size_s; ++i) {
		new_ptr[i] = other[i];
	}
	
	delete[] ptr;
	ptr = new_ptr;

	return *this;
}

Set& Set::operator= (Set&& other) {
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

std::istream& operator>> (std::istream& in, Set& set_) {
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

std::ostream& operator<< (std::ostream& out, Set& set_) {
	for (int i = 0; i < set_.size_s; ++i) {
		out << set_[i] << ' ';
	}

	return out;
}

Iterator* Set::createIterator() const {
	return new Iterator(this);
}

Iterator::Iterator(const Set* s) : index(0), set_(s) {}

int Iterator::first() {
	index = 0;
	return set_->ptr[index];
}

bool Iterator::isDone() {
	return (index >= set_->size_s);
}

void Iterator::next() {
	index++;
}

int& Iterator::currentItem() {
	return set_->ptr[index];
}
