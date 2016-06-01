/**
* Universidad Del Valle de Guatemala
* 22-may-2016
* Pablo Díaz 13203
*/

package compiler;


/**
 *
 * @author Pablo
 */
public class PreCompilation {
    
    private AddressDescriptor address;
    private RegisterDescriptor registers;
    private CodeGenerator asm = new CodeGenerator();
    private int stackPointer = -4;
    private int offset = 0;
    private String lastMethod="";
    private String lastOp = "";
    private int paramsCounter = 0;
    private int sendParams = 0;
    
   
    public PreCompilation(InterCodeTable table){
        this.address = new AddressDescriptor();
        this.registers = new RegisterDescriptor();
        
        genCode(table);
        asm.crearArchivo();
    } 
    
    //generar código base de un archivo .s
    //separar estructura de método main y submétodos
    private void genCode(InterCodeTable tabla){
        for (IntermediateCode inter: tabla.getArrayCode()){
             String etiqueta = inter.getEtiqueta();
            String dir1 = inter.getDir1();
            String dir2 = inter.getDir2();
            String op = inter.getOp();
            String res = inter.getRes();
            boolean global = inter.isGlobal();
            boolean stIF = inter.isStatementIF();
            boolean declaration =inter.isDeclaration();
            StackControl localStack = inter.getLocalStack();
            boolean salidaMetodo = inter.isSalidaMetodo();
            boolean method = inter.isMethod();
            //cuadrupla de código intermedio
            if (dir1 != null && dir2 != null && op != null &&
                  res != null && !stIF){
               getReg(inter);
            }
            //cuando se asigna un valor
            else if (dir1 != null &&op!= null &&
                 res != null && !stIF&&!global){
               getRegDir1(inter);
            }
            //cuando es una etiqueta
            else if (etiqueta != null && global == false && declaration == false){
                //insertar codigo especial cuando es la etiqueta main
                if (etiqueta.equals("main:")){
                    asm.insertCode(etiqueta, 0, 1);
                    asm.insertCode("stmfd sp!, {fp, lr}", 1, 2);
                    lastMethod = "main";
                   
                   
                    asm.insertCode("bl initStack",1, 2, "aumentar stack pointer");
                    //registro de activación
                    //cargar offset para el método
                    //cargar parametros
                  
                }
                else{
                    asm.insertCode(etiqueta, 0, 1);
                   // asm.insertCode("LDR R5, =offset",1,1);
                   // asm.insertCode("LDR R5, [R5]", 1,1, "Cargar offset actual");
                    if (method){
                        asm.insertCode("PUSH {R4-R12, LR} ",1,2);
                      
                     
                        asm.insertCode("bl initStack",1, 2, "aumentar stack pointer");
                        
                      
                        
                        lastMethod = etiqueta;
                   // asm.insertCode("ADD R6, R5, #0", 1, 1, "Calcular offset donde viene el param");
                    }
                    else {
                        //es mejor reiniciar descriptor de registros
                        //en etiquetas condicionales
                        //para estar seguros (se comprobó que a veces falla si no se reinicia)
                        this.registers.resetAll();
                    }
                    
                }
                
            }
            else if(localStack != null){
                genDeclarations(inter);
            }
            else if (salidaMetodo){
                //registro de activación para el retorono del método
                 this.registers.resetAll();
                if (stackPointer >=0){
                  
                    stackPointer+=4;
                    
                   
                    //asm.insertCode("bl currentSP", 1, 1, "Get current SP");
                    asm.insertCode("ADD SP, SP, R8", 1, 2, "Mover StackPointer para olvidar variables");
                    this.stackPointer = -4;
                }
                if (lastMethod.equals("main")){
       
                    asm.insertCode("MOV R0, #0", 1, 1, "Salida al SO");
                    asm.insertCode("MOV R3, #0", 1, 1);
                    asm.insertCode("ldmfd sp!, {fp, pc}", 1, 1);
                    asm.insertCode("bx lr", 1, 1);
                }
                if (lastMethod.equals("input:")){
                   
                    asm.insertCode("LDR R0,=mensaje_ingreso", 1, 1);
                    asm.insertCode("bl puts", 1, 1);
                    
                    asm.insertCode("LDR R0, =input_num", 1, 1);
                    asm.insertCode("LDR R1, =input_literal", 1, 1);
                    asm.insertCode("bl scanf", 1,2);
                    asm.insertCode("LDR R1, =input_literal", 1, 1);
                    asm.insertCode("LDR R0, [R1]", 1, 1, "Devoler valor de retorno");
                    asm.insertCode("POP {R4-R12, pc}", 1,2);
                }
                else {
                    //AL FINALIZAR UNA SUBRUTINA
                    asm.insertCode("POP {R4-R12, pc}", 1,2);
                    paramsCounter = 0;
                }
            }
            //PARAMS    
            else if (dir1 != null && dir2 != null && op == null && res == null){
                //DIR1 LITERAL PARAM
                //DIR2 VALUE PARAM
                 this.sendParams+=1;
                 String param = this.registers.revisarRegistros(dir2);
                 boolean cambia = false;
                 if (param.isEmpty()){
                       Registro rg = this.registers.agregarRegistro(dir2);
                       param = rg.getRegistro();
                       cambia = true;
                 }
                try{
                    int num = Integer.parseInt(dir2);
                    String output = "MOV " + param + ", #"+ num;
                    asm.insertCode(output, 1, 1);
                    String push = "PUSH {"+ param+ "}";
                    asm.insertCode(push, 1, 2, "push param");
                    
                  
                    //asm.insertCode("bl stack",1, 2, "aumentar stack pointer");
                    
                    this.stackPointer += 4;
                }catch(Exception e){
                    
                    //aqui entra si el param no es un número
                    if (!dir2.contains("global")){
                        System.out.println(dir2);
                        int num = Integer.parseInt(dir2.substring(dir2.indexOf("[")+1, dir2.indexOf("]")));
                        String instruccion = "LDR " + param+", " + "[sp , #"+(this.stackPointer-num)+"]";
                        
                        if (cambia)
                            asm.insertCode(instruccion, 1, 1, "Set value param " + dir2);
                        String push = "PUSH {"+ param+ "}";
                        asm.insertCode(push, 1, 2, "push param localStack");
                        
                     
                        //asm.insertCode("bl stack",1, 2, "aumentar stack pointer");
                    }else{
                        int num = Integer.parseInt(dir2.substring(dir2.indexOf("[")+1, dir2.indexOf("]")));
                        String loadGlobal = "LDR " + param + ", =global";
                        if (cambia)
                            this.asm.insertCode(loadGlobal, 1, 1, "Cargar etiqueta global");
                        String instruccion = "LDR " +param+ ", "+ "["+param +", #"+(num)+"]";
                        asm.insertCode(instruccion, 1, 1, "Set value param " + dir2);
                        String push = "PUSH {"+ param+ "}";
                        asm.insertCode(push, 1, 2, "push param localStack");
                        
                        
                        //asm.insertCode("bl stack",1, 2, "aumentar stack pointer");
                    }
                    this.stackPointer += 4;
                }
               
            }
            //method call
            else if (dir1 != null && dir2 != null && res != null && op == null){
                //DIR1 CALL literal
                //dir2 method that is called
                //res temporal value saved
                this.offset += this.stackPointer;
                //String saveOffset = "LDR R0, =offset";
                //String save = "MOV R1, #"+this.offset;
                //String off = "STR R1, [R0]";
                //asm.insertCode(saveOffset, 1, 1);
                //asm.insertCode(save, 1, 1);
               // asm.insertCode(off, 1, 2, "Guardar offset actual");
                //reservar espacio para el valor de retorno
               
                asm.insertCode("bl " + dir2, 1, 2);
                //eliminar params
                String dropParams = "ADD SP, SP, #"+sendParams*4;
                asm.insertCode(dropParams, 1,2, "Drop params from Stack");
                this.registers.resetAll();
                Registro returnRegistro = this.registers.agregarRegistro(res);
               // System.out.println("AQUI ESTA RES " + res);
                String returnInstruccion = "MOV " + returnRegistro.getRegistro() + ", R0";
                asm.insertCode(returnInstruccion, 1, 1, "get return value");
                
                this.stackPointer -= sendParams*4;
                paramsCounter = 0;
                sendParams = 0;
               
              
            }
            else if (stIF){
            
                booleanStatement(inter);
            }
            //Goto statements
            else if (dir1 == null && op != null && dir2 != null){
                String goTo = "b " + dir2;
                asm.insertCode(goTo, 1, 2, "Goto " + dir2);
            }
            //codigo para un return statement
            else if (inter.isReturnStatement()){
                //cargar valor en un registro
                //hacerle push
                String registerDescription = this.registers.revisarRegistros(res);
                if (registerDescription.isEmpty()){
                    Registro rg2 = this.registers.agregarRegistro(res);
                    int num = Integer.parseInt(res.substring(res.indexOf("[")+1, res.indexOf("]")));
                    //String offSet = "ADD R6, R5, #" + (this.stackPointer-num);
                   // asm.insertCode(offSet, 1, 1, "Cargar offset a un registro");
                    String instruccion = "LDR " + rg2.getRegistro()+", " + "[sp , #"+ (this.stackPointer-num)+"]";
                    asm.insertCode(instruccion, 1, 1, "Get value " + res);
                    registerDescription = rg2.getRegistro();
                }
                String outputReturn = "MOV R0" + ", " + registerDescription;
                this.registers.setRegistroValor("R0", res);
                asm.insertCode(outputReturn, 1, 1, "Retrun value is alwasy on R0");
                
            }
            else {
                 System.out.println("Caso no contemplado" + inter);
            }
        }
    }
    
