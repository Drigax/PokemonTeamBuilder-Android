package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.Pokemon;

import junit.framework.Assert;

/**
 * Created by drigax on 3/27/14.
 */
public class SlotView extends LinearLayout {

    private String EMPTY_IMAGE = "com.drigax.teambuilder:drawable/slot_socket";
    private String DEFAULT_IMAGE = "com.drigax.teambuilder:drawable/error_socket";
    private String IMAGE_PREFIX = "com.drigax.teambuilder:drawable/slot_icon_";
    private String IMAGE_SUFFIX = "";

    private int slotId;
    private TextView slotName;
    private ImageView slotImage;
    private Pokemon currentPokemon;

    public SlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SlotView);
        try {
            slotId = a.getInteger(R.styleable.SlotView_slotId, -1);
        } finally {
            a.recycle();
        }
        Assert.assertTrue(slotId != -1);//check to make sure the attribute call went well

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.viewgroup_slot_view_layout, this, true);
        slotImage = (ImageView) rootView.findViewById(R.id.pokemonImage);
        slotName = (TextView) rootView.findViewById(R.id.pokemonName);

    }

    public void setPokemon(Pokemon newPoke) {
        this.currentPokemon = newPoke;
        refresh();
    }

    public void clearPokemon() {
        this.currentPokemon = null;
        refresh();
    }

    public int getSlotId() {
        return slotId;
    }

    public void refresh() {
        if (currentPokemon != null) {
            slotName.setText(currentPokemon.getName());
            slotImage.setImageResource(getPokemonImageResource(currentPokemon));
        } else {
            slotName.setText("");
            int pokemonDrawableId = getContext().getResources().getIdentifier(EMPTY_IMAGE, DEFAULT_IMAGE, getContext().getPackageName());
            slotImage.setImageResource(pokemonDrawableId);
        }

    }

    private int getPokemonImageResource(Pokemon aPokemon) {
        String pokemonImageId = IMAGE_PREFIX + currentPokemon.getName().toLowerCase() + IMAGE_SUFFIX;
        int pokemonDrawableId = getContext().getResources().getIdentifier(pokemonImageId, DEFAULT_IMAGE, getContext().getPackageName());
        if (pokemonDrawableId == 0) {
            pokemonDrawableId = R.drawable.error_socket;
        }
        return pokemonDrawableId;
    }

}
