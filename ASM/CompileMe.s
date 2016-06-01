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
A:
	PUSH {R4-R12, LR} 

	bl initStack	//aumentar stack pointer

	LDR R0, [SP, #40]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param n

	bl stack	//aumentar stack pointer

	LDR R0, [SP, #48]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param m

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para retVal

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op2

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op3

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op4

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op5

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para op6

	bl stack	//aumentar stack pointer

	LDR R0, [sp ,#28]
	MOV R1, #0	//Cargar valor literal
	CMP R0, R1	//temp0 = stack[4] == 0
	beq A0	//Realizar salto condicional
	b A1	//Goto A1

A0:
	LDR R2, [sp ,#32]
	ADD R3, R2, #1	//temp1 = stack[0] + 1
	STR R3, [sp, #24]	//LocalStack 24

A1:
	LDR R0, [sp ,#28]
	MOV R1, #0	//Cargar valor literal
	CMP R0, R1	//temp0 = stack[4] > 0
	ble A3	//Realizar salto condicional
	LDR R2, [sp ,#32]
	MOV R3, #0	//Cargar valor literal
	CMP R2, R3	//temp1 = stack[0] == 0
	bne A3	//Realizar salto condicional
A2:
	LDR R0, [sp ,#28]
	SUB R1, R0, #1	//temp2 = stack[4] - 1
	STR R1, [sp, #20]	//LocalStack 20

	MOV R2, #1
	STR R2, [sp, #16]	//LocalStack 16

	LDR R3, [sp , #20]	//Set value param stack[12]
	PUSH {R3}	//push param localStack

	LDR R0, [sp , #20]	//Set value param stack[16]
	PUSH {R0}	//push param localStack

	bl A

	ADD SP, SP, #8	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #24]	//LocalStack 24

A3:
	LDR R0, [sp ,#28]
	MOV R1, #0	//Cargar valor literal
	CMP R0, R1	//temp0 = stack[4] > 0
	ble A5	//Realizar salto condicional
	LDR R2, [sp ,#32]
	MOV R3, #0	//Cargar valor literal
	CMP R2, R3	//temp1 = stack[0] > 0
	ble A5	//Realizar salto condicional
A4:
	LDR R0, [sp ,#28]
	SUB R1, R0, #1	//temp2 = stack[4] - 1
	STR R1, [sp, #12]	//LocalStack 12

	LDR R2, [sp ,#32]
	SUB R3, R2, #1	//temp0 = stack[0] - 1
	STR R3, [sp, #4]	//LocalStack 4

	STR R0, [sp, #0]	//LocalStack 0

	PUSH {R0}	//push param localStack

	LDR R0, [sp , #8]	//Set value param stack[28]
	PUSH {R0}	//push param localStack

	bl A

	ADD SP, SP, #8	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #8]	//LocalStack 8

	LDR R1, [sp , #12]	//Set value param stack[20]
	PUSH {R1}	//push param localStack

	LDR R2, [sp , #12]	//Set value param stack[24]
	PUSH {R2}	//push param localStack

	bl A

	ADD SP, SP, #8	//Drop params from Stack

	MOV R0, R0	//get return value
	STR R0, [sp, #24]	//LocalStack 24

A5:
	LDR R0, [sp , #24]	//Get value stack[8]
	MOV R0, R0	//Retrun value is alwasy on R0
	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	POP {R4-R12, pc}

main:
	stmfd sp!, {fp, lr}

	bl initStack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para a

	bl stack	//aumentar stack pointer

	MOV R0, #0
	PUSH {R0}	//push param

	MOV R0, #0
	PUSH {R0}	//push param

	bl A

	ADD SP, SP, #8	//Drop params from Stack

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


