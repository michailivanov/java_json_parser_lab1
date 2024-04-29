package michail.ObjectJsonConverters;

import michail.JSONrepresentation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ObjectToJSONConverter {
    public static JSONValue convertToJSON(Object object) throws BuildException {
        if (object == null) {
            return new JSONNull();
        }

        if (isPrimitiveOrWrapper(object.getClass())) {
            return convertPrimitiveOrWrapper(object);
        } else if (object instanceof String) {
            return new JSONString((String) object);
        } else if (object instanceof List) {
            return convertList((List<?>) object);
        } else if (object instanceof Map) {
            return convertMap((Map<?, ?>) object);
        } else {
            return convertObject(object);
        }
    }

    private static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Integer.class || clazz == Long.class ||
                clazz == Double.class || clazz == Float.class || clazz == Boolean.class ||
                clazz == Character.class || clazz == Byte.class || clazz == Short.class;
    }

    private static JSONValue convertPrimitiveOrWrapper(Object object) throws BuildException {
        if (object instanceof Integer) {
            return new JSONNumber((Integer) object);
        } else if (object instanceof Long) {
            return new JSONNumber((Long) object);
        } else if (object instanceof Double) {
            return new JSONNumber((Double) object);
        } else if (object instanceof Float) {
            return new JSONNumber((Float) object);
        } else if (object instanceof Boolean) {
            return new JSONBoolean((Boolean) object);
        } else if (object instanceof Character) {
            return new JSONString(object.toString());
        } else if (object instanceof Byte) {
            return new JSONNumber((Byte) object);
        } else if (object instanceof Short) {
            return new JSONNumber((Short) object);
        } else {
            throw new BuildException("Unsupported primitive or wrapper type: " + object.getClass());
        }
    }

    private static JSONArray convertList(List<?> list) throws BuildException {
        JSONArray jsonArray = new JSONArray();
        for (Object element : list) {
            jsonArray.addValue(convertToJSON(element));
        }
        return jsonArray;
    }

    private static JSONObject convertMap(Map<?, ?> map) throws BuildException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (!(key instanceof String)) {
                throw new BuildException("Map keys must be strings");
            }
            jsonObject.put((String) key, convertToJSON(entry.getValue()));
        }
        return jsonObject;
    }

//    private static JSONObject convertObject(Object object) throws BuildException {
//        JSONObject jsonObject = new JSONObject();
//        Class<?> clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            try {
//                Object fieldValue = convertToJSON(field.get(object));
//                if (fieldValue != null) {
//                    JSONValue jsonValue = (JSONValue) fieldValue;
//                    if(jsonValue.isObject()){
//                        (JSONObject) fieldValue.values();
//                    }
//                    jsonObject.put(jsonValue.toString(), jsonValue);
//                } else {
//                    throw new BuildException("Field value is not a JSONValue instance: " + field.getName());
//                }
//            } catch (IllegalAccessException e) {
//                throw new BuildException("Failed to access field: " + field.getName(), e);
//            }
//        }
//        return jsonObject;
//    }

    private static JSONObjectOutput convertObject(Object object) throws BuildException {
        JSONObjectOutput jsonObjectOutput = new JSONObjectOutput();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                jsonObjectOutput.add(convertToJSON(field.get(object)));
//                jsonArray.put(field.getName(), convertToJSON(field.get(object))); // TODO
            } catch (IllegalAccessException e) {
                throw new BuildException("Failed to access field: " + field.getName(), e);
            }
        }
        return jsonObjectOutput;
    }
}
