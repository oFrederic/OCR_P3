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
    private ArrayList<Neighbour> mFavoritesNeighbours;
    private ArrayList<Neighbour> mFavoriteData;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        mFavoritesNeighbours = new ArrayList<>();
        mFavoriteData = new ArrayList<>();
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
        mFavoriteData = getFavoriteData();
        setFavoritesNeighbours(mFavoritesNeighbours, mFavoriteData);
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavoritesNeighbours));
    }

    /**
     * We are getting all the data from SharedPreferences to return the correct list of favorite
     *
     * @return List of favorite that was saved in sharedPreferences
     */
    private ArrayList<Neighbour> getFavoriteData() {
        List<Neighbour> allNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        ArrayList<Neighbour> neighbours = new ArrayList<>();

        for (int i = 0; i < allNeighbours.size(); i++) {
            if (this.getActivity().getSharedPreferences("PREF", MODE_PRIVATE).getBoolean(allNeighbours.get(i).getName(), false)) {
                neighbours.add(allNeighbours.get(i));
            }
        }

        return neighbours;
    }

    /**
     * Check every user with the favoriteData, if they are favorite they will be added
     * to the list.
     *
     * @param list         favorite list
     * @param favoriteData data of favorite from SharedPreference
     */
    public void setFavoritesNeighbours(ArrayList<Neighbour> list, ArrayList<Neighbour> favoriteData) {
        // Get the full list of users
        List<Neighbour> usersList = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        // Clear list of favorite users
        list.clear();

        // Loop inside usersList to check who is favorite or not, add every favorite user to the list
        for (int i = 0; i < usersList.size(); i++) {
            if (favoriteData.contains(usersList.get(i))) {
                list.add(usersList.get(i));
            }
        }
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event event
     */
    @Subscribe
    public void onDeleteFavorite(DeleteNeighbourEvent event) {
        this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE).edit().putBoolean(event.neighbour.getName(), false).apply();

        initList();
    }
}

