package GUI.Custom;

public interface TableActionEvent {
    public void onUpdate(int row);
    public void onDelete(int row);
    public void onView(int row); 

}