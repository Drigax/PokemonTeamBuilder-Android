package com.drigax.teambuilder.view_code_behind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.Pokedex;
import com.drigax.teambuilder.model.Pokemon;
import com.drigax.teambuilder.model.Team;
import com.drigax.teambuilder.utils.OnChangeListener;
import com.drigax.teambuilder.view_code_behind.controls.SlotView;

import java.util.Hashtable;

public class TeamViewActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnChangeListener<Team>, PokemonSearchPanelFragment.SearchPanelListener {

    private static int TEAM_VIEW_FRAGMENT_INDEX = 0;
    private int currentPosition = TEAM_VIEW_FRAGMENT_INDEX;
    private static int SEARCH_PANEL_FRAGMENT_INDEX = 1;
    private Team teamInstance;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private PokemonSearchPanelFragment mSearchPanelFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ITeamBuilderFragment currentFragment;
    private Hashtable<Integer, ITeamBuilderFragment> fragmentMapping;
    private boolean isHandlingSlotSelection = false;
    private int currentSlotSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Pokedex pokedexInstance = Pokedex.getInstance(getApplicationContext());
        teamInstance = Team.getInstance(pokedexInstance);
        super.onCreate(savedInstanceState);
        fragmentMapping = new Hashtable<Integer, ITeamBuilderFragment>();
        fragmentMapping.put(TEAM_VIEW_FRAGMENT_INDEX, TeamViewFragment.newInstance(TEAM_VIEW_FRAGMENT_INDEX, teamInstance));
        fragmentMapping.put(SEARCH_PANEL_FRAGMENT_INDEX, PokemonSearchPanelFragment.newInstance(pokedexInstance));
        setContentView(R.layout.activity_team_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
         actionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.action_bar_custom_layout,null);
//        actionBar.setCustomView(v);

        teamInstance.addListener(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mSearchPanelFragment = (PokemonSearchPanelFragment) getSupportFragmentManager().findFragmentById(R.id.pokemonSearchPanel);
        mSearchPanelFragment.initializePokedex(pokedexInstance);
        onNavigationDrawerItemSelected(TEAM_VIEW_FRAGMENT_INDEX);

    }

    @Override
    public void onChange(Team currentTeam) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateCurrentFragmentView();
            }
        });
    }

    public void updateCurrentFragmentView() {
        currentFragment.updateView();

    }

    @Override
    public void onSearchPanelSelection(Pokemon selectedPoke) {
        if (!isHandlingSlotSelection) { //this flag gets raised if the drawer was opened from onAddPokemonSlot_click()
            teamInstance.addPokemon(selectedPoke);
        } else {
            teamInstance.addPokemonAt(selectedPoke, currentSlotSelection);
        }
        isHandlingSlotSelection = false;//handled.
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(Gravity.RIGHT);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        fTransaction.replace(R.id.container, (Fragment) changeCurrentFragment(position)).commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.team_view, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPokemonSlot_click(View currentView) {
        SlotView currentSlot = (SlotView) currentView;
        DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        currentSlotSelection = currentSlot.getSlotId();
        isHandlingSlotSelection = true;
        dl.openDrawer(Gravity.RIGHT);
    }

    private ITeamBuilderFragment getCurrentFragment() {
        return fragmentMapping.get(currentPosition);
    }

    private ITeamBuilderFragment changeCurrentFragment(int position) {
        currentFragment = fragmentMapping.get(position);
        return getCurrentFragment();
    }

}
