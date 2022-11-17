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
    }
    
    
    
    
    //Method find by specific RS ???
    
    //Tabla de simbolos TS
}
