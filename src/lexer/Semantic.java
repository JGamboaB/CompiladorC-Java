package lexer;

import java.util.ArrayList;
import java.util.Collections;
import java_cup.runtime.Symbol;
import lexer.SemanticRegisters.KindDo;
import lexer.SemanticRegisters.RegisterCompoundStatement;
import lexer.SemanticRegisters.RegisterDo;
import lexer.SemanticRegisters.RegisterId;
import lexer.SemanticRegisters.RegisterIf;
import lexer.SemanticRegisters.RegisterOperator;
import lexer.SemanticRegisters.RegisterType;
import lexer.SemanticRegisters.RegisterVar;
import lexer.SemanticRegisters.RegisterWhile;
import lexer.SemanticRegisters.iRegister;

public class Semantic {
    private static SemanticStack semanticStack = new SemanticStack();
    private static String generatedCode = "";
    private static int sequenceNumber = 0;
    private static String semanticErrors = "";
    
    public SemanticStack getSemanticStack() {return semanticStack;}
    public void setSemanticStack(SemanticStack semanticStack) {this.semanticStack = semanticStack;}
    public int getSequenceNumber() { return sequenceNumber;}
    public void setSequenceNumber(int sequenceNumber) {this.sequenceNumber = sequenceNumber;}
    public static String getGeneratedCode() {return generatedCode;}
    public void setGeneratedCode(String generatedCode) {this.generatedCode = generatedCode;}
    
    public static String getErrors(){ return semanticErrors; }
    
    public static void printErrors(){System.out.println("/ / / ERRORS / / /\n"+semanticErrors);}
    public static void printSemanticStack(){ System.out.println("/ / / Semantic Stack / / /"); semanticStack.print(); System.out.println(); }
    
    //Tabla de simbolos ST
    public static class STNode{
        private String symbolName;  // a
        private String type; // int
        private String returnType;
        private String scope; //Global, Local, Function-Parameter
        private String line;

        public STNode(String symbolName, String type, String returnType, String line) { //Function
            this.symbolName = symbolName;
            this.type = type;
            this.returnType = returnType;
            this.line = line;
        }
        
        public STNode(String symbolName, String type, String line) { //Variable
            this.symbolName = symbolName;
            this.type = type;
            this.returnType = null;
            this.line = line;
        }        
        
        public void print() {
            System.out.println("Name: " + this.symbolName + "\t" +
                "Type: " + this.type + "\t" + "Return-type: " + this.returnType + "\t" +
                "Scope: " + this.scope + "\t" + "Line: " + this.line + "\n");
        }
        
        public String getText(){
            return "Name: " + this.symbolName + "\t" +
                "Type: " + this.type + "\t" + "Return-type: " + this.returnType + "\t" +
                "Scope: " + this.scope + "\t" + "Line: " + this.line + "\n\n";
        }
        
        public String getSymbolName() { return symbolName; }
        public String getType() { return type; }
        public String getReturnType() { return returnType; }
        public String getLine() { return line; }
        
        public void setSymbolName(String symbolName) { this.symbolName = symbolName; }
        public void setType(String type) { this.type = type; }
        public void setReturnType(String returnType) { this.returnType = returnType; }
        public void setLine(String line) { this.line = line; }
        public void setScope(String s) {this.scope = s;}
    }
    
    public static ArrayList<STNode> ST = new ArrayList();
    public static void printST(){
        System.out.println("/ / / / Symbols Table / / / /");
        for (STNode n: ST){
            n.print();
        }
    }
    
    public static String getTextST(){
        String res = "";
        for (STNode n: ST){
            res += n.getText();
        } return res;
    }
    
    public static boolean containsSymbolName(String n){
        for (STNode node: ST){
            if(node.symbolName.equals(n))
                return true;      
        }return false;
    }
    
    public static STNode returnNode(String n){
        for (STNode node: ST){
            if(node.symbolName.equals(n))
                return node;      
        } return null;
    }
    
