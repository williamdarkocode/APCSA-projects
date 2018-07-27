public class ValueBasedCard extends MTACard
{
    private double balance;
    
    public ValueBasedCard(Date expDate, double initBal)
    {
        super(expDate);
        if(initBal < 0.0)
        {
            initBal = 0.00;
            balance = initBal;
        }
        balance = initBal;
    }
    
    public boolean swipe(Date dateOfSwipe, MilitaryTime timeOfSwipe, double ridePrice)
    {
        if(dateOfSwipe.compareTo(super.getExpirationDate()) < 1
            && ridePrice <= balance)
        {
            balance-=ridePrice;
            return true;
        }
        return false;
    }
    
    public boolean renew(Date dateOfRenewal, double money)
    {
        if(dateOfRenewal.compareTo(super.getExpirationDate()) < 1
            && money > 0.00)
        {
            balance+=money;
            return true;
        }
        return false;
    }
    
    public String getCardInfo()
    {
        return "VALUE BASED CARD" + "\n"+
                "EXPIRATION: " + super.getExpirationDate().toString() + "\n" +
                "BALANCE: " + balance;
    }
    
}