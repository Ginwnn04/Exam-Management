package GUI.Custom;

import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.JTable;
import java.awt.Component;
import style.ColorConfig;

public class TableActionCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
    boolean isSelected, boolean hasFocus, int row, int column) {
        Component com =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelAction action = new PanelAction();
        if(isSelected == false){
            action.setBackground(ColorConfig.WHITE_COLOR_BG);
        }
        else if(isSelected == true){
            action.setBackground(ColorConfig.BLUE);
        }
        // action.setBackground(new java.awt.Color(255, 255, 255));
        return action; 
    }
}