    public void getReg(IntermediateCode codigo){
        String res =  codigo.getRes();
        String dir1 = codigo.getDir1();
        String dir2 = codigo.getDir2();
        String op = codigo.getOp();
        String output ="";
       
        //if dir1 is not in Rdir1 (according to the address descriptor of Rdir)
        String registerDescriptionR1 = this.registers.revisarRegistros(dir1);
        
        if (registerDescriptionR1.isEmpty()){
            Registro rg = this.registers.agregarRegistro(dir1);
            System.out.println(rg);
            registerDescriptionR1 = rg.getRegistro();
        
          
            String instruccion="";
            //int num1 = Integer.parseInt(dir1.substring(dir1.indexOf("[")+1, dir1.indexOf("]")));
            //String instruccion = "LDR " + rg.getRegistro()+", " + "[sp , #"+(this.stackPointer-num1)+"]";
            try {
                int num = Integer.parseInt(dir1);
                if (op.equals("/")){
                    instruccion = "MOV " +"R0" + ", #"+ num;
                    this.registers.setRegistroValor("R0", num+"");
                }else{
                    instruccion = "MOV " + registerDescriptionR1+", #"+num;
                 
                }
            }        
            catch(Exception e){
                //cargar desde memoria, stackpointer, etc.
                int num1 = Integer.parseInt(dir1.substring(dir1.indexOf("[")+1, dir1.indexOf("]")));
                //use R4 as offset
                //String offSet = "ADD R6, R5, #" + (this.stackPointer-num1);
               // asm.insertCode(offSet, 1, 1, "Cargar offset a un registro");
                instruccion = "LDR " +registerDescriptionR1+", " + "[sp ,#"+(this.stackPointer-num1)+"]";
            }
            asm.insertCode(instruccion, 1, 1);  
           
        }
     
        
       
        try {
            int num = Integer.parseInt(dir2);
            if (!op.equals("*")&&!op.equals("/")){
                String rRes = this.registers.revisarRegistros(res);
                if (rRes.isEmpty()){
                    Registro resR = this.registers.agregarRegistro(res);
                    rRes = resR.getRegistro();
                }
                
                //modificar descriptor de registros;
                //this.registers.setRegistroValor(rRes, res);
                if (op.equals("+")){
                    output = "ADD "+ rRes +", "+ registerDescriptionR1 + ", #"+dir2;
                }
                if (op.equals("-")){
                    output = "SUB "+ rRes +", "+ registerDescriptionR1 + ", #"+dir2;
                }
                if (op.equals("==")){
                    lastOp = "==";
                    String preOutPut = "MOV " + rRes+ ", #" +dir2;
                    asm.insertCode(preOutPut, 1, 1, "Cargar valor literal");
                    output = "CMP " + registerDescriptionR1 + ", " + rRes;
                }
                if (op.equals("<=")){
                    lastOp = "<=";
                    String preOutPut = "MOV " + rRes+ ", #" +dir2;
                    asm.insertCode(preOutPut, 1, 1, "Cargar valor literal");
                    output = "CMP " + registerDescriptionR1 + ", " + rRes;
                }
                if (op.equals("<")){
                  lastOp = "<";
                    String preOutPut = "MOV " + rRes+ ", #" +dir2;
                    asm.insertCode(preOutPut, 1, 1, "Cargar valor literal");
                    output = "CMP " + registerDescriptionR1 + ", " + rRes;
                }
                  if (op.equals(">=")){
                    lastOp = ">=";
                    String preOutPut = "MOV " + rRes+ ", #" +dir2;
                    asm.insertCode(preOutPut, 1, 1, "Cargar valor literal");
                    output = "CMP " + registerDescriptionR1 + ", " +rRes;
                }
                    if (op.equals(">")){
                    lastOp = ">";
                    String preOutPut = "MOV " +rRes+ ", #" +dir2;
                    asm.insertCode(preOutPut, 1, 1, "Cargar valor literal");
                    output = "CMP " + registerDescriptionR1 + ", " + rRes;
                }
                if (op.equals("!=")||op.equals("<>")){
                    lastOp = ">";
                    String preOutPut = "MOV " + rRes+ ", #" +dir2;
                    asm.insertCode(preOutPut, 1, 1, "Cargar valor literal");
                    output = "CMP " + registerDescriptionR1 + ", " + rRes;
                }

                System.out.println(output);
                asm.insertCode(output, 1, 1, codigo.getRes() + " = " + dir1 +" " + op + " " + dir2);
            }
            else {
                num = 1/0;//throw exception
            }
            
                
        }
        catch(Exception e){
            
            String registerDescriptionR2 = this.registers.revisarRegistros(dir2);
            if (registerDescriptionR2.isEmpty()){
                Registro rg2 = this.registers.agregarRegistro(dir2);
                registerDescriptionR2 = rg2.getRegistro();
            }
                System.out.println(dir2);
                if (dir2.contains("[")){
                    int num = Integer.parseInt(dir2.substring(dir2.indexOf("[")+1, dir2.indexOf("]")));
                    //String offSet = "ADD R6, R5, #" + (this.stackPointer-num);
                   // asm.insertCode(offSet, 1, 1, "Cargar offset a un registro");
                    String instruccion2 = "LDR " + registerDescriptionR2+", " + "[sp , #"+ (this.stackPointer-num)+"]";
                    asm.insertCode(instruccion2, 1, 1, "Set value " + dir2);
              }
                //es u numero
              else {
                    String instruccion = "";
                    if (op.equals("/")){
                        instruccion = "MOV R1 , #"+dir2;
                    }
                    else {
                        instruccion = "MOV " + registerDescriptionR2 + ", #" + dir2;
                    }
                    asm.insertCode(instruccion, 1, 1, "Cargar valor literal");
                }
            
            
             // Registro rRes = this.registers.buscarRegistroMenor();
                //modificar descriptor de registros;
                //this.registers.setRegistroValor(rRes, res);
                String rRes = this.registers.revisarRegistros(res);
                if (rRes.isEmpty()){
                    Registro ResR = this.registers.agregarRegistro(res);
                    rRes = ResR.getRegistro();
                }
                if (op.equals("+")){
                    output = "ADD "+ rRes +", "+ registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("-")){
                    output = "SUB "+ rRes +", "+ registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("*")){
                    output = "MUL "+ rRes +", "+ registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("/")){
                    //cargar valores en r0 y r1
                    asm.insertCode("bl dividir", 1, 2, "Llamar a la subrutina de division");
                }
                if (op.equals("==")){
                    lastOp = "==";
                    output = "CMP " + registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("<=")){
                    lastOp = "<=";
                     output = "CMP " + registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("<")){
                    lastOp = "<";
                    output = "CMP " + registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals(">=")){
                    lastOp = ">=";
                    output = "CMP " + registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals(">")){
                    lastOp = ">";
                    output = "CMP " + registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("!=")||op.equals("<>")){
                    lastOp = "!=";
                    output = "CMP " + registerDescriptionR1 + ", " + registerDescriptionR2;
                }

                System.out.println(output);
                asm.insertCode(output, 1, 1, codigo.getRes() + " = " + dir1 +" " + op + " " + dir2);
            }
        }
        
