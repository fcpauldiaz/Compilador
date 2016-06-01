/*
* UVG 
* Pablo Diaz 
* Compilador 
* Mayo 2016 
*/

.data
.align 2

salida_num:    .asciz "El resultado es: %d \n"
salida_char:    .asciz "El character es: %c \n"
mensaje_ingreso: .asciz "Ingrese su dato: "
input_num:     .asciz " %d"
input_char:     .asciz "%c"
input_literal:	.word 0
global: .space
.global offset
offset: .word 0

.text
.align 2

.global main
.type main, %function
fact:
	PUSH {R4-R12, LR} 

	bl initStack	//aumentar stack pointer

	LDR R0, [SP, #40]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param n

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para retVal

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op

	bl stack	//aumentar stack pointer

	LDR R0, [sp ,#8]
	MOV R1, #0	//Cargar valor literal
	CMP R0, R1	//temp0 = stack[0] == 0
	beq fact0	//Realizar salto condicional
	b fact2	//Goto fact2

fact0:
	MOV R2, #1
	STR R2, [sp, #4]	//LocalStack 4

	b fact3	//Goto fact3

fact2:
	LDR R3, [sp ,#8]
	SUB R0, R3, #1	//temp0 = stack[0] - 1
	STR R0, [sp, #0]	//LocalStack 0

	LDR R1, [sp , #0]	//Set value param stack[8]
	PUSH {R1}	//push param localStack

	bl fact

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #4]	//LocalStack 4

	LDR R1, [sp ,#8]
	LDR R2, [sp , #4]	//Set value stack[4]
	MUL R0, R1, R2	//temp0 = stack[0] * stack[4]
	STR R0, [sp, #4]	//LocalStack 4

fact3:
	LDR R0, [sp , #4]	//Get value stack[4]
	MOV R0, R0	//Retrun value is alwasy on R0
	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	POP {R4-R12, pc}

main:
	stmfd sp!, {fp, lr}

	bl initStack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para a

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para b

	bl stack	//aumentar stack pointer

	bl input

	ADD SP, SP, #0	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #0]	//LocalStack 0

	LDR R1, [sp , #0]	//Set value param stack[4]
	PUSH {R1}	//push param localStack

	bl fact

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #4]	//LocalStack 4

	LDR R1, [sp , #4]	//Set value param stack[0]
	PUSH {R1}	//push param localStack

	bl printNum

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr
	POP {R4-R12, pc}

input:
	PUSH {R4-R12, LR} 

	bl initStack	//aumentar stack pointer

	LDR R0,=mensaje_ingreso
	bl puts
	LDR R0, =input_num
	LDR R1, =input_literal
	bl scanf

	LDR R1, =input_literal
	LDR R0, [R1]	//Devoler valor de retorno
	POP {R4-R12, pc}

printNum:
	PUSH {R4-R12, LR} 

	bl initStack	//aumentar stack pointer

	LDR R0, [SP, #40]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param x

	bl stack	//aumentar stack pointer

	LDR R1, [sp, #0]	//Cargar en offset
	LDR R0, =salida_num
	bl printf

	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	POP {R4-R12, pc}


