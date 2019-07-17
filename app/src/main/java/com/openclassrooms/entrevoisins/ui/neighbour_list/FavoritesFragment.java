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
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesFragment extends Fragment implements FragmentLifecycle {
    private static final String TAG = "FavoritesFragment";

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavoritesNeighbours;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
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

    @Override
    public void onStart() {
        super.onStart();
        initList();
    }

    @Override
    public void onPauseFragment() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResumeFragment() {
        initList();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
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
        //get the full list of every users
        List<Neighbour> usersList = mApiService.getNeighbours();
        //create a new list of users, we will add favorite users in it
        mFavoritesNeighbours = new ArrayList<>();


        //loop inside the full list to check who is favorite then add it to the favorite list
        for (int i = 0; i < usersList.size(); i++) {
            if (this.getActivity().getSharedPreferences("PREF", MODE_PRIVATE).getBoolean(usersList.get(i).getName(), false)) {
                mFavoritesNeighbours.add(usersList.get(i));
            }
        }
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteFavorite(DeleteNeighbourEvent event) {
        this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE).edit().putBoolean(event.neighbour.getName(), false).apply();

        initList();
    }
}

