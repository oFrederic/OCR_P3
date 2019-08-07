package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Get all my favorites neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighboursFavorites();

    /**
     * Deletes a favorites neighbour
     * @param neighbour
     */
    void deleteNeighbourFavorites(Neighbour neighbour);
}
