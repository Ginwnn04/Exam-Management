package GUI.Custom;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.JTable;
import java.awt.Component;

public class TableActionCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
    boolean isSelected, boolean hasFocus, int row, int column) {
        Component com =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelAction action = new PanelAction();
        if(isSelected==false && row % 2 != 0){
            action.setBackground(new java.awt.Color(243,215,208));
        }
        else if(isSelected==true){
            action.setBackground(new java.awt.Color(225,99,73));
        }
        else {
            action.setBackground(new java.awt.Color(255,255,255));
        }
        // action.setBackground(new java.awt.Color(255, 255, 255));
        return action; 
    }
}
