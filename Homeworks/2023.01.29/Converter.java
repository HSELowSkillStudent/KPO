package org.example;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Converter {
    private static boolean isSatisfies(Object object, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.isAnnotationPresent(JsonElement.class) &&
                !(field.get(object) == null && field.isAnnotationPresent(IgnoreNull.class));
    }

    private static HashMap<String, String> getJson(Object object) {
        Class<?> clazz = object.getClass();
        String key, annotationKey;

        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            return null;
        }
        HashMap<String, String> jsonElementsMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (isSatisfies(object, field)) {
                    annotationKey = field.getAnnotation(JsonElement.class).key();
                    if ("".equals(annotationKey)) {
                        key = field.getName();
                    } else {
                        key = annotationKey;
                    }
                    jsonElementsMap.put(key, (String) field.get(object));
                }
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return jsonElementsMap;
    }

    private static String convertHashMapToString(HashMap<String, String> jsonElementsMap) {
        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> {
                    String result;
                    try {
                        int var = Integer.parseInt(entry.getValue());
                        result = "\"" + entry.getKey() + "\": "
                                + entry.getValue();
                    } catch (Exception e) {
                        result = "\"" + entry.getKey() + "\": \""
                                + entry.getValue() + "\"";
                    }
                    return result;
                })
                .collect(Collectors.joining(", \n"));
        return "{\n" + jsonString + "\n}";
    }

    public static String getJsonString(Object object) throws Exception {
        HashMap<String, String> jsonData = getJson(object);
        if (jsonData != null) {
            return convertHashMapToString(jsonData).strip();
        }
        return null;
    }
}
