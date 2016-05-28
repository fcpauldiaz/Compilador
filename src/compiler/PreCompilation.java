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
    private int stackPointer = 0;
   
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
            
            //cuadrupla de código intermedio
            if (dir1 != null && dir2 != null && op != null &&
                  res != null && !stIF){
               getReg(inter);
            }
            //cuando se asigna un valor
            else if (dir1 != null &&op!= null &&
                 res != null && !stIF){
               getRegDir1(inter);
            }
            //cuando es una etiqueta
            else if (etiqueta != null && global == false && declaration == false){
                //insertar codigo especial cuando es la etiqueta main
                if (etiqueta.equals("main:")){
                    asm.insertCode(etiqueta, 0, 1);
                    asm.insertCode("stmfd sp!, {fp, lr}", 1, 2);
                    //registro de activación
                    //cargar offset para el método
                    //cargar parametros
                }
                else{
                    asm.insertCode(etiqueta, 0, 1);
                }
            }
            else if(localStack != null){
                genDeclarations(inter);
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
            String instruccion = "LDR " + rg.getRegistro();
            try {
                int num = Integer.parseInt(dir1);
                instruccion = "MOV " + rg.getRegistro()+", #"+num;
            }
            catch(Exception e){
                //cargar desde memoria, stackpointer, etc.
            }
            asm.insertCode(instruccion, 1, 1);  
            registerDescriptionR1 = rg.getRegistro();
        }
     
        
       
        try {
            int num = Integer.parseInt(dir2);
            if (!op.equals("*")&&!op.equals("/")){
                Registro rRes = this.registers.buscarRegistroMenor();
                //modificar descriptor de registros;
                this.registers.setRegistroValor(rRes, res);
                if (op.equals("+")){
                    output = "ADD "+ rRes.getRegistro() +", "+ registerDescriptionR1 + ", #"+dir2;
                }
                if (op.equals("-")){
                    output = "SUB "+ rRes.getRegistro() +", "+ registerDescriptionR1 + ", #"+dir2;
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
              String instruccion = "MOV " + rg2.getRegistro()+", #"+dir2;
              asm.insertCode(instruccion, 1, 1, "Set value " + dir2);
              
            }
            
              Registro rRes = this.registers.buscarRegistroMenor();
                //modificar descriptor de registros;
                this.registers.setRegistroValor(rRes, res);
                if (op.equals("+")){
                    output = "ADD "+ rRes.getRegistro() +", "+ registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("-")){
                    output = "SUB "+ rRes.getRegistro() +", "+ registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("*")){
                    output = "MUL "+ rRes.getRegistro() +", "+ registerDescriptionR1 + ", " + registerDescriptionR2;
                }
                if (op.equals("/")){
                    //cargar valores en r0 y r1
                    asm.insertCode("bl dividir", 1, 2, "Llamar a la subrutina de division");
                }

                System.out.println(output);
                asm.insertCode(output, 1, 1, codigo.getRes() + " = " + dir1 +" " + op + " " + dir2);
            }
        }
        
    public void genDeclarations(IntermediateCode codigo){
        StackControl localStack = codigo.getLocalStack();
        this.stackPointer = this.stackPointer + 4;
        asm.insertCode("MOV R0, #0", 1, 1, "Valor default");
        asm.insertCode("push {r0}", 1, 2, "Reservar espacio para " + localStack.getIdentificador());
        //modificar descriptor de registros
        this.registers.setRegistroValor("R0", "0");
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
            String instruccion = "LDR " + rg.getRegistro();
            try {
                int num = Integer.parseInt(dir1);
                instruccion = "MOV " + rg.getRegistro()+", #"+num;
            }
            catch(Exception e){
                //cargar desde memoria, stackpointer, etc.
            }
            asm.insertCode(instruccion, 1, 1);  
            registerDescriptionR1 = rg.getRegistro();
        }
        String output = "";
        if (op.equals("=")){
            Registro rRes = this.registers.buscarRegistroMenor();
            output = "MOV "+rRes.getRegistro()+ ", " + registerDescriptionR1;
        }
        System.out.println("aqui");
        System.out.println(registerDescriptionR1);
        this.asm.insertCode(output, 1, 1, "Assgin " + codigo.getRes() + " " + op + " " + dir1);
        
        
    }
    
    

}
