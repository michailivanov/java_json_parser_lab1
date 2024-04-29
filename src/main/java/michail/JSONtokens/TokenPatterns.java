package michail.JSONtokens;

import java.util.List;

public class TokenPatterns {
    public static final List<TokenPattern> PATTERNS = List.of(
            // 1st capturing group will be captured!
            new TokenPattern(TokenType.LEFT_BRACE, "(\\{)"),
            new TokenPattern(TokenType.RIGHT_BRACE, "(\\})"),
            new TokenPattern(TokenType.LEFT_BRACKET, "(\\[)"),
            new TokenPattern(TokenType.RIGHT_BRACKET, "(\\])"),
            new TokenPattern(TokenType.COLON, "(:)"),
            new TokenPattern(TokenType.COMMA, "(,)"),
            new TokenPattern(TokenType.TRUE, "(true)"),
            new TokenPattern(TokenType.FALSE, "(false)"),
            new TokenPattern(TokenType.NULL, "(null)"),
            new TokenPattern(TokenType.STRING, "\"((?:\\\\.|[^\\\\\"])*)\""),
            new TokenPattern(TokenType.NUMBER, "(-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?)")
    );
}
