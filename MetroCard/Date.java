public class Date
{
    private int month;
    private int day;
    private int year;
    
    public Date(int m, int d, int y)
    {
        year = 1;
        if(y > 1)
        {
            year = y;
        }
        month = 1;
        if(m >= 1 && m <= 12)
        {
            month = m;
        }
        if(month == 1 || month == 3 || month == 5 || 
           month == 7 || month == 8 || month == 10 ||
           month == 12)
        {
            day = 1;
            if(d >= 1 || d <= 31)
            {
                day = d;
            }
        }
        else if(month == 4 || month == 6 || month == 9 || month == 11)
        {
            day = 1;
            if(d >= 1 || d <= 30)
            {
                day = d;
            }
        }
        else if(isLeapYear(year))
        {
            day = 1;
            if(d >= 1 || d<= 29)
            {
                day = d;
            }
        }
        else
        {
            day = 1;
            if(d >= 1 || d<= 28)
            {
                day = d;
            }
            
        }
    }
    
    private static boolean isLeapYear(int year)
    {
        if(year % 4 != 0)
        {
            return false;
        }
        else if(year % 100 == 0 && year % 400 != 0 )
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public Date getTomorrow()
    {
        int tomorrowsDay = day;
        int tomorrowsMonth = month;
        int tomorrowsYear = year;
        if(day == 31 && month == 12)
        {
           tomorrowsDay = 1;
           tomorrowsMonth = 1;
           tomorrowsYear = year + 1;
        }
        else if(day == 31 &&
                (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12))
        {
            tomorrowsMonth = ((month + 1) % 12);
            tomorrowsDay = 1;
        }
        else if(month == 2 && ((isLeapYear(year) && day == 29) || (!isLeapYear(year) && day == 28)))
        {
            tomorrowsMonth = 3;
            tomorrowsDay = 1;
        }
        else if(day == 30 &&
                ((month == 4) || (month == 6) || (month == 9) || (month == 11)))
        {
            tomorrowsMonth = month + 1;
            tomorrowsDay = 1;
        }
        else
        {
            tomorrowsDay = day + 1;
        }
        return new Date(tomorrowsMonth, tomorrowsDay, tomorrowsYear);
    }
    
    public Date getFutureDate(int daysInFuture)
    {
        if(daysInFuture < 0)
        {
            return new Date(month, day, year);
        }
        else
        {
            Date cur = this;
            for(int i = 0; i < daysInFuture; i++)
            {
                cur = cur.getTomorrow();
            }
            return cur;
        }
    }
    
    
    public int compareTo(Date date)
    {
        if(this.year > date.year)
        {
            return 1;
        }
        else if(this.year < date.year)
        {
            return -1;
        }
        else if(this.month > date.month)
        {
            return 1;
        }
        else if(this.month < date.month)
        {
            return -1;
        }
        else if(this.day > date.day)
        {
            return 1;
        }
        else if(this.day < date.day)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    public int getMonth()
    {
        return month;
    }
    
    public int getDay()
    {
        return day;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public String toString()
    {
        return month + "/" + day + "/" + year;
    }
    
    public static void main(String[] args)
    {
        Date dA = new Date(2, 29, 2016);
        Date dB = new Date(2, 28, 2016);
        Date dC = new Date(3, 1, 2014);
        
        System.out.println(dA);
        System.out.println(dB);
        
        System.out.println(dA.compareTo(dB));
        System.out.println(dB.compareTo(dA));
        System.out.println(dA.compareTo(dC));
        System.out.println(dC.compareTo(dA));
        
        Date dD = new Date(13, 8, -1);
        System.out.println(dD);
        
        Date dACopy = new Date(2, 29, 2016);
        System.out.println(dACopy.compareTo(dA));
        System.out.println(dA.compareTo(dACopy));
        
        System.out.println(dA.getTomorrow());
        System.out.println(dB.getTomorrow());
        System.out.println(dC.getTomorrow());
        
        System.out.println(dA.getFutureDate(20));
        System.out.println(dB.getFutureDate(25));
        System.out.println(dC.getFutureDate(365));
        
        // Test me out more!
    }
}