package com.openclassrooms.entrevoisins.service;

import android.util.Log;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoritesFragment;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        //This is the list of favorite users that is shown on view
        ArrayList<Neighbour> neighbours = new ArrayList<>();
        //This is the fake favorite data
        ArrayList<Neighbour> favoriteData = new ArrayList<>();
        //Populate the fake data
        favoriteData.add(new Neighbour(1, "Caroline", "http://i.pravatar.cc/1000?u=a042581f4e29026704d"));
        favoriteData.add(new Neighbour(5, "Elodie", "http://i.pravatar.cc/1000?u=a042581f4e29026704b"));
        favoriteData.add(new Neighbour(9, "Joseph", "http://i.pravatar.cc/1000?u=a042581f4e29026704d"));
        //Add all saved favorite users to favorite user list
        favoritesFragment.setFavoritesNeighbours(neighbours, favoriteData);
        assertEquals(3, neighbours.size());
    }

    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        //TODO: code the delete test
    }
}
