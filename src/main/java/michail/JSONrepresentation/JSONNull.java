package michail.JSONrepresentation;

public class JSONNull extends JSONValue {
    public JSONValueType getType() {
        return JSONValueType.NULL;
    }
    @Override
    public String toString() {
        return "null";
    }
}
