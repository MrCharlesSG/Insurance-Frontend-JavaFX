package hr.algebra.javafxinsurance.utils;

import hr.algebra.javafxinsurance.serialization.Serializer;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {

    public static Class<?>[] getClasses(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Serializer>> serializerClasses = reflections.getSubTypesOf(Serializer.class);
        return serializerClasses.toArray(new Class<?>[0]);
    }

    public static Class<?>[] getGenericTypes(Class<?> clazz) {
        List<Class<?>> genericTypes = new ArrayList<>();
        Type superclass = clazz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType parameterizedType) {
            Type[] types = parameterizedType.getActualTypeArguments();
            for (Type type : types) {
                if (type instanceof Class<?>) {
                    genericTypes.add((Class<?>) type);
                }
            }
        }
        return genericTypes.toArray(new Class<?>[0]);
    }
}

