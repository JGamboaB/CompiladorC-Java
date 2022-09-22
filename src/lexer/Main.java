package lexer;

import java.io.File;


/**
 *
 * @author Sara Morales y Juan Gamboa
 */
public class Main {

    public static void main(String[] args) {
        String source = "./src/lexer/Lexer.flex";
        generateLexer(source);
    }
    
    public static void generateLexer(String source)
    {
        File file = new File(source);
        JFlex.Main.generate(file);
    }
}
