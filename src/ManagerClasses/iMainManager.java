package ManagerClasses;

import java.util.HashMap;

public interface iMainManager {
    HashMap<String, Manager<?>> subManagers = new HashMap<String, Manager<?>>();

    public <T> T getSubManager(String manager, Class<? extends T> type);

}