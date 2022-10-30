public class DateTime {
    private int minute;
    private int hour;
    private int day;
    private int date;
    private int month;
    private int year;

    public DateTime(int minute, int hour, int day, int date, int month, int year) {
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
    }


    public int getMinute() {
        return this.minute;
    }

    public int getHour() {
        return this.hour;
    }

    public int getDate() {
        return this.date;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }
}