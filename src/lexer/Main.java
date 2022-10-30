package lexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Sara Morales y Juan Gamboa
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String source = "./src/lexer/Lexer.flex";
        String lexerCup = "./src/lexer/LexerCup.flex";
        String[] syntax = {"-expect", "1", "-parser", "Syntax", "./src/lexer/Syntax2.cup"};
        generateLexer(source, lexerCup, syntax);
    }
    
    public static void generateLexer(String source, String lexerCup, String[] syntax) throws IOException, Exception{
        File file = new File(source);
        JFlex.Main.generate(file);
        
        JFlex.Main.generate(file);
        file = new File(lexerCup);
        JFlex.Main.generate(file);
        java_cup.Main.main(syntax);
        
        Path routeSym = Paths.get("./src/lexer/sym.java");
        if (Files.exists(routeSym)) {
            Files.delete(routeSym);
        }
        Files.move(
                Paths.get("./sym.java"), 
                Paths.get("./src/lexer/sym.java")
        );
        Path routeSyn = Paths.get("./src/lexer/Syntax.java");
        if (Files.exists(routeSyn)) {
            Files.delete(routeSyn);
        }
        Files.move(
                Paths.get("./Syntax.java"), 
                Paths.get("./src/lexer/Syntax.java")
        );
    }
}
