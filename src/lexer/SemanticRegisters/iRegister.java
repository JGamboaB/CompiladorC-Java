/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lexer.SemanticRegisters;

/**
 *
 * @author Sara
 */
public interface iRegister {
    RegisterIf getAsRegisterIf();
    RegisterWhile getAsRegisterWhile();
    RegisterDo getAsRegisterDo();
    RegisterCompoundStatement getAsRegisterCompoundStatement();
    RegisterId getAsRegisterId();
    RegisterType getAsRegisterType();
    RegisterVar getAsRegisterVar();
    RegisterOperator getAsRegisterOperator();
}
