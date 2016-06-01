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
main:
	stmfd sp!, {fp, lr}

	bl initStack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [1]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [2]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [3]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [4]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [5]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [6]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [7]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [8]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [9]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default array
	push {R0}	//Reservar espacio para array array [10]

	bl stack	//aumentar stack pointer

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para b

	bl stack	//aumentar stack pointer

	MOV R0, #69
	STR R0, [sp, #40]	//LocalStack 40

	LDR R1, [sp, #40]
	STR R1, [sp, #0]	//LocalStack 0

	LDR R2, [sp , #0]	//Set value param stack[40]
	PUSH {R2}	//push param localStack

	bl printNum

	ADD SP, SP, #4	//Drop params from Stack

	MOV R3, R0	//get return value
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


