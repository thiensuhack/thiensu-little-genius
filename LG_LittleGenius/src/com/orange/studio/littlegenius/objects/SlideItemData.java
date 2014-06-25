package com.orange.studio.littlegenius.objects;

public class SlideItemData {
    public int mImage;
    public String mText;

    public SlideItemData(int backgroundColor, String text) {
        mImage = backgroundColor;
        mText = text;
    }

    public int getBackgroundColor() {
        return mImage;
    }

    public String getText() {
        return mText;
    }
}
