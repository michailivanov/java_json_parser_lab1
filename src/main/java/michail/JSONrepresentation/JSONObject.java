package michail.JSONrepresentation;

import java.util.LinkedHashMap;
import java.util.Map;

public class JSONObject extends JSONValue {
    private Map<String, JSONValue> values = new LinkedHashMap<>();

    public JSONObject(Map<String, JSONValue> values) {
        this.values = values;
    }
    public JSONObject() {
    }

    public JSONValueType getType() {
        return JSONValueType.OBJECT;
    }

    public void put(String key, JSONValue value) {
        values.put(key, value);
    }

    public JSONValue get(String key) {
        return values.get(key);
    }

    public Map<String, JSONValue> values() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, JSONValue> entry : values.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue().toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
