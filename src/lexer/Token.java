/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lexer;

/**
 *
 * @author Sara
 */
public class Token {
    KindToken kindToken;
    int line;

    public Token(KindToken kindToken, int line) {
        this.kindToken = kindToken;
        this.line = line;
    }
    
    
}
