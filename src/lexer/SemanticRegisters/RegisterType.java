/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer.SemanticRegisters;

/**
 *
 * @author Sara
 */
public class RegisterType implements iRegister{
    private String type;

    public String getType() {
        return type;
    }

    public RegisterType(String type) {
        this.type = type;
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
        return this;
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
