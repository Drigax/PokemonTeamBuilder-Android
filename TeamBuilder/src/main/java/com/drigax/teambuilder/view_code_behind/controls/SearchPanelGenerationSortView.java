package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drigax.teambuilder.R;

/**
 * Created by Drigax on 6/18/2014.
 */
public class SearchPanelGenerationSortView extends LinearLayout {



    public SearchPanelGenerationSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.viewgroup_search_panel_generation_sort_view, this, true);

    }



}
