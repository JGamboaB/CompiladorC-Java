package lexer;
import lexer.Token;

%%
%class Lexer
%type Token
%line
%column

%{

  StringBuffer string = new StringBuffer();

  public String lexeme;

  private Token createToken(KindToken kindToken){
    return new Token(kindToken, yyline+1);
  }

  private TokenStringValue createStringValueToken(KindToken kindToken, String value){
    return new TokenStringValue(kindToken, yyline+1, value);
  }

  private LexicalError createLexicalError(String message){
    return new LexicalError(message,yyline+1);
    
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

DecIntegerLiteral = 0|[1-9][0-9]*
OctIntegerLiteral = 0[0-9]+
HexIntegerLiteral = 0[xX][0-9A-Fa-f]+
FloatIntegerLiteral = [0-9]*\.[0-9]+([eE]-?[0-9]+)?

Number = {DecIntegerLiteral} | {OctIntegerLiteral} | {HexIntegerLiteral} | {FloatIntegerLiteral}

WrongSymbol = {Number} {Identifier}

%state STRING

%%

/* keywords */
<YYINITIAL> "auto"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "break"          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "case"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "char"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "const"          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "continue"       { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "default"        { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "do"             { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "double"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "else"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "enum"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "extern"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "float"          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "goto"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "if"             { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "int"            { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "long"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "register"       { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "return"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "short"          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "signed"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "sizeof"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "static"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "struct"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "switch"         { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "typedef"        { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "union"          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "unsigned"       { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "void"           { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "volatile"       { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "while"          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD); }


<YYINITIAL> {
  /* identifiers */ 
  {Identifier}                   { lexeme=yytext(); return createToken(KindToken.IDENTIFIER); }
   
  /* error fallback */
  {WrongSymbol}                  { throw createLexicalError("Illegal character <"+yytext()+">"); }

  /* literals */
  {DecIntegerLiteral}            { lexeme=yytext(); return createToken(KindToken.LITERAL); }
  {OctIntegerLiteral}            { lexeme=yytext(); return createToken(KindToken.LITERAL); }
  {HexIntegerLiteral}            { lexeme=yytext(); return createToken(KindToken.LITERAL); }
  {FloatIntegerLiteral}          { lexeme=yytext(); return createToken(KindToken.LITERAL); }


  \"                             { string.setLength(0); yybegin(STRING); }

  /* operators */
  "//".*                         {/*Ignore*/}
  ","                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ";"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "++"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "--"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "=="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ">="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ">"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "?"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "<="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "<"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "!="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "||"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "&&"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "!"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "="                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "+"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "-"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "*"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "/"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "%"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "("                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ")"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "["                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "]"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "{"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "}"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ":"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "."                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "+="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "-="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "*="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "/="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "&"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "^"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "|"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ">>"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "<<"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "~"                            { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "%="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "&="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "^="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "|="                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "<<="                          { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  ">>="                          { lexeme=yytext(); return createToken(KindToken.OPERATOR);}
  "->"                           { lexeme=yytext(); return createToken(KindToken.OPERATOR);}


  /* comments */
  {Comment}                      { /* ignore */ }
    
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); lexeme=string.toString(); return createStringValueToken(KindToken.LITERAL, string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }
  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
}

/* error fallback */
[^]                              { throw createLexicalError("Illegal character <"+yytext()+">"); }

/*
.                                { lexeme=yytext(); createToken(KindToken.ERROR);}
*/
