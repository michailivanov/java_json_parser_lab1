package michail.ObjectJsonConverters;

import michail.JSONrepresentation.JSONArray;
import michail.JSONrepresentation.JSONValue;

public class JSONObjectOutput  extends JSONArray {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        boolean first = true;
        for (JSONValue value : values) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(value.toString());
        }
        return sb.toString();
    }
}
