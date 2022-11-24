/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer.SemanticRegisters;

/**
 *
 * @author Sara
 */
public class RegisterDo implements iRegister{
    private String value;
    private KindDo type;
    
    @Override
    public void print(){
        System.out.println("DO\tval: "+this.value+"\ttype: " + this.type);
    }

    public RegisterDo(String value, KindDo type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public KindDo getType() {
        return type;
    }
    
    
    @Override
    public RegisterIf getAsRegisterIf() {
        return null;
    }

    @Override
    public RegisterWhile getAsRegisterWhile() {
        return null;
    }

    @Override
    public RegisterDo getAsRegisterDo() {
        return this;
    }

    @Override
    public RegisterCompoundStatement getAsRegisterCompoundStatement() {
        return null;
    }

    @Override
    public RegisterId getAsRegisterId() {
        return null;
    }

    @Override
    public RegisterType getAsRegisterType() {
        return null;
    }

    @Override
    public RegisterVar getAsRegisterVar() {
        return null;
    }

    @Override
    public RegisterOperator getAsRegisterOperator() {
        return null;
    }
    
}
