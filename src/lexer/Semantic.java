package lexer;

import java.util.ArrayList;
import java.util.Collections;

public class Semantic {
    /*Register -> Tipo, id, operador, DO (constante/direccion), if, while
    
    Tipo -> .tipo
    id -> .name
    DO -> .tipo, .valor / .name
    Operador -> .operador
    if -> exit_label, else_label
    while -> exit_label, while_label
   
    */
    
    public static class RS{
        String value;//token_actual -> Symbol s.value
    }
    
    public static class RS_Type extends RS{}
    public static class RS_ID extends RS{}
    public class RS_OP extends RS{}
    public class RS_DO extends RS{ String type; } //Address or Constant 
    public class RS_IF extends RS{ String exit_label, else_label; }
    public class RS_WHILE extends RS{ String exit_label, while_label; }
    
    //Pila 
    public static class Queue extends ArrayList<RS>{
        public RS getLastRS_Type(){
            Collections.reverse(this);
            for (RS i : this){
                if ("RS_Type".equals(((Object)i).getClass().getSimpleName())){
                    Collections.reverse(this);
                    return i;
                }
            }
            
            Collections.reverse(this);
            return null;
        }
        
        public void printQueue(){
            for (RS i: this){
                if (i.value != null)
                    System.out.print("Value: "+i.value + " --> ");
                else {
                    if ("RS_IF".equals(((Object)i).getClass().getSimpleName()))
                        System.out.print("Exit_label: "+((RS_IF)i).exit_label + "Else_label: "+((RS_IF)i).else_label +" --> ");
                    else
                        System.out.print("Exit_label: "+((RS_WHILE)i).exit_label + "While_label: "+((RS_WHILE)i).while_label +" --> ");
                }
            }
        }
    }
    
   
    
    //Tabla de simbolos ST
    
    public static class STNode{
        private String symbolName;
        private String type;
        private String returnType;
        private String scope;
        private String line;

        public STNode(String symbolName, String type, String returnType, String scope, String line) {
            this.symbolName = symbolName;
            this.type = type;
            this.returnType = returnType;
            this.scope = scope;
            this.line = line;
        }
        
        
        
        public STNode(String symbolName, String type, String scope, String line) {
            this.symbolName = symbolName;
            this.type = type;
            this.returnType = null;
            this.scope = scope;
            this.line = line;
        }        
        
        public void print() {
        
            System.out.print("\nname: " + this.symbolName + "\t");
            System.out.print("type: " + this.type + "\t");
            System.out.print("return type: " + this.returnType + "\t");
            System.out.print("scope: " + this.scope + "\t");
            System.out.print("line: " + this.line);
            
        }
        
        public String getSymbolName() {
            return symbolName;
        }

        public String getType() {
            return type;
        }

        public String getReturnType() {
            return returnType;
        }
        
        public String getScope() {
            return scope;
        }

        public String getLine() {
            return line;
        }

        public void setSymbolName(String symbolName) {
            this.symbolName = symbolName;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public void setLine(String line) {
            this.line = line;
        }
        
    }
    
    public static ArrayList<STNode> ST = new ArrayList();

    public static void main(String args[]) {
        int a, b;
        RS_Type var1 = new RS_Type();
        var1.value = "int";
        RS_ID var2 = new RS_ID();
        var2.value = "a";
        RS_ID var3 = new RS_ID();
        var3.value = "b";

        RS_Type var11 = new RS_Type();
        var11.value = "char";
        RS_ID var21 = new RS_ID();
        var21.value = "a";
        RS_ID var31 = new RS_ID();
        var31.value = "b";

        Queue q = new Queue();
        q.add(var1);
        q.add(var2);
        q.add(var3);
        q.add(var11);
        q.add(var21);
        q.add(var31);

        System.out.println(q.getLastRS_Type().value);
        q.printQueue();
        
        
        ST.add(new STNode("maain","function","int","global","21"));
        ST.add(new STNode("x","integer","local","1"));
        ST.add(new STNode("z","char","for loop","64"));
        for (STNode node : ST){
            node.print();
        }
        
        
        
        /*
            TO-DO
                1. Acciones semanticas
                2. Sintaxis
                3. Interfaz
                4. Generar ASM :c
                5. Documentacion :cc
        */

      
        
    }
    
}
