package michail;

import michail.ObjectJsonConverters.ObjectBuilder;
import michail.JSONParser.JSONParser;
import michail.JSONtokens.TokenPatterns;
import michail.LexerAnalyser.Lexer;
import michail.ObjectJsonConverters.ObjectToJSONConverter;
import michail.TestClasses.*;
import michail.JSONrepresentation.JSONValue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        try {
            String resourcesPath = "src/main/resources/";

            // 5 JSON examples

            // File names and corresponding class types
            String[] fileNames = {"pizza.json", "book.json", "car.json", "user.json", "school.json"};
            Class<?>[] classes = {Pizza.class, Book.class, Car.class, User.class, School.class};

            // Process each file
            for (int i = 0; i < fileNames.length; i++) {
                String fileName = fileNames[i];
                Class<?> clazz = classes[i];
                System.out.println("FILE: " + fileName);
                System.out.println("\tJSON to specified class:");
                Object object = JsonMaster.createSpecifiedClassFromJson(clazz, resourcesPath + fileName);
                System.out.println("\t\t" + object);

                System.out.println("\tJSON to Map<String, Object>:");
                Map<String, Object> jsonMap = JsonMaster.createMapFromJson(resourcesPath + fileName);
                System.out.println("\t\t" + jsonMap);

                System.out.println("\tJSON to Java Object:");
                Object jsonObject = JsonMaster.createObjectFromJson(resourcesPath + fileName);
                System.out.println("\t\t" + jsonObject);

                System.out.println("\tJava Object to JSON:");
                String jsonString = JsonMaster.createJsonFromObject(jsonObject);
                System.out.println("\t\t" + jsonString);
                System.out.println("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}