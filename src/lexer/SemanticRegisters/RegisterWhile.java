/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer.SemanticRegisters;

/**
 *
 * @author Sara
 */
public class RegisterWhile implements iRegister {
    private String labelWhile = "";
    private String labelExit = "";

    public String getLabelWhile() {
        return labelWhile;
    }

    public String getLabelExit() {
        return labelExit;
    }

    public void setLabelWhile(String labelWhile) {
        this.labelWhile = labelWhile;
    }

    public void setLabelExit(String labelExit) {
        this.labelExit = labelExit;
    }
    
    @Override
    public RegisterIf getAsRegisterIf() {
        return null;
    }

    @Override
    public RegisterWhile getAsRegisterWhile() {
        return this;
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
    
}
