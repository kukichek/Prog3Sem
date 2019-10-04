#pragma once
#include"View.h"
#include"Set.h"

class Controller {
public:
	explicit Controller(View *v);

	void Add(const char* cstring);
	void Remove(const char* cstring);

	void Swap(Controller &c);

	void Union(Controller &c);
	void Intersection(Controller &c);
	void Difference(Controller &c);

	void Out();
private:
	View *view_;
	Set model_{}; // инициализация значением по умолчанию
};