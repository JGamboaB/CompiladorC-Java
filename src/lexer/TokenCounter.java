/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Sara
 */
public class TokenCounter {
    private HashMap<Token, Integer> counter = new HashMap<>() ;
    
    public void countToken(Token token){
        if(counter.containsKey(token)){
            counter.put(token, counter.get(token)+1);
            return;
        }
        counter.put(token, 1);
    }  
    
    
    
    public String getTokenValue(Token token){    
       return token.value;
    }
    
    public String getTokenKind(Token token){
       return token.kindToken.toString();
    }
    
    public String getTokenLine(Token token){
       return String.valueOf(token.line);
               
    }
    
    public HashMap<Integer, Integer> getLinesByTokenValue(String value, KindToken kindToken){
        HashMap<Integer, Integer> result = new HashMap<>();
        for (Token token : this.counter.keySet()) {
            if (token.value.equals(value) && token.kindToken == kindToken) {
                result.put(token.line, this.counter.get(token));
            }
        }
        return result;
    }

    
    public String getFormattedLines (HashMap<Integer, Integer> lines){
        String lineString = "";
        for (Integer line : lines.keySet() ){

            if (lines.get(line)> 1){
                lineString += line + "(" +lines.get(line) + "), "; 
            }else{
                lineString += line + ", "; 
            }
        }
        return lineString;
    }
    
    @Override 
    public String toString(){
        String result = ">>";
        
        HashSet<HashMap<Integer, Integer>> analizados = new HashSet<>();
        for (Token token : this.counter.keySet()) {
            HashMap<Integer, Integer> lines = getLinesByTokenValue(token.value, token.kindToken);
            if (!analizados.contains(lines)){
                analizados.add(lines);
                result += "\ttoken: " + token.value + "\ttipo: " + token.kindToken + "\tlinea: " + getFormattedLines(lines)+"\n";
            }
        }
        return result;
    }
    
    public HashSet<String[]> getTokens(){
        HashSet<String[]> data = new HashSet<>();
        
        HashSet<HashMap<Integer, Integer>> analizados = new HashSet<>();
        for (Token token : this.counter.keySet()) {
            HashMap<Integer, Integer> lines = getLinesByTokenValue(token.value, token.kindToken);
            if (!analizados.contains(lines)){
                analizados.add(lines);
                String[] currentToken = {token.value, token.kindToken.toString(), counter.get(token).toString(), getFormattedLines(lines)};
                data.add(currentToken);
                //result += "\ttoken: " + token.value + "\ttipo: " + token.kindToken + "\tlinea: " + getFormattedLines(lines)+"\n";
            }
        }
        
        return data;
        
        
        
        
        // add to frame
        
        // Count amount of times token appears, add to Token class?
        // Lines in order
        // Errors -> <String, Integer>
    }
}
