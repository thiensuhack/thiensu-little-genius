package com.pn.littlegenius.utils;

/** This is just a simple class for holding data that is used to render our custom view */
public class SlideItemData {
    private int mBackgroundColor;
    private String mText;

    public SlideItemData(int backgroundColor, String text) {
        mBackgroundColor = backgroundColor;
        mText = text;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public String getText() {
        return mText;
    }
}
