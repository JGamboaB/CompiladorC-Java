/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lexer;

import java.util.Objects;

/**
 *
 * @author Sara
 */
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
