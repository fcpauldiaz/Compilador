/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar program;

//********* SCANNER SPECIFICATION ******************
fragment LETTER: ('a'..'z'|'A'..'Z') ;
fragment DIGIT : '0'..'9' ;
ID : LETTER( LETTER | DIGIT)* ;
NUM: DIGIT(DIGIT)* ; 
CHAR: '\'' ('!'..'~' )|'\t'|'\\'|'\"'  '\'';  
STRING: '\"' ( ('!'..'~') |'\t'|'\\'|'\"' )* '\"';
FLOAT: DIGIT (DIGIT)* '.' DIGIT (DIGIT)*;
WS : [ \t\r\n]+ -> skip ; 
COMMENTS: '//' ~('\r' | '\n' )*  -> skip;

// ********** PARSER SPECIFICATION*****************
program : 'class' 'Program' '{' (declaration)* '}'  ;

declaration: structDeclaration| varDeclaration | methodDeclaration  ;

varDeclaration: varType ID ';' | varType ID '[' NUM ']' ';'  ;

structDeclaration : 'struct' ID '{' (varDeclaration)* '}'  ;

varType: 'int' | 'char' | 'boolean' | 'float' |'struct' ID | 'string' | structDeclaration | 'void'  ;

methodDeclaration : methodType ID '(' (parameter (',' parameter)*)* ')' block  ;

methodType : 'int' | 'char' | 'boolean' | 'float' |'void' ;

parameter : parameterType ID | parameterType ID '[' ']' ;

parameterType: 'int' | 'char' | 'boolean' | 'float'   ;

block : '{' (varDeclaration)* (statement)* '}' ;

statement : 'if' '(' expression ')' block ( 'else' block )? 
           | 'while' '(' expression ')' block
           |'return' (expression)? ';' 
           | methodCall ';' 
           | block  
           | location '=' expression 
           | (expression)? ';'  ;

location : (ID|ID '[' expression ']') ('.' location)?  ;

expression: expr2 | expression cond_op1 expr2;

expr2: exprEq | expr2 cond_op2 exprEq;

exprEq: exprRel | exprRel eq_op exprRel;

exprRel: sumExpr | exprRel  rel_op sumExpr;

sumExpr: exprMul | sumExpr op_plusmin exprMul;

exprMul: uniExpr | exprMul op_divmul uniExpr;

uniExpr: '('('int'|'char')')'  valor | valor |	'!' valor |	valor  ;

valor
	:	location| methodCall|	literal 
	|	'(' expression ')'	;

arrayCall :   '[' arg_comma ']' ;

methodCall :    ID '(' arg_comma ')' ;

arg_comma    :   arg_exp | ;

arg_exp    :   (arg) (',' arg)* ;

arg :   expression;

op_plusmin : '+' | '-' ;

op_divmul: | '*' | '/' | '%';

rel_op : '<' | '>' | '<=' | '>=' ;

eq_op : '==' | '!=' ;

cond_op1: '&&';

cond_op2:  '||' ;

literal : int_literal | char_literal | bool_literal | string_literal | float_literal;

int_literal : NUM ;

char_literal : CHAR ;

string_literal: STRING;

float_literal: FLOAT;

bool_literal : 'true' | 'false' ;


