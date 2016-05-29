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
	LDR R5, =offset
	LDR R5, [R5]	//Cargar offset actual

	MOV R10, LR 

	LDR R0, =salida_num
	bl printf

		//hay un param
	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para d

	MOV R0, #4
	STR R0, [sp, #0]	//LocalStack 0

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

	MOV R1, #1
	STR R1, [sp, #8]	//LocalStack 8

	MOV R2, #2
	ADD R0, R2, #4	//temp0 = 2 + 4
	STR R0, [sp, #4]	//LocalStack 4

	LDR R3, [sp , #8]
	LDR R4, [sp , #4]	//Set value stack[4]
	ADD R0, R3, R4	//temp0 = stack[0] + stack[4]
	STR R0, [sp, #0]	//LocalStack 0

	PUSH {R3}	//push param localStack

	LDR R0, =offset
	MOV R1, #8
	STR R1, [R0]	//Guardar offset actual

	bl printNum

	ADD SP, SP, #12	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

