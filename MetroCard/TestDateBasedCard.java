import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDateBasedCard
{
    @Test
    public void testConstructor()
    {
        Date expA = new Date(3, 24, 2017);
        double renewalPriceA = 13.0;
        Date purchasedA = new Date(2, 5, 2016);
        int numberOfDaysA = 30;
        MTACard crdA = new DateBasedCard(expA, renewalPriceA, purchasedA, numberOfDaysA);
        
        Date expB = new Date(5, 7, 2021);
        double renewalPriceB = 85.0;
        Date purchasedB = new Date(7, 4, 2018);
        int numberOfDaysB = 367;
        MTACard crdB = new DateBasedCard(expB, renewalPriceB, purchasedB, numberOfDaysB);
        
        MTACard crdC = new DateBasedCard(new Date(3, 3, 3), -55.0, new Date(1, 1, 1), -2);
    }

    @Test
    public void testSwipeTimeFocus()
    {
        Date exp = new Date(3, 24, 2017);
        double renewalPrice = 13.0;
        Date purchased = new Date(2, 5, 2016);
        int numberOfDays = 30;
        MTACard crd = new DateBasedCard(exp, renewalPrice, purchased, numberOfDays);
        
        Date beforeRenewalNeeded = new Date(3, 6, 2016);

        // Before expiration
        // Very early swipe time
        MilitaryTime veryEarly = new MilitaryTime(0, 0);
        assertTrue(crd.swipe(beforeRenewalNeeded, veryEarly, 2.50));
        
        // Before expiration
        // Early swipe time A
        MilitaryTime earlySwipeA = new MilitaryTime(4, 59);
        assertTrue(crd.swipe(beforeRenewalNeeded, earlySwipeA, 2.50));
        
        // Before expiration
        // Early swipe time B
        MilitaryTime earlySwipeB = new MilitaryTime(5, 0);
        assertTrue(crd.swipe(beforeRenewalNeeded, earlySwipeB, 2.50));
        
        // Before expiration
        // Early swipe time C
        MilitaryTime earlySwipeC = new MilitaryTime(5, 1);
        assertTrue(crd.swipe(beforeRenewalNeeded, earlySwipeC, 2.50));
        
        // Before expiration
        // Late swipe time A
        MilitaryTime lateSwipeA = new MilitaryTime(20, 29);
        assertTrue(crd.swipe(beforeRenewalNeeded, lateSwipeA, 2.50));
        
        // Before expiration
        // Late swipe time B
        MilitaryTime lateSwipeB = new MilitaryTime(20, 30);
        assertTrue(crd.swipe(beforeRenewalNeeded, lateSwipeB, 2.50));
        
        // Before expiration
        // Late swipe time C
        MilitaryTime lateSwipeC = new MilitaryTime(20, 31);
        assertTrue(crd.swipe(beforeRenewalNeeded, earlySwipeC, 2.50));
        
        // Before expiration
        // Very late swipe time
        MilitaryTime veryLate = new MilitaryTime(23, 59);
        assertTrue(crd.swipe(beforeRenewalNeeded, veryLate, 2.50));
    }
    
    @Test
    public void testSwipeDateFocus()
    {
        Date exp = new Date(3, 24, 2017);
        double renewalPrice = 13.0;
        Date purchased = new Date(2, 5, 2016);
        int numberOfDays = 30;
        MTACard crd = new DateBasedCard(exp, renewalPrice, purchased, numberOfDays);
        
        MilitaryTime middleOfDay = new MilitaryTime(12, 00);
        
        // Swipes before renewal needed date
        Date beforeRenewNeededA = new Date(3, 5, 2016);
        assertTrue(crd.swipe(beforeRenewNeededA, middleOfDay, 2.50));
        
        Date beforeRenewNeededB = new Date(2, 8, 2016);
        assertTrue(crd.swipe(beforeRenewNeededB, middleOfDay, 2.50));
        
        Date beforeRenewNeededC = new Date(12, 25, 2015);
        assertTrue(crd.swipe(beforeRenewNeededC, middleOfDay, 2.50));
        
        
        // Swipes on renewal needed date
        Date onRenewal = new Date(3, 6, 2016);
        assertTrue(crd.swipe(onRenewal, middleOfDay, 2.50));

        
        // Swipes after renewal needed
        Date afterRenewNeededA = new Date(3, 7, 2016);
        assertFalse(crd.swipe(afterRenewNeededA, middleOfDay, 2.50));
        
        Date afterRenewNeededB = new Date(4, 3, 2016);
        assertFalse(crd.swipe(afterRenewNeededB, middleOfDay, 2.50));
        
        Date afterRenewNeededC = new Date(1, 1, 2017);
        assertFalse(crd.swipe(afterRenewNeededC, middleOfDay, 2.50));
    }
    
    @Test
    public void testSwipePriceFocus()
    {
        Date exp = new Date(3, 24, 2017);
        double renewalPrice = 13.0;
        Date purchased = new Date(2, 5, 2016);
        int numberOfDays = 30;
        MTACard crdA = new DateBasedCard(exp, renewalPrice, purchased, numberOfDays);
        
        MilitaryTime middleOfDay = new MilitaryTime(12, 00);
        Date swipeDay = new Date(3, 1, 2016);
        
        // Swipes before expiration with a full balance should work
        assertTrue(crdA.swipe(swipeDay, middleOfDay, 0.50));
        
        // Multiple swipes before expiration with remaining balance
        // should work
        for(int i = 1; i < 40; i++)
        {
            assertTrue(crdA.swipe(swipeDay, middleOfDay, ((double) i) + 0.50));
        }
    }
    
    @Test
    public void testGetCardInfo()
    {
        Date exp = new Date(3, 24, 2017);
        double renewalPrice = 234.0;
        Date purchased = new Date(2, 5, 2016);
        int numberOfDays = 365;
        MTACard crd = new DateBasedCard(exp, renewalPrice, purchased, numberOfDays);
        
        String info = "DATE BASED CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "VALID THROUGH: " + purchased.getFutureDate(numberOfDays) + "\n" +
               "LENGTH OF TIME: " + numberOfDays + "\n" + 
               "RENEWAL COST: " + renewalPrice;
        assertEquals(info, crd.getCardInfo());
        
    }
    
    @Test 
    public void testRenew()
    {
        Date exp = new Date(9, 4, 2019);
        double renewalPrice = 35.0;
        Date purchased = new Date(5, 26, 2016);
        int numberOfDays = 90;
        MTACard crd = new DateBasedCard(exp, renewalPrice, purchased, numberOfDays);
        
        // Renews before expiration
        Date beforeExpSameMonthYear = new Date(9, 3, 2019);
        assertTrue(crd.renew(beforeExpSameMonthYear, renewalPrice));
        // Can't renew with wrong price
        assertFalse(crd.renew(beforeExpSameMonthYear, renewalPrice + 1));
        String infoA = "DATE BASED CARD\n" +
               "EXPIRATION: " + exp + "\n" +
               "VALID THROUGH: " + beforeExpSameMonthYear.getFutureDate(numberOfDays) + "\n" +
               "LENGTH OF TIME: " + numberOfDays + "\n" + 
               "RENEWAL COST: " + renewalPrice;
        assertEquals(infoA, crd.getCardInfo());
        
        
        Date beforeExpDifMonthSameYear = new Date(7, 5, 2019);
        assertTrue(crd.renew(beforeExpDifMonthSameYear, renewalPrice));
        assertFalse(crd.renew(beforeExpDifMonthSameYear, 0.0));
        String infoB = "DATE BASED CARD\n" +
               "EXPIRATION: " + exp + "\n" +
               "VALID THROUGH: " + beforeExpDifMonthSameYear.getFutureDate(numberOfDays) + "\n" +
               "LENGTH OF TIME: " + numberOfDays + "\n" + 
               "RENEWAL COST: " + renewalPrice;
        assertEquals(infoB, crd.getCardInfo());
        

        Date beforeDifMonthYear = new Date(10, 5, 2017);
        assertTrue(crd.renew(beforeDifMonthYear, renewalPrice));
        assertFalse(crd.renew(beforeDifMonthYear, -100.5));
        String infoC = "DATE BASED CARD\n" +
               "EXPIRATION: " + exp + "\n" +
               "VALID THROUGH: " + beforeDifMonthYear.getFutureDate(numberOfDays) + "\n" +
               "LENGTH OF TIME: " + numberOfDays + "\n" + 
               "RENEWAL COST: " + renewalPrice;
        assertEquals(infoC, crd.getCardInfo());
        
        // Renews on expiration date
        Date onExp = new Date(9, 4, 2019);
        assertTrue(crd.renew(onExp, renewalPrice));
        assertFalse(crd.renew(onExp, -1.00));
        String infoD = "DATE BASED CARD\n" +
               "EXPIRATION: " + exp + "\n" +
               "VALID THROUGH: " + onExp.getFutureDate(numberOfDays) + "\n" +
               "LENGTH OF TIME: " + numberOfDays + "\n" + 
               "RENEWAL COST: " + renewalPrice;
        assertEquals(infoD, crd.getCardInfo());
       
        // Swipes after expiration
        String infoE = "DATE BASED CARD\n" +
               "EXPIRATION: " + exp + "\n" +
               "VALID THROUGH: " + onExp.getFutureDate(numberOfDays) + "\n" +
               "LENGTH OF TIME: " + numberOfDays + "\n" + 
               "RENEWAL COST: " + renewalPrice;
            
        Date afterExpSameMonthyear = new Date(9, 5, 2019);
        assertFalse(crd.renew(afterExpSameMonthyear, 2.50));
        assertFalse(crd.renew(afterExpSameMonthyear, -1.00));
        assertEquals(infoE, crd.getCardInfo());
        
        Date afterExpDifMonthSameYear = new Date(10, 3, 2019);
        assertFalse(crd.renew(afterExpDifMonthSameYear, 2.50));
        assertFalse(crd.renew(afterExpDifMonthSameYear, -1.00));
        assertEquals(infoE, crd.getCardInfo());
        
        Date afterDifMonthYear = new Date(8, 2, 2020);
        assertFalse(crd.renew(afterDifMonthYear, 2.50));
        assertFalse(crd.renew(afterDifMonthYear, -1.00));
        assertEquals(infoE, crd.getCardInfo());
    }
}
