/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

import java.util.ArrayList;
import lexer.SemanticRegisters.RegisterCompoundStatement;
import lexer.SemanticRegisters.RegisterDo;
import lexer.SemanticRegisters.RegisterId;
import lexer.SemanticRegisters.RegisterIf;
import lexer.SemanticRegisters.RegisterType;
import lexer.SemanticRegisters.RegisterVar;
import lexer.SemanticRegisters.RegisterWhile;
import lexer.SemanticRegisters.iRegister;

/**
 *
 * @author Sara
 */
public class SemanticStack {
    private ArrayList<iRegister> registers = new ArrayList();

    public ArrayList<iRegister> getRegisters() {
        return registers;
    }
    
    public void push(iRegister register){
        registers.add(register);
    }
    
    public RegisterIf popRegisterIf(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterIf registerIf = register.getAsRegisterIf();
            if(registerIf != null){
                registers.remove(i);
                return registerIf;
            }
        }
        return null;
    }
    public RegisterIf peekRegisterIf(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterIf registerIf = register.getAsRegisterIf();
            if(registerIf != null){
                return registerIf;
            }
        }
        return null;
    }
 
    public RegisterWhile popRegisterWhile(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterWhile registerWhile = register.getAsRegisterWhile();
            if(registerWhile != null){
                registers.remove(i);
                return registerWhile;
            }
        }
        return null;
    }
    public RegisterWhile peekRegisterWhile(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterWhile registerWhile = register.getAsRegisterWhile();
            if(registerWhile != null){
                return registerWhile;
            }
        }
        return null;
    }
    
    public RegisterDo popRegisterDo(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterDo registerDo = register.getAsRegisterDo();
            if(registerDo != null){
                registers.remove(i);
                return registerDo;
            }
        }
        return null;
    }
    public RegisterDo peekRegisterDo(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterDo registerDo = register.getAsRegisterDo();
            if(registerDo != null){
                return registerDo;
            }
        }
        return null;
    }
    
    public RegisterCompoundStatement popRegisterCompoundStatement(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterCompoundStatement registerCompoundStatement = register.getAsRegisterCompoundStatement();
            if(registerCompoundStatement != null){
                registers.remove(i);
                return registerCompoundStatement;
            }
        }
        return null;
    }
    public RegisterCompoundStatement peekRegisterCompoundStatement(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterCompoundStatement registerCompoundStatement = register.getAsRegisterCompoundStatement();
            if(registerCompoundStatement != null){
                return registerCompoundStatement;
            }
        }
        return null;
    }
    
    public RegisterType popRegisterType(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterType registerType = register.getAsRegisterType();
            if(registerType != null){
                registers.remove(i);
                return registerType;
            }
        }
        return null;
    }
    public RegisterType peekRegisterType(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterType registerType = register.getAsRegisterType();
            if(registerType != null){
                return registerType;
            }
        }
        return null;
    }
        
    public RegisterId popRegisterId(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterId registerId = register.getAsRegisterId();
            if(registerId != null){
                registers.remove(i);
                return registerId;
            }
        }
        return null;
    }
    public RegisterId peekRegisterId(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            RegisterId registerId = register.getAsRegisterId();
            if(registerId != null){
                return registerId;
            }
        }
        return null;
    }   
    
    public RegisterVar popRegisterVarUntilRegisterCompoundStatement(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            
            RegisterCompoundStatement registerCompoundStatement = register.getAsRegisterCompoundStatement();
            if(registerCompoundStatement != null){
                return null;
            }
            
            RegisterVar registerVar = register.getAsRegisterVar();
            if(registerVar != null){
                registers.remove(i);
                return registerVar;
            }
        }
        return null;
    }
    
    public RegisterId popRegisterIdUntilRegisterType(){
        for(int i = registers.size()-1; i>=0; i--){
            iRegister register = registers.get(i);
            
            RegisterType registerType = register.getAsRegisterType();
            if(registerType != null){
                return null;
            }
            
            RegisterId registerId = register.getAsRegisterId();
            if(registerId != null){
                registers.remove(i);
                return registerId;
            }
        }
        return null;
    }
}