    public static void newRun(){
        semanticStack = new SemanticStack();
        generatedCode = "";
        sequenceNumber = 0;
        semanticErrors = "";
        ST = new ArrayList();
        System.out.println("End of Run");
    }
    
    // / / / / / / / / / / / / / / / / / / / / / / / / / / Acciones Semanticas
    
    // Traduccion de Declaraciones
    public static void rememberType(String i){
        RegisterType registerType = new RegisterType(i);
        semanticStack.push(registerType);
    }
    
    public static void rememberID(String i){
      RegisterId registerId = new RegisterId(i);
      semanticStack.push(registerId);
    }
    
    public static void declInsertTS(String i, int iright, int ileft){ 
        RegisterType registerType = semanticStack.peekRegisterType();
        RegisterId registerId;
        do {
            registerId = semanticStack.popRegisterIdUntilRegisterType();
            if(registerId != null){
                if(!containsSymbolName(registerId.getName())){ // Validar que no esté en tabla de simbolos.
                    STNode n = new STNode(registerId.getName(), registerType.getType(), String.valueOf(iright+1));
                    ST.add(n);
                    semanticStack.push(new RegisterVar(registerId.getName()));
                } else { //Reportar error que esta.
                    semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: '" + registerId.getName()
                            + "'): Name already used to declare a variable/function.\n\n";
                    break;
                }
            }
        } while (registerId != null);
        semanticStack.popRegisterType();
    }
    
    // Traduccion de funciones
    public static void declFunction(String i, int iright, int ileft){ // SEMANTIC STACK MUST BE CLEAN OF R_TYPES AND R_IDs <--------
        RegisterId RID_func = semanticStack.getFirstRegisterId(); //main
        
        RegisterId RID = semanticStack.popRegisterId(); //r
        RegisterType RType = semanticStack.popRegisterType(); //int(r)
        while (RID != RID_func){ //Parameters here
            if (!containsSymbolName(RID.getName())){ //No en la tabla de simbolos
                STNode n = new STNode(RID.getName(), RType.getType(), String.valueOf(iright+1));
                n.setScope("Function-parameter");
                ST.add(n);
                semanticStack.push(new RegisterVar(RID.getName()));
            } else {
                semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: '" + RID.getName()
                    + "'): Name already used to declare a variable/function/parameter.\n\n";
            } 
            RID = semanticStack.popRegisterId(); 
            RType = semanticStack.popRegisterType();
        }
        
        // Function
        
        if(!containsSymbolName(RID.getName())){ // Validar que no esté en tabla de simbolos.
            STNode n = new STNode(RID.getName(), "function",RType.getType(), String.valueOf(iright+1));
            n.setScope("Global");
            ST.add(n);
            semanticStack.push(new RegisterVar(RID.getName()));
        } else { //Reportar error que esta.
            semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: '" + RID.getName()
                + "'): Name already used to declare a variable/function.\n\n";
        }
    }
    
    // Traduccion de expresiones
    public static void rememberConst(String s){
        semanticStack.push( new RegisterDo(s, KindDo.CONSTANT));
    }
    
    public static void rememberOP(String s){
        semanticStack.push( new RegisterOperator(s));
    }
    
    public static void rememberVar(String i, int iright, int ileft){      
        RegisterDo DO = new RegisterDo(i, KindDo.ADDRESS);
        
        //Verificar que la variable este declarada en la tabla de símbolos:
        if (!containsSymbolName(i)){ //Error
            ST.add(new STNode(DO.getValue(), "ERROR", String.valueOf(iright+1))); 
            semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: " + i
                            + "): Variable/function not declared.\n\n";
        }
        semanticStack.push(DO);
    }
    
    
    
