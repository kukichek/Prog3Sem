#include "stdafx.h"
#include "Controller.h"

Controller::Controller(View *v) : view_(v) {}

void Controller::Add(const char* cstring) {
	char *pEnd;

	if (!strlen(cstring)) {
		return;
	}

	int temp = strtol(cstring, &pEnd, 10);
	if (strlen(pEnd) != 0) {
		view_->DisplayError("Invalid input");
		return;
	}

	model_.add(temp);

	Out();
}

void Controller::Remove(const char* cstring) {
	char *pEnd;

	if (!strlen(cstring)) {
		return;
	}

	int temp = strtol(cstring, &pEnd, 10);
	if (strlen(pEnd) != 0) {
		view_->DisplayError("Invalid input");
		return;
	}

	if (model_.size() == 0) {
		view_->DisplayError("Empty set");
		return;
	}

	model_.remove(temp);

	Out();
}

void Controller::Swap(Controller &c) {
	model_.swap(c.model_);

	Out();
	c.Out();
}

void Controller::Union(Controller &c) {
	model_ += c.model_;
	Out();
}

void Controller::Intersection(Controller &c) {
	model_ *= c.model_;
	Out();
}

void Controller::Difference(Controller &c) {
	model_ /= c.model_;
	Out();
}

void Controller::Out() {
	GetModel getModel;
	model_.accept(getModel);

	view_->UpdateEdit(getModel.getModelString());
}