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
    private SemanticStack semanticStack = new SemanticStack();
    private String generatedCode = "";
    private int sequenceNumber = 0;
    private ArrayList<String> NASMVars = new ArrayList<>();
    
    public SemanticStack getSemanticStack() {
        return semanticStack;
    }

    public void setSemanticStack(SemanticStack semanticStack) {
        this.semanticStack = semanticStack;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }
    
    //Tabla de simbolos ST
    public static class STNode{
        private String symbolName;  // a
        private String type; // int
        private String returnType;
        private String line;
        private boolean active;
        private String nasm;

        public STNode(String symbolName, String type, String returnType, String line) {
            this.symbolName = symbolName;
            this.type = type;
            this.returnType = returnType;
            this.line = line;
        }
        
        public STNode(String symbolName, String type, String line) {
            this.symbolName = symbolName;
            this.type = type;
            this.returnType = null;
            this.line = line;
        }        
        
        public void print() {
            System.out.print("\nname: " + this.symbolName + "\t" +
                "type: " + this.type + "\t" + "return-type: " + this.returnType + "\t" +
                "scope: " + "\t" + "line: " + this.line);
        }
        
        public String getSymbolName() { return symbolName; }
        public String getType() { return type; }
        public String getReturnType() { return returnType; }
        public String getLine() { return line; }
        public boolean isActive() { return active; }
        public String getNasm() {return nasm;}
        
        public void setSymbolName(String symbolName) { this.symbolName = symbolName; }
        public void setType(String type) { this.type = type; }
        public void setReturnType(String returnType) { this.returnType = returnType; }
        public void setLine(String line) { this.line = line; }
        public void setActive(boolean active) {this.active = active;}
        public void setNasm(String nasm) {this.nasm = nasm;}
        
    }
    
    public static ArrayList<STNode> ST = new ArrayList();
    public void printST(){
        for (STNode n: ST){
            n.print();
        }
    }
    
    
    public boolean containsSymbolName(String n){
        for (STNode node: ST){
            if(node.symbolName.equals(n))
                return true;      
        }return false;
    }
    
    public STNode returnNode(String n){
        for (STNode node: ST){
            if(node.symbolName.equals(n))
                return node;      
        } return null;
    }
    
    public String getFreeNASMVar(){
        if (NASMVars.isEmpty()){
            NASMVars.add("R0");
            return "R0";
        }
        ArrayList<String> possibleMatches = NASMVars;
        possibleMatches.removeAll(semanticStack.getNASMVarsUsed());
        
        if (!possibleMatches.isEmpty()){
            return possibleMatches.get(0);
        } else {
            NASMVars.add("R"+NASMVars.size());
            return NASMVars.get(-1);
        }    
    }
    
    // ====> Acciones Semanticas
    
    // Traduccion de Declaraciones
    public void rememberType(Symbol s){
        RegisterType registerType =new RegisterType(s.value.toString());
        semanticStack.push(registerType);
    }
    
    public void rememberID(Symbol s){
      RegisterId registerId = new RegisterId(s.value.toString());
      semanticStack.push(registerId);
    }
    
    public void declInsertTS(Symbol s){
    // Caso int a = 3+3/3; ??? -> | declarator EQUAL initializer (218)
    
        RegisterType registerType = semanticStack.peekRegisterType();
        RegisterId registerId;
        do {
            registerId = semanticStack.popRegisterIdUntilRegisterType();
            if(registerId != null){
                if(!containsSymbolName(registerId.getName())){ // validar si no esta en tabla de simbolos
                    STNode n = new STNode(registerId.getName(), registerType.getType(), String.valueOf(s.right+1));
                    n.setActive(true); n.setNasm(getNextLabel(registerId.getName()));
                    ST.add(n);
                    semanticStack.push(new RegisterVar(registerId.getName()));
                } else {
                    // ToDo: Reportar error
                }

            }
        } while (registerId != null);

        semanticStack.popRegisterType();
    }
    
    // Traduccion de expresiones --> Strings ignorados
    public void rememberConst(Symbol s){
        semanticStack.push( new RegisterDo(s.value.toString(), KindDo.CONSTANT));
    }
    
    public void rememberOP(Symbol s){
        semanticStack.push( new RegisterOperator(s.value.toString()));
    }
    
    public void rememberVar(Symbol s){      
        RegisterDo registerDo = new RegisterDo(s.value.toString(), KindDo.ADDRESS);
        
        //Verificar que la variable este declarada en la tabla de símbolos:
        if (!containsSymbolName(registerDo.getValue())){ //Error
            ST.add(new STNode(registerDo.getValue(), "ERROR", String.valueOf(s.right+1))); 
        }
        semanticStack.push(registerDo);
    }
    
    
    
    public void evalBinary(){
        RegisterDo registerDo2 = semanticStack.popRegisterDo();//a = DO --> move [a]
        RegisterDo registerDo1 = semanticStack.popRegisterDo();
        RegisterOperator registerOperator = semanticStack.popRegisterOperator();//pop operands and operator
        RegisterDo DO = null;
        
        //Validate Compatible Types (int, char, short, long) != string & En Tabla de Simbolos --> generar NASMName
        
        if(registerDo1.getType() == KindDo.CONSTANT && registerDo2.getType() == KindDo.CONSTANT) {
            int val = 0;
            
            switch(registerOperator.getValue()){
                case "+" -> val = Integer.valueOf(registerDo1.getValue()) + Integer.valueOf(registerDo2.getValue());
                case "-" -> val = Integer.valueOf(registerDo1.getValue()) - Integer.valueOf(registerDo2.getValue());
                case "*" -> val = Integer.valueOf(registerDo1.getValue()) * Integer.valueOf(registerDo2.getValue());
                case "/" -> val = Integer.valueOf(registerDo1.getValue()) / Integer.valueOf(registerDo2.getValue());
                default -> {
                    //Tirar ERROR --> 1 = 2   
                }
            }
            DO = new RegisterDo(String.valueOf(val), KindDo.CONSTANT);
            
        } else if (registerDo1.getType() != KindDo.MEMORY && registerDo2.getType() != KindDo.MEMORY){
            String DO1 = registerDo1.getValue();
            String DO2 = registerDo2.getValue();
            String DO1copy = DO1;
            String RX = getFreeNASMVar();
            
            if (registerDo1.getType() == KindDo.ADDRESS)
                DO1 = "word ["+returnNode(DO1).nasm+"]"; //get NASMname from TABLE
                 
            if (registerDo1.getType() == KindDo.ADDRESS)
                DO2 = "word ["+returnNode(DO2).nasm+"]"; //get NASMname from TABLE
            
            switch(registerOperator.getValue()){
                case "+" -> {
                    generatedCode += "mov BX, " + DO1 + "\n" +
                            "mov CX, " + DO2 + "\n" +
                            "add BX, CX\n" +
                            "mov word["+RX+"], BX\n";
                    DO = new RegisterDo(RX, KindDo.MEMORY);
                    //Insert into Table with type and 'MEMORY'// ADD TO TABLE
                    //STNode n = new STNode(RX, DO1_Type, "-1"); //DO1_Type = int / char / long / short
                    //n.setActive(true); n.setNasm(RX);
                    //ST.add(n);
                } case "-" -> {
                    generatedCode += "mov BX, " + DO1 + "\n" +
                            "mov CX, " + DO2 + "\n" +
                            "sub BX, CX\n";
                    DO = new RegisterDo("BX", KindDo.MEMORY);
                    //Insert into Table with type and 'MEMORY'
                } case "*" -> {
                    //Must Check That AX & BX not used anywhere else in the Stack
                    
                    generatedCode += "mov AX, " + DO1 + "\n" +
                            "mov BX, " + DO2 + "\n" +
                            "mul BL\n";
                    DO = new RegisterDo("AX", KindDo.MEMORY);
                    //Insert into Table with type and 'MEMORY'
                } case "/" -> {
                    //Must Check That AX & BX not used anywhere else in the Stack
                    
                    generatedCode += "mov AX, " + DO1 + "\n" +
                            "mov BX, " + DO2 + "\n" +
                            "div BL\nxor AH, AH\n";
                    DO = new RegisterDo("AX", KindDo.MEMORY);
                    //Insert into Table with type and 'MEMORY'
                } default -> {
                    //Assignment  
                    if (registerDo1.getType() == KindDo.ADDRESS){ //Validate DO1 is ADDRESS
                        generatedCode += "mov " + DO1 + ", " + DO2 +"\n";
                        DO = new RegisterDo(DO1copy, KindDo.MEMORY);
                    } else {
                        //ERROR
                        //DO = new RegisterDo("ERROR", KindDo.MEMORY);
                    }     
                }     
            }
        } else { //Contains Memory Address
            
        }
        
        if (DO!=null)
            semanticStack.push(DO);  
    }
    
    public void evalAssignment(){
        RegisterDo registerDo2 = semanticStack.popRegisterDo();
        RegisterDo registerDo1 = semanticStack.popRegisterDo();
        RegisterOperator registerOperator = semanticStack.popRegisterOperator();//pop operands and operator 
        
        /*
        verificar que do1 es variable y esta definida en tabla de simbolos
        do2: si es variable y esta definida en tabla de simbolos, conseguir valor de tabla de simbolos
        
        actualizar valor de do1 en tabla de simbolos
        pop de do2, do1 y operador
        */
        
    }
    
    public void evalUnary(){
        //incremento (Var), decremento (Var)
        RegisterDo registerDo1 = semanticStack.popRegisterDo();
        RegisterOperator registerOperator = semanticStack.popRegisterOperator();//pop operands and operator 
        
        /*
        verificar que do1 es variable y esta definida en tabla de simbolos
        actualizar valor de do1 en tabla de simbolos
        pop de do1 y operador
        */
    } 
    
    // Acciones Semanticas para IF    
        /*
            -> selection_statement
            IF #startIf LPAR expression RPAR #testIf compound_statement ELSE #startElse statement #endIf
            IF #startIf LPAR expression RPAR #testIf compound_statement #endif 
        */
    
    public void startIf(){
        RegisterIf registerIf = new RegisterIf();// crear RS_IF
        
        registerIf.setLabelElse(getNextLabel("ELSE_LABEL")); // asignar 2 nombres de labels
        registerIf.setLabelExit(getNextLabel("EXIT_LABEL"));
        
        semanticStack.push(registerIf); //Push RS_IF
    }
    
    public void testIf(){ //Generar con sus JUMPS respectivos
        RegisterDo registerDo = semanticStack.popRegisterDo(); // este DO viene de un #evalBinary, trae el registro en value
        // ToDo: generar el codigo de la evaluacion segun la direccion de registerDo -> CMP [registro], 0 (?)
        generatedCode += "CMP ";
        generatedCode += "JZ " +semanticStack.peekRegisterIf().getLabelElse() + "\n"; // generar jump condicional con RS_IF.else_label
    }
    
    public void startElse(){
        generatedCode += "JUMP " + semanticStack.peekRegisterIf().getLabelExit() + "\n";
        generatedCode += semanticStack.peekRegisterIf().getLabelElse() + ":\n"; 
    }
    
    public void endIf(){
        generatedCode += semanticStack.peekRegisterIf().getLabelExit() + ":\n";
        semanticStack.popRegisterIf();
    }
    
    // Acciones Semanticas para WHILE
        /*
            -> iteration_statement
            WHILE #startWhile LPAR expression RPAR #testWhile compound_statement #endWhile
        */
    
    public void startWhile(){
        RegisterWhile registerWhile = new RegisterWhile(); // crear RS_WHILE
        
        registerWhile.setLabelWhile(getNextLabel("WHILE_LABEL")); // ASIGNAR 2 LABELS
        registerWhile.setLabelExit(getNextLabel("EXIT_LABEL"));
        
        generatedCode += registerWhile.getLabelWhile() + ":\n"; // general RS_WHILE.while_label + ":"
        
        semanticStack.push(registerWhile); // push RS_WHILE
        
    }
    
    public void testWhile(){
        RegisterDo registerDo = semanticStack.popRegisterDo();
        // ToDo: generar el codigo de la evaluacion segun la direccion de registerDo
        generatedCode += "JZ " + semanticStack.peekRegisterWhile().getLabelExit()+"/n"; // generar jump condicional con RS_WHILE.exit_label
    }
    
    public void endWhile(){
        generatedCode += "JUMP" + semanticStack.peekRegisterWhile().getLabelWhile() + "\n";  // JUMP WHILE_LABEL1
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
    
    public void registerContinue(){       
        if(semanticStack.peekRegisterWhile()!=null){
            generatedCode += "JUMP" + semanticStack.peekRegisterWhile().getLabelWhile()+"/n";
        } else {
            // ToDo: reportar error
        }
    }
    public void registerBreak(){
        if(semanticStack.peekRegisterWhile()!=null){
            generatedCode += "JUMP" + semanticStack.peekRegisterWhile().getLabelExit()+"/n";
        } else {
            // ToDo: reportar error
        }
    }
    
    // auxiliary functions
    
    public String getNextLabel(String labelName) {
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
    
    
    //Function <---
    
}

/*
TO DO:
Usar RX en evalBinary y que se inserte correctamente en la tabla;
evalLogical / evalBoolean <-- Solo en IFs y Whiles
evalUnary

IF, WHILE
Functions

Meter en sintaxis
Terminar UI

Documentacion
*/