#include "QueueDinArrIterator.h"

Queue::Queue() : q_size(0), capacity(1), head_index(0), tail_index(0) {
	ptr = new std::string[capacity];
}

Queue::Queue(Queue&& x) : ptr(x.ptr), q_size(x.q_size), capacity(x.capacity), head_index(x.head_index), tail_index(x.tail_index) {
	x.ptr = nullptr;
	x.q_size = 0;
}

Queue::Queue(std::initializer_list<std::string> l)
	: q_size(l.size()), capacity(max(1, q_size)), head_index(0), tail_index(q_size - 1) {
	ptr = new std::string[capacity];
	for (int i = 0; i < q_size; i++) {
		ptr[i] = *(l.begin() + i);
	}
}

Queue::Queue(int _capacity) : q_size(0), capacity(_capacity), head_index(0), tail_index(0) {
	ptr = new std::string[capacity];
}

Queue::~Queue() {
	delete[] ptr;
}

bool Queue::isEmpty() const {
	return (q_size == 0);
}

int Queue::size() const {
	return q_size;
}

void Queue::clear() {
	q_size = 0;
	head_index = 0;
	tail_index = 0;
}

std::string Queue::front() const {
	if (isEmpty()) throw MyException(std::string("Empty queue"), std::string("Queue::front()"));
	return ptr[head_index];
}

std::string Queue::back() const {
	if (isEmpty()) throw MyException(std::string("Empty queue"), std::string("Queue::back()"));
	return ptr[tail_index];
}

typename Queue::Iterator Queue::begin() {
	return Iterator(this, 0);
}

typename Queue::Iterator Queue::end() {
	return Iterator(this, q_size);
}

typename Queue::ConstIterator Queue::begin() const {
	return ConstIterator(this, 0);
}

typename Queue::ConstIterator Queue::end() const {
	return ConstIterator(this, q_size);
}

void Queue::push(std::string new_item) {
	if (q_size == capacity) {
		capacity = round(1.7 * capacity);
		std::string* new_ptr = new std::string[capacity];
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

std::string Queue::pop() {
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

void Queue::swap(Queue &x) {
	std::swap(ptr, x.ptr);
	std::swap(q_size, x.q_size);
	std::swap(capacity, x.capacity);
	std::swap(head_index, x.head_index);
	std::swap(tail_index, x.tail_index);
}

Queue& Queue::operator= (Queue&& x) {
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

std::istream& operator>> (std::istream &in, Queue& q) {
	std::string temp_T;
	q.clear();
	while (in.peek() != '\n') {
		if (!(in >> temp_T)) {
			return in;
		}
		q.push(temp_T);
	}
	return in;
}

std::ostream& operator<< (std::ostream &out, const Queue &q) {
	for (int i = 0, k = q.head_index; i < q.q_size; ++i, ++k) {
		out << q.ptr[k % q.capacity] << ' ';
	}
	out << std::endl;
	return out;
}

Queue Queue::operator+ (const Queue& q) {
	Queue new_q(this->q_size + q.q_size);
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

Queue& Queue::operator+= (const Queue& q) {
	for (int i = 0, k = q.head_index; i < q.q_size; ++i, ++k) {
		push(q.ptr[k % q.capacity]);
	}
	return *this;
}

bool Queue::operator== (const Queue& q) const {
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

bool Queue::operator!= (const Queue &q) const {
	return !(this->operator==(q));
}

std::string Queue::getModelString() {
	std::string modelString;
	for (auto s : *this) {
		modelString += "\r\n" + s;
	}
	modelString.erase(0, 2);
	return modelString;
}

std::string& Queue::Iterator::operator* () const {
	if (isDone()) throw MyException(std::string("Out of bounds"), std::string("Queue::Iterator::operator*()"));
	return *(queue->ptr + getRealIndex());
}

typename Queue::Iterator& Queue::Iterator::operator++ () {
	index++;
	return *this;
}

typename Queue::Iterator Queue::Iterator::operator++ (int) {
	Iterator temp = *this;
	index++;
	return temp;
}

typename Queue::Iterator& Queue::Iterator::operator-- () {
	index--;
	return *this;
}

typename Queue::Iterator Queue::Iterator::operator-- (int) {
	Iterator temp = *this;
	index--;
	return temp;
}

bool Queue::Iterator::operator== (const Iterator& it) const {
	if (queue != it.queue) {
		return 0;
	}
	return (index == it.index);
}

bool Queue::Iterator::operator!= (const Iterator& it) const {
	return !(this->operator==(it));
}

bool Queue::Iterator::isDone() const {
	return (index >= queue->q_size || index < 0);
}

int Queue::Iterator::getRealIndex() const {
	return ((queue->head_index + index) % queue->capacity);
}

Queue::Iterator::Iterator(const Queue* const _q, int _index) :queue(_q), index(_index) {

}

const std::string& Queue::ConstIterator::operator* () const {
	if (isDone()) throw MyException(std::string("Out of bounds"), std::string("Queue::ConstIterator::operator*()"));
	return *(queue->ptr + getRealIndex());
}

typename Queue::ConstIterator& Queue::ConstIterator::operator++ () {
	index++;
	return *this;
}

typename Queue::ConstIterator Queue::ConstIterator::operator++ (int) {
	auto temp = *this;
	index++;
	return temp;
}

typename Queue::ConstIterator& Queue::ConstIterator::operator-- () {
	index--;
	return *this;
}

typename Queue::ConstIterator Queue::ConstIterator::operator-- (int) {
	auto temp = *this;
	index--;
	return temp;
}

bool Queue::ConstIterator::operator== (const ConstIterator& it) const {
	if (queue != it.queue) {
		return 0;
	}
	return (index == it.index);
}

bool Queue::ConstIterator::operator!= (const ConstIterator& it) const {
	return !(this->operator==(it));
}

bool Queue::ConstIterator::isDone() const {
	return (index >= queue->q_size || index < 0);
}

int Queue::ConstIterator::getRealIndex() const {
	return ((queue->head_index + index) % queue->capacity);
}

Queue::ConstIterator::ConstIterator(const Queue* const _q, int _index) : queue(_q), index(_index) {

}