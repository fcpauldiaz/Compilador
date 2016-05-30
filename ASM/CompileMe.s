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
main:
	stmfd sp!, {fp, lr}

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para c

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para d

	MOV R0, #10
	STR R0, [sp, #4]	//LocalStack 4

	STR R0, [sp, #0]	//LocalStack 0

	LDR R1, [sp ,#4]
	LDR R2, [sp , #0]	//Set value stack[4]
	CMP R1, R2	//temp0 = stack[0] == stack[4]
	beq main0	//Realizar salto condicional
	b main1	//Goto main1

main0:
	SUB R0, R1, #1	//temp1 = stack[0] - 1
	STR R0, [sp, #4]	//LocalStack 4

main1:
	ADD SP, SP, #8	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

