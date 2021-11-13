package com.CZ2002.project_managers;

import java.io.Serializable;
import java.util.List;

import com.CZ2002.project_entities.RestaurantEntity;

/**
 * The {@code Manager} class is intended to control all logic surrounding a {@link RestaurantEntity}.
 * <p>
 * The class provides storage of {@code RestaurantEntity} through a {@link List}.
 * <p>
 * Although not required, most implementations of this class should provide a
 * method of adding, retrieving and removing a specific {@code RestaurantEntity} from {@code entities}.
 * All other logic surrounding the managed {@code RestaurantEntity} is implementation specific.
 * <p>
 * The {@code Manager} class is designed to maintain the state of all instances of a particular {@code RestaurantEntity}
 * in the Restaurant and implement all logic surrounding that {@code RestaurantEntity} as required.
 */
public abstract class Manager<T extends RestaurantEntity> implements Serializable {
    /**
     * The {@link List} of all {@link RestaurantEntity} of type {@code T} found in the Restaurant.
     * <p>
     * Within the context of the Restaurant, {@code entities} should be the only access point for all {@code RestaurantEntity}
     * of type T.
     */
    protected List<T> entities; 
}