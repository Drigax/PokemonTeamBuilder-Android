package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.ElementalType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by drigax on 4/2/14.
 */
public class SearchPanelElementListView extends LinearLayout implements View.OnClickListener {

    private static String SEARCH_PANEL_SLOT_IDENTIFIER_PREFIX = "search_panel_element_list_";
    List<ElementSelectionListener> listeners = new ArrayList<ElementSelectionListener>();
    List<ElementalType> selectedElements = new ArrayList<ElementalType>();
    HashMap<ElementalType, ElementSlotView> elementViews = new HashMap<ElementalType, ElementSlotView>();

    public SearchPanelElementListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootview = inflater.inflate(R.layout.element_list_layout, this, true);
        if (!isInEditMode()) {
            initializeElementViewMapping(rootview);
        }

    }

    private void initializeElementViewMapping(View rootView) {
        List<ElementalType> elements = ElementalType.getAllElements();
        ElementSlotView currentElementSlotView;
        for (ElementalType element : elements) {
            currentElementSlotView = getElementSlotView(element, rootView);
            currentElementSlotView.setOnClickListener(this);
            currentElementSlotView.setCurrentType(element);
            elementViews.put(element, currentElementSlotView);
        }
    }

    private ElementSlotView getElementSlotView(ElementalType element, View rootView) {
        String identifierName = SEARCH_PANEL_SLOT_IDENTIFIER_PREFIX + element.toString();
        int id = getResources().getIdentifier(identifierName, "id", getContext().getPackageName());
        ElementSlotView view = (ElementSlotView) rootView.findViewById(id);
        return view;
    }

    private List<ElementalType> getSelectedElements() {
        return selectedElements;
    }

    @Override
    public void onClick(View view) {
        ElementSlotView currentView = (ElementSlotView) view;
        toggleElementSelection(currentView.currentType);
        currentView.toggle();
        update();
    }

    private void toggleElementSelection(ElementalType element) {
        if (selectedElements.contains(element)) {
            selectedElements.remove(element);
        } else {
            selectedElements.add(element);
        }
    }

    public void setElementSelectionListener(ElementSelectionListener lst) {
        if (!listeners.contains(lst)) {
            listeners.add(lst);
        }
    }

    public void removeElementSelectionListener(ElementSelectionListener lst) {
        if (listeners.contains(lst)) {
            listeners.remove(lst);
        }
    }


    public void update() {
        updateListeners();
    }

    public void updateListeners() {
        for (ElementSelectionListener lstr : listeners) {
            lstr.onElementSelectionChange(getSelectedElements());
        }
    }

    public interface ElementSelectionListener {
        public void onElementSelectionChange(List<ElementalType> selectedTypes);
    }


}
