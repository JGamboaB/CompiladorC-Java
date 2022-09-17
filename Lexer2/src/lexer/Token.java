package lexer;

public class Token {

    public Token(type ttype, int line, String value) {
        this.tokentype = ttype;
        this.line = line;
        this.value = value;
    }
    
    public Token(type ttype, int line) {
        this.tokentype = ttype;
        this.line = line;
    }
    
    public enum type {
        RESERVED_WORD,
        OPERATOR,
        IDENTIFIER,
        LITERAL,
        ERROR   
    }
    
    public type tokentype;
    public int line;
    public String value;   
}

