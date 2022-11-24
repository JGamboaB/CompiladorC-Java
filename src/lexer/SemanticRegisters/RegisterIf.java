/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer.SemanticRegisters;

/**
 *
 * @author Sara
 */
public class RegisterIf implements iRegister{
    private String labelElse = "";
    private String labelExit = "";
    
    @Override
    public void print() {
        System.out.println("IF\tElseL: "+this.labelElse+"\tEL: "+this.labelExit);
    }

    public void setLabelElse(String labelElse) {
        this.labelElse = labelElse;
    }

    public void setLabelExit(String labelExit) {
        this.labelExit = labelExit;
    }

    public String getLabelElse() {
        return labelElse;
    }

    public String getLabelExit() {
        return labelExit;
    }

    @Override
    public RegisterIf getAsRegisterIf() {
        return this;
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
        return null;
    }
    
    
}
