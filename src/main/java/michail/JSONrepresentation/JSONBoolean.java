package michail.JSONrepresentation;

public class JSONBoolean extends JSONValue {
    private final boolean value;

    public JSONBoolean(boolean value) {
        this.value = value;
    }

    public JSONValueType getType() {
        return JSONValueType.BOOLEAN;
    }

    public boolean getValue() {
        return value;
    }
    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}