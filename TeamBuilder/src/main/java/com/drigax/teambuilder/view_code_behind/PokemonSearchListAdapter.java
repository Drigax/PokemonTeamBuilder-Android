package com.drigax.teambuilder.view_code_behind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.Pokemon;

import java.util.List;

/**
 * Created by drigax on 2/20/14.
 */
public class PokemonSearchListAdapter extends ArrayAdapter {

    private static int MAX_TEAM_SIZE = 6;

    private static int ERROR_ICON = R.drawable.error_icon;
    private static String DRAWABLE_URI_PREFIX = "com.drigax.teambuilder:drawable/search_icon_";
    private static String ERROR_ICON_URI = "com.drigax.teambuilder:drawable/error_icon";

    List<Pokemon> currentPokemonList;
    int resourceId;
    LayoutInflater inflater;

    public PokemonSearchListAdapter(Context ctx, int _resourceId, List<Pokemon> objects) {
        super(ctx, _resourceId, objects);
        resourceId = _resourceId;
        inflater = LayoutInflater.from(ctx);
        setNotifyOnChange(false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = (LinearLayout) inflater.inflate(resourceId, null);
        Pokemon currentPoke = (Pokemon) getItem(position);

        TextView pokeName = (TextView) convertView.findViewById(R.id.searchListAdapterName);
        ImageView pokeIcon = (ImageView) convertView.findViewById(R.id.searchListAdapterIcon);
        TextView pokeId = (TextView) convertView.findViewById(R.id.searchListAdapterId);

        if (currentPoke != null) {
            pokeName.setText(currentPoke.getName());
            pokeIcon.setImageResource(getIconResource(currentPoke));
            pokeId.setText(formatIdString(currentPoke.getId()));
        } else {
            pokeName.setText("error");
            pokeIcon.setImageResource(ERROR_ICON);
            pokeId.setText("#404 not found");
        }

        return convertView;
    }

    public void setCurrentPokemonList(List<Pokemon> currentPokemonList) {
        super.clear();
        super.addAll(currentPokemonList);
        super.notifyDataSetChanged();
    }

    private int getIconResource(Pokemon currentPoke) {
        String id = DRAWABLE_URI_PREFIX + currentPoke.getName();
        id = id.toLowerCase();
        int pokemonDrawableId = getContext().getResources().getIdentifier(id, ERROR_ICON_URI, getContext().getPackageName());
        if (pokemonDrawableId == 0) {
            pokemonDrawableId = getContext().getResources().getIdentifier(ERROR_ICON_URI, ERROR_ICON_URI, getContext().getPackageName());
        }
        return pokemonDrawableId;
    }

    private String formatIdString(int id) {
        String baseString = "";
        if (id >= 100) {
            baseString += "#";
        } else if (id >= 10) {
            baseString += "#0";
        } else {
            baseString += "#00";
        }
        baseString += Integer.toString(id);
        return baseString;
    }

}
