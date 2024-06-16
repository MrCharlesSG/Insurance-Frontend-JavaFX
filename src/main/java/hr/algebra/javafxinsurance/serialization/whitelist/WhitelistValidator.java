package hr.algebra.javafxinsurance.serialization.whitelist;

import hr.algebra.javafxinsurance.dto.VehicleInfoDTO;
import hr.algebra.javafxinsurance.model.Token;
import hr.algebra.javafxinsurance.serialization.Serializer;
import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;
import hr.algebra.javafxinsurance.utils.ReflectionUtils;

import java.io.*;
import java.util.*;

public class WhitelistValidator {

    private WhitelistValidator(){}

    private static Set<Class> deserializationClassWhitelist;

    /*
    static {
        deserializationClassWhitelist = new HashSet<>();
        deserializationClassWhitelist.add(VehicleInfoDTO.class);
        deserializationClassWhitelist.add(Token.class);
    }
     */

    static {
        deserializationClassWhitelist = new HashSet<>();
        try {
            registerAllowedClasses();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void registerAllowedClasses() throws ClassNotFoundException, IOException {
        String packageName = "hr.algebra.javafxinsurance.serialization";
        Class<?>[] serializerClasses = ReflectionUtils.getClasses(packageName);

        for (Class<?> clazz : serializerClasses) {
            if (Serializer.class.isAssignableFrom(clazz)) {
                Class<?>[] genericTypes = ReflectionUtils.getGenericTypes(clazz);
                deserializationClassWhitelist.addAll(Arrays.asList(genericTypes));
            }
        }
    }

    public static void checkIfIsValidClass(Class clazz) throws NonSerializableClassException {
        if(!deserializationClassWhitelist.contains(clazz)){
            throw new NonSerializableClassException("The class " + clazz + " is not allowed!");
        }
    }

    public static void validateSerializedFile(String binaryFile) throws IOException, NonSerializableClassException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFile))) {
            Object readObject;
            while ((readObject = ois.readObject()) != null) {
                if (!deserializationClassWhitelist.contains(readObject.getClass())) {
                    throw new SecurityException("The class " + readObject.getClass() + " is not allowed!");
                } else {
                    if (readObject instanceof List<?> objectList) {
                        for (Object object : objectList) {
                            checkIfIsValidClass(object.getClass());
                        }
                        objectList.forEach(System.out::println);
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("Everything OK");
        } catch (Exception ex) {
            throw new SecurityException("There was a problem with deserialization!\n" + ex.getMessage(), ex);
        }
    }
}
