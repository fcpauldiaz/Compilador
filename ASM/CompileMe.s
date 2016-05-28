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

.text
.align 2

.global main
.type main, %function
main:
	stmfd sp!, {fp, lr}

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para c

	MOV R0, #1
	MOV R1, #2	//Set value 2
	MUL R0, R0, R1	//temp0 = 1 * 2
	MOV R0, R0	//Assgin stack[0] = temp0

