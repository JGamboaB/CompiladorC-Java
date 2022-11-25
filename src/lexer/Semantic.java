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
        // SQueue.add(new RS("RS_Type", (String) s.value));
        RegisterType registerType =new RegisterType(s.value.toString());
        semanticStack.push(registerType);
    }
    
    public void rememberID(Symbol s){
      //  SQueue.add(new RS("RS_ID", (String) s.value));
      RegisterId registerId = new RegisterId(s.value.toString());
      semanticStack.push(registerId);
    }
    
    public void declInsertTS(Symbol s){
    /*
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
    */
    // Caso int a = 3+3/3; ??? -> | declarator EQUAL initializer (218)
    
        RegisterType registerType = semanticStack.peekRegisterType();
        RegisterId registerId;
        do {
            registerId = semanticStack.popRegisterIdUntilRegisterType();
            if(registerId != null){
                if(!containsSymbolName(registerId.getName())){ // validar si no esta en tabla de simbolos
                    ST.add(new STNode(registerId.getName(), registerType.getType(), String.valueOf(s.right+1)));
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
        // SQueue.add(new RS("RS_DO", (String) s.value, "Constant"));
        semanticStack.push( new RegisterDo(s.value.toString(), KindDo.CONSTANT));
    }
    
    public void rememberOP(Symbol s){
        // SQueue.add(new RS("RS_OP", (String) s.value));
        semanticStack.push( new RegisterOperator(s.value.toString()));
    }
    
    public void rememberVar(Symbol s){
        /*
        RS RS_DO = new RS("RS_DO", (String) s.value, "Address");
        //Verificar en ST
        if (!containsSymbolName(RS_DO.value)){ //Error
            ST.add(new STNode(RS_DO.value, "ERROR", "scope", String.valueOf(s.right+1))); 
        }
        SQueue.add(RS_DO);
        */
        
        RegisterDo registerDo = new RegisterDo(s.value.toString(), KindDo.ADDRESS);
        
        //Verificar que la variable este declarada en la tabla de símbolos:
        if (!containsSymbolName(registerDo.getValue())){ //Error
            ST.add(new STNode(registerDo.getValue(), "ERROR", String.valueOf(s.right+1))); 
        }
        semanticStack.push(registerDo);
    }
    
    public String[] getRegisterNotUsed(String RG){
        switch(RG){
            case "BX": return new String[]{"CX", "DX"};
            case "CX": return new String[]{"BX", "DX"};
            case "DX": return new String[]{"BX", "CX"};
            default: return new String[]{"BX", "CX"};
        }
    }
    
    public void evalBinary(){
        RegisterDo registerDo2 = semanticStack.popRegisterDo();//a = DO --> move [a]
        RegisterDo registerDo1 = semanticStack.popRegisterDo();
        RegisterOperator registerOperator = semanticStack.popRegisterOperator();//pop operands and operator
        RegisterDo DO;
        
        if(registerDo1.getType() == KindDo.CONSTANT && registerDo2.getType() == KindDo.CONSTANT) {
            int val = 0;
            boolean bool;
            
            switch(registerOperator.getValue()){
                case "+" -> val = Integer.valueOf(registerDo1.getValue()) + Integer.valueOf(registerDo2.getValue());
                case "-" -> val = Integer.valueOf(registerDo1.getValue()) - Integer.valueOf(registerDo2.getValue());
                case "*" -> val = Integer.valueOf(registerDo1.getValue()) * Integer.valueOf(registerDo2.getValue());
                case "/" -> val = Integer.valueOf(registerDo1.getValue()) / Integer.valueOf(registerDo2.getValue());
                case "==" -> {
                    bool = Integer.valueOf(registerDo1.getValue()) == Integer.valueOf(registerDo2.getValue());
                    val = (bool)? 1 : 0;
                }
                case "!=" -> { 
                    bool = Integer.valueOf(registerDo1.getValue()) != Integer.valueOf(registerDo2.getValue());
                    val = (bool)? 1 : 0;
                }
                case ">=" -> {
                    bool = Integer.valueOf(registerDo1.getValue()) >= Integer.valueOf(registerDo2.getValue());
                    val = (bool)? 1 : 0;
                }
                case ">" -> {
                    bool = Integer.valueOf(registerDo1.getValue()) > Integer.valueOf(registerDo2.getValue());
                    val = (bool)? 1 : 0;
                }
                case "<=" -> {
                    bool = Integer.valueOf(registerDo1.getValue()) <= Integer.valueOf(registerDo2.getValue());
                    val = (bool)? 1 : 0;
                }
                case "<" -> {
                    bool = Integer.valueOf(registerDo1.getValue()) < Integer.valueOf(registerDo2.getValue());
                    val = (bool)? 1 : 0;
                }
                case "&&" -> {
                    int a = Integer.parseInt(registerDo1.getValue()), b = Integer.parseInt(registerDo2.getValue());
                    var ax = (a==1);
                    var bx = (b==1);
                    bool = ax && bx;
                    val = (bool)? 1 : 0;
                }
                case "||" -> {
                    int c = Integer.parseInt(registerDo1.getValue()), d = Integer.parseInt(registerDo2.getValue());
                    var cx = (c==1);
                    var dx = (d==1);
                    bool = cx || dx;
                    val = (bool)? 1 : 0;
                }
                default -> {
                    //Tirar ERROR --> 1 = 2   
                }
            }
            DO = new RegisterDo(String.valueOf(val), KindDo.CONSTANT);
            
        } else {
            String DO1 = registerDo1.getValue();
            String DO2 = registerDo2.getValue();
            String REGISTER = "AX";
            
            switch (registerDo1.getType()){
                case ADDRESS:
                    DO1 = "word ["+DO1+"]";
                    break;
                case MEMORY:
                    REGISTER = DO1;
                    break;
            }
            
            switch (registerDo2.getType()){
                case ADDRESS:
                    DO2 = "word ["+DO2+"]";
                    break;
                case MEMORY:
                    REGISTER = DO2;
                    break;
            }
            String RGs[] = getRegisterNotUsed(REGISTER);
            

            switch(registerOperator.getValue()){
                case "+":
                    generatedCode += "mov " + RGs[0] + ", " + DO1 + "\n" +
                            "mov " + RGs[1] + ", " + DO2 + "\n" +
                            "add " + RGs[0] + ", " + RGs[1] + "\n";
                    DO = new RegisterDo(RGs[0], KindDo.MEMORY);
                    break;
                    
            }
        } 
        
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
    
/*    
    public void evalBinary1(){
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
    */
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
    
    public void testIf(){
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
