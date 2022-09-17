import lexer.Token;

%%
%class Lexer
%line
%column

%{
  StringBuffer string = new StringBuffer();

  private Token token(type ttype){
    return new Token(ttype, yyline);
  }

  private Token token(type ttype, String value){
    return new Token(ttype, yyline, value);
  }

%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
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
<YYINITIAL> "auto"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "break"          { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "case"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "char"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "const"          { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "continue"       { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "default"        { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "do"             { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "double"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "else"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "enum"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "extern"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "float"          { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "goto"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "if"             { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "int"            { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "long"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "register"       { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "return"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "short"          { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "signed"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "sizeof"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "static"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "struct"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "switch"         { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "typedef"        { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "union"          { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "unsigned"       { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "void"           { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "volatile"       { return token(Token.type.RESERVED_WORD); }
<YYINITIAL> "while"          { return token(Token.type.RESERVED_WORD); }


<YYINITIAL> {
  /* identifiers */ 
  {Identifier}                   { return token(Token.type.IDENTIFIER); }
   
  /* literals */
  {DecIntegerLiteral}            { return token(Token.type.LITERAL); }
  {OctIntegerLiteral}            { return token(Token.type.LITERAL); }
  {HexIntegerLiteral}            { return token(Token.type.LITERAL); }
  {FloatIntegerLiteral}          { return token(Token.type.LITERAL); }


  \"                             { string.setLength(0); yybegin(STRING); }

  /* operators */
  "//".*                         {/*Ignore*/}
  ","                            {return token(Token.type.OPERATOR);}
  ";"                            {return token(Token.type.OPERATOR);}
  "++"                           {return token(Token.type.OPERATOR);}
  "--"                           {return token(Token.type.OPERATOR);}
  "=="                           {return token(Token.type.OPERATOR);}
  ">="                           {return token(Token.type.OPERATOR);}
  ">"                            {return token(Token.type.OPERATOR);}
  "?"                            {return token(Token.type.OPERATOR);}
  "<="                           {return token(Token.type.OPERATOR);}
  "<"                            {return token(Token.type.OPERATOR);}
  "!="                           {return token(Token.type.OPERATOR);}
  "||"                           {return token(Token.type.OPERATOR);}
  "&&"                           {return token(Token.type.OPERATOR);}
  "!"                            {return token(Token.type.OPERATOR);}
  "="                            {return token(Token.type.OPERATOR);}
  "+"                            {return token(Token.type.OPERATOR);}
  "-"                            {return token(Token.type.OPERATOR);}
  "*"                            {return token(Token.type.OPERATOR);}
  "/"                            {return token(Token.type.OPERATOR);}
  "%"                            {return token(Token.type.OPERATOR);}
  "("                            {return token(Token.type.OPERATOR);}
  ")"                            {return token(Token.type.OPERATOR);}
  "["                            {return token(Token.type.OPERATOR);}
  "]"                            {return token(Token.type.OPERATOR);}
  "{"                            {return token(Token.type.OPERATOR);}
  "}"                            {return token(Token.type.OPERATOR);}
  ":"                            {return token(Token.type.OPERATOR);}
  "."                            {return token(Token.type.OPERATOR);}
  "+="                           {return token(Token.type.OPERATOR);}
  "-="                           {return token(Token.type.OPERATOR);}
  "*="                           {return token(Token.type.OPERATOR);}
  "/="                           {return token(Token.type.OPERATOR);}
  "&"                            {return token(Token.type.OPERATOR);}
  "^"                            {return token(Token.type.OPERATOR);}
  "|"                            {return token(Token.type.OPERATOR);}
  ">>"                           {return token(Token.type.OPERATOR);}
  "<<"                           {return token(Token.type.OPERATOR);}
  "~"                            {return token(Token.type.OPERATOR);}
  "%="                           {return token(Token.type.OPERATOR);}
  "&="                           {return token(Token.type.OPERATOR);}
  "^="                           {return token(Token.type.OPERATOR);}
  "|="                           {return token(Token.type.OPERATOR);}
  "<<="                          {return token(Token.type.OPERATOR);}
  ">>="                          {return token(Token.type.OPERATOR);}
  "->"                           {return token(Token.type.OPERATOR);}


  /* comments */
  {Comment}                      { /* ignore */ }
    
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return token(Token.type.LITERAL, string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }
  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
}

/* error fallback */
[^]                              { throw new Error("Illegal character <"+yytext()+">"); }
.                                {return token(Token.type.ERROR);}