package ru.meldren.weblab2.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Meldren on 07/08/2021
 */
@UtilityClass
public class ReflectionUtil {

    /**
     * Gets private or non-private field value of provided object
     * @param obj Object which contains requested field
     * @param fieldClass Class of requested field
     * @param name Field name
     * @return Field value if field exists, null otherwise
     */
    public Object getField(Object obj, Class<?> fieldClass, String name) {
        Object o = null;
        try {
            Field f = fieldClass.getDeclaredField(name);
            f.setAccessible(true);
            o = f.get(obj);
            f.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return o;
    }

    /**
     * Gets private or non-private field value of provided object
     * @param obj Object which contains requested field
     * @param name Field name
     * @return Field value if field exists, null otherwise
     */
    public Object getField(Object obj, String name) {
        return getField(obj, obj.getClass(), name);
    }

    /**
     * Gets all fields values of provided object
     * @param obj Object which contains requested field
     * @return Map with key of field name and value of field value
     */
    public Map<String, Object> getAllFields(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toMap(field -> field, field -> getField(obj, field)));
    }

    /**
     * Sets the value of a field on the object
     * @param obj The object
     * @param fieldName The name of the field
     * @param field The value to set
     */
    public void setField(Object obj, String fieldName, Object field) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, field);
            f.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object invokeMethod(Object invoker, Class<?> clazz, String name, Object... args) {
        Object o = null;
        try {
            Optional<Method> optional = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.getName().equals(name))
                    .findAny();
            if (optional.isPresent()) {
                Method m = optional.get();
                m.setAccessible(true);
                o = m.invoke(invoker, args);
                m.setAccessible(false);
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return o;
    }

    public Object invokeMethod(Object invoker, String name, Object... args) {
        return invokeMethod(invoker, invoker.getClass(), name, args);
    }

    /**
     * Creates an object of provided class
     * @param clazz Class for instance creation
     * @param args Arguments for object creation
     * @return Object if object was instantiated, null otherwise
     */
    public Object allocateInstance(Class<?> clazz, Object... args) {
        Object o = null;
        try {
            Class<?>[] classes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
            Constructor<?> constructor = clazz.getDeclaredConstructor(classes);
            constructor.setAccessible(true);
            o = constructor.newInstance(args);
            constructor.setAccessible(false);
        } catch (Exception ex) {
            (ex instanceof InstantiationException ? ex.getCause() : ex).printStackTrace();
        }
        return o;
    }
}
