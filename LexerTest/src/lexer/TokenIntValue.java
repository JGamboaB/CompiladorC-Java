/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

/**
 *
 * @author Sara
 */
public class TokenIntValue extends Token{
    private int value;
    
    public TokenIntValue(KindToken kindToken, int line, int value) {
        super(kindToken, line);
        this.value = value;
    }
    
}
