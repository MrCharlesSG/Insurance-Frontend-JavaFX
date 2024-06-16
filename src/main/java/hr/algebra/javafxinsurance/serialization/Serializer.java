package hr.algebra.javafxinsurance.serialization;

import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;
import hr.algebra.javafxinsurance.serialization.whitelist.WhitelistValidator;

import java.io.*;

public abstract class Serializer <T> {

    protected void save(T classToSave, String location) throws NonSerializableClassException {
        WhitelistValidator.checkIfIsValidClass(classToSave.getClass());
        try (FileOutputStream fileOut = new FileOutputStream(location);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(classToSave);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    protected T load(String location) throws NonSerializableClassException {
        T classToLoad = null;
        try (FileInputStream fileIn = new FileInputStream(location);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            WhitelistValidator.validateSerializedFile(location);
            classToLoad = (T) in.readObject();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return classToLoad;
    }

    public abstract void save(T classToSave) throws NonSerializableClassException;
    public abstract T load() throws NonSerializableClassException;
}
