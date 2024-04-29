package michail.TestClasses;

public class Pizza {
    private String name;
    private String description;
    private double price;

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
