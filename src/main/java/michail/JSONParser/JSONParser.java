package michail.JSONParser;

import michail.JSONrepresentation.*;
import michail.JSONtokens.Token;
import michail.JSONtokens.TokenType;
import michail.LexerAnalyser.Lexer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser {
    private Lexer lexer;
    private Token currentToken;

    public JSONParser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    public JSONValue parse() throws ParseException {
        JSONValue value = parseValue();
        consumeWhitespace();
        if (currentToken != null) {
            throw new ParseException("Unexpected token after JSON value", 0);
        }
        return value;
    }

    private JSONValue parseValue() throws ParseException {
        JSONValue value;
        switch (currentToken.getType()) {
            case LEFT_BRACE:
                value = parseObject();
                break;
            case LEFT_BRACKET:
                value = parseArray();
                break;
            case STRING:
                value = parseString();
                break;
            case NUMBER:
                value = parseNumber();
                break;
            case TRUE:
                value = parseBoolean(true);
                break;
            case FALSE:
                value = parseBoolean(false);
                break;
            case NULL:
                value = parseNull();
                break;
            default:
                throw new ParseException("Unexpected token: " + currentToken.getValue(), 0);
        }
        return value;
    }

    // Methods for parsing various JSON value types
    private JSONObject parseObject() throws ParseException {
        expectToken(TokenType.LEFT_BRACE);
        advanceToken();

        Map<String, JSONValue> members = new HashMap<>();
        if (currentToken.getType() != TokenType.RIGHT_BRACE) {
            parseObjectMembers(members);
        }

        expectToken(TokenType.RIGHT_BRACE);
        advanceToken();

        return new JSONObject(members);
    }
    private void parseObjectMembers(Map<String, JSONValue> members) throws ParseException {
        do {
            String key = parseString().getValue();
            expectToken(TokenType.COLON);
            advanceToken();
            JSONValue value = parseValue();
            members.put(key, value);

            // Check for a comma to parse the next key-value pair
            if (currentToken.getType() == TokenType.COMMA) {
                advanceToken(); // Move past the comma
            } else {
                break; // Exit the loop if there's no comma
            }
        } while (currentToken.getType() != TokenType.RIGHT_BRACE);
    }
    private JSONArray parseArray() throws ParseException {
        expectToken(TokenType.LEFT_BRACKET);
        advanceToken();

        List<JSONValue> values = new ArrayList<>();
        if (currentToken.getType() != TokenType.RIGHT_BRACKET) {
            parseArrayValues(values);
        }

        expectToken(TokenType.RIGHT_BRACKET);
        advanceToken();

        return new JSONArray(values);
    }
    private void parseArrayValues(List<JSONValue> values) throws ParseException {
        do {
            values.add(parseValue());
            if (currentToken.getType() == TokenType.COMMA) {
                advanceToken(); // Advance to next token after comma
            } else {
                break; // Exit loop if there is no comma
            }
        } while (true);
    }
    private JSONString parseString() throws ParseException {
        String value = currentToken.getValue();
        expectToken(TokenType.STRING);
        advanceToken();
        return new JSONString(value);
    }
    private JSONNumber parseNumber() throws ParseException {
        double value = Double.parseDouble(currentToken.getValue());
        expectToken(TokenType.NUMBER);
        advanceToken();
        return new JSONNumber(value);
    }
    private JSONBoolean parseBoolean(boolean value) throws ParseException {
        expectToken(value ? TokenType.TRUE : TokenType.FALSE);
        advanceToken();
        return new JSONBoolean(value);
    }
    private JSONNull parseNull() throws ParseException {
        expectToken(TokenType.NULL);
        advanceToken();
        return new JSONNull();
    }

    // Helper Methods
    private void consumeWhitespace() {
        while (currentToken != null && currentToken.getType() == TokenType.WHITESPACE) {
            advanceToken();
        }
    }
    private void advanceToken() {
        currentToken = lexer.getNextToken();
    }
    private void expectToken(TokenType type) throws ParseException {
        if (currentToken == null || currentToken.getType() != type) {
            throw new ParseException("Expected token " + type + ", but got " + (currentToken != null ? currentToken.getType() : "null"), 0);
        }
    }
}
