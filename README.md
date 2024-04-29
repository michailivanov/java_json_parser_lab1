## JSON Parser Project

### Overview

This project provides functionalities to parse JSON files and convert Java objects to JSON and vice versa. It includes classes for parsing JSON, converting JSON to Java objects, converting Java objects to JSON, and analyzing JSON tokens.
### Project
1. **Project Structure**: The project is structured into several packages:
   - `michail.JSONrepresentation`: Includes package includes various essential JSON data types.
   - `michail.JSONtokens`: Contains token patterns used by the lexer.
   - `michail.LexerAnalyser`: Contains the `Lexer` class for tokenizing input JSON strings.
   - `michail.JSONParser`: Includes the `JSONParser` class for parsing JSON data and generating JSON object representations.
   - `michail.ObjectJsonConverters`: Contains the `ObjectBuilder` class responsible for creating Java objects from parsed JSON data.
   - `michail.TestClasses`: Includes example Java classes used for testing the JSON parser.

2. **Main Class**: The `Main` class processes a set of test JSON files located in the "src/main/resources/" directory. For each JSON file, it demonstrates various functionalities such as converting JSON to specified Java classes, mapping JSON to a Map<String, Object>, parsing JSON to Java objects, and converting Java objects back to JSON.
3. **JsonMaster Class**:
   The `JsonMaster` class provides static methods for handling JSON processing tasks.
   - `createSpecifiedClassFromJson`: Reads a JSON file specified by the filePath, parses its content, and constructs an instance of the specified class (clazz) by mapping JSON attributes to class fields.
   - `createMapFromJson`: Reads a JSON file specified by the filePath, parses its content, and constructs a Map<String, Object> representing the JSON structure.
   - `createObjectFromJson`: Reads a JSON file specified by the filePath, parses its content, and constructs a generic Java object representing the JSON structure.
   - `createJsonFromObject`: Converts a Java object (obj) into its corresponding JSON representation as a string.

4. **Example Usage**: Here's an example of how to use the JSON master:
   ```java
      String filepath = "src/main/resources/user.json";
      
      Pizza pizza = JsonMaster.createSpecifiedClassFromJson(Pizza.class, filepath);
      Map<String, Object> jsonMap = JsonMaster.createMapFromJson(filepath);
      Object jsonObject = JsonMaster.createObjectFromJson(filepath);
      String jsonString = JsonMaster.createJsonFromObject(jsonObject);
   
      System.out.println(pizza);
      System.out.println(jsonMap);
      System.out.println(jsonObject);
      System.out.println(jsonString);
   ```
   
   *Output:*
   ```
   User{name='John Doe', email='john.doe@example.com', age=32, active=true, roles=[admin, editor], preferences=UserPreferences{theme='dark', notifications=true}}
   {preferences={theme=dark, notifications=true}, roles=[admin, editor], name=John Doe, active=true, email=john.doe@example.com, age=32.0}
   {"preferences": {"theme": "dark", "notifications": true}, "roles": ["admin", "editor"], "name": "John Doe", "active": true, "email": "john.doe@example.com", "age": 32.0}
   {"preferences": {"theme": "dark", "notifications": true}, "roles": ["admin", "editor"], "name": "John Doe", "active": true, "email": "john.doe@example.com", "age": 32.0}
   ```
   *src/main/resources/user.json:*
   ```json
   {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 32,
    "active": true,
    "roles": ["admin", "editor"],
    "preferences": {
     "theme": "dark",
     "notifications": true
    }
   }
   ```
   *Specified class* `michail.TestClasses.User.java`:
   ```java
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
   ```
### Testing

The project includes sample JSON files located in the `src/main/resources/` directory. These files contain JSON data representing various objects, such as pizza, book, car, user, and school. These files were used to test the JSON parser with different types of JSON data.


### Dependencies

The project has no external dependencies beyond the Java standard library.
