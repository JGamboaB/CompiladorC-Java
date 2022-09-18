/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

/**
 *
 * @author Sara
 */
public class TokenFloatValue extends Token{
    
    private float value;
    
    public TokenFloatValue(KindToken kindToken, int line, float value) {
        super(kindToken, line);
        this.value = value;
    }
    
}
