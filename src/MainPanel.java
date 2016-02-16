

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MainPanel extends JPanel{


    public   boolean   modified = false;
    public   boolean   hasSaved = false;
    public   File file = new File("untitled.txt");

    private JSplitPane	      splitPaneV;
    private JSplitPane	      splitPaneH;
    private JTabbedPane       tp1;
    public  JTabbedPane       tp2;
   // private TreePanel         treePanel = new TreePanel();
    private OutputPanel       outputPanel = new OutputPanel();
    private GrammarPanel      grammarPanel = new GrammarPanel();
    private TextPanel         textPanel = new TextPanel();
    private IntermediatePanel intermediatePanel = new IntermediatePanel();
    private TablePanel        SimbolTablePanel;
    private TablePanel        StructTablePanel;
    private TablePanel        MethodTablePanel;
    
    /*****************************************
    * MainPanel
    * Constructor
    *****************************************/
    public MainPanel(){

        this.SimbolTablePanel = new TablePanel(SymbolTable.getColumNames());
        this.StructTablePanel = new TablePanel(StructTable.getColumNames());
        this.MethodTablePanel = new TablePanel(MethodTable.getColumNames());

        this.setLayout(new BorderLayout());
        this.InitSplitPane();
        
        this.setAutoscrolls(true);
        this.setPreferredSize(new Dimension(900,600));
        this.setBackground(Color.white);
    }

    /*****************************************
     * InitSplitPane
     * @return void
     * Inicialize the components of the split panes
     *****************************************/
    private void InitSplitPane(){

        // Create a splitter pane
        this.splitPaneH = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
        this.splitPaneV = new JSplitPane( JSplitPane.VERTICAL_SPLIT );

        splitPaneV.setOneTouchExpandable(true);
        splitPaneV.setDividerLocation(150);
        splitPaneH.setOneTouchExpandable(true);
        splitPaneH.setDividerLocation(150);

        this.splitPaneV.setDividerLocation(360);
        this.splitPaneH.setDividerLocation(900-270);

        this.tp1 = new JTabbedPane();
        this.tp2 = new JTabbedPane();

       // this.tp1.addTab("Tree",Main.iconTree, this.getTreePanel(),"");
       // this.tp1.addTab("Intermediate code",Main.iconConstruction, intermediatePanel,"");
        this.tp2.addTab("Output", this.getOutputPanel());
        this.tp2.addTab("Grammar", this.getGrammarPanel());
        this.tp2.addTab("Tabla Símboos", this.getSimbolTablePanel());
        //this.tp2.addTab("Method Table", this.getMethodTablePanel());
        //this.tp2.addTab("Struct Table", this.getStructTablePanel());

        this.add(splitPaneV, BorderLayout.CENTER);

        this.splitPaneH.setLeftComponent(this.getTextPanel());
        this.splitPaneH.setRightComponent(this.tp1);
        
        this.splitPaneV.setLeftComponent(this.splitPaneH);
        this.splitPaneV.setRightComponent(this.tp2);
    }

    /*****************************************
     * @return the treePanel
     *****************************************/
   /* public TreePanel getTreePanel() {
        return treePanel;
    }*/

    /*****************************************
     * @return the outputPanel
     *****************************************/
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }

    /*****************************************
     * @return the grammarPanel
     *****************************************/
    public GrammarPanel getGrammarPanel() {
        return grammarPanel;
    }

    /*****************************************
     * @return the textPanel
     *****************************************/
    public TextPanel getTextPanel() {
        return textPanel;
    }

    /*****************************************
     * @param textPanel the textPanel to set
     *****************************************/
    public void setTextPanel(TextPanel textPanel) {
        this.textPanel = textPanel;
    }

    /*****************************************
     * @return the SimbolTablePanel
     *****************************************/
    public TablePanel getSimbolTablePanel() {
        return SimbolTablePanel;
    }

    /*****************************************
     * @return the StructTablePanel
     *****************************************/
    public TablePanel getStructTablePanel() {
        return StructTablePanel;
    }

    /*****************************************
     * @return the MethodTablePanel
     *****************************************/
    public TablePanel getMethodTablePanel() {
        return MethodTablePanel;
    }

}