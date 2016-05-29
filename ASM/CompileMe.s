/*
* UVG 
* Pablo Diaz 
* Compilador 
* Mayo 2016 
*/

.data
.align 2

salida_num:    .asciz "%d \n"
salida_str:    .asciz "%f \n"
global: .space
offset: .space

.text
.align 2

.global main
.type main, %function
printNum:
	MOV R10, LR 

	LDR R0, [SP, #0]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param x

	LDR R1, [sp, #0]	//Cargar en offset
	LDR R0, =salida_num
	bl printf

	ADD SP, SP, #8	//Mover StackPointer para olvidar variables

	MOV PC, R10

main:
	stmfd sp!, {fp, lr}

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para c

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para d

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para a

	MOV R0, #1
	STR R0, [sp, #8]	//LocalStack 8

	MOV R1, #2
	ADD R0, R1, #4	//temp0 = 2 + 4
	STR R0, [sp, #4]	//LocalStack 4

	LDR R2, [sp ,#8]
	LDR R3, [sp , #4]	//Set value stack[4]
	ADD R0, R2, R3	//temp0 = stack[0] + stack[4]
	STR R0, [sp, #0]	//LocalStack 0

	PUSH {R3}	//push param localStack

	bl printNum

	ADD SP, SP, #12	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

