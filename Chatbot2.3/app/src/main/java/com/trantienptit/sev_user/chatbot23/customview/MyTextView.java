package com.trantienptit.sev_user.chatbot23.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by trant on 9/28/2016.
 */
public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/Fun_Raiser.ttf");
        this.setTypeface(typeface);
    }
}
