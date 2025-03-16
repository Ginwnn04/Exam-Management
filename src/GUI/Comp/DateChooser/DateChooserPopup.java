package GUI.Comp.DateChooser;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Date;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import style.MyFont;

public class DateChooserPopup extends JDateChooser {
    private DateChooser dateChooser;
    private ArrayList<Consumer<SelectedDate>> onDateChangedCallbacks = new ArrayList<>();
    private boolean isEmpty = false;
    private SelectedDate currentSelected = null;

    public DateChooserPopup() {
        super();
        initComponents();
    }

    public void addOnDateChangedCallback(Consumer<SelectedDate> callback) {
        onDateChangedCallbacks.add(callback);
    }

    private void initComponents() {
        replaceDateChooser();
        configDateEditor();
    }

    private void replaceDateChooser() {
        popup.removeAll();

        dateChooser = new DateChooser();
        dateChooser.setMaximumSize(new Dimension(260, 215));
        dateChooser.setAlignmentX(CENTER_ALIGNMENT);
        dateChooser.addEventDateChooser(this::dateSelected);

        popup.add(dateChooser);
    }

    private void configDateEditor() {
        JTextField dateTF = (JTextField) dateEditor.getUiComponent();
        dateTF.setFont(MyFont.fontText);
        dateTF.setEnabled(false);
        dateTF.setEditable(false);
        dateTF.setForeground(Color.BLACK);
        dateTF.setDisabledTextColor(Color.BLACK);
        dateTF.setBackground(Color.WHITE);
    }

    @Override
    public Date getDate() {
        if (isEmpty) return null;

        var selectedDate = dateChooser.getSelectedDate();
        Date date = Date.valueOf(String.format("%d-%d-%d", 
                                 selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDay()));
        
        return date;
    }

    private void dateSelected(SelectedAction action, SelectedDate selectedDate) {
        onDateChanged(selectedDate);
    }

    private void onDateChanged(SelectedDate selectedDate) {
        if (currentSelected != null && currentSelected.equals(selectedDate)) {
            onClear();
            return;
        }
        
        isEmpty = false;

        var date = getDate();
        dateEditor.setDate(date);
        currentSelected = selectedDate.clone();

        invokeOnDateChanged();
    }

    private void onClear() {
        JTextField dateTF = (JTextField) dateEditor.getUiComponent();
        dateTF.setText("");
        isEmpty = true;
        currentSelected = null;

        popup.setVisible(false);
        invokeOnDateChanged();
    }

    private void invokeOnDateChanged() {
        for (var callback : onDateChangedCallbacks) {
            callback.accept(dateChooser.getSelectedDate());
        }
    }
}
