/*
* UVG 
* Pablo Diaz 
* Compilador 
* Mayo 2016 
*/

.data
.align 2

salida_num:    .asciz "El resultado es: %d \n"
salida_str:    .asciz "El character es: %s \n"
input_num:     .asciz "Ingrese el input %s \n"
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

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para x

	bl stack	//aumentar stack pointer

	MOV R0, #2
	STR R0, [sp, #0]	//LocalStack 0

	ADD SP, SP, R8	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

