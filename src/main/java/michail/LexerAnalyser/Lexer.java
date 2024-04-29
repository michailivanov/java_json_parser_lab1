package michail.LexerAnalyser;

import michail.JSONtokens.Token;
import michail.JSONtokens.TokenPattern;
import java.util.List;

import java.util.regex.Matcher;

public class Lexer {
    private final String input;
    private int currentPosition;
    private final List<TokenPattern> tokenPatterns;

    public Lexer(String input, List<TokenPattern> tokenPatterns) {
        this.input = input;
        this.currentPosition = 0;
        this.tokenPatterns = tokenPatterns;
    }

    public Token getNextToken() {
        if (currentPosition >= input.length()) {
            return null; // End of input line
        }

        consumeWhitespace();

        for (TokenPattern tokenPattern : tokenPatterns) {
            Matcher matcher = tokenPattern.matcher(input.substring(currentPosition));
            if (matcher.find()) {
                String allTokenValue = matcher.group(0);
                String tokenValue = matcher.group(1);
                currentPosition += allTokenValue.length();
                return new Token(tokenPattern.getTokenType(), tokenValue);
            }
        }

        throw new IllegalStateException("Unexpected character at position " + currentPosition);
    }

    private void consumeWhitespace() {
        while (currentPosition < input.length() && Character.isWhitespace(input.charAt(currentPosition))) {
            currentPosition++;
        }
    }
}