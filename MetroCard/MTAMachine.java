public class MTAMachine
{
    private Date curDate;
    private MilitaryTime studentStart;
    private MilitaryTime studentEnd;
    private static final double NEW_CARD_FEE = 1.50;
    private static final double WEEKLY_COST = 31.00;
    private static final double MONTHLY_COST = 116.50;
    
    public MTAMachine(Date startDate,
                      MilitaryTime studStartTime,
                      MilitaryTime studEndTime)
    {
        curDate = startDate;
        studentStart = new MilitaryTime(0, 0);
        studentEnd = new MilitaryTime(23, 59);
        if(studStartTime.compareTo(studEndTime) < 0)
        {
            studentStart = studStartTime;
            studentEnd = studEndTime;   
        }
    }
    
    public void nextDay()
    {
        curDate = curDate.getTomorrow();
    }
    
    public MTACard getValueBasedCard(double payment)
    {
        if(payment > NEW_CARD_FEE)
        {
            Date expDate = curDate.getFutureDate(365);
            MTACard vbc = new ValueBasedCard(expDate, payment - NEW_CARD_FEE);
            return vbc;
        }
        else
        {
            return null;
        }
    }
    
    public MTACard getWeeklyUnlimited(double payment)
    {
        if(payment > WEEKLY_COST + NEW_CARD_FEE)
        {
            Date expDate = curDate.getFutureDate(365);
            MTACard dbc = new DateBasedCard(expDate, MONTHLY_COST, curDate, 7);
            return dbc;
        }
        return null;
    }
    
    public MTACard getMonthlyUnlimited(double payment)
    {
        if(payment > MONTHLY_COST + NEW_CARD_FEE)
        {
            Date expDate = curDate.getFutureDate(365);
            MTACard dbc = new DateBasedCard(expDate, MONTHLY_COST, curDate, 30);
            return dbc;
        }
        return null;
    }
    
    public MTACard getStudentCard()
    {
        Date expDate = curDate.getFutureDate(365);
        MTACard sc = new StudentCard(expDate, studentStart, studentEnd);
        return sc;
    }
    
    public static void main(String[] args)
    {
        Date d = new Date(3, 24, 2016);
        MilitaryTime studStart = new MilitaryTime(6, 0);
        MilitaryTime studEnd = new MilitaryTime(19, 15);
        MTAMachine mac = new MTAMachine(d, studStart, studEnd);
        
        //Test me out!
    }
}
