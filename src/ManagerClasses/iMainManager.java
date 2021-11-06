package ManagerClasses;

import java.util.HashMap;

/**
 * This interface provides an access point to all other {@link ManagerClasses.Manager} classes within the Restaurant.
 * Implementations of this interface should provide some way of retrieving a particular {@code Manager} through
 * {@code getSubManager(String manager, Class<? extends T> type)}.
 */
public interface iMainManager {
    /**
     * The storage point of all {@code Manager} classes within the Restaurant. As {@code Manager} classes
     * are stored in a {@link java.util.HashMap}, they can be added and removed through the {@code get} and {@code remove}
     * methods of {@code HashMap}.
     */
    HashMap<String, Manager<?>> subManagers = new HashMap<String, Manager<?>>();


    /**
     * Retrieves a specific {@link ManagerClasses.Manager} from {@link subManagers}.
     * @param <T> Any class that extends {@code Manager}
     * @param manager The key value that maps to the desired {@code Manager}, typically its instance name
     * @param type The expected class of the desired {@code Manager}
     * @return The {@code Manager} instance stored in {@code subManagers}
     */
    public <T> T getSubManager(String manager, Class<? extends T> type);
}