    public void genDeclarations(IntermediateCode codigo){
        StackControl localStack = codigo.getLocalStack();
     
       
        if (codigo.isParam()== false){
           
            //declaracion de variable normal
            if (!localStack.getTipo().contains("array")){
                asm.insertCode("MOV R0, #0", 1, 1, "Valor default");
                asm.insertCode("push {r0}", 1, 2, "Reservar espacio para " + localStack.getIdentificador());
                 asm.insertCode("bl stack",1, 2, "aumentar stack pointer");
                  this.stackPointer = this.stackPointer + 4;
            }
            //declaracion de ARRAY
            else {
                int tamaño = localStack.getSize();
                for (int i = 1; i < tamaño+1; i++){
                    asm.insertCode("MOV R0, #0", 1,1, "Valor default array");
                    asm.insertCode("push {R0}", 1, 2, "Reservar espacio para array " + localStack.getIdentificador()+ " ["+i+"]");
                    asm.insertCode("bl stack",1, 2, "aumentar stack pointer");
                    this.stackPointer += 4;
                }
            }
           
            
           
        }
        //si es  parametro se le pone el valor que trae y se le reserva el espacio en pila.
        else{
            //asm.insertCode("ADD R6, R5, #0", 1, 1, "Offset del param dentro la pila");
            paramsCounter += 1;
            stackPointer +=4;
            asm.insertCode("LDR R0, [SP, #"+(stackPointer+(36+paramsCounter*4))+"]", 1, 1, "Valor del param se saca de la pila");
            asm.insertCode("push {r0}", 1, 2, "Reservar espacio para param " + localStack.getIdentificador());
            
          
            asm.insertCode("bl stack",1, 2, "aumentar stack pointer");
            
            //this.stackPointer = this.stackPointer + 4;
            if (lastMethod.equals("printNum:")){
                //colocar instruccion de print después de reservar espacio para el param.
                asm.insertCode("LDR R1, [sp, #0]",1, 1, "Cargar en offset");
                asm.insertCode("LDR R0, =salida_num", 1, 1);
                asm.insertCode("bl printf", 1, 2);
            }
            if (lastMethod.equals("printChar:")){
                asm.insertCode("LDR R1, [SP, #0]", 1, 1, "Cargar valor actual en pila");
                asm.insertCode("LDR R0, =salida_char", 1,1);
                asm.insertCode("bl printf", 1, 1);
            }
          
          
        }
    }
    
    
    public void getRegDir1(IntermediateCode codigo){
        String res =  codigo.getRes();
        String dir1 = codigo.getDir1();
        String op = codigo.getOp();
      
         //if dir1 is not in Rdir1 (according to the address descriptor of Rdir)
        String registerDescriptionR1 = this.registers.revisarRegistros(dir1);
        
        if (registerDescriptionR1.isEmpty()){
            Registro rg = this.registers.agregarRegistro(dir1);
            System.out.println(rg);
            String instruccion = "";
            try {
                int num = Integer.parseInt(dir1);
                instruccion = "MOV " + rg.getRegistro()+", #"+num;
            }
            catch(Exception e){
                //cargar desde memoria, stackpointer, etc.
                System.out.println(dir1);
                if (dir1.contains("[")){
                    int num = Integer.parseInt(dir1.substring(dir1.indexOf("[")+1, dir1.indexOf("]")));
                   // String offSet = "ADD R6, R5, #" + (this.stackPointer-num);
                   // asm.insertCode(offSet, 1, 1, "Cargar offset a un registro");
                    instruccion = "LDR " + rg.getRegistro() + ", [sp, #"+(this.stackPointer-num)+"]";
                }
                else if (dir1.contains("'")){
                    instruccion = "MOV " + rg.getRegistro() + ", #"+dir1;
                }
                else {
                    instruccion = "MOV " + rg.getRegistro() + ", " + this.registers.revisarRegistros(dir1);
                }
            }
            asm.insertCode(instruccion, 1, 1);  
            registerDescriptionR1 = rg.getRegistro();
        }
        String output = "";
       
        Registro rRes = this.registers.buscarRegistroMenor();
        //output = "MOV "+rRes.getRegistro()+ ", " + registerDescriptionR1;
        
        //System.out.println("aqui");
        //System.out.println(registerDescriptionR1);
        //this.asm.insertCode(output, 1, 1, "Assgin " + codigo.getRes() + " " + op + " " + dir1);
        if (!codigo.getRes().contains("global")){
            int num = Integer.parseInt(codigo.getRes().substring(codigo.getRes().indexOf("[")+1, codigo.getRes().indexOf("]")));
            //String offSet = "ADD R6, R5, #" + (this.stackPointer-num);
            //asm.insertCode(offSet, 1, 1, "Cargar offset a un registro");
            String saveLocal = "STR " + registerDescriptionR1 + ", [sp, #"+(this.stackPointer-num)+"]";
            this.asm.insertCode(saveLocal, 1, 2, "LocalStack " + (this.stackPointer-num));
          
        }
        else {
            //es una asignacion global
            int num = Integer.parseInt(codigo.getRes().substring(codigo.getRes().indexOf("[")+1, codigo.getRes().indexOf("]")));
          
            String rGlobal = this.registers.revisarRegistros(res);
            if (rGlobal.isEmpty()){
                Registro rg = this.registers.agregarRegistro(res);
                rGlobal = rg.getRegistro();
                
            }
            String loadGlobal = "LDR " + rGlobal + ", =global";
            this.asm.insertCode(loadGlobal, 1, 1, "Cargar etiqueta global");
            String saveLocal = "STR " +rRes.getRegistro()+ ", "+ "["+rGlobal +", #"+(num)+"]";
            this.asm.insertCode(saveLocal, 1, 2, "GlobalStack " + (num));
        }
        
    }
    
