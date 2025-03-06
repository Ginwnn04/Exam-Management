package GUI.Custom;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import org.apache.poi.hwpf.usermodel.Table;
import java.awt.Component;
import javax.swing.JCheckBox;


public class TableActionCellEditor extends DefaultCellEditor {
    private TableActionEvent event;
    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) {
        PanelAction action = new PanelAction();
        action.initEvent(event, row);
        action.setBackground(table.getSelectionBackground());
        return action; 
    }
}

