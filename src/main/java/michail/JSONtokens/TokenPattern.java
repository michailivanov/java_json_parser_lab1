package michail.JSONtokens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenPattern {
    private final TokenType tokenType;
    private final Pattern pattern;

    public TokenPattern(TokenType tokenType, String regex) {
        this.tokenType = tokenType;
        this.pattern = Pattern.compile("^" + regex);
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Matcher matcher(String input) {
        return pattern.matcher(input);
    }
}



