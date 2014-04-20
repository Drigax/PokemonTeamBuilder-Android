package com.drigax.teambuilder.view_code_behind.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.Pokemon;
import com.drigax.teambuilder.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drigax on 3/9/14.
 */
public class TeamView extends LinearLayout {

    List<SlotView> pokemonSlots;
    private Team currentTeam;

    public TeamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TeamView,
                0,
                0);
        try {
            //pokemonSlots = a.getInteger(R.styleable.TeamView_numPokemonSlots,6);
        } finally {
            a.recycle();
        }
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.viewgroup_team_view_layout, this, true);
        initializeSlotList();
    }

    public void setTeam(Team newTeam) {
        this.currentTeam = newTeam;
    }

    public void refresh() {
        List<Pokemon> teamList = currentTeam.getPokemon();
        for (int i = 0; i < pokemonSlots.size(); i++) {
            pokemonSlots.get(i).setPokemon(teamList.get(i));
        }
        for (SlotView currentSlot : pokemonSlots) {
            currentSlot.refresh();
        }
    }

    private SlotView getSlotView(int currentSlot) {
        return pokemonSlots.get(currentSlot);

    }

    private void initializeSlotList() {
        pokemonSlots = new ArrayList<SlotView>();
        pokemonSlots.add((SlotView) getRootView().findViewById(R.id.pokemonSlot0));
        pokemonSlots.add((SlotView) getRootView().findViewById(R.id.pokemonSlot1));
        pokemonSlots.add((SlotView) getRootView().findViewById(R.id.pokemonSlot2));
        pokemonSlots.add((SlotView) getRootView().findViewById(R.id.pokemonSlot3));
        pokemonSlots.add((SlotView) getRootView().findViewById(R.id.pokemonSlot4));
        pokemonSlots.add((SlotView) getRootView().findViewById(R.id.pokemonSlot5));

    }

    private void setAverage() {
        //TextView average = (TextView)getRootView().findViewById(R.id.pokemonStatAverage);
        //average.setText(currentTeam.getAverageStats());
    }

    @Override
    public boolean isInEditMode() {
        return true;

    }

}
