package lexer;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char

%{
  
  StringBuffer string = new StringBuffer();StringBuffer string = new StringBuffer();

  private Symbol symbol(int type, Object value){
    return new Symbols(type, yyline, yycolumn, value)
  }

  private Symbol symbol(int type){
    return new Symbols(type, yyline, yycolumn)
  }

%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [a-zA-Z_][a-zA-Z_0-9]* 
String = \"([^\\\"]|\\.)*\"

DecIntegerLiteral = 0|[1-9][0-9]*
OctIntegerLiteral = 0[0-7]+
HexIntegerLiteral = 0[xX][0-9A-Fa-f]+
FloatIntegerLiteral = [0-9]*\.[0-9]+([eE]-?[0-9]+)?

CharAll = '.'
CharN = '\\n'
CharR = '\\r'
CharT = '\\t'
CharC = '\\''
CharB = '\\\\'
CharLiteral = {CharAll} | {CharN} | {CharR} | {CharT} | {CharC} | {CharB}

Number = {DecIntegerLiteral} | {OctIntegerLiteral} | {HexIntegerLiteral} | {FloatIntegerLiteral}

%%

/* keywords */
<YYINITIAL> "break"          { return new Symbol(sym.Break, yychar, yyline, yytext()); }
<YYINITIAL> "case"           { return new Symbol(sym.Case, yychar, yyline, yytext()); }
<YYINITIAL> "char"           { return new Symbol(sym.Char, yychar, yyline, yytext()); }
<YYINITIAL> "const"          { return new Symbol(sym.Const, yychar, yyline, yytext()); }
<YYINITIAL> "continue"       { return new Symbol(sym.Continue, yychar, yyline, yytext()); }
<YYINITIAL> "default"        { return new Symbol(sym.Default, yychar, yyline, yytext()); }
<YYINITIAL> "do"             { return new Symbol(sym.Do, yychar, yyline, yytext()); }
<YYINITIAL> "else"           { return new Symbol(sym.Else, yychar, yyline, yytext()); }
<YYINITIAL> "for"            { return new Symbol(sym.For, yychar, yyline, yytext()); }
<YYINITIAL> "if"             { return new Symbol(sym.If, yychar, yyline, yytext()); }
<YYINITIAL> "int"            { return new Symbol(sym.Int, yychar, yyline, yytext()); }
<YYINITIAL> "long"           { return new Symbol(sym.Long, yychar, yyline, yytext()); }
<YYINITIAL> "return"         { return new Symbol(sym.Return, yychar, yyline, yytext()); }
<YYINITIAL> "short"          { return new Symbol(sym.Short, yychar, yyline, yytext()); }
<YYINITIAL> "switch"         { return new Symbol(sym.Switch, yychar, yyline, yytext()); }
<YYINITIAL> "void"           { return new Symbol(sym.Void, yychar, yyline, yytext()); }
<YYINITIAL> "while"          { return new Symbol(sym.While, yychar, yyline, yytext()); }

<YYINITIAL> "main"           { return new Symbol(sym.Main, yychar, yyline, yytext()); }
<YYINITIAL> "read"           { return new Symbol(sym.Read, yychar, yyline, yytext()); }
<YYINITIAL> "write"          { return new Symbol(sym.Write, yychar, yyline, yytext()); }

<YYINITIAL> {
  /* identifiers */ 
  {Identifier}                   { return new Symbol(sym.Identifier, yychar, yyline, yytext()); }

  /* literals */
  {Number}                       { return new Symbol(sym.NumberLiteral, yychar, yyline, yytext()); }
  {CharLiteral}                  { return new Symbol(sym.CharLiteral, yychar, yyline, yytext()); }
  {String}                       { return symbol(sym.String, new String(yytext())); }


  /* operators */
  "//".*   {/*Ignore*/}
  ","   {return new Symbol(sym.Comma, yychar, yyline, yytext());}  
  ";"   {return new Symbol(sym.Semicolon, yychar, yyline, yytext());}

  "="   {return new Symbol(sym.Equal, yychar, yyline, yytext());}
  "+"   {return new Symbol(sym.Sum, yychar, yyline, yytext());}
  "-"   {return new Symbol(sym.Minus, yychar, yyline, yytext());}
  "*"   {return new Symbol(sym.Mult, yychar, yyline, yytext());}
  "/"   {return new Symbol(sym.Div, yychar, yyline, yytext());}
  "%"   {return new Symbol(sym.Mod, yychar, yyline, yytext());}
  "("   {return new Symbol(sym.RPar, yychar, yyline, yytext());}
  ")"   {return new Symbol(sym.LPar, yychar, yyline, yytext());}
  "["   {return new Symbol(sym.RBracket, yychar, yyline, yytext());}
  "]"   {return new Symbol(sym.LBracket, yychar, yyline, yytext());}
  "{"   {return new Symbol(sym.RBraces, yychar, yyline, yytext());}
  "}"   {return new Symbol(sym.LBraces, yychar, yyline, yytext());}

  /* Logic */
  ( "&&" | "||" | "!") {return new Symbol(sym.OpLogic, yychar, yyline, yytext());}

  /* Relational */
  ( ">" | "<" | "==" | "!=" | ">=" | "<=") {return new Symbol(sym.OpRelational, yychar, yyline, yytext());}

  /* Assignment */
  ( "+=" | "-="  | "*=" | "/=" ) {return new Symbol(sym.OpAssignment, yychar, yyline, yytext());}

  /* Increment & Decrement */
  ( "++" | "--" ) {return new Symbol(sym.OpIncDec, yychar, yyline, yytext());}

  /* Bool */
  ( true | false ) {return new Symbol(sym.OpBool, yychar, yyline, yytext());} 

  /* comments */
  {Comment}                      { /* ignore */ }
    
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}


/* error */ 
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }