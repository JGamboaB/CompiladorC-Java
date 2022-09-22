package lexer;

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
