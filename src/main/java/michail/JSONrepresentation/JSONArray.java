package michail.JSONrepresentation;

import java.util.ArrayList;
import java.util.List;

public class JSONArray extends JSONValue {
    protected List<JSONValue> values = new ArrayList<>();

    public JSONArray() {
    }

    public JSONArray(List<JSONValue> values) {
        this.values = values;
    }

    public JSONValueType getType() {
        return JSONValueType.ARRAY;
    }

    public void add(JSONValue value) {
        values.add(value);
    }

    public JSONValue get(int index) {
        return values.get(index);
    }

    public JSONValue[] getValues() {
        return values.toArray(new JSONValue[0]);
    }

    public void addValue(JSONValue jsonValue) {
        values.add(jsonValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (JSONValue value : values) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(value.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}