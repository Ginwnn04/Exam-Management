package GUI.Comp.DateChooser;

import java.sql.Date;

public class SelectedDate {

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SelectedDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date convert() {
        String value = String.format("%d-%d-%d", year, month, day);
        Date date = Date.valueOf(value);

        return date;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            SelectedDate date = (SelectedDate) obj;
            return day == date.day && month == date.month && year == date.year;
        }
        catch (Exception ignore) {
            return false;
        } 
    }

    public SelectedDate() {

    }

    @Override
    protected SelectedDate clone() {
        return new SelectedDate(day, month, year);
    }

    private int day;
    private int month;
    private int year;
}
