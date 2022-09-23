package lexer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

    public String sortLines(String lines){
        String[] numbersStr = lines.split(", ");
        int[][] numbers = new int[numbersStr.length][2]; // number, place in string (i) -> sort numbers
        
        for (int i = 0; i < numbersStr.length; i++){
            if (!numbersStr[i].contains("(")){
                numbers[i] = new int[]{Integer.parseInt(numbersStr[i]),i};
            } else {
                numbers[i] = new int[]{Integer.parseInt(numbersStr[i].substring(0,numbersStr[i].indexOf("("))),i};
            } 
        }
  
        List<int[]> list = Arrays.asList(numbers);
        list.sort(new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });
        
        String result = "";
        
        for (int[] value:list){
            result += numbersStr[value[1]] + ", ";
        }
        
        result = result.substring(0, result.length()-2); // Remove Last ','
        
        return result;
    }
    
    public String unEscapeString(String s){ // For Printing Escape Characters
        StringBuilder str = new StringBuilder();
        for (int i=0; i<s.length(); i++)
            switch (s.charAt(i)){
                case '\n': str.append("\\n"); break;
                case '\t': str.append("\\t"); break;
                case '\b': str.append("\\b"); break;
                case '\r': str.append("\\r"); break;
                case '\f': str.append("\\f"); break;
                case '\'': str.append("\\'"); break;
                case '\"': str.append("\\\""); break;
                case '\\': str.append("\\\\"); break;
                default: str.append(s.charAt(i));
            }
        return str.toString();
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
    
    public String[] getFormattedLines2 (HashMap<Integer, Integer> lines){
        int amount = 0;
        String lineString = "";
        for (Integer line : lines.keySet() ){
            if (lines.get(line)> 1){
                lineString += line + "(" +lines.get(line) + "), ";
                amount+=lines.get(line)-1;
            }else{
                lineString += line + ", "; 
            }
            amount++;
        }
        lineString = lineString.substring(0, lineString.length()-2); // Remove Last ','
              
        String[] result = new String[]{lineString, String.valueOf(amount)};
        return result;
    }
    
    @Override 
    public String toString(){
        String result = ">>";
        
        HashSet<String> tokensIn = new HashSet<>();
        for (Token token : this.counter.keySet()) {
            HashMap<Integer, Integer> lines = getLinesByTokenValue(token.value, token.kindToken);
            
            if (!tokensIn.contains(token.value)){
                tokensIn.add(token.value);
                result += "\ttoken: " + token.value + "\ttipo: " + token.kindToken + "\tlinea: " + getFormattedLines(lines)+"\n";
            }
        }
        return result;
    }
    
    public HashSet<String[]> getTokens(){
        HashSet<String[]> data = new HashSet<>();
        
        HashSet<String> tokensIn = new HashSet<>();
        for (Token token : this.counter.keySet()) {
            HashMap<Integer, Integer> lines = getLinesByTokenValue(token.value, token.kindToken);
            if (!tokensIn.contains(token.value)){
                tokensIn.add(token.value);
                String[] line_amount = getFormattedLines2(lines);
                String[] currentToken = {unEscapeString(token.value), token.kindToken.toString(), line_amount[1], sortLines(line_amount[0])};
                data.add(currentToken);
            }
        }
        
        return data;
        
        // Token appearances in table in order <--------
        
    }
}
