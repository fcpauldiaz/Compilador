package GUI;

/***********************************
* Name: TablePane.java
* Date: Aug 23, 2010
* @author martin
* Description:
***********************************/

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class TablePanel extends JPanel {


    private JTable table;
    private JScrollPane scroll;

    /*****************************************
    * TablePanel
    * Constructor
    *****************************************/
    public TablePanel(Object[] columNames){
        InitTable(columNames);

        scroll = new JScrollPane(this.getTable());
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }

    /*****************************************
    * InitTable
    * @return void
    * Initialize the table
    *****************************************/
    private void InitTable(Object[] columNames) {

        Object rowData[][] = new String[1][columNames.length];
        
        this.table = new JTable(rowData, columNames);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    /*****************************************
     * @return the table
     *****************************************/
    public JTable getTable() {
        return table;
    }

    /*****************************************
     * @param table the table to set
     *****************************************/
    public void setTable(JTable table) {
        this.table = table;
    }

    /*****************************************
     * @param table the table to set
     *****************************************/
    public void setTable(Object rowData[][], Object[] columNames) {
        this.table = new JTable(rowData, columNames);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    /*****************************************
     * @param table the table to set
     *****************************************/
    public void setModel(TableModel model) {
        table.setModel(model);
    }
    
}
