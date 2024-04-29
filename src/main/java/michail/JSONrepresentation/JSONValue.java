package michail.JSONrepresentation;

public abstract class JSONValue {
    public abstract JSONValueType getType();

    public boolean isObject() {
        return getType() == JSONValueType.OBJECT;
    }

    public boolean isArray() {
        return getType() == JSONValueType.ARRAY;
    }

    public boolean isString() {
        return getType() == JSONValueType.STRING;
    }

    public boolean isNumber() {
        return getType() == JSONValueType.NUMBER;
    }

    public boolean isBoolean() {
        return getType() == JSONValueType.BOOLEAN;
    }

    public boolean isNull() {
        return getType() == JSONValueType.NULL;
    }

    public abstract String toString();
}
