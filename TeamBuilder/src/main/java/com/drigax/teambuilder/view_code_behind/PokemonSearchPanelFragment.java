package com.drigax.teambuilder.view_code_behind;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.ElementalType;
import com.drigax.teambuilder.model.GenerationType;
import com.drigax.teambuilder.model.Pokedex;
import com.drigax.teambuilder.model.Pokemon;
import com.drigax.teambuilder.view_code_behind.controls.SearchPanelElementListView;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class PokemonSearchPanelFragment extends ListFragment implements ITeamBuilderFragment, SearchView.OnQueryTextListener, SearchPanelElementListView.ElementSelectionListener {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Pokedex currentPokedex;

    List<ElementalType> currentFilterTypes;
    List<GenerationType> currentFilterGenerations;
    String currentFilterText = "";


    private SearchPanelListener mListener;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonSearchPanelFragment() {
        currentFilterGenerations = new ArrayList<GenerationType>();
        currentFilterTypes = new ArrayList<ElementalType>();
    }


    public static PokemonSearchPanelFragment newInstance(Pokedex aPokedex) {
        PokemonSearchPanelFragment fragment = new PokemonSearchPanelFragment();
        Bundle args = new Bundle();
        Assert.assertTrue(aPokedex != null);
        fragment.currentPokedex = aPokedex;
        //put arguments if any here vvv


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //Put arguments to get from args here
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_search_panel, container, false);
        SearchView sv = (SearchView) rootView.findViewById(R.id.searchView);
        SearchPanelElementListView elv = (SearchPanelElementListView) rootView.findViewById(R.id.elementList);
        sv.setOnQueryTextListener(this);
        elv.setElementSelectionListener(this);

        return rootView;
    }

    private void updateListAdapter() {
        List<Pokemon> currentPokemon = currentPokedex.getAllPokemon();
        List<Pokemon> removePokemon = new ArrayList<Pokemon>();
        for (Pokemon aPokemon : currentPokemon) {
            for (ElementalType currentType : currentFilterTypes) {
                if (!aPokemon.getTypes().contains(currentType)) {
                    removePokemon.add(aPokemon);
                    continue;
                }
            }
            if (currentFilterGenerations.size() != 0) {
                if (!currentFilterGenerations.contains(GenerationType.getGeneration(aPokemon))) {
                    removePokemon.add(aPokemon);
                    continue;
                }
            }
            String currentName = aPokemon.getName();
            String lowercaseName = currentName.toLowerCase();
            boolean isPartofName = lowercaseName.contains(currentFilterText);
            boolean isPartofId = (String.valueOf(aPokemon.getId())).contains(currentFilterText);
            if (!isPartofName && !isPartofId) {
                removePokemon.add(aPokemon);
                continue;
            }
        }
        currentPokemon.removeAll(removePokemon);
        PokemonSearchListAdapter currentListAdapter = (PokemonSearchListAdapter) getListAdapter();
        currentListAdapter.setCurrentPokemonList(currentPokemon);
    }

    public void initializePokedex(Pokedex aPokedex) {
        this.currentPokedex = aPokedex;
        List<Pokemon> allPokemon = currentPokedex.getAllPokemon();

        setListAdapter(new PokemonSearchListAdapter(getActivity(),
                R.layout.list_item_search_panel, allPokemon));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SearchPanelListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            Pokemon selectedPoke = (Pokemon) l.getAdapter().getItem(position);
            mListener.onSearchPanelSelection(selectedPoke);

        }
    }

    public void onElementSelectionChange(List<ElementalType> elementTypes) {
        currentFilterTypes = elementTypes;
        updateListAdapter();
    }

    public boolean onQueryTextChange(String text) {
        currentFilterText = text;
        updateListAdapter();
        return true;
    }

    public boolean onQueryTextSubmit(String text) {
        currentFilterText = text;
        updateListAdapter();
        return true;
    }

    @Override
    public void updateView() {

    }

    public interface SearchPanelListener {

        public void onSearchPanelSelection(Pokemon selectedPoke);
    }

}
