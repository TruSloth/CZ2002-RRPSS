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

/**
 * {@link DataStore} is a utility class designed to provided data persistence.
 */
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

    /**
     * Loads the serialized {@link Object} instance from {@link Path} specified by {@code filePath}.
     * <p>
     * 
     * @param filePath the {@code Path} to deserialize the {@code Object} instance from
     * @return the {@code Object} after deserialization, null otherwise
     * @throws FileNotFoundException if the {@code filePath} could not be resolved
     * @throws IOException if an I/O error occurs while reading stream header
     * 
     * @see Serializable
     */
    public static Object loadFromFile(Path filePath) throws FileNotFoundException, IOException {
        FileInputStream fin = new FileInputStream(filePath.toString());
        try {
            ObjectInputStream ois = new ObjectInputStream(fin);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("The class of the serialized object could not be found");
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
     * Saves the {@link Object} instance to the file specified by {@code fileName}.
     * <p>
     * The {@link Path} that the file identified by {@code fileName} must be in the directory data.
     * If the file cannot be found there, then this method creates that file by calling {@link #createDataFile(fileName)}.
     * <p>
     * {@code obj} must implement {@link Serializable}, otherwise a {@link NotSerializableException} will be thrown.
     * 
     * @param obj the {@code Object} instance to be saved
     * @param fileName the {@code fileName} that {@code manager} should be saved to
     * 
     * @see Serializable
     */
    public static void saveToFile(Object obj, String fileName) {
        Path filePath = createDataFile(fileName);
    
        try {    
            FileOutputStream fout = new FileOutputStream(filePath.toString());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
