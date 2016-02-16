//Pablo Díaz UVG DECAF GRAMMAR
grammar program;

//*********************LEXER SPECIFICATION **************
CLASS :   'class' ;
STRUCT :  'struct' ;
TRUE :    'true' ;
FALSE :   'false' ;
VOID :    'void' ;
IF :      'if' ;
ELSE :    'else' ;
WHILE :   'while' ;
RETURN :  'return' ;
INT :     'int' ;
CHAR :    'char' ;
BOOLEAN : 'boolean' ;

fragment LETTER : ('a'..'z'|'A'..'Z') ;
fragment DIGIT :'0'..'9' ;
fragment ASCII : (' ' ..'~') | '\\' | '\'' | '"' | '\t' | '\n' ;

ID : LETTER ( LETTER | DIGIT )* ;
NUM : DIGIT ( DIGIT )* ;
Char : '\'' ASCII '\'';


WSD : 
    [\t\r\n\f ]+ -> skip
    ;

COMMENT
    :   ( '//' ~[\r\n]* '\r'? '\n'	
        | '/*' .*? '*/'			
        ) -> skip
    ;								

//************** PARSER SPECIFICATION **************

program
	: CLASS ID '{' (declaration | methodDeclaration)* '}' 
	;

declaration
	:	structDeclaration	#declarationStructDeclaration
	|	varDeclaration		#declarationVarDeclaration
	|	methodDeclaration	#declarationMethodDeclaration
	;
varDeclaration
	: 	varType ID ';'			#varDeclarationID
	| 	varType ID '[' NUM ']' ';'	#varDeclarationArray
	;

varDeclarationStruct
	: 	varType ID ';'			#varDeclarationStructID
	| 	varType ID '[' NUM ']' ';'	#varDeclarationStructArray
	;

structDeclaration
	:	STRUCT ID '{' (varDeclarationStruct)* '}'
	;

varType
	: 	INT				#varTypeInt
	|	CHAR				#varTypeChar
	|	BOOLEAN				#varTypeBoolean
	|	STRUCT ID			#varTypeStruct
	|	structDeclaration               #varTypeStructDeclaration
	| 	VOID				#varTypeVoid
	;

methodDeclaration
	:	methodType ID '(' (params | ) ')' block
	;

	
params
	:	params ',' parameter	#paramsParameterWithComma
	|	parameter		#paramsParameter
	;
	
methodType
	:	INT				#methodTypeInt
	|	CHAR				#methodTypeChar
	|	BOOLEAN				#methodTypeBoolean
	|	VOID				#methodTypeVoid
	;

parameter
	: 	parameterType ID		#parameterID
	|	parameterType ID '[' NUM ']'	#parameterArray
	;
	
parameterType
	:	INT				#parameterTypeInt
	|	CHAR				#parameterTypeChar
	|	BOOLEAN				#parameterTypeBoolean
	;
	

block
	:	'{' (varDeclaration | statement)* '}'
	;

statement
	:	IF '(' expression ')' block (ELSE block)?	#statementIF
	|	WHILE '(' expression ')' block			#statementWhile
	|	'return' (expression | ) ';'			#statementReturn
	|	methodCall ';'					#statementMethodCall
	| 	block						#statementBlock				
	|	location '=' expression ';'			#statementLocation
	|	(expression)?';'				#statementExpression
	;
	
location
	:	(ID | ID '[' expression ']') ('.' locationMember)?
	;
	
locationMember
	:	(ID | ID '[' expression ']')('.' locationMember)?
	;


expression 
	:	andExpr				#expressionAndExpr
	| 	expression cond_op_or andExpr  	#expressionCondOpOr
	;

andExpr
	: 	eqExpr				#andExprEqExpr 
	| 	andExpr cond_op_and eqExpr 	#andExprCondOpAnd
	;

eqExpr
	: 	relationExpr 			#eqExprRelationExpr
	| 	eqExpr eq_op relationExpr 	#eqExprEqOp
	;

relationExpr
	: 	addExpr 			#relationExprAddExpr
	| 	relationExpr rel_op addExpr 	#relationExprRelOp
	;


addExpr
	: 	multExpr 			#addExprMultExpr
	| 	addExpr minusplus_op multExpr 	#addExprMinusPlusOp
	;

multExpr
	: 	unaryExpr 			#multExprUnary
	| 	multExpr multdiv_op unaryExpr 	#multExprMultDivOp
	;


unaryExpr
	:  	'('(INT|CHAR)')'  value		#unaryExprCast
	| 	'-' value 			#unaryExprMinus
	|	'!' value 			#unaryExprNot
	|	value   			#unaryExprOther
	;

value
	:	location			#valueLocation
	|	methodCall			#valueMethodCall
	|	literal				#valueLiteral
	|	'(' expression ')'		#valueExprWithParent
	;


	
methodCall
	:	ID '(' (arg (',' arg)*)? ')' 
	;
	
arg
	:	expression
	;
	

minusplus_op	
	:	'+'
	| 	'-'
	;

multdiv_op
	:	'*'
	|	'/'
	|	'%'
	;

powmod_op
	:	'%'
	;
	
rel_op
	:	'<'
	|	'>'
	| 	'<='
	|	'>='
	;
	
eq_op
	:	'=='
	|	'!='
	;
	
cond_op_or : '||';
cond_op_and: '&&';

literal
	:	int_literal
	|	char_literal
	|	bool_literal
	;
	
int_literal
	:	NUM
	;

char_literal
	:	Char 
	;
	
bool_literal
	:	'true'
	|	'false'
	;
