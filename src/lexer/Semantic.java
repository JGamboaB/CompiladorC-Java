package lexer;

import java.util.ArrayList;
import java.util.Collections;
import java_cup.runtime.Symbol;

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
        String label; // RS_Type, RS_ID, RS_OP, RS_IF, RS_WHILE
        String value;//token_actual -> Symbol s.value
        String type; //Address or Constant      // RS_DO
        String exit_label, else_while_label;    //RS_IF, RS_WHILE

        public RS(String label, String value) { //RS_Type, RS_ID, RS_OP
            this.label = label;
            this.value = value;
        }

        public RS(String label, String value, String type) { //RS_DO
            this.label = label;
            this.value = value;
            this.type = type;
        }
        
        public RS(String label) { //RS_IF, RS_WHILE
            this.label = label;
        }
        
        public RS(){}
    }
    
    //Pila 
    public static class Queue extends ArrayList<RS>{
        public RS getLastRS_Type(){
            Collections.reverse(this);
            for (RS i : this){
                if (i.label.equals("RS_Type")){
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
                    System.out.print("Exit_label: "+i.exit_label + "Else_While_label: "+i.else_while_label +" --> ");
                }
            }
        }
    }
    
    public static Queue SQueue = new Queue();
   
    
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
            System.out.print("\nname: " + this.symbolName + "\t" +
                "type: " + this.type + "\t" + "return-type: " + this.returnType + "\t" +
                "scope: " + this.scope + "\t" + "line: " + this.line);
        }
        
        public String getSymbolName() { return symbolName; }
        public String getType() { return type; }
        public String getReturnType() { return returnType; }
        public String getScope() { return scope; }
        public String getLine() { return line; }
        
        public void setSymbolName(String symbolName) { this.symbolName = symbolName; }
        public void setType(String type) { this.type = type; }
        public void setReturnType(String returnType) { this.returnType = returnType; }
        public void setScope(String scope) { this.scope = scope; }
        public void setLine(String line) { this.line = line; }
        
    }
    
    public static ArrayList<STNode> ST = new ArrayList();
    
    public boolean containsSymbolName(String n){
        for (STNode node: ST){
            if(node.symbolName.equals(n))
                return true;      
        }return false;
    }
    
    /*
            TO-DO
                1. Acciones semanticas
                2. Sintaxis
                3. Interfaz
                4. Generar ASM :c
                5. Documentacion :cc
    */
    
    // ====> Acciones Semanticas
    // Traduccion de Declaraciones
    public void rememberType(Symbol s){
        SQueue.add(new RS("RS_Type", (String) s.value));
    }
    
    public void rememberID(Symbol s){
        SQueue.add(new RS("RS_ID", (String) s.value));
    }
    
    public void declInsertTS(Symbol s){
        RS type = SQueue.getLastRS_Type();
        RS top = SQueue.get(-1);
        while (top != type){
            SQueue.remove(-1); //POP RS_ID
            //Verificar
            if (!containsSymbolName(top.value)){
                ST.add(new STNode(top.value, type.value, "scope", String.valueOf(s.right+1)));
            } else { //Error
                ST.add(new STNode(top.value, "ERROR", "scope", String.valueOf(s.right+1))); 
            } top = SQueue.get(-1);   
        } SQueue.remove(-1); //POP RS_Type
    }
    
    // Caso int a = 3+3/3; ??? -> | declarator EQUAL initializer (218)
    

    // Traduccion de expresiones --> Strings ignorados
    public void rememberConst(Symbol s){
        SQueue.add(new RS("RS_DO", (String) s.value, "Constant"));
    }
    
    public void rememberOP(Symbol s){
        SQueue.add(new RS("RS_OP", (String) s.value));
    }
    
    public void rememberVar(Symbol s){
        RS RS_DO = new RS("RS_DO", (String) s.value, "Address");
        //Verificar en ST
        if (!containsSymbolName(RS_DO.value)){ //Error
            ST.add(new STNode(RS_DO.value, "ERROR", "scope", String.valueOf(s.right+1))); 
        }
        SQueue.add(RS_DO);
    }
    
    public void evalBinary(){
        RS RS_DO2 = SQueue.get(-1), RS_OP = SQueue.get(-2), RS_DO1 = SQueue.get(-3);
        
        for (int i = 0; i < 3; i++)
            SQueue.remove(-1);
        
        //Validar tipos de DO junto con el operador -> Sumas, Restas, Multiplicaciones, Divisiones, y asignaciones) 
        //Falta: Validar Que no haya strings (excepto en asignacion)
        RS RS_DO = new RS();
        if ("Constant".equals(RS_DO1.type) && "Constant".equals(RS_DO2.type)){
            RS_DO.type = "Constant";
            
            int val = 0;
            switch(RS_OP.value){
                case "+":
                    val = Integer.valueOf(RS_DO1.value) + Integer.valueOf(RS_DO2.value);
                    break;
                case "-":
                    val = Integer.valueOf(RS_DO1.value) - Integer.valueOf(RS_DO2.value);
                    break;
                case "*":
                    val = Integer.valueOf(RS_DO1.value) * Integer.valueOf(RS_DO2.value);
                    break;
                case "/":
                    val = Integer.valueOf(RS_DO1.value) / Integer.valueOf(RS_DO2.value);
                    break;
                default:
                    //ERROR --> 1 = 1 
                    ;
            }
            
            RS_DO.value = String.valueOf(val);
            
        } else {
            int val1, val2;
            if (!RS_DO1.type.equals("Address"))
                val1 = Integer.valueOf(RS_DO1.value);
            else {
                //val1 = SQueue.   
            }
            
        }
     
        
        
    }
    
    public void evalUnary(){} //incremento (Var), decremento (Var)
    
    
    
    // if/else
    
    // while
    
    //Function <---
    
    /*
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
        
        
        ST.add(new STNode("main","function","int","global","21"));
        ST.add(new STNode("x","integer","local","1"));
        ST.add(new STNode("z","char","for loop","64"));
        for (STNode node : ST){
            node.print();
        }
    }*/
}
