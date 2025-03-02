package com.example.mameal.main.view;

public interface MainView {
    void showToast(String message);
    void updateBottomNavVisibility(boolean isVisible);
    void setBottomPadding(int padding);
    void navigateTo(int destination);
    void disableMenuItem(int itemId, boolean isDisabled);
    int getBottomNavHeight();

}
