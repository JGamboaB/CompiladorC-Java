/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

import java.util.HashMap;

/**
 *
 * @author Sara
 */
public class TokenCounter {
    private HashMap<String, Integer> counter = new HashMap<>() ;
    
    public void countToken(String tokenValue, String tokenKind, int line){
        String key = tokenValue + "/" + tokenKind + "/" +line;
        if(counter.containsKey(key)){
            counter.put(key, counter.get(key)+1);
            return;
        }
        counter.put(key, 1);
    }  
    
    
    // todo: decirle a juan de estos metodos y ver como usarlos en la tabla
    
    public String getTokenValue(String token){    
       return token.split("/")[0];
    }
    
    public String getTokenKind(String token){
       return token.split("/")[1];
    }
    
    public String getTokenLine(String token){
       return token.split("/")[2]; 
    }

    @Override // fix this
    public String toString(){
        String result = "hola";
        for (String token : counter.keySet()) {
            result = result.concat(this.getTokenValue(token)+"\t"+this.getTokenKind(token)+"\t"+this.getTokenLine(token));
        }
        return result;
    }
}