    public void booleanStatement(IntermediateCode codigo){
        String res = codigo.getRes(); //la condicion
        String dir2 = codigo.getDir2(); //la etiqueta a saltar
        String branch ="Error";
        if (res.contains("True")&&lastOp.equals("==")){
            //insertar branch con eq
             branch = "beq " + dir2;
        }
        if (res.contains("True")&&lastOp.equals("<")){
            branch = "blt " + dir2;
        }
        if (res.contains("True")&&lastOp.equals("<=")){
            branch = "ble " + dir2; 
        }
        if (res.contains("True")&&lastOp.equals(">")){
            branch = "bgt " + dir2;
        }
        if (res.contains("True")&&lastOp.equals(">=")){
            branch = "bge " + dir2;
        }
        if (res.contains("True")&&lastOp.equals("!=")){
            branch = "bne " + dir2;
        }
        if (res.contains("False")&&lastOp.equals("==")){
            //insertar branch con eq
             branch = "bne " + dir2;
        }
        if (res.contains("False")&&lastOp.equals("<")){
            branch = "bge " + dir2;
        }
        if (res.contains("False")&&lastOp.equals("<=")){
            branch = "bgt " + dir2; 
        }
        if (res.contains("False")&&lastOp.equals(">")){
            branch = "ble " + dir2;
        }
        if (res.contains("False")&&lastOp.equals(">=")){
            branch = "blt " + dir2;
        }
        if (res.contains("False")&&lastOp.equals("!=")){
            branch = "beq " + dir2;
        }
         
       
        asm.insertCode(branch, 1, 1, "Realizar salto condicional");
    }

}
