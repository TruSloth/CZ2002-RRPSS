package com.CZ2002;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.CZ2002.project_boundaries.Manager;
import com.CZ2002.project_entities.RestaurantEntity;

public class DataStore {
    private static Path dataDirPath = Paths.get(System.getProperty("user.dir"), "src", "com", "CZ2002", "data");

    private static Path createDataFile(String fileName) {
        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }
        Path filePath = Paths.get(dataDirPath.toString(), fileName);
        try {
            Files.createDirectories(dataDirPath);
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static <T extends RestaurantEntity> Manager<T> loadManagerFromFile(Path filePath) throws FileNotFoundException {
        FileInputStream fin = new FileInputStream(filePath.toString());
        try {
            ObjectInputStream ois = new ObjectInputStream(fin);
            return (Manager<T>) ois.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find the class to cast to");
        } catch (IOException e) {
            System.out.println("IO exception occurred!");
        }
        return null;
    }

    /**
     * Returns the {@link Path} instance that leads to directory where all data is stored.
     * 
     * @return the {@code Path} to the data storage directory
     */
    public static Path getDataDirPath() {
        return dataDirPath;
    }

    /**
     * 
     * @param <T>
     * @param manager
     * @param fileName
     */
    public static <T extends RestaurantEntity> void saveManagerToFile(Manager<T> manager, String fileName) {
            Path filePath = createDataFile(fileName);
    
        try {    
            FileOutputStream fout = new FileOutputStream(filePath.toString());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(manager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
