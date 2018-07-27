//Import to show errors with files
import java.io.IOException;
//Import to draw Colors
import java.awt.Color;

public class SQFMapper
{   
    /**
     * Reads in a Stop, Question & Frisk database from a file and visually maps
     * the reports in the database.
     */
    public static void mapData() throws IOException
    {
        // Get all the data from the file
        SQFReport[] allReports = SQFDatabaseReader.read("2014_cleaned.csv");
        System.out.println("Reports :" +  allReports.length);
        System.out.println("Min XCoord : " + SQFMapper.getMinXCoord(allReports));
        System.out.println("Max XCoord : " + SQFMapper.getMaxXCoord(allReports));
        System.out.println("Min YCoord : " + SQFMapper.getMinYCoord(allReports));
        System.out.println("Max YCoord : " + SQFMapper.getMaxYCoord(allReports));
        SQFReport[] arr = SQFMapper.filterByBorough(allReports, "MANHATTAN");
        System.out.println(arr.length);
        // Get ONLY the data for the appropriate borough
		String[] cityNames = {"QUEENS", "BRONX", "BROOKLYN", "MANHATTAN", "STATENISLAND"};
		int pos = 0;
		String city = cityNames[pos];
        SQFReport[] boroughReports = 
            SQFMapper.filterByBorough(allReports, "QUEENS");
            System.out.println(boroughReports.length);
        SQFReport[] asianPacReps = SQFMapper.filterByRaceEnc(boroughReports, "A");
        SQFReport[] hispanicReps = SQFMapper.filterByRaceEnc(boroughReports, "PQ");
        SQFReport[] blackReps = SQFMapper.filterByRaceEnc(boroughReports, "B");
        SQFReport[] amIndAkNatReps = SQFMapper.filterByRaceEnc(boroughReports, "I");
        SQFReport[] whiteReps = SQFMapper.filterByRaceEnc(boroughReports, "W");
        SQFReport[] otherRaceReps = SQFMapper.filterByRaceEnc(boroughReports, "XZ");

        int minX = SQFMapper.getMinXCoord(boroughReports);
        int maxX = SQFMapper.getMaxXCoord(boroughReports);
        int minY = SQFMapper.getMinYCoord(boroughReports);
        int maxY = SQFMapper.getMaxYCoord(boroughReports);
        //balck x and y coords
        //////////////////////////Notice! use filter by race method to return report for each race and pass it into draw method
        //
        StdDraw.setXscale(minX, maxX);
        StdDraw.setYscale(minY, maxY);
        StdDraw.setPenRadius(0.005);
        
       
        StdDraw.enableDoubleBuffering();
        while(true && pos < cityNames.length)
        {
            SQFMapper.draw(boroughReports, StdDraw.BLACK, 3000);
          
            //asian pacific rep. maping
            SQFMapper.draw(asianPacReps, StdDraw.YELLOW, 3000);
            
            //black rep. maping
            SQFMapper.draw(blackReps, StdDraw.BLUE, 3000);
            
            //hispanic rep. maping
            SQFMapper.draw(hispanicReps, StdDraw.RED, 3000);
            
            //white rep maping
            SQFMapper.draw(whiteReps, StdDraw.GREEN, 3000);
            
            // Indian alaskan Native rep. mapping
            SQFMapper.draw(amIndAkNatReps, StdDraw.PINK, 3000);
            
            
            //other race
            SQFMapper.draw(otherRaceReps, StdDraw.ORANGE, 3000);
            
        }
    }
    
    /**
     * Given an array of Stop, Question, & Frisk reports, a color, and a time 
     * (measured in milliseconds), draw the location of each report on a map in
     * the given color for the given amoutn of time.
     * 
     * @param reports the Stop, Question, and Frisk reports to draw on a map
     * @param c the Color of each point
     * @param timeMil the time in milliseconds to show each frame
     */
    public static void draw(SQFReport[] reports, Color c, int timeMil)
    {
        StdDraw.clear();
        StdDraw.setPenColor(c);
        for(int i = 0; i < reports.length; i++)
        {
            StdDraw.point(reports[i].getXCoord(), reports[i].getYCoord());
        }
        StdDraw.show();
        StdDraw.pause(timeMil);
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
     * Returns the minimum x-coordinate contained in the Stop, Question, & Frisk reports.
     * 
     * @param reports an array of SQFReport objects
     * @return the minimum x-coordinate in the reports 
     */
    public static int getMinXCoord(SQFReport[] reports)
    {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getXCoord() < min)
            {
                min = reports[i].getXCoord();
            }
        }
        return min;
    }
    
    /**
     * Returns the minimum y-coordinate contained in the Stop, Question, & Frisk reports.
     * 
     * @param reports an array of SQFReport objects
     * @return the minimum y-coordinate in the reports 
     */
    public static int getMinYCoord(SQFReport[] reports)
    {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getYCoord() < min)
            {
                min = reports[i].getYCoord();
            }
        }
        return min;
    }
    
    /**
     * Returns the maximum x-coordinate contained in the Stop, Question, && Frisk reports.
     * 
     * @param reports an array of SQFReport objects
     * @return the maximum x-coordinate in the reports
     */
    public static int getMaxXCoord(SQFReport[] reports)
    {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getXCoord() > max)
            {
                max = reports[i].getXCoord();
            }
        }
        return max;
    }
    
    /**
     * Returns the maximum y-coordinate contained in the Stop, Question, && Frisk reports.
     * 
     * @param reports an array of SQFReport objects
     * @return the maximum y-coordinate in the reports
     */
    public static int getMaxYCoord(SQFReport[] reports)
    {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getYCoord() > max)
            {
                max = reports[i].getYCoord();
            }
        }
        return max;
    }
    
    /**
     * get x and y coordinates of each race and print them in different colors
     * blue : black suspects
     * pink : Asian Pacific
     * red hispanic
     */
    public static int getMaxXCoordByRace(SQFReport[] reports, String race)
    {
        int maxX = Integer.MIN_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getXCoord() > maxX && reports[i].isRace(race))
            {
                maxX = reports[i].getXCoord();
            }
        }
        return maxX;
    }
    
    public static int getMinXCoordByRace(SQFReport[] reports, String race)
    {
        int minX = Integer.MAX_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getXCoord() < minX && reports[i].isRace(race))
            {
                minX = reports[i].getXCoord();
            }
        }
        return minX;
    }
    
    public static int getMaxYCoordByRace(SQFReport[] reports, String race)
    {
        int maxY = Integer.MIN_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getYCoord() > maxY && reports[i].isRace(race))
            {
                maxY = reports[i].getYCoord();
            }
        }
        return maxY;
    }
    
    public static int getMinYCoordByRace(SQFReport[] reports, String race)
    {
        int minY = Integer.MAX_VALUE;
        for(int i = 0; i < reports.length; i++)
        {
            if(reports[i].getYCoord() < minY && reports[i].isRace(race))
            {
                minY = reports[i].getYCoord();
            }
        }
        return minY;
    }
}