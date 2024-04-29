package michail.TestClasses;
import java.util.Arrays;

public class User {
    private String name;
    private String email;
    private int age;
    private boolean active;
    private String[] roles;
    private UserPreferences preferences;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", roles=" + Arrays.toString(roles) +
                ", preferences=" + preferences +
                '}';
    }
}

class UserPreferences {
    private String theme;
    private boolean notifications;

    @Override
    public String toString() {
        return "UserPreferences{" +
                "theme='" + theme + '\'' +
                ", notifications=" + notifications +
                '}';
    }
}