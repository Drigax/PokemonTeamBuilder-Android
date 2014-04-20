package com.drigax.teambuilder.view_code_behind;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drigax.teambuilder.R;
import com.drigax.teambuilder.model.Team;
import com.drigax.teambuilder.view_code_behind.controls.TeamView;

/**
 * Created by drigax on 2/17/14.
 */
public class TeamViewFragment extends Fragment implements ITeamBuilderFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    TeamView currentView;
    private Team tbInstance;

    public TeamViewFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TeamViewFragment newInstance(int sectionNumber, Team _currentTeam) {

        TeamViewFragment fragment = new TeamViewFragment();
        Bundle args = new Bundle();
        fragment.tbInstance = _currentTeam;
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_team_view, container, false);
        currentView = (TeamView) rootView.findViewById(R.id.teamView);
        initializeCurrentView();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeamViewActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void initializeCurrentView() {
        currentView.setTeam(tbInstance);
    }

    public void updateView() {
        refreshListView();
    }

    public void refreshListView() {
        currentView.refresh();
    }

}
