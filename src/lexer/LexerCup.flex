package lexer;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
%column

%{
  private Symbol symbol(int type, Object value){
    return new Symbol(type, yyline, yycolumn, value);
  }

  private Symbol symbol(int type){
    return new Symbol(type, yyline, yycolumn);
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

ID = [a-zA-Z_][a-zA-Z_0-9]* 
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
<YYINITIAL> "break"          { return new Symbol(sym.BREAK, yycolumn, yyline, yytext()); }
<YYINITIAL> "case"           { return new Symbol(sym.CASE, yycolumn, yyline, yytext()); }
<YYINITIAL> "char"           { return new Symbol(sym.CHAR, yycolumn, yyline, yytext()); }
<YYINITIAL> "const"          { return new Symbol(sym.CONST, yycolumn, yyline, yytext()); }
<YYINITIAL> "continue"       { return new Symbol(sym.CONTINUE, yycolumn, yyline, yytext()); }
<YYINITIAL> "default"        { return new Symbol(sym.DEFAULT, yycolumn, yyline, yytext()); }
<YYINITIAL> "do"             { return new Symbol(sym.DO, yycolumn, yyline, yytext()); }
<YYINITIAL> "else"           { return new Symbol(sym.ELSE, yycolumn, yyline, yytext()); }
<YYINITIAL> "for"            { return new Symbol(sym.FOR, yycolumn, yyline, yytext()); }
<YYINITIAL> "if"             { return new Symbol(sym.IF, yycolumn, yyline, yytext()); }
<YYINITIAL> "int"            { return new Symbol(sym.INT, yycolumn, yyline, yytext()); }
<YYINITIAL> "long"           { return new Symbol(sym.LONG, yycolumn, yyline, yytext()); }
<YYINITIAL> "return"         { return new Symbol(sym.RETURN, yycolumn, yyline, yytext()); }
<YYINITIAL> "short"          { return new Symbol(sym.SHORT, yycolumn, yyline, yytext()); }
<YYINITIAL> "switch"         { return new Symbol(sym.SWITCH, yycolumn, yyline, yytext()); }
<YYINITIAL> "void"           { return new Symbol(sym.VOID, yycolumn, yyline, yytext()); }
<YYINITIAL> "while"          { return new Symbol(sym.WHILE, yycolumn, yyline, yytext()); }

// <YYINITIAL> "main"           { return new Symbol(sym.MAIN, yycolumn, yyline, yytext()); }
<YYINITIAL> "read"           { return new Symbol(sym.READ, yycolumn, yyline, yytext()); }
<YYINITIAL> "write"          { return new Symbol(sym.WRITE, yycolumn, yyline, yytext()); }

<YYINITIAL> {
  /* identifiers */ 
  {ID}                   { return new Symbol(sym.ID, yycolumn, yyline, yytext()); }

  /* literals */
  {Number}                       { return new Symbol(sym.NUMBERLITERAL, yycolumn, yyline, yytext()); }
  {CharLiteral}                  { return new Symbol(sym.CHARLITERAL, yycolumn, yyline, yytext()); }
  {String}                       { return symbol(sym.STRING, new String(yytext())); }


  /* operators */
  "//".*        {/*Ignore*/}
  "#include".*  {/*Ignore*/}
  "."   {return new Symbol(sym.DOT, yycolumn, yyline, yytext());}  
  ","   {return new Symbol(sym.COMMA, yycolumn, yyline, yytext());}  
  ";"   {return new Symbol(sym.SEMICOLON, yycolumn, yyline, yytext());}
  ":"   {return new Symbol(sym.COLON, yycolumn, yyline, yytext());}

  "="   {return new Symbol(sym.EQUAL, yycolumn, yyline, yytext());}
  "+"   {return new Symbol(sym.SUM, yycolumn, yyline, yytext());}
  "-"   {return new Symbol(sym.MINUS, yycolumn, yyline, yytext());}
  "*"   {return new Symbol(sym.MULT, yycolumn, yyline, yytext());}
  "/"   {return new Symbol(sym.DIV, yycolumn, yyline, yytext());}
  "%"   {return new Symbol(sym.MOD, yycolumn, yyline, yytext());}
  "("   {return new Symbol(sym.LPAR, yycolumn, yyline, yytext());}
  ")"   {return new Symbol(sym.RPAR, yycolumn, yyline, yytext());}
  "["   {return new Symbol(sym.LBRACKET, yycolumn, yyline, yytext());}
  "]"   {return new Symbol(sym.RBRACKET, yycolumn, yyline, yytext());}
  "{"   {return new Symbol(sym.LBRACES, yycolumn, yyline, yytext());}
  "}"   {return new Symbol(sym.RBRACES, yycolumn, yyline, yytext());}

  /* Logic */
  "||" {return new Symbol(sym.OR, yycolumn, yyline, yytext());}
  "&&" {return new Symbol(sym.AND, yycolumn, yyline, yytext());}
  "!"  {return new Symbol(sym.NOT, yycolumn, yyline, yytext());}

  /* Relational */
  ( ">" | "<" | ">=" | "<=") {return new Symbol(sym.OPRELATIONAL, yycolumn, yyline, yytext());}
  ( "==" | "!=" ) {return new Symbol(sym.OPEQUALITY, yycolumn, yyline, yytext());}

  /* Assignment */
  ( "+=" | "-="  | "*=" | "/=" ) {return new Symbol(sym.OPASSIGNMENT, yycolumn, yyline, yytext());}

  /* Increment & Decrement */
  ( "++" | "--" ) {return new Symbol(sym.OPINCDEC, yycolumn, yyline, yytext());}

  /* comments */
  {Comment}                      { /* ignore */ }
    
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}


/* error */ 
[^]                              { /* ignore */ }