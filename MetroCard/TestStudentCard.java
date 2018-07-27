import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestStudentCard
{
    @Test
    public void testConstructor()
    {
        Date expA = new Date(3, 24, 2017);
        MilitaryTime startA = new MilitaryTime(5, 0);
        MilitaryTime endA = new MilitaryTime(20, 30);
        MTACard crdA = new StudentCard(expA, startA, endA);
        
        Date expB = new Date(5, 7, 2021);
        MilitaryTime startB = new MilitaryTime(23, 59);
        MilitaryTime endB = new MilitaryTime(0, 0);
        MTACard crdB = new StudentCard(expA, startB, endB);
    }

    @Test
    public void testSwipeTimeFocus()
    {
        Date exp = new Date(3, 24, 2017);
        MilitaryTime start = new MilitaryTime(5, 0);
        MilitaryTime end = new MilitaryTime(20, 30);
        MTACard crd = new StudentCard(exp, start, end);
        
        Date beforeRenewalNeeded = new Date(3, 6, 2016);

        // Before expiration
        // Very early swipe time
        MilitaryTime veryEarly = new MilitaryTime(0, 0);
        assertFalse(crd.swipe(beforeRenewalNeeded, veryEarly, 2.50));
        
        // Before expiration
        // Early swipe time A
        MilitaryTime earlySwipeA = new MilitaryTime(4, 59);
        assertFalse(crd.swipe(beforeRenewalNeeded, earlySwipeA, 2.50));
        
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
        assertFalse(crd.swipe(beforeRenewalNeeded, veryLate, 2.50));
    }
    
    @Test
    public void testSwipeDateFocus()
    {
        Date exp = new Date(3, 24, 2017);
        MilitaryTime start = new MilitaryTime(5, 0);
        MilitaryTime end = new MilitaryTime(20, 30);
        MTACard crd = new StudentCard(exp, start, end);
        
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
    public void testSwipeDateAndPriceFocus()
    {
        Date exp = new Date(3, 6, 2017);
        MilitaryTime start = new MilitaryTime(5, 0);
        MilitaryTime end = new MilitaryTime(20, 30);
        MTACard crd = new StudentCard(exp, start, end);
        
        MilitaryTime middleOfDay = new MilitaryTime(12, 00);
        
        Date swipeDay = new Date(2, 5, 2017);
        
        // Swipes before renewal needed should work
        assertTrue(crd.swipe(swipeDay, middleOfDay, 0.50));
        
        // Multiple swipes before renewal needed should work
        for(int i = 0; i < 40; i++)
        {
            assertTrue(crd.swipe(swipeDay, middleOfDay, ((double) i) + 0.50));
        }
    }
    
    @Test
    public void testGetCardInfo()
    {
        Date exp = new Date(11, 25, 2019);
        MilitaryTime start = new MilitaryTime(11, 13);
        MilitaryTime end = new MilitaryTime(14, 25);
        MTACard crd = new StudentCard(exp, start, end);
        
        String info = "STUDENT CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "START TIME: " + start + "\n" +
               "END TIME: " + end;
        assertEquals(info, crd.getCardInfo());
        
    }
    
    @Test 
    public void testRenew()
    {
        Date exp = new Date(9, 4, 2019);
        MilitaryTime start = new MilitaryTime(11, 13);
        MilitaryTime end = new MilitaryTime(14, 25);
        MTACard crd = new StudentCard(exp, start, end);
        
        double renewalPrice = 100.5;
        
        // Renews before expiration
        Date beforeExpSameMonthYear = new Date(9, 3, 2019);
        assertFalse(crd.renew(beforeExpSameMonthYear, renewalPrice));
        // Can't renew with wrong price
        assertFalse(crd.renew(beforeExpSameMonthYear, renewalPrice + 1));
        String infoA = "STUDENT CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "START TIME: " + start + "\n" +
               "END TIME: " + end;
        assertEquals(infoA, crd.getCardInfo());
        
        
        Date beforeExpDifMonthSameYear = new Date(7, 5, 2019);
        assertFalse(crd.renew(beforeExpDifMonthSameYear, renewalPrice));
        assertFalse(crd.renew(beforeExpDifMonthSameYear, 0.0));
        String infoB = "STUDENT CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "START TIME: " + start + "\n" +
               "END TIME: " + end;
        assertEquals(infoB, crd.getCardInfo());
        

        Date beforeDifMonthYear = new Date(10, 5, 2017);
        assertFalse(crd.renew(beforeDifMonthYear, renewalPrice));
        assertFalse(crd.renew(beforeDifMonthYear, -100.5));
        String infoC = "STUDENT CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "START TIME: " + start + "\n" +
               "END TIME: " + end;
        assertEquals(infoC, crd.getCardInfo());
        
        // Renews on expiration date
        Date onExp = new Date(9, 4, 2019);
        assertFalse(crd.renew(onExp, renewalPrice));
        assertFalse(crd.renew(onExp, -1.00));
        String infoD = "STUDENT CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "START TIME: " + start + "\n" +
               "END TIME: " + end;
        assertEquals(infoD, crd.getCardInfo());
       
        // Swipes after expiration
        String infoE = "STUDENT CARD\n"+
               "EXPIRATION: " + exp + "\n" +
               "START TIME: " + start + "\n" +
               "END TIME: " + end;
            
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
