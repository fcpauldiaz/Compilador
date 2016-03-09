package GUI;


import antlr4.programLexer;
import antlr4.programParser;
import static compiler.ANTGui.leerArchivo;
import compiler.DescriptiveErrorListener;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class ToolbarFrame extends JFrame {

    protected JToolBar  toolBar;
    //private   MainPanel panel;
    private   LinkedList<MainPanel> panelList = new LinkedList<MainPanel>();
    public    static    JButton save_button;
    public    static    JMenuItem savemenuItem;
    public    static    String title_text = "COMPILER IDE";
    public    static    File archivoSeleccionado;
    private   String    last_path = "";
    private   JTabbedPane tp = new JTabbedPane();
    public static  JTextPane outputTextPane;
    
    /*****************************************
    * ToolbarFrame
    * Constructor
    *****************************************/
    public ToolbarFrame() {
        super(""+title_text+"-SDX");
        this.setSize(900, 600);
        JMenuBar menuBar = createMenuBar();
        this.setJMenuBar(menuBar);

        this.toolBar.setOrientation(JToolBar.HORIZONTAL);
        this.toolBar.setBorderPainted(false);
        //this.setLayout(new GridLayout(2,1));
        //this.getContentPane().add(toolBar);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        
        //Add the Main Panel
        //panel = new MainPanel();
        this.panelList.add(new MainPanel());
        this.tp.addTab("untitled.txt", this.panelList.get(0));
        
        this.getContentPane().add(this.tp);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*****************************************
    * createMenuBar
    * @return JMenuBar with all the File and Help bar
    * creates all the bars
    *****************************************/
    protected JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();

        //************************ Actions ************************
        Action actionNew = new AbstractAction("New", Main.iconNew) {
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        };
        Action actionOpen = new AbstractAction("Open...", Main.iconOpen) {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        };
        Action actionClose = new AbstractAction("Close...", Main.iconClose) {
            public void actionPerformed(ActionEvent e) {
                closeFile();
            }
        };
        Action actionSave = new AbstractAction("Save...", Main.iconSave) {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        };
        Action actionSaveAs = new AbstractAction("Save As...", Main.iconSaveAs) {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        };
        Action actionExit = new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
        Action actionParse = new AbstractAction("Parse", Main.iconParse) {
            public void actionPerformed(ActionEvent e) {
                parse();
            }
        };
        Action actionCopy = new AbstractAction("Copy", Main.iconCopy) {
            public void actionPerformed(ActionEvent e) {
                copy();
            }
        };
        Action actionPaste = new AbstractAction("Paste", Main.iconPaste) {
            public void actionPerformed(ActionEvent e) {
                paste();
            }
        };
        Action actionCut = new AbstractAction("Cut", Main.iconCut) {
            public void actionPerformed(ActionEvent e) {
                cut();
            }
        };
        Action actionSearch = new AbstractAction("Search", Main.iconSearch) {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        };
        //*********************************************************

        //************************* JMenu *************************
        JMenu mFile = new JMenu("File");
        mFile.setMnemonic('F');
        JMenuItem item = mFile.add(actionNew);
            item.setMnemonic('n');
            item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        //-----------------------------
        item = mFile.add(actionOpen);
            item.setMnemonic('o');
            item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        //-----------------------------
        savemenuItem = mFile.add(actionSave);
            savemenuItem.setMnemonic('s');
            savemenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
            //savemenuItem.setEnabled(false);
        //-----------------------------
        item = mFile.add(actionSaveAs);
            item.setMnemonic('a');
            //item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        //-----------------------------
        mFile.addSeparator();
        //-----------------------------
        item = mFile.add(actionClose);
            item.setMnemonic('c');
            item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        mFile.addSeparator();
        item = mFile.add(actionExit);
            item.setMnemonic('x');
            item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        //*****************************
        JMenu mHelp = new JMenu("Help");
        mHelp.setMnemonic('H');
        mHelp.addSeparator();
        //*****************************
        JMenu mRun = new JMenu("Run");
        mRun.setMnemonic('R');
        item = mRun.add(actionParse);
            mRun.add(item);
            item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        //-----------------------------
        //*****************************
        JMenu mEdit = new JMenu("Edit");
        mEdit.setMnemonic('E');
        item = mEdit.add(actionCut);
        //-----------------------------
        item = mEdit.add(actionCopy);
        //-----------------------------
        item = mEdit.add(actionPaste);
        //-----------------------------
        item = mEdit.add(actionSearch);
            item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        //-----------------------------
        mHelp.addSeparator();
        menuBar.add(mFile);
        menuBar.add(mEdit);
        menuBar.add(mRun);
        menuBar.add(mHelp);
        //-----------------------------
        //*********************************************************
        //*********************** JToolBar ************************
        toolBar = new JToolBar();
        JButton btn = toolBar.add(actionNew);
        btn.setToolTipText("Nuevo Archivo");
        //-----------------------------
        btn = toolBar.add(actionOpen);
        btn.setToolTipText("Abrir archivo");
        //-----------------------------
        save_button = toolBar.add(actionSave);
        save_button.setToolTipText("Guardar archivo");
        //save_button.setEnabled(false);
        //-----------------------------
        toolBar.addSeparator();
        btn = toolBar.add(actionClose);
        btn.setToolTipText("Cerrar tab");
        //-----------------------------
        toolBar.addSeparator();
        toolBar.addSeparator();
        //-----------------------------
        btn = toolBar.add(actionCut);
        btn.setToolTipText("Cortar");
        //-----------------------------
        btn = toolBar.add(actionCopy);
        btn.setToolTipText("Copiar");
        //-----------------------------
        btn = toolBar.add(actionPaste);
        btn.setToolTipText("Pegar");
        //-----------------------------
        btn = toolBar.add(actionSearch);
        btn.setToolTipText("Buscar");
        //-----------------------------
        toolBar.addSeparator();
        toolBar.addSeparator();
        //-----------------------------
        btn = toolBar.add(actionParse);
        btn.setToolTipText("Compilar");
        //*********************************************************
        return menuBar;
    }

    /*****************************************
    * search
    * @return void
    * search function
    *****************************************/
    private void search() {
        String search_for = JOptionPane.showInputDialog("Search for:");
        if(search_for== null || search_for.isEmpty()){
            return;
        }
        this.panelList.get(this.tp.getSelectedIndex()).getTextPanel().highlight(search_for);
    }

    /*****************************************
    * copy
    * @return void
    * copy function
    *****************************************/
    private void copy() {
        //TODO falta hacer copy
        System.out.println("copy");
    }

    /*****************************************
    * paste
    * @return void
    * paste function
    *****************************************/
    private void paste() {
        //TODO falta hacer paste
        System.out.println("paste");
    }

    /*****************************************
    * cut
    * @return void
    * cut function
    *****************************************/
    private void cut() {
        //TODO falta hacer cut
        System.out.println("cut");
    }

    /*****************************************
    * exit
    * @return void
    * leave the program
    *****************************************/
    private void exit() {
        
        if(this.panelList.get(this.tp.getSelectedIndex()).modified){
            boolean cancel = save_before();
            if(cancel)
                return;
        }
        System.exit(0);
    }

    /*****************************************
    * newFile
    * @return void
    * creates an empty file
    *****************************************/
    private void newFile() {

        System.out.println("Nuevo archivo");
        this.panelList.add(new MainPanel());
        this.tp.addTab(this.panelList.getLast().file.getName(), this.panelList.getLast());
        this.tp.setSelectedIndex(this.panelList.size()-1);
    }

    /*****************************************
    * save_before
    * @return void
    * creates an empty file
    *****************************************/
    private boolean save_before() {

        int a = JOptionPane.showConfirmDialog(this,"El contenido de este archivo ha cambiado.\nDesea Guardarlo?","Modificaciones",JOptionPane.YES_NO_CANCEL_OPTION);
        if(a == JOptionPane.CANCEL_OPTION){
            //System.out.println("cancel");
            return true;
        }
        if(a == JOptionPane.YES_OPTION){
            //System.out.println("yes");
            this.saveFile();
        }
        if(a == JOptionPane.NO_OPTION){
            //System.out.println("no -> do nothing");
        }
        return false;
    }

    /*****************************************
    * closeFile
    * @return void
    * open a file
    *****************************************/
    private void closeFile() {
        if(this.panelList.size()==1){
            JOptionPane.showMessageDialog(this,"Cannot remove the last tab","Removing tab error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(this.panelList.get(this.tp.getSelectedIndex()).modified){
            boolean cancel = save_before();
            if(cancel)
                return;
        }
        int index = this.tp.getSelectedIndex();
        this.tp.removeTabAt(index);
        this.panelList.remove(index);
    }

    /*****************************************
    * openFile
    * @return void
    * open a file
    *****************************************/
    private void openFile() {

        if(this.panelList.get(this.tp.getSelectedIndex()).modified){
            boolean cancel = save_before();
            if(cancel)
                return;
        }

        System.out.println("abriendo archivo");
        JFileChooser jfc;
        if(this.last_path.isEmpty()){
            jfc = new JFileChooser();
        }else{
            jfc = new JFileChooser(last_path);
        }
        jfc.setFileFilter(new FileNameExtensionFilter("Text document", "txt"));
        int result = jfc.showOpenDialog(this);
        if(result == JFileChooser.CANCEL_OPTION)
            return;
        try{
            File selectedFile = jfc.getSelectedFile();
            boolean acepted = this.loadText(selectedFile);
            if(acepted){
                this.panelList.get(this.tp.getSelectedIndex()).getTextPanel().setText(loadStringText(selectedFile));
                this.panelList.get(this.tp.getSelectedIndex()).file = new File(selectedFile.getAbsolutePath());
                this.last_path = this.panelList.get(this.tp.getSelectedIndex()).file.getParent();
                //System.out.println("last path: "+this.file.getParent());
            }
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(this,"Error cargando el archivo:\n"+exp.getMessage(),"Error archivo",JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.panelList.get(this.tp.getSelectedIndex()).hasSaved = true;
        setModified(false);
    }

    /*****************************************
    * saveFile
    * @return void
    * save a file
    *****************************************/
    private void saveFile( ) {
        if(save_button.isEnabled()==false){
            return;
        }
        if(this.panelList.get(this.tp.getSelectedIndex()).hasSaved==false){
            this.saveAsFile();
        }
        else{
            if(this.panelList.get(this.tp.getSelectedIndex()).modified==false)
                return;
            System.out.println("save file - "+this.panelList.get(this.tp.getSelectedIndex()).file.getAbsolutePath());
            File current_file = this.panelList.get(this.tp.getSelectedIndex()).file;
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(current_file));
                bw.write(this.getAllText());
                bw.close();
            }
            catch (Exception exp) {
                JOptionPane.showMessageDialog(this,"An error has ocurred while saving:\n"+exp.getMessage(),"Saving Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        setModified(false);
   }

   /*****************************************
    * saveAsFile
    * @return void
    * leave the program
    *****************************************/
    private void saveAsFile() {
        System.out.println("save As File");
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Text document", "txt"));
        int result = jfc.showSaveDialog(this);
        if(result == JFileChooser.CANCEL_OPTION){
            return;
        }
        File current_file = jfc.getSelectedFile();
        
        try {
           BufferedWriter bw = new BufferedWriter(new FileWriter(current_file));
           bw.write(this.getAllText());
           bw.close();
        }
        catch (Exception exp) {
           JOptionPane.showMessageDialog(this,"An error has ocurred while saving:\n"+exp.getMessage(),"Saving Error",JOptionPane.ERROR_MESSAGE);
           return;
        }
        this.panelList.get(this.tp.getSelectedIndex()).file = new File(current_file.getAbsolutePath());
        this.panelList.get(this.tp.getSelectedIndex()).hasSaved = true;
        setModified(false);
    }

    /*****************************************
     * True if the file value has been modified but not saved.  The
     * default value of this property is false.
     * <p>
     * This is a bound read-only property.
     *
     * @return the value of the modified property.
     * @see #isModified
     *****************************************/
    public boolean isModified() {
        return this.panelList.get(this.tp.getSelectedIndex()).modified;
    }

    /*****************************************
    * Parse
    * @return void
    * parse
    *****************************************/
    private void parse() {
        System.out.println("parse");
        Scope.cont = 0;
        //ver de guardar....
        if(this.panelList.get(this.tp.getSelectedIndex()).modified){
            this.saveFile();
        }
        //por si le da cancel o no
        if(this.panelList.get(this.tp.getSelectedIndex()).modified){
            return;
        }
        this.panelList.get(this.tp.getSelectedIndex()).tp2.setSelectedIndex(0);
        outputTextPane = this.panelList.get(this.tp.getSelectedIndex()).getOutputPanel().getTextPane();
        //JTree treePane = this.panelList.get(this.tp.getSelectedIndex()).getTreePanel().getTree();
         File current_file = this.panelList.get(this.tp.getSelectedIndex()).file;
        CharStream cs =  new ANTLRInputStream(leerArchivo(current_file));
       
       programLexer lexer = new programLexer(cs);
       
       CommonTokenStream tokens = new CommonTokenStream( lexer);
       programParser parser = new programParser(tokens);
       ParseTree tree = parser.program();
       
       //System.out.println(tokens.getTokens().toString());
      // System.out.println(parser.program().getText());
           // System.out.println(result.getParent().getText());
       
        
        parser.removeErrorListeners();
        parser.addErrorListener(DescriptiveErrorListener.INSTANCE);
            

        // Specify our entry point
        programParser.ProgramContext contexto = parser.program();
        //programParser.DeclarationContext declaration = parser.declaration();
       
        
        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
      
        
       // ANTLRListener listener = new ANTLRListener();
       // walker.DEFAULT.walk(listener, contexto);
        Trees.inspect(contexto, parser);

        
        int errorsCount = parser.getNumberOfSyntaxErrors();
            System.out.println(errorsCount);
        if(errorsCount == 0){
          System.out.println("Parseo Exitoso");
          ToolbarFrame.outputTextPane.setText("Parseo Exitoso");
        }
        
       
       // DecafParser parser = new DecafParser();
        
        /*
        //System.out.println("Path completo del archivo usado: "+ this.file.getAbsolutePath());
        parser.Parse(this.panelList.get(this.tp.getSelectedIndex()).file.getAbsolutePath());
        //si no hubo un error, generar el arbol en jtree
        if(parser.isError() == false){
            //System.out.println("se debería actualizar el árbol...");
            //** escribir el texto ***************************
            SimpleAttributeSet set = new SimpleAttributeSet();
            outputTextPane.setCharacterAttributes(set, true);
            outputTextPane.setText(parser.getDebug_log()+"\n"+parser.getOutput_log()+"\n\n");
            //outputTextPane.setText("\n"+parser.getOutput_log()+"\n");
            //*************************************************
            //StyleConstants.setBold(set, true);
            String texto_error = "BUILD SUCCESSFUL";
            StyleConstants.setForeground(set, new Color(29,148,23));//green
            Document doc = outputTextPane.getStyledDocument();
            outputTextPane.setCharacterAttributes(set, true);
            try {
                doc.insertString(doc.getLength(), texto_error, set);
            } catch (BadLocationException ex) {
                //Logger.getLogger(DocumentEditorView.class.getName()).log(Level.SEVERE, null, ex);
            }
            //*************************************************
            treePane.setModel(parser.getJTree().getModel());
            treePane.setVisible(true);
            
            //------- Symbol table pane ----------------------------------------
            String[][] data = parser.getSymbolTable().getData();
            this.panelList.get(this.tp.getSelectedIndex()).getSimbolTablePanel().setModel((new JTable(data,SymbolTable.getColumNames())).getModel());
            //------- Method table pane ----------------------------------------
            data = parser.getMethodTable().getData();
            this.panelList.get(this.tp.getSelectedIndex()).getMethodTablePanel().setModel((new JTable(data,MethodTable.getColumNames())).getModel());
            //------- Structure table pane -------------------------------------
            data = parser.getStructTable().getData();
            this.panelList.get(this.tp.getSelectedIndex()).getStructTablePanel().setModel((new JTable(data,StructTable.getColumNames())).getModel());
            //------------------------------------------------------------------


        }
        else{
            //hubo un error... ver el error_log

            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setForeground(set, Color.red);
            outputTextPane.setCharacterAttributes(set, true);
            outputTextPane.setText(parser.getError_log()+"\n");

            //**************** escribe la palabra error con un estilo diferente *******
            StyleConstants.setBold(set, true);
            String texto_error = "\nAn error has occurred. ¡Check the log!";

            Document doc = outputTextPane.getStyledDocument();
            outputTextPane.setCharacterAttributes(set, true);

            try {
                doc.insertString(doc.getLength(), texto_error, set);
            } catch (BadLocationException ex) {
                //Logger.getLogger(DocumentEditorView.class.getName()).log(Level.SEVERE, null, ex);
            }

            //------- Symbol table pane ----------------------------------------
            String[][] data = parser.getSymbolTable().getDefaultData();
            this.panelList.get(this.tp.getSelectedIndex()).getSimbolTablePanel().setModel((new JTable(data,SymbolTable.getColumNames())).getModel());
            //------- Method table pane ----------------------------------------
            data = parser.getMethodTable().getDefaultData();
            this.panelList.get(this.tp.getSelectedIndex()).getMethodTablePanel().setModel((new JTable(data,MethodTable.getColumNames())).getModel());
            //------- Structure table pane -------------------------------------
            data = parser.getStructTable().getDefaultData();
            this.panelList.get(this.tp.getSelectedIndex()).getStructTablePanel().setModel((new JTable(data,StructTable.getColumNames())).getModel());
            //------------------------------------------------------------------
        }

*/
    }

    /*****************************************
    * getAllText
    * @return void
    * obtain all the text to save
    *****************************************/
    private String getAllText() {
        System.out.println("get all text");
        String text = this.panelList.get(this.tp.getSelectedIndex()).getTextPanel().getText();
        if(text.endsWith("\n")==false){
            text+="\n";
        }
        return text;
    }

    /*****************************************
    * loadText
    * @return true if the text was load suscessfully
    * load all the text
    *****************************************/
    private boolean loadText(File selectedFile) {
        String text = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(selectedFile);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ToolbarFrame.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
          while (scanner.hasNextLine()){
            text+=(scanner.nextLine() + "\n");
          }
        }
        finally{
          scanner.close();
        }
        return true;
    }

    /*****************************************
    * loadText
    * @return true if the text was load suscessfully
    * load all the text
    *****************************************/
    private String loadStringText(File selectedFile) {

        String text = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(selectedFile);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ToolbarFrame.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        try {
          while (scanner.hasNextLine()){
            text+=(scanner.nextLine() + "\n");
          }
        }
        finally{
          scanner.close();
        }
        return text;
    }

    /*****************************************
    * setModified
    * @return modified
    *****************************************/
    public void setModified(boolean modified) {
        if(modified == true){
            String texto = "* "+this.panelList.get(this.tp.getSelectedIndex()).file.getName();
            //String texto = "* "+title_text+" - "+this.panelList.get(this.tp.getSelectedIndex()).file.getName();
            //Main.frame.setTitle(texto);
            this.tp.setTitleAt(this.tp.getSelectedIndex(), texto);
        }
        else{
            String texto = ""+this.panelList.get(this.tp.getSelectedIndex()).file.getName();
            //String texto = ""+title_text+" - "+this.panelList.get(this.tp.getSelectedIndex()).file.getName();
            //Main.frame.setTitle(texto);
            this.tp.setTitleAt(this.tp.getSelectedIndex(), texto);
        }
        //save_button.setEnabled(modified);
        //savemenuItem.setEnabled(modified);
        this.panelList.get(this.tp.getSelectedIndex()).modified = modified;
    }
    
}