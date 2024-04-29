package michail;

import michail.JSONParser.JSONParser;
import michail.JSONrepresentation.JSONValue;
import michail.JSONtokens.TokenPatterns;
import michail.LexerAnalyser.Lexer;
import michail.ObjectJsonConverters.ObjectBuilder;
import michail.ObjectJsonConverters.ObjectToJSONConverter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonMaster {
    public static <T> T createSpecifiedClassFromJson(Class<T> clazz, String filePath) throws Exception {
        String jsonString = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        Lexer lexer = new Lexer(jsonString, TokenPatterns.PATTERNS);
        JSONParser parser = new JSONParser(lexer);
        ObjectBuilder<T> objectBuilder = new ObjectBuilder<>(clazz, parser);
        return objectBuilder.buildObject();
    }
    public static Map<String, Object> createMapFromJson(String filePath) throws Exception {
        String jsonString = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        Lexer lexer = new Lexer(jsonString, TokenPatterns.PATTERNS);
        JSONParser parser = new JSONParser(lexer);
        ObjectBuilder<Object> objectBuilder = new ObjectBuilder<>(Object.class, parser);
        return objectBuilder.buildMap();
    }
    public static Object createObjectFromJson(String filePath) throws Exception {
        String jsonString = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        Lexer lexer = new Lexer(jsonString, TokenPatterns.PATTERNS);
        JSONParser parser = new JSONParser(lexer);
        ObjectBuilder<Object> objectBuilder = new ObjectBuilder<>(Object.class, parser);
        return objectBuilder.getObject();
    }
    public static String createJsonFromObject(Object obj) throws Exception {
        JSONValue jsonValue = ObjectToJSONConverter.convertToJSON(obj);
        return jsonValue.toString();
    }
}
