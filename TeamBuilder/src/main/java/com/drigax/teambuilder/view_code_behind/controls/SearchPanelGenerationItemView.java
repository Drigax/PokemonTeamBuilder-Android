package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.drigax.teambuilder.R;

/**
 * Created by Drigax on 6/19/2014.
 */
public class SearchPanelGenerationItemView extends LinearLayout {
    public SearchPanelGenerationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.viewgroup_search_panel_generation_item_view, this, true);

    }


}
