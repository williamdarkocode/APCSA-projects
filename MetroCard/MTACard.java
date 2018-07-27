public abstract class MTACard
{

    private Date expirationDate;
    
    public MTACard(Date expDate)
    {
        expirationDate = expDate;
    }
    
    public Date getExpirationDate()
    {
        return expirationDate;
    }

    abstract boolean swipe(Date dateOfSwipe, MilitaryTime timeOfSwipe, double ridePrice);
    
    abstract boolean renew(Date dateOfRenewal, double money);
    
    abstract String getCardInfo();
}
