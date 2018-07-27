import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestValueBasedCard
{
    @Test
    public void testConstructor()
    {
        Date dA = new Date(3, 24, 2017);
        MTACard crdA = new ValueBasedCard(dA, 50.0);
        
        Date dB = new Date(12, 8, 1886);
        MTACard crdB = new ValueBasedCard(dB, -1.0);
    }

    @Test
    public void testSwipeTimeFocus()
    {
        Date expirDate = new Date(3, 24, 2017);
        MTACard crd = new ValueBasedCard(expirDate, 50.0);
        
        Date beforeExp = new Date(3, 23, 2017);

        // Before expiration
        // Very early swipe time
        MilitaryTime veryEarly = new MilitaryTime(0, 0);
        assertTrue(crd.swipe(beforeExp, veryEarly, 2.50));
        
        // Before expiration
        // Early swipe time A
        MilitaryTime earlySwipeA = new MilitaryTime(4, 59);
        assertTrue(crd.swipe(beforeExp, earlySwipeA, 2.50));
        
        // Before expiration
        // Early swipe time B
        MilitaryTime earlySwipeB = new MilitaryTime(5, 0);
        assertTrue(crd.swipe(beforeExp, earlySwipeB, 2.50));
        
        // Before expiration
        // Early swipe time C
        MilitaryTime earlySwipeC = new MilitaryTime(5, 1);
        assertTrue(crd.swipe(beforeExp, earlySwipeC, 2.50));
        
        // Before expiration
        // Late swipe time A
        MilitaryTime lateSwipeA = new MilitaryTime(20, 29);
        assertTrue(crd.swipe(beforeExp, lateSwipeA, 2.50));
        
        // Before expiration
        // Late swipe time B
        MilitaryTime lateSwipeB = new MilitaryTime(20, 30);
        assertTrue(crd.swipe(beforeExp, lateSwipeB, 2.50));
        
        // Before expiration
        // Late swipe time C
        MilitaryTime lateSwipeC = new MilitaryTime(20, 31);
        assertTrue(crd.swipe(beforeExp, earlySwipeC, 2.50));
        
        // Before expiration
        // Very late swipe time
        MilitaryTime veryLate = new MilitaryTime(23, 59);
        assertTrue(crd.swipe(beforeExp, veryLate, 2.50));
    }
    
    @Test
    public void testSwipeDateFocus()
    {
        Date expirDate = new Date(3, 24, 2017);
        MTACard crd = new ValueBasedCard(expirDate, 50.0);
        
        MilitaryTime middleOfDay = new MilitaryTime(12, 00);
        
        // Swipes before expiration
        Date beforeExpSameMonthYear = new Date(3, 23, 2017);
        assertTrue(crd.swipe(beforeExpSameMonthYear, middleOfDay, 2.50));
        
        Date beforeExpDifMonthSameYear = new Date(2, 28, 2017);
        assertTrue(crd.swipe(beforeExpDifMonthSameYear, middleOfDay, 2.50));
        
        Date beforeDifMonthYear = new Date(12, 25, 2016);
        assertTrue(crd.swipe(beforeDifMonthYear, middleOfDay, 2.50));
        
        // Swipes on expiration
        Date onExp = new Date(3, 24, 2017);
        assertTrue(crd.swipe(onExp, middleOfDay, 2.50));
        
        
        // Swipes after expiration
        Date afterExpSameMonthyear = new Date(3, 25, 2017);
        assertFalse(crd.swipe(afterExpSameMonthyear, middleOfDay, 2.50));
        
        Date afterExpDifMonthSameYear = new Date(4, 22, 2017);
        assertFalse(crd.swipe(afterExpDifMonthSameYear, middleOfDay, 2.50));
        
        Date afterDifMonthYear = new Date(1, 23, 2018);
        assertFalse(crd.swipe(afterExpDifMonthSameYear, middleOfDay, 2.50));
    }
    
    @Test
    public void testSwipePriceFocus()
    {
        Date expirDate = new Date(3, 24, 2017);
        MTACard crdA = new ValueBasedCard(expirDate, 24.50);
        
        MilitaryTime middleOfDay = new MilitaryTime(12, 00);
        Date swipeDay = new Date(3, 23, 2017);
        
        // Swipes before expiration with a full balance should work
        assertTrue(crdA.swipe(swipeDay, middleOfDay, 0.50));
        
        // Multiple swipes before expiration with remaining balance
        // should work
        for(int i = 1; i < 7; i++)
        {
            assertTrue(crdA.swipe(swipeDay, middleOfDay, ((double) i) + 0.50));
        }
        
        // Swipe after depleted balance should work
        assertFalse(crdA.swipe(swipeDay, middleOfDay, 0.01));
        
        MTACard crdB = new ValueBasedCard(expirDate, 24.50);
        assertFalse(crdB.swipe(swipeDay, middleOfDay, 24.51));
    }
    
    @Test
    public void testGetCardInfo()
    {
        Date expirDate = new Date(12, 23, 1992);
        MTACard crd = new ValueBasedCard(expirDate, 1.99);
        
        String info = "VALUE BASED CARD\n"+
            "EXPIRATION: " + expirDate + "\n" +
            "BALANCE: " + 1.99;
        assertEquals(info, crd.getCardInfo());
        
    }
    
    @Test 
    public void testRenew()
    {
        Date expirDate = new Date(9, 4, 2019);
        MTACard crd = new ValueBasedCard(expirDate, 24.50);
        
        // Renews before expiration
        Date beforeExpSameMonthYear = new Date(9, 3, 2019);
        assertTrue(crd.renew(beforeExpSameMonthYear, 2.50));
        assertFalse(crd.renew(beforeExpSameMonthYear, -1.00));
        String infoA = "VALUE BASED CARD\n"+
            "EXPIRATION: " + expirDate + "\n" +
            "BALANCE: " + 27.0;
        assertEquals(infoA, crd.getCardInfo());
        
        
        Date beforeExpDifMonthSameYear = new Date(7, 5, 2019);
        assertTrue(crd.renew(beforeExpDifMonthSameYear, 1.00));
        assertFalse(crd.renew(beforeExpDifMonthSameYear, 0.0));
        String infoB = "VALUE BASED CARD\n"+
            "EXPIRATION: " + expirDate + "\n" +
            "BALANCE: " + 28.0;
        assertEquals(infoB, crd.getCardInfo());
        

        Date beforeDifMonthYear = new Date(10, 5, 2017);
        assertTrue(crd.renew(beforeDifMonthYear, 5.75));
        assertFalse(crd.renew(beforeDifMonthYear, -100.5));
        String infoC = "VALUE BASED CARD\n"+
            "EXPIRATION: " + expirDate + "\n" +
            "BALANCE: " + 33.75;
        assertEquals(infoC, crd.getCardInfo());
        
        // Renews on expiration
        Date onExp = new Date(9, 4, 2019);
        assertTrue(crd.renew(onExp, 2.50));
        assertFalse(crd.renew(onExp, -1.00));
        String infoD = "VALUE BASED CARD\n"+
            "EXPIRATION: " + expirDate + "\n" +
            "BALANCE: " + 36.25;
        assertEquals(infoD, crd.getCardInfo());
       
        // Renews after expiration
        String infoE = "VALUE BASED CARD\n"+
            "EXPIRATION: " + expirDate + "\n" +
            "BALANCE: " + 36.25;
            
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