    public static void evalBinary(String i, int iright, int ileft){
        RegisterDo DO2 = semanticStack.popRegisterDo();
        RegisterDo DO1 = semanticStack.popRegisterDo();
        RegisterOperator OP = semanticStack.popRegisterOperator();//pop operands and operator
        RegisterDo DO;
        
        // Validate Compatible Types (int, char, short, long) != string //Nasm name generated by getting variable/funcion ///////////////
        // If KinDo.MEMORY Don't validate types because it must have been validated before (?) --> FIX LATER /////////
        
        if(DO1.getType() == KindDo.CONSTANT && DO2.getType() == KindDo.CONSTANT) {
            int val;
            
            switch(OP.getValue()){
                case "+" -> val = Integer.valueOf(DO1.getValue()) + Integer.valueOf(DO2.getValue());
                case "-" -> val = Integer.valueOf(DO1.getValue()) - Integer.valueOf(DO2.getValue());
                case "*" -> val = Integer.valueOf(DO1.getValue()) * Integer.valueOf(DO2.getValue());
                case "/" -> val = Integer.valueOf(DO1.getValue()) / Integer.valueOf(DO2.getValue());
                default -> {
                    semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: " + i
                            + "): Invalid mix of operand(s) with operation.\n\n";  
                    return; // EXIT AND DON'T ADD ANYTHING TO THE STACK
                }
            }
            DO = new RegisterDo(String.valueOf(val), KindDo.CONSTANT);
            
        } else { //if (DO1.getType() != KindDo.MEMORY && DO2.getType() != KindDo.MEMORY){
            String DO1val = DO1.getValue();
            String DO2val = DO2.getValue();
            String DO1valcopy = DO1val;
            
            if (DO1.getType() == KindDo.ADDRESS)
                DO1val = "word ["+DO1val+"]";
                 
            if (DO2.getType() == KindDo.ADDRESS)
                DO2val = "word ["+DO2val+"]";
            
            switch(OP.getValue()){
                case "+" -> {
                    generatedCode += "MOV BX, " + DO1val + "\n" +
                            "MOV CX, " + DO2val + "\n" +
                            "ADD BX, CX\n\n";
                    DO = new RegisterDo("BX", KindDo.MEMORY);
                } case "-" -> {
                    generatedCode += "MOV BX, " + DO1val + "\n" +
                            "MOV CX, " + DO2val + "\n" +
                            "SUB BX, CX\n\n";
                    DO = new RegisterDo("BX", KindDo.MEMORY);
                } case "*" -> {                  
                    generatedCode += "MOV AX, " + DO1val + "\n" +
                            "MOV BX, " + DO2val + "\n" +
                            "MUL BL\n\n";
                    DO = new RegisterDo("AX", KindDo.MEMORY);
                } case "/" -> {
                    generatedCode += "MOV AX, " + DO1val + "\n" + //Must Check That AX & BX not used anywhere else in the Stack ??
                            "MOV BX, " + DO2val + "\n" +
                            "DIV BL\nxor AH, AH\n\n";
                    DO = new RegisterDo("AX", KindDo.MEMORY);
                } default -> {
                    //Assignment  
                    if (DO1.getType() == KindDo.ADDRESS){ //Validate DO1 is ADDRESS
                        generatedCode += "mov " + DO1val + ", " + DO2val +"\n\n";
                        DO = new RegisterDo(DO1valcopy, KindDo.ADDRESS);
                    } else { //ERROR
                        semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: " + i
                            + "): Invalid mix of operand(s) with operation.\n\n";
                        return; // EXIT AND DON'T ADD ANYTHING TO THE STACK
                    }     
                }     
            }
        } // else {} //Contains Memory Address
        semanticStack.push(DO);  
    }
    
