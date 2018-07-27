public class DateBasedCard extends MTACard
{
    private double renewPrice;
    private Date lastDayUse;
    private int numDays;
    
    public DateBasedCard(Date exp, double price, Date purchDate, int daysValid)
    {
        super(exp);
        if(price < 0.00)
        {
            price = 0.00;
        }
        renewPrice = price;
        if(daysValid < 0)
        {
            daysValid = 1;
        }
        numDays = daysValid;
        lastDayUse = purchDate.getFutureDate(numDays);
    }
    
    public boolean swipe(Date dateOfSwipe, MilitaryTime timeOfSwipe, double ridePrice)
    {
        if(dateOfSwipe.compareTo(super.getExpirationDate()) < 1
            && dateOfSwipe.compareTo(lastDayUse) < 1)
        {
            return true;
        }
        return false;
    }
    
    public boolean renew(Date dateOfRenewal, double money)
    {
        if(dateOfRenewal.compareTo(super.getExpirationDate()) < 1
            && money == renewPrice)
        {
            lastDayUse = dateOfRenewal.getFutureDate(numDays);
            return true;
        }
        return false;
    }
    
    public String getCardInfo()
    {
        return "DATE BASED CARD" + "\n"+
                "EXPIRATION: " + super.getExpirationDate().toString()+"\n"+
                "VALID THROUGH: " + lastDayUse.toString()+"\n"+
                "LENGTH OF TIME: " + numDays+"\n"+
                "RENEWAL COST: " +renewPrice;
    }
}