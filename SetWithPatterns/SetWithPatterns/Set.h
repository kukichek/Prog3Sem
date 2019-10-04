#pragma once
#include <iostream>
#include <initializer_list>

class Iterator;

class Visitor;

class Set {
public:
	Set();
	Set(std::initializer_list<int> args);
	Set(Set&& other);
	Set(const Set& other);
	~Set();

	int size() const;
	void clear();
	void swap(Set& other);

	void add(const int& new_item);
	void remove(const int& item);
	bool isContaining(int item) const;

	int operator[] (int index) const;

	Set operator+ (const Set& other);
	Set& operator+= (const Set& other);

	Set operator* (const Set& other);
	Set& operator*= (const Set& other);

	Set operator/ (const Set& other);
	Set& operator/= (const Set& other);

	bool operator==(const Set& other);
	bool operator!=(const Set& other);

	Set& operator=(const Set& other);
	Set& operator=(Set&& other);

	friend std::istream& operator>>(std::istream& in, Set& set_);
	friend std::ostream& operator<< (std::ostream& out, Set& set_);

	Iterator* createIterator() const;
	void accept(Visitor &visitor);

private:
	friend class Iterator;

	int* ptr = nullptr;
	int size_s = 0;
	int capacity = 1;
};

class Iterator {
public:
	Iterator(const Set* s);
	int first();
	bool isDone();
	void next();
	int& currentItem();
private:
	const Set *set_;
	int index;
};

class Visitor {
public:
	virtual void visitSetEl(int item) = 0;
};

class GetModel : public Visitor {
public:
	void visitSetEl(int item) override;
	std::string getModelString();
private:
	std::string stringModel;
};
