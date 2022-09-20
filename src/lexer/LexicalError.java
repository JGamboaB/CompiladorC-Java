/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

/**
 *
 * @author Sara
 */
public class LexicalError extends Error{
    int line;

    public LexicalError(String message, int line) {
        super(message);
        this.line = line;
    }
    
    public int getLine(){
        return this.line;
    }
    
    
}
