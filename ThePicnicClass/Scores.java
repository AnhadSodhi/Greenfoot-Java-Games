public class Scores implements Comparable
{
    private int points_;
    private String name_;
    public Scores(String name, int points)
    {
        name_ = name;
        points_ = points;
    }
    
    public String getName()
    {
        return name_;
    }
    
    public int getPoints()
    {
        return points_;
    }
    
    public int compareTo(Object other)
    {
        Scores s = (Scores)other;
        return points_ -  s.points_;
    }
}