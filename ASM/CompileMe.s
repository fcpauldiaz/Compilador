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

	MOV R0, #4
	ADD R0, R0, #2	//temp0 = 4 + 2
	ADD R0, R0, #3	//temp1 = temp0 + 3
	LDR R1, =global	//Cargar etiqueta global
	STR R0, [R1, #0]	//GlobalStack 0

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

