/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

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
