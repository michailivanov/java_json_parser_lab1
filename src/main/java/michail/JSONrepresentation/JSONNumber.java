package michail.JSONrepresentation;

public class JSONNumber extends JSONValue {
    private final double value;

    public JSONNumber(double value) {
        this.value = value;
    }

    public JSONValueType getType() {
        return JSONValueType.NUMBER;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}