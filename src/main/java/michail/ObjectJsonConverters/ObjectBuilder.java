package michail.ObjectJsonConverters;

import michail.JSONParser.JSONParser;
import michail.JSONrepresentation.*;

import java.lang.reflect.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ObjectBuilder<T> {
    private final Class<T> clazz;
    private final JSONParser parser;

    public ObjectBuilder(Class<T> clazz, JSONParser parser) {
        this.clazz = clazz;
        this.parser = parser;
    }

    public T buildObject() throws BuildException, ParseException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            JSONValue rootValue = parser.parse();

            if (rootValue.isObject()) {
                JSONObject rootObject = (JSONObject) rootValue;
                populateObjectFields(instance, rootObject);
            } else {
                throw new BuildException("Root JSON value is not an object");
            }

            return instance;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new BuildException("Failed to create an instance of the class: " + clazz.getName(), e);
        }
    }

    private void populateObjectFields(Object instance, JSONObject jsonObject) throws BuildException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            JSONValue jsonValue = jsonObject.get(fieldName);
            if (jsonValue != null) {
                setFieldValue(instance, field, jsonValue);
            } else {
                throw new BuildException("Missing JSON property: " + fieldName);
            }
        }
    }

    private void setFieldValue(Object instance, Field field, JSONValue jsonValue) throws BuildException {
        try {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();

            if (fieldType == String.class) {
                field.set(instance, ((JSONString) jsonValue).getValue());
            } else if (fieldType == int.class || fieldType == Integer.class) {
                field.set(instance, (int) ((JSONNumber) jsonValue).getValue());
            } else if (fieldType == double.class || fieldType == Double.class) {
                field.set(instance, ((JSONNumber) jsonValue).getValue());
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                field.set(instance, ((JSONBoolean) jsonValue).getValue());
            } else if (fieldType.isArray()) {
                setArrayField(instance, field, jsonValue);
            } else if (List.class.isAssignableFrom(fieldType)) {
                setListField(instance, field, jsonValue);
            } else if (jsonValue.isObject()) {
                // Handle nested objects
                Object nestedObject = createNestedObject(fieldType, (JSONObject) jsonValue);
                field.set(instance, nestedObject);
            } else {
                throw new BuildException("Unsupported field type: " + fieldType.getName());
            }
        } catch (IllegalAccessException e) {
            throw new BuildException("Failed to set field value for field: " + field.getName(), e);
        }
    }

    private void setListField(Object instance, Field field, JSONValue jsonValue) throws BuildException {
        try {
            List<?> tempList = (List<?>) convertToList(jsonValue);
            List<Object> newList = createList(field);
            newList.addAll(tempList);
            field.set(instance, newList);
        } catch (IllegalAccessException e) {
            throw new BuildException("Failed to set list field value for field: " + field.getName(), e);
        }
    }

    private List<?> convertToList(JSONValue jsonValue) throws BuildException {
        if (jsonValue instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) jsonValue;
            return List.of(jsonArray.getValues());
        } else {
            throw new BuildException("Expected a JSONArray value for a List field");
        }
    }

    private List<Object> createList(Field field) throws BuildException {
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            if (fieldArgTypes.length == 1 && fieldArgTypes[0] instanceof Class) {
                Class<?> listClass = (Class<?>) fieldArgTypes[0];
                return new ArrayList<>(); // Instantiate based on the specific type
            }
        }
        throw new BuildException("Cannot create an instance of a raw or unsupported List type.");
    }

    private Object createNestedObject(Class<?> fieldType, JSONObject jsonObject) throws BuildException {
        try {
            Constructor<?> constructor = fieldType.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object nestedObject = constructor.newInstance();
            populateObjectFields(nestedObject, jsonObject); // Recursively populate nested object fields
            return nestedObject;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new BuildException("Failed to create nested object of type: " + fieldType.getName(), e);
        }
    }

    private Object createElementObject(Class<?> elementType, JSONValue jsonValue) throws BuildException {
        if (elementType == String.class) {
            return ((JSONString) jsonValue).getValue();
        } else if (elementType == int.class || elementType == Integer.class) {
            return (int) ((JSONNumber) jsonValue).getValue();
        } else if (elementType == double.class || elementType == Double.class) {
            return ((JSONNumber) jsonValue).getValue();
        } else if (elementType == boolean.class || elementType == Boolean.class) {
            return ((JSONBoolean) jsonValue).getValue();
        } else {
            throw new BuildException("Unsupported list element type: " + elementType.getName());
        }
    }

    private void setArrayField(Object instance, Field field, JSONValue jsonValue) throws BuildException {
        try {
            Class<?> componentType = field.getType().getComponentType();
            JSONArray jsonArray = (JSONArray) jsonValue;
            List<JSONValue> jsonValues = List.of(jsonArray.getValues());

            Object array = Array.newInstance(componentType, jsonValues.size());

            for (int i = 0; i < jsonValues.size(); i++) {
                JSONValue elementValue = jsonValues.get(i);
                Object elementObject;

                if (componentType.isPrimitive() || componentType == String.class || componentType == Integer.class ||
                        componentType == Double.class || componentType == Boolean.class) {
                    elementObject = createElementObject(componentType, elementValue);
                } else {
                    // Handle custom object types
                    elementObject = buildCustomObject(componentType, elementValue);
                }

                Array.set(array, i, elementObject);
            }

            field.set(instance, array);
        } catch (IllegalAccessException e) {
            throw new BuildException("Failed to set array field value for field: " + field.getName(), e);
        }
    }

    private Object buildCustomObject(Class<?> componentType, JSONValue jsonValue) throws BuildException {
        try {
            // Get the constructor of the componentType
            Constructor<?> constructor = componentType.getDeclaredConstructor();
            // Set the constructor to be accessible
            constructor.setAccessible(true);
            // Instantiate the object using the constructor
            Object object = constructor.newInstance();
            if (jsonValue instanceof JSONObject) {
                populateObjectFields((T) object, (JSONObject) jsonValue);
            } else {
                throw new BuildException("JSON value is not an object for type: " + componentType.getName());
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new BuildException("Failed to instantiate component type: " + componentType.getName(), e);
        }
    }

    public Map<String, Object> buildMap() throws BuildException, ParseException {
        JSONValue rootValue = parser.parse();
        if (rootValue.isObject()) {
            JSONObject rootObject = (JSONObject) rootValue;
            return convertJSONObjectToMap(rootObject);
        } else {
            throw new BuildException("Root JSON value is not an object");
        }
    }

    public Object getObject() throws ParseException, BuildException {
        JSONValue rootValue = parser.parse();
        if (rootValue.isObject()) {
            return rootValue;
        } else {
            throw new BuildException("Root JSON value is not an object");
        }
    }

    private Map<String, Object> convertJSONObjectToMap(JSONObject jsonObject) throws BuildException {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, JSONValue> entry :  jsonObject.values().entrySet()) {
            String key = entry.getKey();
            JSONValue value = entry.getValue();
            Object mappedValue = convertJSONValueToObject(value);
            map.put(key, mappedValue);
        }
        return map;
    }

    public Object convertJSONValueToObject(JSONValue jsonValue) throws BuildException {
        if (jsonValue.isString()) {
            return ((JSONString) jsonValue).getValue();
        } else if (jsonValue.isNumber()) {
            return ((JSONNumber) jsonValue).getValue();
        } else if (jsonValue.isBoolean()) {
            return ((JSONBoolean) jsonValue).getValue();
        } else if (jsonValue.isArray()) {
            JSONArray jsonArray = (JSONArray) jsonValue;
            List<Object> list = new ArrayList<>();
            for (JSONValue element : jsonArray.getValues()) {
                list.add(convertJSONValueToObject(element));
            }
            return list;
        } else if (jsonValue.isObject()) {
            JSONObject jsonObject = (JSONObject) jsonValue;
            return convertJSONObjectToMap(jsonObject);
        } else {
            throw new BuildException("Unsupported JSON value type");
        }
    }
}
