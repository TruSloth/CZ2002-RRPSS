package ManagerClasses;

import java.util.List;

import RestaurantClasses.RestaurantEntity;

public abstract class Manager<T extends RestaurantEntity> {
    protected List<T> entities;
}
