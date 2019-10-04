#pragma once

class View{
public:
	void UpdateEdit(const std::string &s);

	void DisplayError(const std::string &s);
	void SetHEditOut(HWND hEditOut);
	void SetHErrorOutWnd(HWND hWnd);
private:
	std::string curEditState;
	HWND hEditOut_;
	HWND hErrorOutWnd_;
};