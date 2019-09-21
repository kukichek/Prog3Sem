#pragma once
#include "stdafx.h"

#define STR_SIZE 80

class ErrorHandler {
public:
	void operator() (HWND hWnd, MyException &exception) {
		std::string errorMsg = "Error: " + exception.getErr() + "\nIn method: " + exception.getMethod();

		wchar_t *wcstring = new wchar_t[STR_SIZE]; // разобраться, шо оно делает
		size_t convertedChars = 0;
		mbstowcs_s(&convertedChars, wcstring, STR_SIZE, errorMsg.c_str(), _TRUNCATE);

		MessageBox(hWnd, wcstring, NULL, MB_OKCANCEL);
	}
};