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
    
    public class RS{
        String value;//token_actual -> Symbol s.value
    }
    
    public class RS_Type extends RS{}
    public class RS_ID extends RS{}
    public class RS_OP extends RS{}
    public class RS_DO extends RS{ String type; } //Address or Constant 
    public class RS_IF extends RS{ String exit_label, else_label; }
    public class RS_WHILE extends RS{ String exit_label, while_label; }
    
    //Pila 
    public class Queue extends ArrayList<RS>{
        public RS getLastRS_Type(){
            Collections.reverse(this);
            for (RS i : this){
                if (((Object)i).getClass().getSimpleName() == "RS_Type"){
                    Collections.reverse(this);
                    return i;
                }
            }
            
            Collections.reverse(this);
            return null;
        }
    }
    
    int a, b;
    RS_Type var1 = new RS_Type();
    RS_ID var2 = new RS_ID();
    RS_ID var3 = new RS_ID();
    
    Queue q = new Queue();
    q.add(var1);
    
    
    
    //Method find by specific RS ???
    
    //Tabla de simbolos TS
}
