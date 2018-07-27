public class SQFReport
{
    private int xcoordOfSus;
    private int ycoordOfSus;
    private String cityOfSus;
    private String raceOfSus;
    private int perStopOfSus;
    private boolean arstmadeOfSus;
    private boolean sumissueOfSus;
    private boolean pf_hands;
    private boolean pf_wall;
    private boolean pf_grnd;
    private boolean pf_drwep;
    private boolean pf_ptwep;
    private boolean pf_baton;
    private boolean pf_hcuff;
    private boolean pf_pepsp;
    private boolean pf_other;
    private boolean refusedComply;
    private boolean contrabn;
    private boolean suspVCrime;
    private boolean otherSuspWeapons;
    private boolean knife;
    private boolean pistol;
    private boolean asltWeapon;
    private boolean othrweap;
    private boolean machineGun;
    private boolean rifle;
    private boolean rf_attrInapropriate;
    private boolean cs_cloth;
    private int ht_feet;
    private int ht_inches;
    private String build;
    private String sex;
    private boolean otherfeats;
    
    
    // Delete me and add more instance variables here
    
    /**
     * Constructor for a Stop, Question, & Frisk report.
     * 
     * @param row An array of Strings that represents a SINGLE ROW of a 
     * Stop, Question, & Frisk database. Each String in the array is a SIGNLE 
     * VALUE of a single column of the database. See the Stop, Question, &
     * Frisk data specification to determine which index in the String 
     * corresponds to which column in the data.
     */
    public SQFReport(String[] row)
    {
        xcoordOfSus = Integer.parseInt(row[107]);
        ycoordOfSus = Integer.parseInt(row[108]);
        cityOfSus =  row[100];
        raceOfSus = row[81];
        arstmadeOfSus = row[14].equals("Y");
        sumissueOfSus = row[16].equals("Y");
        if(row[10].equals("**"))
        {
            row[10] = "0";
        }
        perStopOfSus = Integer.parseInt(row[10]);
        pf_hands = row[32].equals("Y");
        pf_wall = row[33].equals("Y");
        pf_grnd = row[34].equals("Y");
        pf_drwep = row[35].equals("Y");
        pf_ptwep = row[36].equals("Y");
        pf_baton = row[37].equals("Y");
        pf_hcuff = row[38].equals("Y");
        pf_pepsp = row[39].equals("Y");
        pf_other = row[40].equals("Y");
        refusedComply = row[58].equals("Y");
        contrabn = row[24].equals("Y");
        suspVCrime = row[44].equals("Y");
        otherSuspWeapons = row[45].equals("Y");
        knife = row[29].equals("Y");
        pistol = row[26].equals("Y");
        asltWeapon = row[28].equals("Y");
        othrweap = row[31].equals("Y");
        machineGun = row[30].equals("Y");
        rifle = row[27].equals("Y");
        rf_attrInapropriate = row[47].equals("Y");
        cs_cloth = row[53].equals("Y");
        ht_feet = Integer.parseInt(row[84]);
        ht_inches = Integer.parseInt(row[85]);
        build = row[89];
        sex = row[80];
        otherfeats = row[90].equals("Y");
    }
    
    /**
     * Accessor for x-coordinate of a Stop, Question, & Frisk report.
     * 
     * @return the x-coordinate of the Stop, Question, & Frisk report.
     */
    public int getXCoord()
    {
        return xcoordOfSus;
    }
    
    /**
     * Accessor for y-coordinate of a Stop, Question, & Frisk report.
     * 
     * @return the y-coordinate of the Stop and Frisk data point.
     */
    public int getYCoord()
    {
        return ycoordOfSus;
    }
    
    public String getRace()
    {
        return raceOfSus;
    }
    
    public boolean isRace(String filterRace)
    {
        return raceOfSus.indexOf(filterRace) >= 0;
    }
    
    public boolean isBorough(String cityFilter)
    {
        return cityOfSus.equals(cityFilter);
    }
   
    public String getBorough()
    {
        return cityOfSus;
    }
    
    public boolean susCharged()
    {
        return arstmadeOfSus || sumissueOfSus;
    }
    
    public int getPerStop()
    {
        return perStopOfSus;
    }
    
    public boolean usedPhysForce()
    {
        return pf_hands || pf_wall || pf_grnd || pf_drwep || pf_ptwep || pf_baton || pf_hcuff || pf_pepsp || pf_other;
    }
    
    public boolean suspRefComply()
    {
        return refusedComply;
    }
    public boolean suspRefComplyAndPhysForceUsed()
    {
        return refusedComply && usedPhysForce();
    }
    
    public boolean stopOnReasonWeapAndForce()
    {
        return otherSuspWeapons && usedPhysForce();
    }
    
    public boolean suspHadContrabn()
    {
        return contrabn;
    }
    
    public boolean carriedWeapon()
    {
        return knife || pistol || machineGun || asltWeapon || rifle || othrweap;
    }
    
    public boolean suspOfViolentCrimForce()
    {
        return suspVCrime && usedPhysForce();
    }
    
    
    public boolean forceAndSusHadWeap()
    {
        return usedPhysForce() && carriedWeapon();
    }
    
    public boolean physForceAndContrabn()
    {
        return usedPhysForce() && contrabn;
    }
    
    public boolean physForceJustified()
    {
        return (usedPhysForce() && carriedWeapon()) || (usedPhysForce() && suspHadContrabn()) || (usedPhysForce() && suspRefComply()) 
                || (usedPhysForce() && suspVCrime) || (usedPhysForce() && otherSuspWeapons);
    }
    
    public boolean isGender(String gender)
    {
        return sex.equals(gender);
    }
    
    public boolean suspiciousClothing()
    {
        return rf_attrInapropriate || cs_cloth;
    }
    
    public boolean isBuild(String buildFilter)
    {
        return build.equals(buildFilter);
    }
    
    public int computeHeight()
    {
        int totInch = ht_inches + (ht_feet * 12);
        return totInch;
    }
    
    public boolean susHadOtherFeats()
    {
        return otherfeats;
    }
    
    public boolean suspPhysAppear()
    {
        return rf_attrInapropriate || cs_cloth;
    }
    
    
    //public boolean usedPhysForce()
    //{
        //return pf_hands.equals("Y") || pf_wall.equals("Y") || pf_grnd.equals("Y") 
               //|| pf_drwep.equals("Y") || pf_ptwep.equals("Y") 
               //|| pf_baton.equals("Y") || pf_hcuff.equals("Y") || pf_pepsp.equals("Y") 
               //| pf_other.equals("Y");
    //}
    
    /**
     * Returns a String representation of Stop, Question, & Frisk report. 
     */
    public String toString()
    {
        return "x-coordiante: " + xcoordOfSus + "\n" +
               "y-coordinate: " + ycoordOfSus + "\n"+
               "city: " + cityOfSus + "\n" + 
               "race: " + raceOfSus + "\n"+
               " period of stop: " + perStopOfSus + "\n"+
               "was arrest made: " + arstmadeOfSus + "\n"+
               "was summons issued: " + sumissueOfSus + "\n"+
               "was physical force used (hands): "+ pf_hands + "\n"+
               "was physical force used (wall): "+ pf_wall +"\n"+
               "was physical force used (ground): "+ pf_grnd +"\n"+
               "was physical force used (weapon drawn): "+ pf_drwep +"\n"+
               "was physical force used (weapon pointed): "+ pf_ptwep +"\n"+
               "was physical force used (baton): "+ pf_baton +"\n"+
               "was physical force used (handcuffs): "+ pf_hcuff +"\n"+
               "was physical force used (pepper spray): "+ pf_pepsp +"\n"+
               "was physical force used (other form of force): "+ pf_other +"\n"+
               "susp refused to comply: "+ refusedComply +"\n"+
               "susp was suspected of a violent crime: "+ suspVCrime +"\n"+
               "susp had a contraban: "+ contrabn +"\n"+
               "susp had a other suspicous weapon: "+ otherSuspWeapons +"\n"+
               "susp had a knife: "+ knife +"\n"+
               "susp had a pistol: "+ pistol +"\n"+
               "susp had a machineGun: "+ machineGun +"\n"+
               "susp had a assault weapon: "+ asltWeapon +"\n"+
               "susp had a rifle: "+ rifle +"\n"+
               "susp had a other form of weapon: "+ othrweap +"\n";
        // Continue updating this to return a String
        // with all the values of all the instance variables
    }
    
    /**
     * Quick test of methods on SQFReport
     */
    public static void main(String[] args)
    {
        String[] exInputData = new String[109];
        exInputData[107] = "35";
        exInputData[108] = "57";
        exInputData[100] = "QUEENS";
        exInputData[81] = "B";
        exInputData[10] = "5";
        exInputData[14] = "N";
        exInputData[16] = "Y";
        exInputData[32] = "Y";
        exInputData[33] = "N";
        exInputData[34] = "N";
        exInputData[35] = "Y";
        exInputData[36] = "N";
        exInputData[37] = "Y";
        exInputData[38] = "N";
        exInputData[39] = "Y";
        exInputData[40] = "N";
        exInputData[58] = "Y";
        exInputData[24] = "N";
        exInputData[44] = "Y";
        exInputData[45] = "N";
        exInputData[29] = "Y";
        exInputData[26] = "N";
        exInputData[28] = "Y";
        exInputData[31] = "Y";
        exInputData[30] = "Y";
        exInputData[27] = "Y";
        exInputData[47] = "Y";
        exInputData[53] = "Y";
        exInputData[84] = "5";
        exInputData[85] = "6";
        exInputData[89] = "H";
        exInputData[80] = "M";
        exInputData[90] = "Y";
        
        SQFReport testReport = new SQFReport(exInputData);
        
        System.out.println("y = " + testReport.getYCoord());
        System.out.println("x = " + testReport.getXCoord());
        System.out.println(testReport.getBorough());
        System.out.println(testReport.getRace());
        System.out.println(testReport.susCharged());
        System.out.println(testReport.toString());
        System.out.println(testReport.usedPhysForce());
        System.out.println(testReport.isRace("W"));
        System.out.println("SQFR String Formmat " + "\n" + testReport.toString());
        
        // As you define instance variables and methods,
        // test them here to make sure they work.
    }
}