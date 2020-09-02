package com.cgy.mycollections.functions.ui.textdemo.linkify;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

public class NoUnderlineSpan extends UnderlineSpan {
    @Override
    public void updateDrawState(TextPaint ds) {
        if(ds!=null) {
            ds.setUnderlineText(false);
        }
    }
}