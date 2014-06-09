package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.ElementalType;

/**
 * Created by drigax on 4/11/14.
 */
public class ElementSlotView extends LinearLayout {

    private static String DEFAULT_ELEMENT_IMAGE = "com.drigax.teambuilder:drawable/search_panel_element_slot_icon_error";
    private static String IMAGE_PREFIX = "com.drigax.teambuilder:drawable/search_panel_element_slot_icon_";
    private static String IMAGE_ENABLED_SUFFIX = "_enabled";
    private static String IMAGE_DISABLED_SUFFIX = "";
    ElementalType currentType;
    boolean isEnabled;
    ImageView slotImage;
    TextView slotName;

    public ElementSlotView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.viewgroup_search_panel_element_slot_view, this, true);
        slotImage = (ImageView) rootView.findViewById(R.id.search_panel_element_slot_image);
        slotName = (TextView) rootView.findViewById(R.id.search_panel_element_slot_name);
    }

    public void setCurrentType(ElementalType type) {
        currentType = type;
        update();
    }

    public void toggle() {
        isEnabled = !isEnabled;
        update();
    }

    private int getElementSlotImageId() {
        String elementImageId = DEFAULT_ELEMENT_IMAGE;
        if (currentType != null) {
            elementImageId = IMAGE_PREFIX + currentType.toString();
            if (this.isEnabled) {
                elementImageId += IMAGE_ENABLED_SUFFIX;
            } else {
                elementImageId += IMAGE_DISABLED_SUFFIX;
            }
        }

        int id = getContext().getResources().getIdentifier(elementImageId, DEFAULT_ELEMENT_IMAGE, getContext().getPackageName());
        if (id == 0) {
            if(this.isEnabled){
                id = R.drawable.search_panel_element_slot_icon_error_enabled;
            }else {
                id = R.drawable.search_panel_element_slot_icon_error;
            }
        }
        return id;
    }

    private void update() {
        slotImage.setImageResource(getElementSlotImageId());
        slotName.setText(properCapitalization(currentType.toString()));
    }

    private String properCapitalization(String string){
        if (string != null){
            String firstLetterUppercase = "";
            String firstLetter = "";
            firstLetter += string.charAt(0);
            firstLetterUppercase += Character.toUpperCase(firstLetter.charAt(0));
            return string.replaceFirst(firstLetter, firstLetterUppercase);
        }
        return null;
    }
}