    public static void evalLogical(String i, int iright, int ileft){
        RegisterDo DO2 = semanticStack.popRegisterDo();
        RegisterDo DO1 = semanticStack.popRegisterDo();
        RegisterOperator OP = semanticStack.popRegisterOperator();//pop operands and operator
        
        // Validate Compatible Types (int, char, short, long) != string /////////////// -> Try Catch ???
        String DO1val = DO1.getValue();
        String DO2val = DO2.getValue();
        
        if (DO1.getType() == KindDo.ADDRESS)
            DO1val = "["+DO1val+"]";

        if (DO2.getType() == KindDo.ADDRESS)
            DO2val = "["+DO2val+"]";

        switch(OP.getValue()){
            case "==" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "CMP BX, "+ DO2val +"\n" +
                        "JNZ ";
            } case "!=" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "CMP BX, "+ DO2val +"\n" +
                        "JZ ";
            } case ">=" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "CMP BX, "+ DO2val +"\n" +
                        "JL ";
            } case ">" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "CMP BX, "+ DO2val +"\n" +
                        "JLE ";
            } case "<=" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "CMP BX, "+ DO2val +"\n" +
                        "JG ";
            } case "<" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "CMP BX, "+ DO2val +"\n" +
                        "JGE ";  
            } case "&&" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "TEST BX, "+ DO2val +"\n" +
                        "JZ ";  
            } case "||" -> {
                generatedCode += "MOV BX, " + DO1val + "\n" + //Const or Address
                        "OR BX, "+ DO2val +"\n" +
                        "JZ ";  
            }
        } 
    }
      
    public static void evalUnary(int iright, int ileft){ //incremento (Var), decremento (Var)
        RegisterDo DO1 = semanticStack.popRegisterDo();
        String DO1val = DO1.getValue();
        RegisterOperator OP = semanticStack.popRegisterOperator();//pop operands and operator 
        
        //DO1 already in table
        // Check it's type can be used in operation -- Anything except string
        
        if (DO1.getType() != KindDo.ADDRESS){ //Not a Variable
            semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: " + DO1val
                            + "): Invalid mix of operand(s) with operation.\n\n";
            return; // EXIT AND DON'T ADD ANYTHING TO THE STACK
        }
        
        switch (OP.getValue()){
            case "++" -> generatedCode += "INC word [" + DO1val + "]\n\n";
            case "--" -> generatedCode += "DEC word [" + DO1val + "]\n\n";
        }
        
        RegisterDo DO = new RegisterDo(DO1val, KindDo.ADDRESS);
        semanticStack.push(DO);
    } 
    
    
    // Acciones Semanticas para IF    
        /*
            -> selection_statement
            IF #startIf LPAR expression RPAR #testIf compound_statement ELSE #startElse statement #endIf
            IF #startIf LPAR expression RPAR #testIf compound_statement #endif    
        */
    
    public static void startIf(){
        RegisterIf IF = new RegisterIf();// crear RS_IF
        IF.setLabelElse(getNextLabel("ELSE_LABEL")); // asignar 2 nombres de labels
        IF.setLabelExit(getNextLabel("EXIT_LABEL"));
        semanticStack.push(IF); //Push RS_IF
    }
    
    public static void testIf(){ //Generar con sus JUMPS respectivos
        generatedCode += semanticStack.peekRegisterIf().getLabelElse() + "\n"; //Else label //Used as exit if there's no else
        
        //RegisterDo registerDo = semanticStack.popRegisterDo(); // este DO viene de un #evalBinary, trae el registro en value
        // ToDo: generar el codigo de la evaluacion segun la direccion de registerDo -> CMP [registro], 0 (?)
        //generatedCode += "CMP ";
        //generatedCode += "JZ " + label + "\n"; // generar jump condicional con RS_IF.else_label
    }
    
    public static void startElse(){
        generatedCode += "JMP " + semanticStack.peekRegisterIf().getLabelExit() + "\n\n";
        generatedCode += semanticStack.peekRegisterIf().getLabelElse() + ":\n"; 
    }
    
    public static void endIf(){
        generatedCode += semanticStack.peekRegisterIf().getLabelExit() + ":\n";
        semanticStack.popRegisterIf();
    }
    
    // Acciones Semanticas para WHILE
        /* -> iteration_statement
            WHILE #startWhile LPAR expression RPAR #testWhile compound_statement #endWhile
        */
    
    public static void startWhile(){
        RegisterWhile WHILE = new RegisterWhile(); // crear RS_WHILE
        WHILE.setLabelWhile(getNextLabel("WHILE_LABEL")); // ASIGNAR 2 LABELS
        WHILE.setLabelExit(getNextLabel("EXIT_LABEL"));
        generatedCode += WHILE.getLabelWhile() + ":\n"; // general RS_WHILE.while_label + ":"
        semanticStack.push(WHILE); // push RS_WHILE
        
    }
    
    public static void testWhile(){
        generatedCode += semanticStack.peekRegisterWhile().getLabelExit() + "\n\n";
        
        //RegisterDo registerDo = semanticStack.popRegisterDo();
        // ToDo: generar el codigo de la evaluacion segun la direccion de registerDo
        //generatedCode += "JZ " + semanticStack.peekRegisterWhile().getLabelExit()+"/n"; // generar jump condicional con RS_WHILE.exit_label
    }
    
    public static void endWhile(){
        generatedCode += "JMP " + semanticStack.peekRegisterWhile().getLabelWhile() + "\n\n";  // JUMP WHILE_LABEL1
        generatedCode += semanticStack.peekRegisterWhile().getLabelExit() + ":\n"; // EXIT_LABEL:
        semanticStack.popRegisterWhile();
    }    
    
    // Acciones Semanticas para COMPOUND STATEMENT (bloque)
        /*
            -> compound_statement
            #startCompoundStatement LBRACES RBRACES #endCompoundStatement
            #startCompoundStatement LBRACES statement_list RBRACES #endCompoundStatement
            #startCompoundStatement LBRACES declaration_list RBRACES #endCompoundStatement
            #startCompoundStatement LBRACES declaration_list statement_list RBRACES #endCompoundStatement
    
            - Maneja el SCOPE
            - aplica en IF, WHILE, ...
        */
    
    public void startCompoundStatement(){
        semanticStack.push(new RegisterCompoundStatement()); // meter un registo de compound statement
    }
    
    public void endCompoundStatement(){
        RegisterVar registerVar;
        do {
            registerVar = semanticStack.popRegisterVarUntilRegisterCompoundStatement();
        } while (registerVar != null);
        
        semanticStack.popRegisterCompoundStatement();
    }
    
    // Acciones Semanticas para BREAK y CONTINUE
        /*
            -> jump_statement
            CONTINUE #registerContinue SEMICOLON 
            BREAK #registerBreak SEMICOLON
    
            ToDo: (maybe) implementar analisis semantico para el for (no generacion de codigo)
        */
    
    public void registerContinue(int iright, int ileft){  
        if(semanticStack.peekRegisterWhile() == null)
            semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: 'continue'): Continue outside of Loop.\n\n";
        
        /*
        if(semanticStack.peekRegisterWhile()!=null){
            generatedCode += "JMP" + semanticStack.peekRegisterWhile().getLabelWhile()+"\n";
        } else {// ToDo: reportar error
        }*/
    }
    public void registerBreak(int iright, int ileft){
        if(semanticStack.peekRegisterWhile() == null)
            semanticErrors += "Error (Line: " + (iright+1) + ", Column: " + (ileft + 1) + ", Value: 'break'): Continue outside of Loop.\n\n";

        /*
        if(semanticStack.peekRegisterWhile()!=null){
            generatedCode += "JMP" + semanticStack.peekRegisterWhile().getLabelExit()+"\n";
        } else { // ToDo: reportar error
        }*/
    }
    
    // auxiliary functions
    public static String getNextLabel(String labelName) {
        return labelName + sequenceNumber++;
    }
        
    public STNode findSymbol(String symbolName){
        for(STNode symbol : ST){
            if(symbolName.equals(symbol.getSymbolName())){
                return symbol;
            
            }
        }
        return null;
    }
    
    public boolean isOperable(STNode symbol1, STNode symbol2, String operator){
        // ToDo: reemplazar logica a que solo vea si se esta operando STRING con !STRING (char?)
        return symbol1.getType().equals(symbol2.getType());
    }
    
    
}

/*
TO DO:
evalLogical / evalBoolean <-- Solo en IFs y Whiles

IF, WHILE

Documentacion
*/