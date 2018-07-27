public class StudentCard extends MTACard
{
    private MilitaryTime earliest;
    private MilitaryTime latest;
    
    public StudentCard(Date exp, MilitaryTime early, MilitaryTime late)
    {
        super(exp);
        if(early.compareTo(late)>=0)
        {
            early = new MilitaryTime(00,00);
            late = new MilitaryTime(23,59);
        }
        earliest = early;
        latest = late;
    }
    
    public boolean swipe(Date dateOfSwipe, MilitaryTime timeOfSwipe, double ridePrice)
    {
        if(dateOfSwipe.compareTo(super.getExpirationDate()) < 1
            && timeOfSwipe.compareTo(earliest) >=0 
            && timeOfSwipe.compareTo(latest) <=0)
        {
            return true;
        }
        return false;
    }
    
    public boolean renew(Date dateOfRenewal, double money)
    {
        return false;
    }
    
    public String getCardInfo()
    {
        return "STUDENT CARD"+"\n"+
                "EXPIRATION: " + super.getExpirationDate()+"\n"+
                "START TIME: " + earliest + "\n" +
                "END TIME: " + latest;
    }
}