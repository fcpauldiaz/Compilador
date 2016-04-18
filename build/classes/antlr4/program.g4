//Pablo Díaz UVG DECAF GRAMMAR
grammar program;

//*********************LEXER SPECIFICATION **************
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
fragment ASCII : (' ' ..'~') | '\\' | '\'' | '\"' | '\t' | '\n' ;
//* \'
ID : LETTER ( LETTER | DIGIT )* ;
NUM : DIGIT ( DIGIT )* ;
Char : '\'' ASCII '\'';


WS : 
    [\t\r\n\f ]+ -> skip
    ;

COMMENT
    :   ( '//' ~[\r\n]* '\r'? '\n'	
        | '/*' .*? '*/'	
        ) -> skip
    ;								

//************** PARSER SPECIFICATION **************


program
	: 'class' ID '{' (declaration)* '}'
	;

declaration
	:	structDeclaration	
	|	varDeclaration		
	|	methodDeclaration	
	;
varDeclaration
	: 	varType ID ';'			#varDeclarationID
	| 	varType ID '[' NUM ']' ';'      #varDeclarationArray
        
	;


structDeclaration
	:	STRUCT ID '{' (varDeclaration)* '}'
	;

varType                                         
	: 	INT				
	|	CHAR				
	|	BOOLEAN				
	|	STRUCT ID			
	|	structDeclaration              
	| 	VOID				
	;

methodDeclaration
	:	methodType ID '('  (parameter (',' parameter)*)* ')' block
	;

	
	
methodType
	:	INT				
	|	CHAR				
	|	BOOLEAN				
	|	VOID				
	;

parameter
	: 	parameterType ID		#parameterID
	|	parameterType ID '[' NUM ']'	#parameterArray
	;
	
parameterType
	:	INT				
	|	CHAR				
	|	BOOLEAN				
	;
	

block
	:	'{' (varDeclaration|statement)* '}'
	;

statement
	:	'if' '(' expression ')' block (statementElse)?	#statementIF
	|	WHILE '(' expression ')' block			#statementWhile
	|	'return' (expression | ) ';'			#statementReturn
	|	methodCall ';'					#statementMethodCall
	| 	block						#statementBlock				
	|	location '=' expression ';'			#statementLocation
        |       locationArray '=' expression ';'                #statementLocationArray
	|	(expression)?';'				#statementExpression
	;

statementElse
    :
        ELSE block
    ;
	
location
	:	(ID)('.' locationMember)?
	;
	
locationMember
	:	(ID)('.' locationMember)?    #locationMemberMethod
        |       locationArray                #locationMemberArray
	;

locationArray
        :   ID '[' expression ']' (locationMethod)?
            
        ;
locationArray2
        :   ID '[' expression ']' (locationMethod)?
            
        ;

locationMethod
    :
        '.' locationMember
    ;    
expression 
	:	andExpr				
	| 	expression cond_op_or andExpr  	
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
	: 	addExpr 			
	| 	relationExpr rel_op addExpr 	
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
	:  	'('(INT|CHAR)')'  value		
	| 	'-' value 			
	|	'!' value 			
	|	value   	
        
	;

value
	:	location			#valueLocation
        |       locationArray2                   #valueLocationArray
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
	|	boolean_literal
	;
	
int_literal
	:	NUM
	;

char_literal
	:	Char 
	;
	
boolean_literal
	:	'true'
	|	'false'
	;
