//Import to show errors with files
import java.io.IOException;

public class SQFFactFinder
{   
    /**
     * Reads in a Stop and Frisk database from a file and prints out
     * facts extracted from the database.
     */
    public static void printFacts() throws IOException
    {
        SQFReport[] allReports = SQFDatabaseReader.read("2014_cleaned.csv");
        
        // First, filter by borough
        SQFReport[] boroughReports = 
            SQFFactFinder.filterByBorough(allReports, "QUEENS");
        
        // Next, filter the borough data by race
        SQFReport[] asianPacIslReps =
            SQFFactFinder.filterByRaceEnc(boroughReports, "A");
        SQFReport[] hispReps =
            SQFFactFinder.filterByRaceEnc(boroughReports, "PQ");
        SQFReport[] blackReps =
            SQFFactFinder.filterByRaceEnc(boroughReports, "B");
        SQFReport[] amIndAkNatReps =
            SQFFactFinder.filterByRaceEnc(boroughReports, "I");
        SQFReport[] whiteReps =
            SQFFactFinder.filterByRaceEnc(boroughReports, "W");
        SQFReport[] other =
            SQFFactFinder.filterByRaceEnc(boroughReports, "XZ");
        //filtered by physical force
        SQFReport[] forceVictims = SQFFactFinder.filterByPhysForce(boroughReports);
        
        // Find out how many stops (by race)
        System.out.println("----Number of Stops (by Race)----");
        System.out.println("Asian/Pacific Islander: " + asianPacIslReps.length);
        System.out.println("Hispanic: " + hispReps.length);
        System.out.println("Black: " + blackReps.length);
        System.out.println("American Indi an/Alaskan Native: " + amIndAkNatReps.length);
        System.out.println("White: " + whiteReps.length);
        System.out.println("other length : " + other.length);
        // Find out average period of stop (by race)
        System.out.println("----Average Period of Stop (by Race)----");
        System.out.println("Asian/Pacific Islander: " + 
                            SQFFactFinder.getAvgPerStop(asianPacIslReps));
        System.out.println("Hispanic avgPerStop: " + SQFFactFinder.getAvgPerStop(hispReps));
        System.out.println("Black avgPerStop: " + SQFFactFinder.getAvgPerStop(blackReps));
        System.out.println("White avgPerStop: " + SQFFactFinder.getAvgPerStop(whiteReps));
        System.out.println("AIAN avgPerStop: " + SQFFactFinder.getAvgPerStop(amIndAkNatReps));
        System.out.println("other avgPerStop: " + SQFFactFinder.getAvgPerStop(other));
        // Find out percentage of time a stop resulted in a charges (by race)
        System.out.println("\n" + "\n" + "----Percentages of charges on suspects according to race----" + "\n");
        System.out.println("% of Asian/Pac.Is. stops ending in charge : " + SQFFactFinder.getPctCharged(asianPacIslReps) 
                           + " vs other method:" + SQFFactFinder.getPctChargedByRace(allReports, "A"));
        System.out.println("% of Hispanics stops ending in charge : " + SQFFactFinder.getPctCharged(hispReps) 
                           + " vs other method: " + SQFFactFinder.getPctChargedByRace(allReports, "PQ"));
        System.out.println("% of Black stops ending in charge : " + SQFFactFinder.getPctCharged(blackReps) 
                           + " vs other method: " + SQFFactFinder.getPctChargedByRace(allReports, "B"));
        System.out.println("% of White stops ending in charge : " + SQFFactFinder.getPctCharged(whiteReps) 
                           + " vs other method:" + SQFFactFinder.getPctChargedByRace(allReports, "W"));
        System.out.println("% of other stops ending in charge : " + SQFFactFinder.getPctCharged(other) 
                           + " vs other method:" + SQFFactFinder.getPctChargedByRace(allReports, "XZ")); 
        System.out.println("% of American Indian/ Alaskan Native stops ending in charge : " + SQFFactFinder.getPctCharged(amIndAkNatReps) 
                           + " vs other method:" + SQFFactFinder.getPctChargedByRace(allReports, "I"));                   
        
        // Find out percentage of time physical force was used (by race)
        System.out.println("\n" + "\n" + "----Percentages of physical force used on suspects according to race----" + "\n");
        System.out.println(" was physical force used on Asian Pacific suspects: " + SQFFactFinder.getPctPhysForce(asianPacIslReps));
        System.out.println(" was physical force used on Hipanic suspects : " + SQFFactFinder.getPctPhysForce(hispReps));
        System.out.println(" was physical force used on Black suspects : " + SQFFactFinder.getPctPhysForce(blackReps));
        System.out.println(" was physical force used on White suspects : " + SQFFactFinder.getPctPhysForce(whiteReps));
        System.out.println(" was physical force used on American Indian / Alaskan Native suspects : " 
                            + SQFFactFinder.getPctPhysForce(amIndAkNatReps));
        System.out.println(" was physical force used on other race suspects : " + SQFFactFinder.getPctPhysForce(other) + "\n");
        
        //percentages of physical force used in various instances
        System.out.println("----Physical Force Analysis----" + "\n");
        System.out.println("---Total number of stops in volving physical force according to race---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.countPhysForceByRace(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.countPhysForceByRace(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.countPhysForceByRace(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.countPhysForceByRace(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.countPhysForceByRace(amIndAkNatReps) + "\n");
        
        System.out.println("---Total percentages of stops involving physical force according to race, and suspects not complying---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.pctPhysForceAndRefComply(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.pctPhysForceAndRefComply(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.pctPhysForceAndRefComply(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.pctPhysForceAndRefComply(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.pctPhysForceAndRefComply(amIndAkNatReps) + "\n");
        
        
        System.out.println("---Total percentages of stops involving physical force according to race, and suspects carrying contraban items---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.pctPhysForceAndContraban(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.pctPhysForceAndContraban(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.pctPhysForceAndContraban(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.pctPhysForceAndContraban(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.pctPhysForceAndContraban(amIndAkNatReps) + "\n");
        
        System.out.println("---Total percentages of stops involving physical force according to race, and suspects carrying a weapon item---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.pctPhysForceAndSuspCarriedWeap(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.pctPhysForceAndSuspCarriedWeap(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.pctPhysForceAndSuspCarriedWeap(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.pctPhysForceAndSuspCarriedWeap(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.pctPhysForceAndSuspCarriedWeap(amIndAkNatReps) + "\n");
        
        System.out.println("---Total percentages of stops involving physical force according to race, and suspects suspected of a crime---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.pctPhysForceAndViolentCrimeSusp(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.pctPhysForceAndViolentCrimeSusp(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.pctPhysForceAndViolentCrimeSusp(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.pctPhysForceAndViolentCrimeSusp(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.pctPhysForceAndViolentCrimeSusp(amIndAkNatReps) + "\n");
        
        System.out.println("---Total percentages of stops involving physical force that was justified due to the suspect prtraying criminal acts---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.pctPhysForceJustified(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.pctPhysForceJustified(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.pctPhysForceJustified(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.pctPhysForceJustified(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.pctPhysForceJustified(amIndAkNatReps) + "\n");
        
        
        System.out.println("---Total number of stops based on the suspicious clothing os suspect---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.countStopsOfSuspCloth(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.countStopsOfSuspCloth(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.countStopsOfSuspCloth(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.countStopsOfSuspCloth(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.countStopsOfSuspCloth(amIndAkNatReps) + "\n");
        
        System.out.println("---Total number of stops based on the build type of the suspect---"+ "\n");
        System.out.println("Asian Pacific Islander---- : " + "\n" 
                           + "number of THIN built SUSPECTS:   "+ SQFFactFinder.countBuildType(asianPacIslReps, "T") + "\n"
                           + "number of MEDIUM built SUSPECTS:   "+ SQFFactFinder.countBuildType(asianPacIslReps, "M") + "\n"
                           + "number of MUSCULAR built SUSPECTS:   "+ SQFFactFinder.countBuildType(asianPacIslReps, "U") + "\n"
                           + "number of HEAVY SUSPECTS:   "+ SQFFactFinder.countBuildType(asianPacIslReps, "H") + "\n");
        System.out.println("Hispanic ---- : " + "\n" 
                           + "number of THIN built SUSPECTS:   "+ SQFFactFinder.countBuildType(hispReps, "T") + "\n"
                           + "number of MEDIUM built SUSPECTS:   "+ SQFFactFinder.countBuildType(hispReps, "M") + "\n"
                           + "number of MUSCULAR built SUSPECTS:   "+ SQFFactFinder.countBuildType(hispReps, "U") + "\n"
                           + "number of HEAVY SUSPECTS:   "+ SQFFactFinder.countBuildType(hispReps, "H") + "\n");
        System.out.println("Black ---- : " + "\n" 
                           + "number of THIN built SUSPECTS:   "+ SQFFactFinder.countBuildType(blackReps, "T") + "\n"
                           + "number of MEDIUM built SUSPECTS:   "+ SQFFactFinder.countBuildType(blackReps, "M") + "\n"
                           + "number of MUSCULAR built SUSPECTS:   "+ SQFFactFinder.countBuildType(blackReps, "U") + "\n"
                           + "number of HEAVY SUSPECTS:   "+ SQFFactFinder.countBuildType(blackReps, "H") + "\n");
        System.out.println("White ---- : " + "\n" 
                           + "number of THIN built SUSPECTS:   "+ SQFFactFinder.countBuildType(whiteReps, "T") + "\n"
                           + "number of MEDIUM built SUSPECTS:   "+ SQFFactFinder.countBuildType(whiteReps, "M") + "\n"
                           + "number of MUSCULAR built SUSPECTS:   "+ SQFFactFinder.countBuildType(whiteReps, "U") + "\n"
                           + "number of HEAVY SUSPECTS:   "+ SQFFactFinder.countBuildType(whiteReps, "H") + "\n");
        System.out.println("American Indian/ Alaskan Native ---- : " + "\n" 
                           + "number of THIN built SUSPECTS:   "+ SQFFactFinder.countBuildType(amIndAkNatReps, "T") + "\n"
                           + "number of MEDIUM built SUSPECTS:   "+ SQFFactFinder.countBuildType(amIndAkNatReps, "M") + "\n"
                           + "number of MUSCULAR built SUSPECTS:   "+ SQFFactFinder.countBuildType(amIndAkNatReps, "U") + "\n"
                           + "number of HEAVY SUSPECTS:   "+ SQFFactFinder.countBuildType(amIndAkNatReps, "H") + "\n");
                       
        System.out.println("---Total number of suspects above average height---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.countAboveAvgHt(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.countAboveAvgHt(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.countAboveAvgHt(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.countAboveAvgHt(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.countAboveAvgHt(amIndAkNatReps) + "\n");
        
        System.out.println("---Total number of suspects below average height---"+ "\n");
        System.out.println("Asian Pacific Islander : " +SQFFactFinder.countBelowAvgHt(asianPacIslReps) + "\n");
        System.out.println("Hispanic : " +SQFFactFinder.countBelowAvgHt(hispReps) + "\n");
        System.out.println("Black : " +SQFFactFinder.countBelowAvgHt(blackReps) + "\n");
        System.out.println("White : " + SQFFactFinder.countBelowAvgHt(whiteReps)+ "\n");
        System.out.println("American Indian/ Alaskan Native : " +SQFFactFinder.countBelowAvgHt(amIndAkNatReps) + "\n");
        
        
        //print phys appearance data
        //System.out.println("----specific physical appearances and the number of stops involving them----" +"\n");
        ///System.out.println("avg height : " + avgHeight(boroughReports) + "\n");
        //System.out.println("number of people above the avg height : " + SQFFactFinder.countAboveAvgHt(boroughReports) + "\n");
        //System.out.println("number of people below the avg height : " + SQFFactFinder.countBelowAvgHt(boroughReports) + "\n");
        //System.out.println("number of people below the avg height : " + SQFFactFinder.countBelowAvgHt(boroughReports) + "\n");
        //System.out.println("number of heavy built suspects : " + SQFFactFinder.countBuildType(boroughReports, "H") + "\n");
        //System.out.println("number of medium built suspects : " + SQFFactFinder.countBuildType(boroughReports, "M") + "\n");
        //System.out.println("number of slim built suspects : " + SQFFactFinder.countBuildType(boroughReports, "T") + "\n");
        //System.out.println("number of muscular built suspects : " + SQFFactFinder.countBuildType(boroughReports, "U") + "\n");
    }
    
    /**
     * Given an array of Stop, Question, & Frisk reports and a borough, returns an 
     * array of only the reports that occurred in the borough.
     * 
     * @param reports an array of SQFReport objects
     * @param borough the borough to filter
     *   Precondition: borough should be "BRONX", "BROOKLYN", "MANHATTAN",
     *   "QUEENS", or "STATEN ISLAND"
     * @return an array of SQFReport objects that occurred in the specific borough   
     */
    public static SQFReport[] filterByBorough(SQFReport[] reports, String borough)
    {
        SQFReport[] repInBorough;
        int count  = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getBorough().equals(borough))
            { count+=1;
            }
        }
        repInBorough = new SQFReport[count];
        int track = 0;
        int pos = 0;
        while(track < count)
        {
            for(int i = 0; i < reports.length; i++)
            {
              if(reports[i].getBorough().equals(borough))
              {
                 repInBorough[pos] = reports[i];
                 pos+=1;
                 track+=1;
              }
            }
        }
        return repInBorough;
    }
 
    /**
     * Given an array of Stop, Question, & Frisk reports and a set of race, 
     * encodings, returns an  array of only the reports where the suspect's race
     * matches ONE of the race encodings.
     * 
     * @param reports an array of SQFReport objects
     * @param raceEncodings the race encodings (i.e. could be one or more) to filter
     *        (e.g. hispanics can be represented by "P" or "Q" so to filter all
     *         hispanics, this parameter would be set to "PQ").
     *   Precondition: raceEnc contains ONLY one or more of the capital letters
     *   each of the following capital letters: A, B, I, P, Q, W, X, Z
     * @return an array of SQFReport objects with suspects whose race matches at least
     *  one of the race encodings
     */
    public static SQFReport[] filterByRaceEnc(SQFReport[] reports, String raceEncodings)
    {
        SQFReport[] repOfRace;
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(raceEncodings.indexOf(reports[i].getRace()) >=0)
            {
                count+=1;
            }
        }
        repOfRace = new SQFReport[count];
        int pos = 0;
        int track = 0; 
        while(track < count)
        {
            for(int i = 0; i < reports.length; i++)
            {
               if(raceEncodings.indexOf(reports[i].getRace()) >=0)
               {   
                   repOfRace[pos] = reports[i];
                   pos+=1;
                   track+=1;
               }
            }
        }
        return repOfRace;
    }
    
    /**
     * Return the average period of a stop in the Stop, Question, & Frisk reports.
     * 
     * @param reports an array of SQFReport objects
     * @return the average period of a stop across the reports
     */
    public static double getAvgPerStop(SQFReport[] reports)
    {
        int sum = 0;
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            sum+=reports[i].getPerStop();
            count+=1;
        }
        return (double)sum / (double)count;
    }
    
    /**
     * Return the percentage of Stop, Quesiton, & Frisk reports where the police charged
     * the suspect with a crime. NOTE: When police charge a suspect with a crime, they
     * issue a SUMMMONS or ARREST the suspect.
     * 
     * @param reports an array of SQFReport objects
     * @return the percentage of reports where police charged a suspect with a crime
     */
    public static double getPctCharged(SQFReport[] reports)
    {
        // Replace this code
        double countCharged = 0.0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].susCharged())
            {
                countCharged+=1.0;
            }
        }
        double pct = (countCharged / reports.length)* 100;
        return pct;
    }
    
    public static double getPctChargedByRace(SQFReport[] reports, String raceToFilter)
    {
        double countCharged = 0.0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].susCharged() && reports[i].isRace(raceToFilter))
            {
                countCharged+=1.0;
            }
        }
        double pct = (countCharged / reports.length)* 100;
        return pct;
    }
   
    /**
     * Return the percentage of reports where police used physical force.
     * 
     * @param reports an array of SQFREport objects
     * @return the percentage of reports where police used phyiscal force.
     */
    public static double getPctPhysForce(SQFReport[] reports)
    {
        double countPhysForce = 0.0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].usedPhysForce())
            {
                countPhysForce+=1.0;
            }
        }
        double pct = (countPhysForce / reports.length)* 100; 
        return pct;
    }
    //analyzing use of physical force
    public static SQFReport[] filterByPhysForce(SQFReport[] reports)
    {
        SQFReport[] repPhysForce;
        int count  = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].usedPhysForce())
            { count+=1;
            }
        }
        repPhysForce = new SQFReport[count];
        int track = 0;
        int pos = 0;
        while(track < count)
        {
            for(int i = 0; i < reports.length; i++)
            {
              if(reports[i].usedPhysForce())
              {
                 repPhysForce[pos] = reports[i];
                 pos+=1;
                 track+=1;
              }
            }
        }
        return repPhysForce;
    }
    
    public static int countPhysForceByRace(SQFReport[] reports)
    {
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].usedPhysForce())
            {
                count+=1;
            }
        }
        return count;
    }
    //ban and force
    public static double pctPhysForceAndContraban(SQFReport[] forceVictims)
    {
        double count = 0.0;
        for(int i = 0; i < forceVictims.length; i++)
        {
            if(forceVictims[i].physForceAndContrabn())
            {
                count+=1.0;
            }
        }
        double pct = (count / forceVictims.length) * 100;
        return pct;
    }
    //no comply and force
    public static double pctPhysForceAndRefComply(SQFReport[] forceVictims)
    {
        double count = 0.0;
        for(int i = 0; i < forceVictims.length; i++)
        {
            if(forceVictims[i].suspRefComplyAndPhysForceUsed())
            {
                count+=1.0;
            }
        }
        double pct = (count / forceVictims.length) * 100;
        return pct;
    }
    //force and weap suspicion
    public static double pctPhysForceAndSuspWeapon(SQFReport[] forceVictims)
    {
        double count = 0.0;
        for(int i = 0; i < forceVictims.length; i++)
        {
            if(forceVictims[i].stopOnReasonWeapAndForce())
            {
                count+=1.0;
            }
        }
        double pct = (count / forceVictims.length) * 100;
        return pct;
    }
    //force and carried weap
    public static double pctPhysForceAndSuspCarriedWeap(SQFReport[] forceVictims)
    {
        double count = 0.0;
        for(int i = 0; i < forceVictims.length; i++)
        {
            if(forceVictims[i].forceAndSusHadWeap())
            {
                count+=1.0;
            }
        }
        double pct = (count / forceVictims.length) * 100;
        return pct;
    }
    //crime suspected and force used
    public static double pctPhysForceAndViolentCrimeSusp(SQFReport[] forceVictims)
    {
        double count = 0.0;
        for(int i = 0; i < forceVictims.length; i++)
        {
            if(forceVictims[i].suspOfViolentCrimForce())
            {
                count+=1.0;
            }
        }
        double pct = (count / forceVictims.length) * 100;
        return pct;
    }
    //force jsutified according to find of criminal act
    public static double pctPhysForceJustified(SQFReport[] reports)
    {
        double count = 0.0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].physForceJustified())
            {
                count+=1.0;
            }
        }
        double pct = (count / reports.length) * 100;
        return pct;
    }
    
    //phys appearances
    
    public static double avgHeight(SQFReport[] reports)
    {
        int totHeight = 0;
        for(int i = 0; i < reports.length; i++)
        {
            totHeight+=reports[i].computeHeight();
        } 
        double avgHeight = (double)totHeight / reports.length;
        return avgHeight;
    }
    
    public static int countAboveAvgHt(SQFReport[] reports)
    {
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].computeHeight() > avgHeight(reports))
            {
                count++;
            }
        }
        return count;
    }
    
    public static int countBelowAvgHt(SQFReport[] reports)
    {
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].computeHeight() < avgHeight(reports))
            {
                count++;
            }
        }
        return count;
    }
    
    public static int countBuildType(SQFReport[] reports, String build)
    {
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].isBuild(build))
            {
                count+=1;
            }
        }
        return count;
    }
    
    public static int countStopsOfAppear(SQFReport[] reports)
    {
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].suspPhysAppear())
            {
                count+=1;
            }
        }
        return count;
    }
    
    public static int countStopsOfSuspCloth(SQFReport[] reports)
    {
        int count = 0;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].suspiciousClothing())
            {
                count+=1;
            }
        }
        return count;
    }
}