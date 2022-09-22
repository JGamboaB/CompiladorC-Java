package lexer;

import java.util.Objects;

public class Token {
    KindToken kindToken;
    int line;
    String value;

    public Token(String value, KindToken kindToken, int line) {
        this.value = value;
        this.kindToken = kindToken;
        this.line = line;
    }
    
    @Override
    public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) return false;
    Token that = (Token) o;
    return value.equals(that.value) &&
        kindToken.equals(that.kindToken)&&
        line == that.line;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.kindToken);
        hash = 29 * hash + this.line;
        hash = 29 * hash + Objects.hashCode(this.value);
        return hash;
    }
    
    
    
}
