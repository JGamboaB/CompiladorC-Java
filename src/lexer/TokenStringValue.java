/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

/**
 *
 * @author Sara
 */
public class TokenStringValue extends Token{
    private String value;
    
    public TokenStringValue(KindToken kindToken, int line, String value) {
        super(kindToken, line);
        this.value = value;
    }
    
}
