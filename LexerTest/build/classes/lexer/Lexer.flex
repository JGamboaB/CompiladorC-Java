package lexer;
import lexer.Token;

%%
%class Lexer
%type Token
%line
%column

%{

  StringBuffer string = new StringBuffer();

  private Token createToken(KindToken kindToken){
    return new Token(kindToken, yyline);
  }

  private Token createStringValueToken(KindToken kindToken, String value){
    return new TokenStringValue(kindToken, yyline, value);
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

Identifier = [:jletter:] [:jletterdigit:]* 

DecIntegerLiteral = -?0 | -?[1-9][0-9]*
OctIntegerLiteral = -?0[0-9]+
HexIntegerLiteral = -?0[xX][0-9A-Fa-f]+
FloatIntegerLiteral = -?[0-9]*.[0-9]+[eE]?-?[0-9]*

%state STRING

%%

/* keywords */
<YYINITIAL> "auto"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "break"          { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "case"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "char"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "const"          { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "continue"       { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "default"        { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "do"             { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "double"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "else"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "enum"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "extern"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "float"          { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "goto"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "if"             { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "int"            { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "long"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "register"       { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "return"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "short"          { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "signed"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "sizeof"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "static"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "struct"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "switch"         { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "typedef"        { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "union"          { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "unsigned"       { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "void"           { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "volatile"       { return createToken(KindToken.RESERVED_WORD); }
<YYINITIAL> "while"          { return createToken(KindToken.RESERVED_WORD); }


<YYINITIAL> {
  /* identifiers */ 
  {Identifier}                   { return createToken(KindToken.IDENTIFIER); }
   
  /* literals */
  {DecIntegerLiteral}            { return createToken(KindToken.LITERAL); }
  {OctIntegerLiteral}            { return createToken(KindToken.LITERAL); }
  {HexIntegerLiteral}            { return createToken(KindToken.LITERAL); }
  {FloatIntegerLiteral}          { return createToken(KindToken.LITERAL); }


  \"                             { string.setLength(0); yybegin(STRING); }

  /* operators */
  "//".*                         {/*Ignore*/}
  ","                            {return createToken(KindToken.OPERATOR);}
  ";"                            {return createToken(KindToken.OPERATOR);}
  "++"                           {return createToken(KindToken.OPERATOR);}
  "--"                           {return createToken(KindToken.OPERATOR);}
  "=="                           {return createToken(KindToken.OPERATOR);}
  ">="                           {return createToken(KindToken.OPERATOR);}
  ">"                            {return createToken(KindToken.OPERATOR);}
  "?"                            {return createToken(KindToken.OPERATOR);}
  "<="                           {return createToken(KindToken.OPERATOR);}
  "<"                            {return createToken(KindToken.OPERATOR);}
  "!="                           {return createToken(KindToken.OPERATOR);}
  "||"                           {return createToken(KindToken.OPERATOR);}
  "&&"                           {return createToken(KindToken.OPERATOR);}
  "!"                            {return createToken(KindToken.OPERATOR);}
  "="                            {return createToken(KindToken.OPERATOR);}
  "+"                            {return createToken(KindToken.OPERATOR);}
  "-"                            {return createToken(KindToken.OPERATOR);}
  "*"                            {return createToken(KindToken.OPERATOR);}
  "/"                            {return createToken(KindToken.OPERATOR);}
  "%"                            {return createToken(KindToken.OPERATOR);}
  "("                            {return createToken(KindToken.OPERATOR);}
  ")"                            {return createToken(KindToken.OPERATOR);}
  "["                            {return createToken(KindToken.OPERATOR);}
  "]"                            {return createToken(KindToken.OPERATOR);}
  "{"                            {return createToken(KindToken.OPERATOR);}
  "}"                            {return createToken(KindToken.OPERATOR);}
  ":"                            {return createToken(KindToken.OPERATOR);}
  "."                            {return createToken(KindToken.OPERATOR);}
  "+="                           {return createToken(KindToken.OPERATOR);}
  "-="                           {return createToken(KindToken.OPERATOR);}
  "*="                           {return createToken(KindToken.OPERATOR);}
  "/="                           {return createToken(KindToken.OPERATOR);}
  "&"                            {return createToken(KindToken.OPERATOR);}
  "^"                            {return createToken(KindToken.OPERATOR);}
  "|"                            {return createToken(KindToken.OPERATOR);}
  ">>"                           {return createToken(KindToken.OPERATOR);}
  "<<"                           {return createToken(KindToken.OPERATOR);}
  "~"                            {return createToken(KindToken.OPERATOR);}
  "%="                           {return createToken(KindToken.OPERATOR);}
  "&="                           {return createToken(KindToken.OPERATOR);}
  "^="                           {return createToken(KindToken.OPERATOR);}
  "|="                           {return createToken(KindToken.OPERATOR);}
  "<<="                          {return createToken(KindToken.OPERATOR);}
  ">>="                          {return createToken(KindToken.OPERATOR);}
  "->"                           {return createToken(KindToken.OPERATOR);}


  /* comments */
  {Comment}                      { /* ignore */ }
    
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return createStringValueToken(KindToken.LITERAL, string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }
  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
}

/* error fallback */
[^]                              { throw new Error("Illegal character <"+yytext()+">"); }
.                                {return createToken(KindToken.ERROR);}

