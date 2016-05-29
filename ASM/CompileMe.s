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
test:
	MOV R10, LR 

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para d

	MOV R0, #1
	STR R0, [sp, #0]	//LocalStack 0

	ADD SP, SP, #4	//Mover StackPointer para olvidar variables

	MOV PC, R10

main:
	stmfd sp!, {fp, lr}

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para c

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para d

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para e

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para f

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para g

	STR R0, [sp, #16]	//LocalStack 16

	MOV R1, #4
	STR R1, [sp, #12]	//LocalStack 12

	MOV R2, #8
	STR R2, [sp, #8]	//LocalStack 8

	MOV R3, #12
	STR R3, [sp, #4]	//LocalStack 4

	MOV R0, #16
	STR R0, [sp, #0]	//LocalStack 0

	LDR R0, [sp ,#12]
		//temp0 = stack[4] == 5
main0:
	MOV R10, LR 

	STR R1, [sp, #-8]	//LocalStack -8

main1:
	MOV R10, LR 

	MOV PC, R10


