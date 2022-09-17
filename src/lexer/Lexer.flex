package lexer;
import static lexer.Tokens.*;

%%
%class Lexer
%type Tokens
%line

L=[a-zA-Z_]+
D=[0-9]+
H=[a-fA-F0-9]+

%{
    public String lexeme;

    private Symbol symbol(int type) {
        return new Symbol(type, yyline);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, value);
    }
%}

 LineTerminator = \r|\n|\r\n
 InputCharacter = [^\r\n]
 WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {OneLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
OneLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

%%
/* Reserved words */
"auto"			{ return(RESERVED); }
"break"			{ return(RESERVED); }
"case"			{ return(RESERVED); }
"char"			{ return(RESERVED); }
"const"			{ return(RESERVED); }
"continue"		{ return(RESERVED); }
"default"		{ return(RESERVED); }
"do"			{ return(RESERVED); }
"double"		{ return(RESERVED); }
"else"			{ return(RESERVED); }
"enum"			{ return(RESERVED); }
"extern"		{ return(RESERVED); }
"float"			{ return(RESERVED); }
"for"			{ return(RESERVED); }
"goto"			{ return(RESERVED); }
"if"			{ return(RESERVED); }
"int"			{ return(RESERVED); }
"long"			{ return(RESERVED); }
"register"		{ return(RESERVED); }
"return"		{ return(RESERVED); }
"short"			{ return(RESERVED); }
"signed"		{ return(RESERVED); }
"sizeof"		{ return(RESERVED); }
"static"		{ return(RESERVED); }
"struct"		{ return(RESERVED); }
"switch"		{ return(RESERVED); }
"typedef"		{ return(RESERVED); }
"union"			{ return(RESERVED); }
"unsigned"		{ return(RESERVED); }
"void"			{ return(RESERVED); }
"volatile"		{ return(RESERVED); }
"while"			{ return(RESERVED); }

/* Operators */
"//".* {/*Ignore*/}

"," {return OPERATOR;}
";" {return OPERATOR;}
"++" {return OPERATOR;}
"--" {return OPERATOR;}
"==" {return OPERATOR;}
">=" {return OPERATOR;}
">" {return OPERATOR;}
"?" {return OPERATOR;}
"<=" {return OPERATOR;}
"<" {return OPERATOR;}
"!=" {return OPERATOR;}
"||" {return OPERATOR;}
"&&" {return OPERATOR;}
"!" {return OPERATOR;}
"=" {return OPERATOR;}
"+" {return OPERATOR;}
"-" {return OPERATOR;}
"*" {return OPERATOR;}
"/" {return OPERATOR;}
"%" {return OPERATOR;}
"(" {return OPERATOR;}
")" {return OPERATOR;}
"[" {return OPERATOR;}
"]" {return OPERATOR;}
"{" {return OPERATOR;}
"}" {return OPERATOR;}
":" {return OPERATOR;}
"." {return OPERATOR;}
"+=" {return OPERATOR;}
"-=" {return OPERATOR;}
"*=" {return OPERATOR;}
"/=" {return OPERATOR;}
"&" {return OPERATOR;}
"^" {return OPERATOR;}
"|" {return OPERATOR;}
">>" {return OPERATOR;}
"<<" {return OPERATOR;}
"~" {return OPERATOR;}
"%=" {return OPERATOR;}
"&=" {return OPERATOR;}
"^=" {return OPERATOR;}
"|=" {return OPERATOR;}
"<<=" {return OPERATOR;}
">>=" {return OPERATOR;}
"->" {return OPERATOR;}

{Comment} { /* ignore */ }
{WhiteSpace} { /* ignore */ }


{L}({L}|{D})* {lexeme=yytext(); return IDENTIFIER;} 

/* LITERALS */
L?\"(\\.|[^\\"])*\"	{lexeme=yytext(); return(LITERAL); }
("(-"{D}+")")|{D}+ {lexeme=yytext(); return LITERAL;}


 . {return ERROR;}
