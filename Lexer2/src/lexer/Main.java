package lexer;

import java.io.File;
import lexer.Token;

public class Main {

    public static void main(String[] args) {
        //System.out.println("HOLAAAAAAAAAAAA");
        //Token a = new Token(Token.type.RESERVED_WORD,12);
        //System.out.println(a.tokentype);
        
        String source = "./src/lexer/Lexer.flex";
        generateLexer(source);
    }
    
    public static void generateLexer(String source) {
        File file = new File(source);
        JFlex.Main.generate(file);
    }
    
}
