package GUI.Custom;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.JCheckBox;


public class TableActionCellEditor extends DefaultCellEditor {
    private TableActionEvent event;
    private boolean isRenderView = true;
    private boolean isRenderUpdate = true;
    private boolean isRenderDelete = true;

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    public TableActionCellEditor(TableActionEvent event, boolean isRenderView, boolean isRenderUpdate, boolean isRenderDelete) {
        super(new JCheckBox());
        this.event = event;

        this.isRenderView = isRenderView;
        this.isRenderUpdate = isRenderUpdate;
        this.isRenderDelete = isRenderDelete;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) {
        PanelAction action = new PanelAction(isRenderView, isRenderUpdate, isRenderDelete);
        action.initEvent(event, row);
        action.setBackground(table.getSelectionBackground());
        return action; 
    }
}

