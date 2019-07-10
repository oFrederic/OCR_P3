package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesFragment extends Fragment implements FragmentLifecycle {
    private static final String TAG = "FavoritesFragment";

    private List<Neighbour> mFavoritesNeighbours;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        setFavoritesNeighbours();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavoritesNeighbours));
    }

    /**
     * Check every user to see if they are favorite or not, if they are favorite they will be added
     * to the list.
     */
    private void setFavoritesNeighbours() {
        mFavoritesNeighbours = new ArrayList<>();

        List<Neighbour> usersNames = Arrays.asList(
                new Neighbour(1, "Caroline", "http://i.pravatar.cc/1000?u=a042581f4e29026704d"),
                new Neighbour(2, "Jack", "http://i.pravatar.cc/1000?u=a042581f4e29026704e"),
                new Neighbour(3, "Chloé", "http://i.pravatar.cc/1000?u=a042581f4e29026704f"),
                new Neighbour(4, "Vincent", "http://i.pravatar.cc/1000?u=a042581f4e29026704a"),
                new Neighbour(5, "Elodie", "http://i.pravatar.cc/1000?u=a042581f4e29026704b"),
                new Neighbour(6, "Sylvain", "http://i.pravatar.cc/1000?u=a042581f4e29026704c"),
                new Neighbour(7, "Laetitia", "http://i.pravatar.cc/1000?u=a042581f4e29026703d"),
                new Neighbour(8, "Dan", "http://i.pravatar.cc/1000?u=a042581f4e29026703b"),
                new Neighbour(9, "Joseph", "http://i.pravatar.cc/1000?u=a042581f4e29026704d"),
                new Neighbour(10, "Emma", "http://i.pravatar.cc/1000?u=a042581f4e29026706d"),
                new Neighbour(11, "Patrick", "http://i.pravatar.cc/1000?u=a042581f4e29026702d"),
                new Neighbour(12, "Ludovic", "http://i.pravatar.cc/1000?u=a042581f3e39026702d")
        );
        //loop inside the list to check who is favorite or not.
        for (int i = 0; i < usersNames.size(); i++) {
            if (this.getActivity().getSharedPreferences("PREF", MODE_PRIVATE).getBoolean(usersNames.get(i).getName(), false)) {
                mFavoritesNeighbours.add(usersNames.get(i));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initList();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        initList();
    }
}

