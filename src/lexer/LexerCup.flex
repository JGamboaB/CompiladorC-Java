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
<YYINITIAL> "break"          { return new Symbol(sym.BREAK, yychar, yyline, yytext()); }
<YYINITIAL> "case"           { return new Symbol(sym.CASE, yychar, yyline, yytext()); }
<YYINITIAL> "char"           { return new Symbol(sym.CHAR, yychar, yyline, yytext()); }
<YYINITIAL> "const"          { return new Symbol(sym.CONST, yychar, yyline, yytext()); }
<YYINITIAL> "continue"       { return new Symbol(sym.CONTINUE, yychar, yyline, yytext()); }
<YYINITIAL> "default"        { return new Symbol(sym.DEFAULT, yychar, yyline, yytext()); }
<YYINITIAL> "do"             { return new Symbol(sym.DO, yychar, yyline, yytext()); }
<YYINITIAL> "else"           { return new Symbol(sym.ELSE, yychar, yyline, yytext()); }
<YYINITIAL> "for"            { return new Symbol(sym.For, yychar, yyline, yytext()); }
<YYINITIAL> "if"             { return new Symbol(sym.IF, yychar, yyline, yytext()); }
<YYINITIAL> "int"            { return new Symbol(sym.INT, yychar, yyline, yytext()); }
<YYINITIAL> "long"           { return new Symbol(sym.LONG, yychar, yyline, yytext()); }
<YYINITIAL> "return"         { return new Symbol(sym.RETURN, yychar, yyline, yytext()); }
<YYINITIAL> "short"          { return new Symbol(sym.SHORT, yychar, yyline, yytext()); }
<YYINITIAL> "switch"         { return new Symbol(sym.SWITCH, yychar, yyline, yytext()); }
<YYINITIAL> "void"           { return new Symbol(sym.VOID, yychar, yyline, yytext()); }
<YYINITIAL> "while"          { return new Symbol(sym.WHILE, yychar, yyline, yytext()); }

<YYINITIAL> "main"           { return new Symbol(sym.MAIN, yychar, yyline, yytext()); }
<YYINITIAL> "read"           { return new Symbol(sym.READ, yychar, yyline, yytext()); }
<YYINITIAL> "write"          { return new Symbol(sym.WRITE, yychar, yyline, yytext()); }

<YYINITIAL> {
  /* identifiers */ 
  {ID}                   { return new Symbol(sym.ID, yychar, yyline, yytext()); }

  /* literals */
  {Number}                       { return new Symbol(sym.NUMBERLITERAL, yychar, yyline, yytext()); }
  {CharLiteral}                  { return new Symbol(sym.CHARLITERAL, yychar, yyline, yytext()); }
  {String}                       { return symbol(sym.STRING, new String(yytext())); }


  /* operators */
  "//".*   {/*Ignore*/}
  "."   {return new Symbol(sym.DOT, yychar, yyline, yytext());}  
  ","   {return new Symbol(sym.COMMA, yychar, yyline, yytext());}  
  ";"   {return new Symbol(sym.SEMICOLON, yychar, yyline, yytext());}
  ":"   {return new Symbol(sym.COLON, yychar, yyline, yytext());}

  "="   {return new Symbol(sym.EQUAL, yychar, yyline, yytext());}
  "+"   {return new Symbol(sym.SUM, yychar, yyline, yytext());}
  "-"   {return new Symbol(sym.MINUS, yychar, yyline, yytext());}
  "*"   {return new Symbol(sym.MULT, yychar, yyline, yytext());}
  "/"   {return new Symbol(sym.DIV, yychar, yyline, yytext());}
  "%"   {return new Symbol(sym.MOD, yychar, yyline, yytext());}
  "("   {return new Symbol(sym.LPAR, yychar, yyline, yytext());}
  ")"   {return new Symbol(sym.RPAR, yychar, yyline, yytext());}
  "["   {return new Symbol(sym.LBRACKET, yychar, yyline, yytext());}
  "]"   {return new Symbol(sym.RBRACKET, yychar, yyline, yytext());}
  "{"   {return new Symbol(sym.LBRACES, yychar, yyline, yytext());}
  "}"   {return new Symbol(sym.RBRACES, yychar, yyline, yytext());}

  /* Logic */
  "||" {return new Symbol(sym.OR, yychar, yyline, yytext());}
  "&&" {return new Symbol(sym.AND, yychar, yyline, yytext());}
  "!"  {return new Symbol(sym.NOT, yychar, yyline, yytext());}

  /* Relational */
  ( ">" | "<" | ">=" | "<=") {return new Symbol(sym.OPRELATIONAL, yychar, yyline, yytext());}
  ( "==" | "!=" ) {return new Symbol(sym.OPEQUALITY, yychar, yyline, yytext());}

  /* Assignment */
  ( "+=" | "-="  | "*=" | "/=" ) {return new Symbol(sym.OPASSIGNMENT, yychar, yyline, yytext());}

  /* Increment & Decrement */
  ( "++" | "--" ) {return new Symbol(sym.OPINCDEC, yychar, yyline, yytext());}

  /* comments */
  {Comment}                      { /* ignore */ }
    
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}


/* error */ 
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }