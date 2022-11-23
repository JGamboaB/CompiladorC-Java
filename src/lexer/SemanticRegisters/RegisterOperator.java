/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer.SemanticRegisters;

/**
 *
 * @author Sara
 */
public class RegisterOperator implements iRegister{
    
    private String value;

    public RegisterOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
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
        return null;
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
        return this;
    }
    
}
