package michail.JSONrepresentation;

public class JSONString extends JSONValue {
    private final String value;

    public JSONString(String value) {
        this.value = value;
    }

    public JSONValueType getType() {
        return JSONValueType.STRING;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}