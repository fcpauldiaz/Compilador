/***********************************
* Name: TreePanel.java
* Date: Aug 17, 2010
* @author martin
* Description:
***********************************/

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreePanel extends JPanel{

    private JTree tree;

    /*****************************************
     * constructor
     *****************************************/
    public TreePanel(){

        this.resetTree();
        JScrollPane scroll = new JScrollPane(this.getTree());

        //this.tree.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }

    /*****************************************
     * @return the tree
     *****************************************/
    public JTree getTree() {
        return tree;
    }

    /*****************************************
     * @param tree the tree to set
     *****************************************/
    public void setTree(JTree tree) {
        this.tree = tree;
    }
    
    /*****************************************
     * @param tree the tree to set
     *****************************************/
    public void resetTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Program");
        tree = new JTree(root);
    }


    
}
