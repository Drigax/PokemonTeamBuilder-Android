package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.drigax.teambuilder.R;

import junit.framework.Assert;

/**
 * Created by Drigax on 4/27/2014.
 */
public class FontTextView extends TextView {
    private final static int HELVETICA_NEUE = 0;
    private final static int HELVETICA_NEUE_CONDENSED = 1;

    private static boolean fontsLoaded = false;

    private static Typeface helveticaNeue;
    private static Typeface helveticaNeueCondensed;

    public FontTextView(Context context){
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        if (!fontsLoaded && !isInEditMode()){
            loadFonts(context);
        }
        parseAttributes(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        if (!fontsLoaded){
            loadFonts(context);
        }
        parseAttributes(context, attrs);
    }

    private static void loadFonts(Context context){
        helveticaNeue = Typeface.createFromAsset(context.getAssets(), "HelveticaNeue.otf" );
        helveticaNeueCondensed = Typeface.createFromAsset(context.getAssets(), "HelveticaNeueCondensed.otf");
        Assert.assertTrue(helveticaNeue != null);
        Assert.assertTrue(helveticaNeueCondensed != null);
        fontsLoaded = true;
    }

    private void parseAttributes(Context context, AttributeSet attrs){
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        int typeface = values.getInt(R.styleable.FontTextView_typeface, 0);
        if(!isInEditMode()) {
            switch (typeface) {
                case HELVETICA_NEUE:
                default:
                    setTypeface(helveticaNeue);
                    break;
                case HELVETICA_NEUE_CONDENSED:
                    setTypeface(helveticaNeueCondensed);
                    break;

            }
        }
        values.recycle();
    }
}
