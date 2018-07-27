//Import list create and manipulate lists
import java.util.List;
import java.util.ArrayList;
//Import to read files
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Import to read files
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;

public class SQFDatabaseReader
{
    /**
     * Given a file name for a CSV file representing a Stop, Question, & Frisk database, 
     * returns an array of SQFReport objects that represents the data set in the file.
     * 
     * @param fileName a CSV file representing a Stop, Question & Frisk database
     * @return an array of SQFReport objects
     */
    public static SQFReport[] read(String fileName) throws IOException
    {
        ArrayList<SQFReport> sqfRepList = new ArrayList<SQFReport>();
        CSVReader reader = new CSVReader(new FileReader(fileName), 1, new CSVParser());
        String[] row;
        while ((row = reader.readNext()) != null)
        {
            SQFReport sqfr = new SQFReport(row);
            sqfRepList.add(sqfr);
        }
        
        SQFReport[] sqfReports = new SQFReport[sqfRepList.size()];
        sqfReports = sqfRepList.toArray(sqfReports);
        return sqfReports;
    }
    
    
}
