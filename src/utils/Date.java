package utils;

public class Date {

    private int dd;
    private int mm;
    private int yyyy;

    public Date() {

    }

    public Date(String dateString) {

        String[] tokens = dateString.split("-");

        this.yyyy = Integer.parseInt(tokens[0]);
        this.mm = Integer.parseInt(tokens[1]);
        this.dd = Integer.parseInt(tokens[2]);
    }

    public Date(Date d) {
        Date date = new Date();

        date.setDay(d.getDay());
        date.setMonth(d.getMonth());
        date.setYear(d.getYear());
    }

    public int getDay() {
        return dd;
    }

    public void setDay(int dd) {
        this.dd = dd;
    }

    public int getMonth() {
        return mm;
    }

    public void setMonth(int mm) {
        this.mm = mm;
    }

    public int getYear() {
        return yyyy;
    }

    public void setYear(int yyyy) {
        this.yyyy = yyyy;
    }

    public boolean isEqual(String ds1, String ds2) {
        return ds1.equals(ds2);
    }

    @Override
    public String toString() {
        String ddString = String.valueOf(this.dd);
        String mmString = String.valueOf(this.mm);
        String yyString = String.valueOf(this.yyyy);

        String sep = "-";

        return ddString + sep + mmString + sep + yyString;
    }

    public static Date parseAsDate(String dateString) {
        return new Date(dateString);
    }

    // checks of ds2 < ds1
    public static boolean isEarlier(String ds1, String ds2) {
        Date d1 = new Date(ds1);
        Date d2 = new Date(ds2);

        if (d2.getYear() < d1.getYear()) return true;
        if (d2.getMonth() < d2.getMonth()) return true;
        if (d2.getDay() <= d2.getMonth()) return true;

        return false;
    }

    public static boolean isEarlier(Date d1, Date d2) {

        int v1 = getValue(d1);
        int v2 = getValue(d2);

        return v2 < v1;

//        if (d2.getYear() < d1.getYear()) {
//            return true;
//        } else if (d2.getMonth() < d1.getMonth()) {
//            return true;
//        } else if (d2.getDay() < d1.getDay()) {
//            return true;
//        }
//
//        return false;
    }

    private static int getValue(Date d1) {

        return d1.getDay() + 10 * d1.getMonth() + 100 * d1.getYear();
    }

    // checks of ds2 > ds1
    private static boolean isLater(String ds1, String ds2) {
        Date d1 = new Date(ds1);
        Date d2 = new Date(ds2);

        if (d2.getYear() > d1.getYear()) return true;
        if (d2.getMonth() > d2.getMonth()) return true;
        if (d2.getDay() >= d2.getMonth()) return true;

        return false;
    }

    public static boolean isLater(Date d1, Date d2) {

        int v1 = getValue(d1);
        int v2 = getValue(d2);

        return v2 > v1;
    }

    // checks if currentDate lies in between startDate and endDate
    public static boolean liesInBetween(String startDate, String endDate, String currentDate) {

        return (isEarlier(endDate, currentDate) && isLater(startDate, currentDate));
    }

    public static boolean liesInBetween(Date startDate, Date endDate, Date currentDate) {

        int currentValue = getValue(currentDate);
        int startValue = getValue(startDate);
        int endValue = getValue(endDate);

        return (currentValue >= startValue && currentValue <= endValue);
    }


    public static Date getBaseDate() {
        return new Date("1900-1-1");
    }

    public static Date getSixMonthsEarlier(Date currentDate) {

        Date newDate = new Date(currentDate);

        int mm = currentDate.getMonth();
        int yy = currentDate.getYear();

        if (mm >= 6) {
            newDate.setMonth(mm - 6);
        } else {
            newDate.setMonth(mm + 6);
            newDate.setYear(yy - 1);
        }

        return newDate;
    }
}
