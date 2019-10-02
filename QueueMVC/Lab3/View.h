#pragma once

class View{
public:
	void Push(const std::string &s);
	void Pop();

	void BoundaryElement(const std::string &s);
	void UpdateEdit(const std::string &s);

	void Clear();

	void DisplayError(const std::string &s);
	void SetHEditOut(HWND hEditOut);
	void SetHErrorOutWnd(HWND hWnd);
private:
	std::string curEditState;
	HWND hEditOut_;
	HWND hErrorOutWnd_;
};