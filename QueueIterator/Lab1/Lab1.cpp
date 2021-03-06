// Lab1.cpp : Определяет точку входа для приложения.
//

#include "stdafx.h"
#include "Lab1.h"

#define MAX_LOADSTRING 100
#define STR_SIZE 80
#define ID_PUSH 1011
#define ID_POP 1012
#define ID_FRONT 1013
#define ID_BACK 1014
#define ID_CLEAR 1015
#define ID_OUT 1016
#define ID_EDITIN 1017
#define ID_EDITOUT 1018

// Глобальные переменные:
HINSTANCE hInst;                                // текущий экземпляр
WCHAR szTitle[MAX_LOADSTRING];                  // Текст строки заголовка
WCHAR szWindowClass[MAX_LOADSTRING];            // имя класса главного окна
Queue<int> queue{1, 2, 3, 4, 5, 6, 7, 8, 9};

// Отправить объявления функций, включенных в этот модуль кода:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // TODO: Разместите код здесь.

    // Инициализация глобальных строк
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_LAB1, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Выполнить инициализацию приложения:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_LAB1));

    MSG msg;

    // Цикл основного сообщения:
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int) msg.wParam;
}



//
//  ФУНКЦИЯ: MyRegisterClass()
//
//  ЦЕЛЬ: Регистрирует класс окна.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;

    wcex.cbSize = sizeof(WNDCLASSEX);

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_LAB1));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_LAB1);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

    return RegisterClassExW(&wcex);
}

BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance; // Сохранить маркер экземпляра в глобальной переменной

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
      CW_USEDEFAULT, 0, 600, 410, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	static HWND hButtonPush, hButtonPop, hButtonFront, hButtonBack, 
		hButtonClear, hButtonOut, hEditIn, hEditOut;
	std::string out, errorMsg;
    switch (message)
    {
	case WM_CREATE: {
		hButtonPush = CreateWindow(L"Button", L"Push", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			40, 30, 120, 30, hWnd, (HMENU)ID_PUSH, hInst, NULL);
		hButtonPop = CreateWindow(L"Button", L"Pop", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			40, 70, 120, 30, hWnd, (HMENU)ID_POP, hInst, NULL);

		hButtonFront = CreateWindow(L"Button", L"Front", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			40, 140, 120, 30, hWnd, (HMENU)ID_FRONT, hInst, NULL);
		hButtonBack = CreateWindow(L"Button", L"Back", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			40, 180, 120, 30, hWnd, (HMENU)ID_BACK, hInst, NULL);

		hButtonClear = CreateWindow(L"Button", L"Clear", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			40, 250, 120, 30, hWnd, (HMENU)ID_CLEAR, hInst, NULL);
		hButtonOut = CreateWindow(L"Button", L"Out", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			40, 290, 120, 30, hWnd, (HMENU)ID_OUT, hInst, NULL);

		hEditIn = CreateWindow(L"Edit", L"", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_RIGHT,
			200, 30, 120, 30, hWnd, (HMENU)ID_EDITIN, hInst, NULL);
		hEditOut = CreateWindow(L"Edit", L"", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_AUTOVSCROLL | ES_MULTILINE | ES_READONLY,
			360, 30, 180, 290, hWnd, (HMENU)ID_EDITOUT, hInst, NULL);
		break;
	}
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            switch (wmId)
            {
			case ID_PUSH:
				try {
					char *cstr = new char[STR_SIZE];
					char *pEnd;
					GetWindowTextA(hEditIn, cstr, 10);

					if (!strlen(cstr))
						throw MyException(std::string("Invalid input"), std::string("Push"));

					int temp = strtol(cstr, &pEnd, 10);
					if (strlen(pEnd) != 0)
						throw MyException(std::string("Invalid input"), std::string("Push"));

					queue.push(temp);
				}
				catch (MyException &exception) {
					ErrorHandler()(hWnd, exception);
				}
				break;
			case ID_POP:
				try {
					out = std::to_string(queue.pop());
					SetWindowTextA(hEditOut, LPSTR(out.c_str()));
				}
				catch (MyException &exception) {
					ErrorHandler()(hWnd, exception);
				}
				break;
			case ID_FRONT:
				try {
					out = std::to_string(queue.front());
					SetWindowTextA(hEditOut, LPSTR(out.c_str()));
				}
				catch (MyException &exception) {
					ErrorHandler()(hWnd, exception);
				}
				break;
			case ID_BACK:
				try {
					out = std::to_string(queue.back());
					SetWindowTextA(hEditOut, LPSTR(out.c_str()));
				}
				catch (MyException &exception) {
					ErrorHandler()(hWnd, exception);
				}
				break;
			case ID_CLEAR:
				queue.clear();
				break;
			case ID_OUT:
				for (auto item : queue) {
					out += std::to_string(item);
					out += "\r\n";
				}
				SetWindowTextA(hEditOut, LPSTR(out.c_str()));
				break;
            case IDM_ABOUT:
                DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
                break;
            case IDM_EXIT:
                DestroyWindow(hWnd);
                break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}

INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    UNREFERENCED_PARAMETER(lParam);
    switch (message)
    {
    case WM_INITDIALOG:
        return (INT_PTR)TRUE;

    case WM_COMMAND:
        if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
        {
            EndDialog(hDlg, LOWORD(wParam));
            return (INT_PTR)TRUE;
        }
        break;
    }
    return (INT_PTR)FALSE;
}
