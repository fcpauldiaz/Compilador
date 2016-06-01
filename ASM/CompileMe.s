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
input_num:     .asciz "Ingrese el input %d \n"
input_char:     .asciz "Ingrese el input character: %c \n"
global: .space
.global offset
offset: .word 0

.text
.align 2

.global main
.type main, %function
fib:
	PUSH {R4-R12, LR} 

	bl initStack	//aumentar stack pointer

	LDR R0, [SP, #40]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param n

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para retVal

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para f1

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para f2

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op1

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op2

	bl stack	//aumentar stack pointer

	LDR R0, [sp ,#20]
	MOV R1, #0	//Cargar valor literal
	CMP R0, R1	//temp0 = stack[0] == 0
	beq fib0	//Realizar salto condicional
	MOV R2, #1	//Cargar valor literal
	CMP R0, R2	//temp1 = stack[0] == 1
	beq fib0	//Realizar salto condicional
	b fib2	//Goto fib2

fib0:
	MOV R3, #1
	STR R3, [sp, #16]	//LocalStack 16

	b fib3	//Goto fib3

fib2:
	LDR R0, [sp ,#20]
	SUB R1, R0, #1	//temp0 = stack[0] - 1
	STR R1, [sp, #4]	//LocalStack 4

	SUB R1, R0, #2	//temp0 = stack[0] - 2
	STR R1, [sp, #0]	//LocalStack 0

	LDR R2, [sp , #4]	//Set value param stack[16]
	PUSH {R2}	//push param localStack

	bl fib

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #12]	//LocalStack 12

	LDR R1, [sp , #0]	//Set value param stack[20]
	PUSH {R1}	//push param localStack

	bl fib

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #8]	//LocalStack 8

	LDR R1, [sp ,#12]
	LDR R2, [sp , #8]	//Set value stack[12]
	ADD R0, R1, R2	//temp0 = stack[8] + stack[12]
	STR R0, [sp, #16]	//LocalStack 16

fib3:
	LDR R0, [sp , #16]	//Get value stack[4]
	MOV R0, R0	//Retrun value is alwasy on R0
	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	POP {R4-R12, pc}

main:
	stmfd sp!, {fp, lr}

	bl initStack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para x

	bl stack	//aumentar stack pointer

	MOV R0, #5
	PUSH {R0}	//push param

	bl fib

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #0]	//LocalStack 0

	LDR R1, [sp , #0]	//Set value param stack[0]
	PUSH {R1}	//push param localStack

	bl printNum

	ADD SP, SP, #4	//Drop params from Stack

	MOV R0, R0	//get return value
	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr
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


