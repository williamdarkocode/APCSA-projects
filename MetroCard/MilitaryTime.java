public class MilitaryTime
{
    private int hours;
    private int minutes;

    public MilitaryTime(int h, int m)
    {
        hours = 0;
        if(h >= 0 && h <= 23)
        {
            hours = h;
        }
        minutes = 0;
        if(m >= 0 && m <= 59)
        {
            minutes = m;
        }
    }

    public String toString()
    {
        String hrs = "";
        if(hours < 10)
        {
            hrs = hrs + "0";
        }
        String mins = "";
        if(minutes < 10)
        {
            mins = "0";
        }
        return hrs + hours + ":" + mins + minutes;
    }
    
    public int compareTo(MilitaryTime t)
    {
        if(this.hours < t.hours)
        {
            return -1;
        }
        else if(this.hours > t.hours)
        {
            return 1;
        }
        else if(this.minutes < t.minutes)
        {
            return -1;
        }
        else if(this.minutes > t.minutes)
        {
            return 1;
        }
        else
        {
            return 0;
        } 
    }
    
    public static void main(String[] args)
    {
        MilitaryTime t = new MilitaryTime(8, 55);
        
        // Test me out!
    }
}
