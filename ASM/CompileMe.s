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
	PUSH {LR} 

	LDR R0, [SP, #4]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param x

	LDR R1, [sp, #0]	//Cargar en offset
	LDR R0, =salida_num
	bl printf

	ADD SP, SP, #4	//Mover StackPointer para olvidar variables

	POP {pc}

test:
	PUSH {LR} 

	LDR R0, [SP, #4]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param x

	LDR R1, [sp, #0]	//Cargar en offset
	LDR R0, =salida_num
	bl printf

	LDR R0, [SP, #4]	//Valor del param se saca de la pila
	push {r0}	//Reservar espacio para param y

	LDR R1, [sp, #0]	//Cargar en offset
	LDR R0, =salida_num
	bl printf

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para c

	MOV R0, #100
	MOV R1 , #2	//Cargar valor literal
	bl dividir	//Llamar a la subrutina de division

		//temp0 = 100 / 2
	STR R0, [sp, #8]	//LocalStack 8

	ADD SP, SP, #20	//Mover StackPointer para olvidar variables

	POP {pc}

main:
	stmfd sp!, {fp, lr}

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para c

	MOV R0, #0	//Valor default
	push {r0}	//Reservar espacio para d

	MOV R2, #10
	STR R2, [sp, #4]	//LocalStack 4

	LDR R3, [sp , #4]	//Set value param stack[0]
	PUSH {R3}	//push param localStack

	LDR R0, [sp , #4]	//Set value param stack[4]
	PUSH {R0}	//push param localStack

	bl test

	ADD SP, SP, #16	//Mover StackPointer para olvidar variables

	MOV R0, #0	//Salida al SO
	MOV R3, #0
	ldmfd sp!, {fp, pc}
	bx lr

