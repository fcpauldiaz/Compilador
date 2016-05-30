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

	MOV R0, #2
	STR R0, [sp, #12]	//LocalStack 12

whileLoop_main0:
	LDR R1, [sp ,#12]
	CMP R0, R1	//temp0 = stack[0] == 2
	beq main0	//Realizar salto condicional
	b main1	//Goto main1

main0:
	MOV R2, #1
	STR R2, [sp, #12]	//LocalStack 12

	b whileLoop_main0	//Goto whileLoop_main0

main1:
	PUSH {R1}	//push param localStack

	bl printNum

	ADD SP, SP, #16	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

