package ManagerClasses;

import java.util.List;

import RestaurantClasses.RestaurantEntity;

public abstract class Manager<T extends RestaurantEntity> {
    List<T> entities;
}
