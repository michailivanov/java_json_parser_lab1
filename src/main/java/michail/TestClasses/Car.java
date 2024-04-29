package michail.TestClasses;

import java.util.Arrays;

public class Car {
    private String type;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String[] features;
    private CarOwner[] owners;

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", features=" + Arrays.toString(features) +
                ", carOwners=" + Arrays.toString(owners) +
                '}';
    }
}

class CarOwner {
    private String name;
    private int since;

    @Override
    public String toString() {
        return "CarOwner{" +
                "name='" + name + '\'' +
                ", since=" + since +
                '}';
    }
}
